package com.t1t.t1c.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class UriUtils {

    private UriUtils() {
    }

    public static String constructURI(String uri, String contextPath) {
        return uriFinalSlashAppender(uriFinalSlashAppender(uri) + uriLeadingSlashRemover(contextPath));
    }

    public static String uriFinalSlashAppender(String uri) {
        if (!uri.endsWith("/")) return uri + "/";
        else return uri;
    }

    public static String uriFinalSlashRemover(String uri) {
        if (uri.endsWith("/")) {
            return uri.substring(0, uri.length() - 1);
        } else return uri;
    }

    public static String uriLeadingSlashRemover(String uri) {
        if (uri.startsWith("/")) {
            return uri.substring(1);
        } else return uri;
    }

    public static String uriLeadingSlashPrepender(String uri) {
        if (!uri.startsWith("/")) return "/" + uri;
        else return uri;
    }

    public static String getFullUri(String domain, String contextPath) throws MalformedURLException, URISyntaxException {
        URL url = new URL(domain);
        return uriFinalSlashAppender(new URI(url.getProtocol(), url.getHost(), contextPath, null).toString());
    }

    public static String getDomain(String uri) throws MalformedURLException, URISyntaxException {
        URL url = new URL(uri);
        return uriFinalSlashAppender(new URI(url.getProtocol(), url.getHost(), null, null).toString());
    }

    public static String getContextPath(String uri) throws MalformedURLException {
        return uriLeadingSlashPrepender(uriFinalSlashAppender(new URL(uri).getPath()));
    }

}