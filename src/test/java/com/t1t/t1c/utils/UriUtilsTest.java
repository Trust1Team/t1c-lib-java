package com.t1t.t1c.utils;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UriUtilsTest {

    @Test
    public void testUriFinalSlashAppender() throws Exception {
        final String uriWithFinalSlash = "https://some.uri/";
        final String uriWithoutFinalSlash = "https://some.uri";
        assertEquals(uriWithFinalSlash, UriUtils.uriFinalSlashAppender(uriWithFinalSlash));
        assertEquals(uriWithFinalSlash, UriUtils.uriFinalSlashAppender(uriWithoutFinalSlash));
    }

    @Test
    public void testUriFinalSlashRemover() throws Exception {
        final String uriWithFinalSlash = "https://some.uri/";
        final String uriWithoutFinalSlash = "https://some.uri";
        assertEquals(uriWithoutFinalSlash, UriUtils.uriFinalSlashRemover(uriWithFinalSlash));
        assertEquals(uriWithoutFinalSlash, UriUtils.uriFinalSlashRemover(uriWithoutFinalSlash));
    }

    @Test
    public void testUriLeadingSlashRemover() throws Exception {
        final String contextPathWithLeadingSlash = "/contextpath";
        final String contextPathWithOutLeadingSlash = "contextpath";
        assertEquals(contextPathWithOutLeadingSlash, UriUtils.uriLeadingSlashRemover(contextPathWithLeadingSlash));
        assertEquals(contextPathWithOutLeadingSlash, UriUtils.uriLeadingSlashRemover(contextPathWithOutLeadingSlash));
    }

    @Test
    public void testUriLeadingSlashPrepender() throws Exception {
        final String contextPathWithLeadingSlash = "/contextpath";
        final String contextPathWithOutLeadingSlash = "contextpath";
        assertEquals(contextPathWithLeadingSlash, UriUtils.uriLeadingSlashPrepender(contextPathWithLeadingSlash));
        assertEquals(contextPathWithLeadingSlash, UriUtils.uriLeadingSlashPrepender(contextPathWithOutLeadingSlash));
    }

}