package com.t1t.t1c.rest;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.utils.UriUtils;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public final class RestServiceBuilder {
    private static final Logger log = LoggerFactory.getLogger(RestServiceBuilder.class);
    /* Headers */
    private static final String APIKEY_HEADER_NAME = "apikey";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";
    private RestServiceBuilder() {}

    /**
     * GCL Rest client communicates over TLS to T1C-GCL (local).
     * No apikey or JWT required.
     *
     * @param config
     * @return
     */
    public static GclRestClient getGclRestClient(LibConfig config) {
        return getClient(config.getGclClientUri(), GclRestClient.class, null, null, true);
    }

    /**
     * Gcl Admin Test client communicates over TLS to T1C-GCL (local).
     * JWT required.
     *
     * @param config
     * @return
     */
    public static GclAdminRestClient getGclAdminRestClient(LibConfig config) {
        if (config.isTokenCompatible()) {
            return getClient(config.getGclClientUri(), GclAdminRestClient.class, null, config.getJwt(), true);
        } else {
            return getClient(config.getGclClientUri(), GclAdminRestClient.class, null, null, true);
        }
    }

    /**
     * DS Rest client communicates over TLS towards T1C-DS (cloud).
     * Api-key required.
     *
     * @param config
     * @return
     */
    public static DsRestClient getDsRestClient(LibConfig config) {
        return getClient(config.getDsUri(), DsRestClient.class, config.getApiKey(), null, false);
    }

    /**
     * Container Rest client communicates over TLS to T1C-GCL (local) addressing a specific container.
     * No apikey or JWT required.
     * @param config
     * @param clazz
     * @param <U>
     * @return
     */
    public static <U extends ContainerRestClient> U getContainerRestClient(LibConfig config, Class<U> clazz) {
        return getClient(UriUtils.uriFinalSlashAppender(config.getGclClientUri() + ContainerRestClient.CONTAINER_CONTEXT_PATH), clazz, null, null, true);
    }

    /**
     * OCV (Open Certificate Validation API) Rest client communicates over TLS to OCV-API.
     * Api-key required.
     *
     * @param config
     * @return
     */
    public static OcvRestClient getOcvRestClient(LibConfig config) {
        return getClient(config.getOcvUri(), OcvRestClient.class, config.getApiKey(), null, false);
    }

    /**
     * Proxy method to instantiate a client connection.
     *
     * @param uri
     * @param iFace
     * @param apikey
     * @param jwt
     * @param useGclCertificateSslConfig
     * @param <T>
     * @return
     */
    private static <T> T getClient(String uri, Class<T> iFace, String apikey, String jwt, boolean useGclCertificateSslConfig) {
        try { Builder retrofitBuilder = new Builder()
                    .client(gethttpClient(apikey, jwt, useGclCertificateSslConfig))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(uri);
            return retrofitBuilder.build().create(iFace);
        } catch (Exception ex) {
            log.error("Error creating client: ", ex);
            throw ExceptionFactory.gclClientException("Error creating client: " + ex.getMessage());
        }
    }

    /**
     * Builds a http client instance.
     *
     * @param apikey
     * @param jwt
     * @param setSslConfig
     * @return
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws KeyManagementException
     * @throws KeyStoreException
     * @throws IOException
     */
    private static OkHttpClient gethttpClient(final String apikey, final String jwt, final Boolean setSslConfig) throws NoSuchAlgorithmException, CertificateException, KeyManagementException, KeyStoreException, IOException {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        if (setSslConfig) {
            TrustManagerFactory tmf = getTrustManagerFactory();
            SSLContext context = getSSLConfig(tmf);
            okHttpBuilder.sslSocketFactory(context.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
        } else {
            okHttpBuilder.sslSocketFactory(new TLSSocketFactory());
        }

        final boolean apikeyPresent = StringUtils.isNotBlank(apikey);
        final boolean jwtPresent = StringUtils.isNotBlank(jwt);

        if (apikeyPresent || jwtPresent) {
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    if (apikeyPresent) {
                        requestBuilder.addHeader(APIKEY_HEADER_NAME, apikey);
                    }
                    if (jwtPresent) {
                        if (jwt.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
                            requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, jwt);
                        } else {
                            requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE_PREFIX + jwt);
                        }
                    }
                    return chain.proceed(requestBuilder.build());
                }
            });
        }
        return okHttpBuilder.build();
    }

    //TODO - GCL expose SSL certificate -> check with T1C-GCL
    /**
     * Utility method to resolve the TLC context.
     * @param trustManagerFactory
     * @return
     * @throws CertificateException
     * @throws IOException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static SSLContext getSSLConfig(TrustManagerFactory trustManagerFactory) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (InputStream cert = RestServiceBuilder.class.getResourceAsStream("/t1c.crt")) {
            ca = cf.generateCertificate(cert);
        }
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }

    private static TrustManagerFactory getTrustManagerFactory() throws NoSuchAlgorithmException {
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        return TrustManagerFactory.getInstance(tmfAlgorithm);
    }
}