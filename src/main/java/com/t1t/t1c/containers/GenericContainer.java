package com.t1t.t1c.containers;

import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;

import java.util.List;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 */
public abstract class GenericContainer<T extends GenericContainer> implements GclContainer{
    /*Properties*/
    protected String readerId;
    protected transient String pin;
    /*Instantiation*/
    public GenericContainer() {}
    public GenericContainer(String readerId, ContainerRestClient httpClient, String pin) { createInstance(readerId, httpClient, pin); }
    protected abstract T createInstance(String readerId, ContainerRestClient httpClient, String pin);
    /*Data Related*/
    protected abstract List<String> getAllDataFilters();
    protected abstract List<String> getAllCertificateFilters();
    protected abstract AllData getAllData() throws GenericContainerException;
    protected abstract AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllData getAllData(Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates() throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException;
    /*Token Functionality*/
    protected abstract Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;
    protected abstract String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;
    protected abstract String sign(GclAuthenticateOrSignData data) throws GenericContainerException;
}
