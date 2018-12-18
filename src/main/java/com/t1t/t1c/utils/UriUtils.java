package com.t1t.t1c.utils;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public final class UriUtils {

    private UriUtils() {
    }

    public static String uriFinalSlashAppender(final String uri) {
        if (!uri.endsWith("/")) return uri + "/";
        else return uri;
    }

    public static String uriFinalSlashRemover(final String uri) {
        if (uri.endsWith("/")) {
            return uri.substring(0, uri.length() - 1);
        } else return uri;
    }

    public static String uriLeadingSlashRemover(final String uri) {
        if (uri.startsWith("/")) {
            return uri.substring(1);
        } else return uri;
    }

    public static String uriLeadingSlashPrepender(final String uri) {
        if (!uri.startsWith("/")) return "/" + uri;
        else return uri;
    }
}