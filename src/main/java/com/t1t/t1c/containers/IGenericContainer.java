package com.t1t.t1c.containers;

import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGenericContainer<V, W> extends IGclContainer {
    Class<V> getAllDataClass();
    Class<W> getAllCertificateClass();
    /*Data Related*/
    List<String> getAllDataFilters();
    List<String> getAllCertificateFilters();
    V getAllData() throws GenericContainerException;
    V getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    V getAllData(Boolean... parseCertificates) throws GenericContainerException;
    W getAllCertificates() throws GenericContainerException;
    W getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    W getAllCertificates(Boolean... parseCertificates) throws GenericContainerException;
    /*Token Functionality*/
    Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;
    String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;
    String sign(GclAuthenticateOrSignData data) throws GenericContainerException;
}
