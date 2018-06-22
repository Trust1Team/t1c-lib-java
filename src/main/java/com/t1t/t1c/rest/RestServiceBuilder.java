package com.t1t.t1c.rest;

import com.t1t.t1c.auth.GatewayAuthClient;
import com.t1t.t1c.auth.GatewayAuthRestClient;
import com.t1t.t1c.auth.IGatewayAuthClient;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclCitrixRestClient;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.ds.DsRestClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.ocv.OcvRestClient;
import com.t1t.t1c.utils.ClientFingerprintUtil;
import com.t1t.t1c.utils.UriUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public final class RestServiceBuilder {

    private static final Logger log = LoggerFactory.getLogger(RestServiceBuilder.class);

    /* Headers */
    private static final String CONTAINER_CONTEXT_PATH = "containers/";
    private static final String APIKEY_HEADER_NAME = "apikey";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String X_RELAY_STATE_PREFIX = "X-Relay-State-%s-NS";
    private static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";
    private static final String ORIGIN_HEADER_NAME = "origin";
    private static final String ORIGIN_HEADER_VALUE = "https://localhost";
    private static final String X_AUTH_HEADER_NAME = "X-Authentication-Token";
    private static final String CITRIX_AGENT_PATH = "agent/%s/";

    private RestServiceBuilder() {
    }

    /**
     * GCL Rest client communicates over TLS to T1C-GCL (local).
     * No apikey or JWT required.
     *
     * @param config
     * @return
     */
    public static GclRestClient getGclRestClient(LibConfig config) {
        return getLocalClient(config.getGclClientUri(), GclRestClient.class, config, false);
    }

    /**
     * GCL Rest client communicates over TLS to T1C-GCL (local). Client specific for Citrix agents
     * No apikey or JWT required.
     *
     * @param config
     * @return
     */
    public static GclCitrixRestClient getGclCitrixRestClient(LibConfig config) {
        return getLocalClient(config.getGclClientUri(), GclCitrixRestClient.class, config, false);
    }

    /**
     * Gcl Admin Test client communicates over TLS to T1C-GCL (local).
     * JWT required.
     *
     * @param config
     * @return
     */
    public static GclAdminRestClient getGclAdminRestClient(LibConfig config) {
        return getLocalClient(config.getGclClientUri(), GclAdminRestClient.class, config, true);
    }

    /**
     * DS Rest client communicates over TLS towards T1C-DS (cloud).
     * Api-key required.
     *
     * @param config
     * @return
     */
    public static DsRestClient getDsRestClient(LibConfig config) {
        return getClient(config.getDsUri(), DsRestClient.class, config, getGatewayAuthClient(config));
    }

    /**
     * Gateway auth client that communicates through gateway
     *
     * @param config
     * @return
     */
    public static IGatewayAuthClient getGatewayAuthClient(LibConfig config) {
        if (StringUtils.isNotEmpty(config.getApiKey())) {
            return new GatewayAuthClient(getClient(config.getAuthUri(), GatewayAuthRestClient.class, config, null));
        } else {
            return null;
        }
    }

    /**
     * Container Rest client communicates over TLS to T1C-GCL (local) addressing a specific container.
     * No apikey or JWT required.
     *
     * @param config
     * @param clazz
     * @param <U>
     * @return
     */
    public static <U> U getContainerRestClient(LibConfig config, Class<U> clazz) {
        String uri;
        if (config.isCitrix() && config.getAgentPort() != null) {
            uri = UriUtils.uriFinalSlashAppender(config.getGclClientUri() + String.format(CITRIX_AGENT_PATH, config.getAgentPort()) + CONTAINER_CONTEXT_PATH);
        } else {
            uri = UriUtils.uriFinalSlashAppender(config.getGclClientUri() + CONTAINER_CONTEXT_PATH);
        }
        return getLocalClient(uri, clazz, config, false);
    }

    /**
     * OCV (Open Certificate Validation API) Rest client communicates over TLS to OCV-API.
     * Api-key required.
     *
     * @param config
     * @return
     */
    public static OcvRestClient getOcvRestClient(LibConfig config) {
        return getClient(config.getOcvUri(), OcvRestClient.class, config, getGatewayAuthClient(config));
    }

    /**
     * Proxy method to instantiate a client connection.
     *
     * @param uri
     * @param iFace
     * @param config
     * @param gatewayAuthClient
     * @param <T>
     * @return
     */
    private static <T> T getClient(String uri, Class<T> iFace, LibConfig config, IGatewayAuthClient gatewayAuthClient) {
        try {
            Builder retrofitBuilder = new Builder()
                    .client(gethttpClient(config, gatewayAuthClient))
                    .addConverterFactory(GsonConverterFactory.create())
                    // base URL must always end with /
                    .baseUrl(UriUtils.uriFinalSlashAppender(uri));
            return retrofitBuilder.build().create(iFace);
        } catch (Exception ex) {
            log.error("Error creating client: ", ex);
            throw ExceptionFactory.gclClientException("Error creating client: " + ex.getMessage());
        }
    }

    //TODO remove duplicate code
    private static <T> T getLocalClient(String uri, Class<T> iFace, LibConfig config, boolean sendAuthToken) {
        try {
            Builder retrofitBuilder = new Builder()
                    .client(getHttpClientSkipTLS(config, sendAuthToken))
                    .addConverterFactory(GsonConverterFactory.create())
                    // base URL must always end with /
                    .baseUrl(UriUtils.uriFinalSlashAppender(uri));
            return retrofitBuilder.build().create(iFace);
        } catch (Exception ex) {
            log.error("Error creating client: ", ex);
            throw ExceptionFactory.gclClientException("Error creating client: " + ex.getMessage());
        }
    }

    /**
     * Builds a http client instance.
     *
     * @param config
     * @param gatewayAuthClient
     * @return
     */
    private static OkHttpClient gethttpClient(final LibConfig config, final IGatewayAuthClient gatewayAuthClient) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        final boolean apikeyPresent = StringUtils.isNotBlank(config.getApiKey());

        String token;
        if (gatewayAuthClient != null) {
            token = gatewayAuthClient.getToken();
        } else {
            token = config.getGwJwt();
        }

        if (config.getCustomSslConfig() != null) {
            okHttpBuilder.sslSocketFactory(config.getCustomSslConfig().getSslContext().getSocketFactory(), config.getCustomSslConfig().getTrustManager());
            if (config.getCustomSslConfig().getHostnameVerifier() != null) {
                okHttpBuilder.hostnameVerifier(config.getCustomSslConfig().getHostnameVerifier());
            }
        }

        final boolean jwtPresent = StringUtils.isNotEmpty(token);
        final String gwToken = token;
        if (apikeyPresent || jwtPresent) {
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    if (apikeyPresent) {
                        requestBuilder.addHeader(APIKEY_HEADER_NAME, config.getApiKey());
                    }
                    if (jwtPresent) {
                        requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE_PREFIX + gwToken);
                    }
                    return chain.proceed(requestBuilder.build());
                }
            });
        }
        return okHttpBuilder
                // Set timeouts a little higher, because reading data from cards can take time
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Returns a http handler using TLS, but accepting all certificates.
     * Needed in order to bootstrap the T1C-GCL:
     * Retrieve public key from GCL through TLS
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static OkHttpClient getHttpClientSkipTLS(final LibConfig config, final boolean sendAuthToken) throws NoSuchAlgorithmException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains

        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                // Do nothing
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                // Do nothing
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        final TrustManager[] trustAllCerts = new TrustManager[]{x509TrustManager};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });

        final boolean jwtPresent = StringUtils.isNotBlank(config.getGclJwt()) && sendAuthToken;
        final boolean contextTokenRequired = config.getContextToken() != null && !config.isManaged();
        if (jwtPresent || contextTokenRequired) {
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    if (jwtPresent) {
                        requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE_PREFIX + config.getGclJwt());
                    }
                    if (contextTokenRequired) {
                        requestBuilder.addHeader(String.format(X_RELAY_STATE_PREFIX, String.valueOf(config.getContextToken())), String.valueOf(config.getContextToken()));
                    }
                    return chain.proceed(requestBuilder.build());
                }
            });
        }

        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.addHeader(ORIGIN_HEADER_NAME, ORIGIN_HEADER_VALUE);
                String fingerprint = ClientFingerprintUtil.getClientFingerPrint(config.getClientFingerprintDirectoryPath());
                log.debug("client fingerprint: {}", fingerprint);
                requestBuilder.addHeader(X_AUTH_HEADER_NAME, fingerprint);
                return chain.proceed(requestBuilder.build());
            }
        });

        // Set timeouts a little higher, because reading data from cards can take time
        // The timeout should also be greater than the consent timeout, otherwise an error will occur when exceeding it
        // As such the timeout should default to either 30 seconds or the default consent timeout + 1s
        int timeout = config.getDefaultConsentTimeout() + 1 < 30 ? 30 : config.getDefaultConsentTimeout() + 1;
        return okHttpBuilder
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }
}