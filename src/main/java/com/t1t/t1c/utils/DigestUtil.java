package com.t1t.t1c.utils;

import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;

import java.util.Arrays;

/**
 * Created by michallispashidis on 29/11/2016.
 */
public class DigestUtil {

    private static final String MD5_DIGEST = "MD5";
    private static final String SHA1_DIGEST = "SHA1";
    private static final String SHA256_DIGEST = "SHA256";
    private static final String SHA512_DIGEST = "SHA512";

    private static final String SHA1_DASH = "SHA-1";
    private static final String SHA256_DASH = "SHA-256";
    private static final String SHA512_DASH = "SHA-512";

    private static final String MD5_SIG = "MD5withRSA";
    private static final String SHA1_SIG = "SHA1withRSA";
    private static final String SHA256_SIG = "SHA256withRSA";
    private static final String SHA512_SIG = "SHA512withRSA";

    public static String digestAlgorithmToSignatureAlgorithm(final String digest) {
        switch (digest.toUpperCase()) {
            case MD5_DIGEST:
                return MD5_SIG;
            case SHA1_DIGEST:
            case SHA1_DASH:
                return SHA1_SIG;
            case SHA256_DIGEST:
            case SHA256_DASH:
                return SHA256_SIG;
            case SHA512_DIGEST:
            case SHA512_DASH:
                return SHA512_SIG;
            default:
                return SHA256_SIG;
        }
    }

    public static String signatureAlgorithmToDigest(final String digest) {
        switch (digest.toUpperCase().replace("WITH", "with")) {
            case MD5_SIG:
                return MD5_DIGEST;
            case SHA1_SIG:
                return SHA1_DIGEST;
            case SHA256_SIG:
                return SHA256_DIGEST;
            case SHA512_SIG:
                return SHA512_DIGEST;
            default:
                return "unknown";
        }
    }

    public static byte[] digestOf(final byte[] input, final String digest) {
        switch (digest.toUpperCase()) {
            case MD5_DIGEST:
                return md5DigestOf(input);
            case SHA1_DIGEST:
                return sha1DigestOf(input);
            case SHA256_DIGEST:
                return sha256DigestOf(input);
            case SHA512_DIGEST:
                return sha512DigestOf(input);
            default:
                return sha256DigestOf(input);
        }
    }

    public static String resolveDigest(final String digest) {
        final String upperCaseDigest = digest.toUpperCase();
        if (Arrays.asList(MD5_DIGEST, SHA1_DIGEST, SHA512_DIGEST, SHA1_DASH, SHA256_DASH, SHA512_DASH).contains(upperCaseDigest)) {
            return upperCaseDigest.replace("-", "");
        } else return SHA256_DIGEST;
    }

    public static byte[] md5DigestOf(final byte[] input) {
        final MD5Digest d = new MD5Digest();
        d.update(input, 0, input.length);
        final byte[] result = new byte[d.getDigestSize()];
        d.doFinal(result, 0);
        return result;
    }

    public static byte[] sha256DigestOf(final byte[] input) {
        final SHA256Digest d = new SHA256Digest();
        d.update(input, 0, input.length);
        final byte[] result = new byte[d.getDigestSize()];
        d.doFinal(result, 0);
        return result;
    }

    public static byte[] sha1DigestOf(final byte[] input) {
        final SHA1Digest d = new SHA1Digest();
        d.update(input, 0, input.length);
        final byte[] result = new byte[d.getDigestSize()];
        d.doFinal(result, 0);
        return result;
    }

    public static byte[] sha512DigestOf(final byte[] input) {
        final SHA512Digest d = new SHA512Digest();
        d.update(input, 0, input.length);
        final byte[] result = new byte[d.getDigestSize()];
        d.doFinal(result, 0);
        return result;
    }
}
