package com.t1t.t1c.utils;

import com.t1t.t1c.core.*;
import com.t1t.t1c.exceptions.AbstractRuntimeException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class PinUtil {

    private PinUtil() {
    }

    public static void pinEnforcementCheck(GclReader reader, boolean osPinDialog, boolean forcePinPad, String... pins) {
        boolean pinPresent = true;
        for (String pin : pins) {
            if (StringUtils.isEmpty(pin)) {
                pinPresent = false;
            }
        }
        boolean hardwarePinPadPresent = reader.getPinpad();
        if (forcePinPad) {
            if (hardwarePinPadPresent) {
                if (pinPresent) {
                    throw ExceptionFactory.verifyPinException("Strict PIN-pad enforcement is enabled. This request was sent with a PIN, but the reader has a PIN-pad.");
                }
            } else if (!pinPresent) {
                throw ExceptionFactory.verifyPinException("Strict PIN-pad enforcement is enabled. This request was sent without a PIN, but the reader does not have a PIN-pad.");
            }
        } else {
            if (!hardwarePinPadPresent && !pinPresent && !osPinDialog) {
                throw ExceptionFactory.verifyPinException("The request was sent without a PIN, but the reader doest not have a PIN-pad");
            }
        }
    }

    public static AbstractRuntimeException checkPinExceptionMessage(RestException ex) {
        if (ex.getGclError() != null) {
            return ExceptionFactory.verifyPinException(ex.getGclError());
        } else return ex;
    }

    public static GclAuthenticateOrSignData createEncryptedAuthSignData(String data, String algorithmReference, Boolean pinpad, Boolean osPinDialog, String pin) {
        return new GclAuthenticateOrSignData()
                .withData(data)
                .withAlgorithmReference(algorithmReference)
                .withPinpad(pinpad)
                .withOsDialog(osPinDialog)
                .withPin(getEncryptedPinIfPresent(pin));
    }

    public static String getEncryptedPinIfPresent(String pin) {
        return StringUtils.isNotEmpty(pin) ? CryptUtil.encrypt(pin) : null;
    }

    public static GclVerifyPinRequest createEncryptedRequest(Boolean pinpad, Boolean osPinDialog, String pin) {
        return createEncryptedRequest(pinpad, osPinDialog, null, pin);
    }

    public static GclVerifyPinRequest createEncryptedRequest(Boolean pinpad, Boolean osPinDialog, GclPrivateKeyReference privateKeyReference, String pin) {
        return new GclVerifyPinRequest()
                .withPin(getEncryptedPinIfPresent(pin))
                .withPinpad(pinpad)
                .withOsDialog(osPinDialog)
                .withPrivateKeyReference(privateKeyReference);
    }

    public static GclChangePinRequest createEncryptedPinChangeRequest(Boolean pinpad, Boolean osPinDialog, String oldPin, String newPin) {
        return new GclChangePinRequest()
                .withNewPin(getEncryptedPinIfPresent(newPin))
                .withOldPin(getEncryptedPinIfPresent(oldPin))
                .withOsDialog(osPinDialog);
    }

    public static GclResetPinRequest createEncryptedPinResetRequest(Boolean pinpad, Boolean osPinDialog, boolean resetOnly, String puk, String newPin) {
        return new GclResetPinRequest()
                .withOsDialog(osPinDialog)
                .withPin(getEncryptedPinIfPresent(newPin))
                .withPuk(getEncryptedPinIfPresent(puk))
                .withResetOnly(resetOnly);
    }
}