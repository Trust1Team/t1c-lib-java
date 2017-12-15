package com.t1t.t1c.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclAdminActivationTest {

    @Test
    public void getSuccess() {
        GclAdminActivation act = new GclAdminActivation().withSuccess(true);
        GclAdminActivation actFalse = new GclAdminActivation().withSuccess(false);
        assertTrue(act.getSuccess());
        assertFalse(actFalse.getSuccess());
    }

    @Test
    public void setSuccess() {
        GclAdminActivation act = new GclAdminActivation();
        act.setSuccess(false);
        assertFalse(act.getSuccess());
    }

    @Test
    public void withSuccess() {
        GclAdminActivation act = new GclAdminActivation();
        assertNotNull(act.withSuccess(true));
        assertTrue(act.getSuccess());
    }

    @Test
    public void testToString() {
        GclAdminActivation act1 = new GclAdminActivation().withSuccess(true);
        assertNotNull(act1.toString());
    }

    @Test
    public void testHashCode() {
        GclAdminActivation act1 = new GclAdminActivation().withSuccess(true);
        GclAdminActivation act2 = new GclAdminActivation().withSuccess(true);
        assertEquals(act1.hashCode(), act2.hashCode());
    }

    @Test
    public void equals() {
        GclAdminActivation act1 = new GclAdminActivation().withSuccess(true);
        GclAdminActivation act2 = new GclAdminActivation().withSuccess(true);
        assertEquals(act1, act2);
    }
}