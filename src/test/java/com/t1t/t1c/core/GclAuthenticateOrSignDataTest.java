package com.t1t.t1c.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclAuthenticateOrSignDataTest {

    private GclAuthenticateOrSignData obj1;
    private GclAuthenticateOrSignData obj2;

    @Before
    public void init() {
        obj1 = new GclAuthenticateOrSignData().withAlgorithmReference("SHA256").withData("data").withPin("1234");
        obj2 = new GclAuthenticateOrSignData().withAlgorithmReference("SHA256").withData("data").withPin("1234");
    }

    @Test
    public void getAlgorithmReference() {
        assertEquals("SHA256", obj1.getAlgorithmReference());
    }

    @Test
    public void setAlgorithmReference() {
        obj1.setAlgorithmReference("SHA1");
        assertEquals("SHA1", obj1.getAlgorithmReference());
    }

    @Test
    public void withAlgorithmReference() {
        assertNotNull(obj1.withAlgorithmReference("MD5"));
        assertEquals("MD5", obj1.getAlgorithmReference());
    }

    @Test
    public void getData() {
        assertEquals("data", obj1.getData());
    }

    @Test
    public void setData() {
        obj1.setData("data2");
        assertEquals("data2", obj1.getData());
    }

    @Test
    public void withData() {
        assertNotNull(obj1.withData("data3"));
        assertEquals("data3", obj1.getData());
    }

    @Test
    public void getPin() {
        assertEquals("1234", obj1.getPin());
    }

    @Test
    public void setPin() {
        obj1.setPin("2345");
        assertEquals("2345", obj1.getPin());
    }

    @Test
    public void withPin() {
        assertNotNull(obj1.withPin("3456"));
        assertEquals("3456", obj1.getPin());
    }

    @Test
    public void testToString() {
        assertNotNull(obj1.toString());
    }

    @Test
    public void testHashCode() {
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void testEquals() {
        assertEquals(obj1, obj2);
    }
}