package com.t1t.t1c.utils;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.AbstractRuntimeException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class PinUtil {

    private static String devicePubKey;

    private PinUtil() {
    }

    public static void pinEnforcementCheck(GclReader reader, boolean forcePinPad, String... pin) {
        boolean pinPresent = pin.length > 0 && StringUtils.isNotBlank(pin[0]);
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
            if (!hardwarePinPadPresent && !pinPresent) {
                throw ExceptionFactory.verifyPinException("The request was sent without a PIN, but the reader doest not have a PIN-pad");
            }
        }
    }

    public static AbstractRuntimeException checkPinExceptionMessage(RestException ex) {
        if (ex.getGclError() != null) {
            return ExceptionFactory.verifyPinException(ex.getGclError());
        } else return ex;
    }

    public static GclAuthenticateOrSignData setPinIfPresent(GclAuthenticateOrSignData data, String... pin) {
        if (pin != null && pin.length > 0) {
            data.setPin(PinUtil.encryptPin(pin[0]));
        }
        return data;
    }

    public static String encryptPin(String pin) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, KeyUtil.getPublicKey(devicePubKey));
            return Base64.encodeBase64String(cipher.doFinal(pin.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException ex) {
            return null;
        }
    }

    public static void setDevicePubKey(String devicePubKey) {
        PinUtil.devicePubKey = devicePubKey;
    }
}