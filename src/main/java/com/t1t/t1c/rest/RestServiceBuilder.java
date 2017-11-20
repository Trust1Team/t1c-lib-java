package com.t1t.t1c.rest;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
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
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class RestServiceBuilder {

    private static final Logger _LOG = LoggerFactory.getLogger(RestServiceBuilder.class);
    private static final String APIKEY_HEADER_NAME = "apikey";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";
    private static final String CONTAINER_CONTEXT_PATH = "plugins/";

    private RestServiceBuilder() {}

    public static GclRestClient getGclRestClient(LibConfig config) {
        return getClient(config.getGclClientUri(), GclRestClient.class, null, null, true);
    }

    public static GclAdminRestClient getGclAdminRestClient(LibConfig config) {
        if (config.isTokenCompatible()) {
            return getClient(config.getGclClientUri(), GclAdminRestClient.class, null, config.getJwt(), true);
        } else {
            return getClient(config.getGclClientUri(), GclAdminRestClient.class, null, null, true);
        }
    }

    public static DsRestClient getDsRestClient(LibConfig config) {
        return getClient(config.getDsUri(), DsRestClient.class, config.getApiKey(), null, false);
    }

    public static ContainerRestClient getContainerRestClient(LibConfig config) {
        return getClient(UriUtils.uriFinalSlashAppender(config.getGclClientUri() + CONTAINER_CONTEXT_PATH), ContainerRestClient.class, null, null, true);
    }

    public static OcvRestClient getOcvRestClient(LibConfig config) {
        return getClient(config.getOcvUri(), OcvRestClient.class, config.getApiKey(), null, false);
    }

    private static <T> T getClient(String uri, Class<T> iFace, String apikey, String jwt, boolean useGclCertificateSslConfig) {
        try {
            if (StringUtils.isBlank(uri)) {
                throw ExceptionFactory.configException("Base URI not provided.");
            }
            Builder retrofitBuilder = new Builder()
                    .client(gethttpClient(apikey, jwt, useGclCertificateSslConfig))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(uri);
            return retrofitBuilder.build().create(iFace);
        } catch (Exception ex) {
            _LOG.error("Error creating client: ", ex);
            throw ExceptionFactory.gclClientException("Error creating client: " + ex.getMessage());
        }
    }

    //TODO - GCL expose SSL certificate -> create ticket
    private static SSLContext getSSLConfig(TrustManagerFactory trustManagerFactory) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // Loading CAs from an InputStream
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (InputStream cert = RestServiceBuilder.class.getResourceAsStream("/t1c.crt")) {
            ca = cf.generateCertificate(cert);
        }
        // Creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        // Creating a TrustManager that trusts the CAs in our KeyStore.
        trustManagerFactory.init(keyStore);
        // Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }

    private static OkHttpClient gethttpClient(final String apikey, final String jwt, boolean setSslConfig) throws NoSuchAlgorithmException, CertificateException, KeyManagementException, KeyStoreException, IOException {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (setSslConfig) {
            TrustManagerFactory tmf = getTrustManagerFactory();
            SSLContext context = getSSLConfig(tmf);
            okHttpBuilder.sslSocketFactory(context.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
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

    private static TrustManagerFactory getTrustManagerFactory() throws NoSuchAlgorithmException {
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        return TrustManagerFactory.getInstance(tmfAlgorithm);
    }
}