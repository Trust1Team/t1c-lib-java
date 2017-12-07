package com.t1t.t1c.rest;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerRestClient;
import com.t1t.t1c.core.GclAdminRestClient;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.ds.DsRestClient;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.ocv.OcvRestClient;
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
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

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
        return getLocalClient(config.getGclClientUri(), GclRestClient.class, null, null);
    }

    /**
     * Gcl Admin Test client communicates over TLS to T1C-GCL (local).
     * JWT required.
     *
     * @param config
     * @return
     */
    public static GclAdminRestClient getGclAdminRestClient(LibConfig config) {
        return getLocalClient(config.getGclClientUri(), GclAdminRestClient.class, null, config.getJwt());
    }

    /**
     * DS Rest client communicates over TLS towards T1C-DS (cloud).
     * Api-key required.
     *
     * @param config
     * @return
     */
    public static DsRestClient getDsRestClient(LibConfig config) {
        return getClient(UriUtils.constructURI(config.getDsUri(), config.getDsContextPath()), DsRestClient.class, config.getApiKey(), null);
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
        return getLocalClient(UriUtils.uriFinalSlashAppender(config.getGclClientUri() + ContainerRestClient.CONTAINER_CONTEXT_PATH), clazz, null, null);
    }

    /**
     * OCV (Open Certificate Validation API) Rest client communicates over TLS to OCV-API.
     * Api-key required.
     *
     * @param config
     * @return
     */
    public static OcvRestClient getOcvRestClient(LibConfig config) {
        return getClient(UriUtils.constructURI(config.getOcvUri(), config.getOcvContextPath()), OcvRestClient.class, config.getApiKey(), null);
    }

    /**
     * Proxy method to instantiate a client connection.
     *
     * @param uri
     * @param iFace
     * @param apikey
     * @param jwt
     * @param <T>
     * @return
     */
    private static <T> T getClient(String uri, Class<T> iFace, String apikey, String jwt) {
        try {
            Builder retrofitBuilder = new Builder()
                    .client(gethttpClient(apikey, jwt))
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
    private static <T> T getLocalClient(String uri, Class<T> iFace, String apikey, String jwt) {
        try {
            Builder retrofitBuilder = new Builder()
                    .client(getHttpClientSkipTLS(apikey, jwt))
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
     * @param apikey
     * @param jwt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws KeyManagementException
     * @throws KeyStoreException
     * @throws IOException
     */
    private static OkHttpClient gethttpClient(final String apikey, final String jwt) throws NoSuchAlgorithmException, CertificateException, KeyManagementException, KeyStoreException, IOException {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.sslSocketFactory(new TLSSocketFactory());

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
     * @throws CertificateException
     * @throws KeyManagementException
     */
    private static OkHttpClient getHttpClientSkipTLS(final String apikey, final String jwt) throws NoSuchAlgorithmException, CertificateException, KeyManagementException {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.sslSocketFactory(sslSocketFactory)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });

        final boolean jwtPresent = StringUtils.isNotBlank(jwt);
        if (jwtPresent) {
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    if (jwt.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
                        requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, jwt);
                    } else {
                        requestBuilder.addHeader(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE_PREFIX + jwt);
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

    private static TrustManagerFactory getTrustManagerFactory() throws NoSuchAlgorithmException {
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        return TrustManagerFactory.getInstance(tmfAlgorithm);
    }
}