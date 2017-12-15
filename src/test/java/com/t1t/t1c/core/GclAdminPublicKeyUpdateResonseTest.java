package com.t1t.t1c.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclAdminPublicKeyUpdateResonseTest {
    @Test
    public void getSuccess() {
        GclAdminPublicKeyUpdateResonse pub = new GclAdminPublicKeyUpdateResonse().withSuccess(true);
        GclAdminPublicKeyUpdateResonse pubFalse = new GclAdminPublicKeyUpdateResonse().withSuccess(false);
        assertTrue(pub.getSuccess());
        assertFalse(pubFalse.getSuccess());
    }

    @Test
    public void setSuccess() {
        GclAdminPublicKeyUpdateResonse pub = new GclAdminPublicKeyUpdateResonse();
        pub.setSuccess(false);
        assertFalse(pub.getSuccess());
    }

    @Test
    public void withSuccess() {
        GclAdminPublicKeyUpdateResonse pub = new GclAdminPublicKeyUpdateResonse();
        assertNotNull(pub.withSuccess(true));
        assertTrue(pub.getSuccess());
    }

    @Test
    public void testToString() {
        GclAdminPublicKeyUpdateResonse pub = new GclAdminPublicKeyUpdateResonse().withSuccess(true);
        assertNotNull(pub.toString());
    }

    @Test
    public void testHashCode() {
        GclAdminPublicKeyUpdateResonse pub1 = new GclAdminPublicKeyUpdateResonse().withSuccess(true);
        GclAdminPublicKeyUpdateResonse pub2 = new GclAdminPublicKeyUpdateResonse().withSuccess(true);
        assertEquals(pub1.hashCode(), pub2.hashCode());
    }

    @Test
    public void equals() {
        GclAdminPublicKeyUpdateResonse pub1 = new GclAdminPublicKeyUpdateResonse().withSuccess(true);
        GclAdminPublicKeyUpdateResonse pub2 = new GclAdminPublicKeyUpdateResonse().withSuccess(true);
        assertEquals(pub1, pub2);
    }

}