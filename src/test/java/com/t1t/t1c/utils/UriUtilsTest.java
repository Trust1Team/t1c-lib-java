package com.t1t.t1c.utils;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UriUtilsTest {
    @Test
    public void testConstructURI() throws Exception {

    }

    @Test
    public void testUriFinalSlashAppender() throws Exception {
        final String uriWithFinalSlash = "https://some.uri/";
        final String uriWithoutFinalSlash = "https://some.uri";
        assertEquals(uriWithFinalSlash, UriUtils.uriFinalSlashAppender(uriWithFinalSlash));
        assertEquals(uriWithFinalSlash, UriUtils.uriFinalSlashAppender(uriWithoutFinalSlash));
    }

    @Test
    public void testUriFinalSlashRemover() throws Exception {
    }

    @Test
    public void testUriLeadingSlashRemover() throws Exception {
    }

    @Test
    public void testUriLeadingSlashPrepender() throws Exception {
    }

}