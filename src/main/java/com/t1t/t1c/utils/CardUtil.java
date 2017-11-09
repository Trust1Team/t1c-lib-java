package com.t1t.t1c.utils;

import com.google.common.base.Preconditions;
import com.t1t.t1c.model.rest.GclCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class CardUtil {

    private static final Logger log = LoggerFactory.getLogger(CardUtil.class);

    private static final String SHA_256 = "sha256";

    private CardUtil() {
    }

    public static boolean canAuthenticate(GclCard card) {
        Preconditions.checkNotNull(card, "Card info must be provided");
        switch (ContainerUtil.determineContainer(card)) {
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
        switch (ContainerUtil.determineContainer(card)) {
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

    public static boolean canVerifyPin(GclCard card) {
        Preconditions.checkNotNull(card, "Card info must be provided");
        switch (ContainerUtil.determineContainer(card)) {
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
                return true;
            case EMV:
            case MOBIB:
            default:
                return false;
        }
    }

    public static String determineDefaultAlgorithm(GclCard card) {
        Preconditions.checkNotNull(card, "Card info must be provided");
        switch (ContainerUtil.determineContainer(card)) {
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

}