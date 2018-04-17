package com.t1t.t1c.utils;

import com.google.common.base.Preconditions;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.core.GclCard;
import com.t1t.t1c.exceptions.ExceptionFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class ContainerUtil {

    private static final Logger log = LoggerFactory.getLogger(ContainerUtil.class);

    private static final String SHA_256 = "sha256";

    private ContainerUtil() {
    }

    public static boolean canAuthenticate(ContainerType type) {
        switch (type) {
            case BEID:
            case LUXID:
            case LUXTRUST:
            case DNIE:
            case PT:
            case EST:
            case AVENTRA:
            case OBERTHUR:
            case PIV:
                return true;
            case OCRA:
            case EMV:
            case MOBIB:
            default:
                return false;
        }
    }

    public static boolean canAuthenticate(GclCard card) {
        Preconditions.checkNotNull(card, "Card info must be provided");
        return canAuthenticate(determineContainer(card));
    }

    public static boolean canSign(ContainerType type) {
        switch (type) {
            case BEID:
            case LUXID:
            case LUXTRUST:
            case DNIE:
            case PT:
            case EST:
            case AVENTRA:
            case OBERTHUR:
            case PIV:
                return true;
            case OCRA:
            case EMV:
            case MOBIB:
            default:
                return false;
        }
    }

    public static boolean canSign(GclCard card) {
        Preconditions.checkNotNull(card, "Card info must be provided");
        return canSign(determineContainer(card));
    }

    public static boolean canVerifyPin(ContainerType type) {
        switch (type) {
            case BEID:
            case LUXID:
            case LUXTRUST:
            case DNIE:
            case PT:
            case EST:
            case AVENTRA:
            case OBERTHUR:
            case PIV:
            case OCRA:
            case EMV:
                return true;
            case MOBIB:
            default:
                return false;
        }
    }

    public static boolean canVerifyPin(GclCard card) {
        Preconditions.checkNotNull(card, "Card info must be provided");
        return canVerifyPin(determineContainer(card));
    }

    public static String determineDefaultAlgorithm(GclCard card) {
        Preconditions.checkNotNull(card, "Card info must be provided");
        switch (determineContainer(card)) {
            case AVENTRA:
            case BEID:
            case DNIE:
            case OBERTHUR:
            case PIV:
            case LUXID:
            case LUXTRUST:
            case PT:
                return SHA_256;
            default:
                return null;
        }
    }

    public static ContainerType determineContainer(GclCard card) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(card.getDescription()), "Card description must be present");
        for (String description : card.getDescription()) {
            ContainerType type = ContainerType.valueOfCardDescription(description);
            if (type != null) return type;
        }
        throw ExceptionFactory.genericContainerException("Could not determine container for card");
    }

    public static Integer getPinVerificationRetriesLeftFor(Integer code) {
        if (code != null) {
            switch (code) {
                case 111:
                    return 1;
                case 112:
                    return 3;
                case 103:
                    return 2;
                case 104:
                    return 1;
                case 105:
                    return 0;
                default:
                    log.warn("GCL error code does not match known PIN retries codes: ", code);
                    return null;
            }
        } else {
            return null;
        }
    }
}