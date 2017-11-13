package com.t1t.t1c.rest;

import com.t1t.t1c.exceptions.ExceptionFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
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
public class MockRestServiceBuilder {

    private static final Logger log = LoggerFactory.getLogger(MockRestServiceBuilder.class);

    private static final String APIKEY_HEADER_NAME = "apikey";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";

    public static Retrofit getRetrofit(String uri, String apiKey, String jwt, boolean useGclCertificate) {
        try {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                    .client(gethttpClient(apiKey, jwt, useGclCertificate))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(uri);
            return retrofitBuilder.build();
        } catch (Exception ex) {
            log.error("Error creating client: ", ex);
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