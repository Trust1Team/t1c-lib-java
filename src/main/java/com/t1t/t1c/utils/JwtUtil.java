package com.t1t.t1c.utils;

import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.InvalidTokenException;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public final class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private static final Integer SECONDS_BEFORE_EXPIRATION = 5 * 60;

    private JwtUtil() {
    }

    /**
     * If the token is expired, an
     *
     * @param token
     * @return
     */
    public static boolean isTokenAlmostExpired(final String token) throws InvalidTokenException {
        try {
            if (StringUtils.isEmpty(token)) {
                return false;
            }
            final JwtConsumer consumer = new JwtConsumerBuilder().setSkipSignatureVerification().setSkipDefaultAudienceValidation().setAllowedClockSkewInSeconds(10).setRequireExpirationTime().build();
            final JwtContext context = consumer.process(token);
            log.debug("Token is not expired, claims: {}", context.getJwtClaims());
            final NumericDate shouldNotExceed = context.getJwtClaims().getExpirationTime();
            shouldNotExceed.addSeconds(SECONDS_BEFORE_EXPIRATION);
            return NumericDate.now().isOnOrAfter(shouldNotExceed);
        } catch (final InvalidJwtException | MalformedClaimException ex) {
            throw ExceptionFactory.invalidTokenException(ex);
        }
    }
}