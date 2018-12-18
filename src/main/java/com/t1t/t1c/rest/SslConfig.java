package com.t1t.t1c.rest;

import com.t1t.t1c.exceptions.ExceptionFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class SslConfig {

    private SSLContext sslContext;
    private X509TrustManager trustManager;
    private HostnameVerifier hostnameVerifier;

    /**
     * This specifies a custom SSL context & trust manager to use when calling on external REST API's such as the OCV &
     * Distribution service.
     *
     * @param sslContext the SSL context.
     * @param trustManager the trust manager.
     * @throws IllegalArgumentException if either the trust managers are empty or null or the SSL context is null.
     */
    public SslConfig(final SSLContext sslContext, final X509TrustManager trustManager) throws IllegalArgumentException {
        validateConstructorArgs(sslContext, trustManager);
        this.sslContext = sslContext;
        this.trustManager = trustManager;
    }

    /**
     * This specifies a custom SSL context & trust manager to use when calling on external REST API's such as the OCV &
     * Distribution service. This constructor also allows to specifiy a custom host name verifier; if not specified a
     * default verifier will be used.
     *
     * @param sslContext the SSL context.
     * @param trustManager the trust manager.
     * @param hostnameVerifier the host name verifier.
     * @throws IllegalArgumentException if any of the constructor arguments are empty or null.
     */
    public SslConfig(final SSLContext sslContext, final X509TrustManager trustManager, final HostnameVerifier hostnameVerifier) throws IllegalArgumentException {
        validateConstructorArgs(sslContext, trustManager);

        if (hostnameVerifier == null) {
            throw ExceptionFactory.nullOrEmptyConstructorArgument("hostnameVerifier");
        }
        this.sslContext = sslContext;
        this.trustManager = trustManager;
        this.hostnameVerifier = hostnameVerifier;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    private void validateConstructorArgs(final SSLContext context, final X509TrustManager trustManager) {
        if (trustManager == null) {
            throw ExceptionFactory.nullOrEmptyConstructorArgument("trustManager");
        }
        if (context == null) {
            throw ExceptionFactory.nullOrEmptyConstructorArgument("sslContext");
        }
    }
}