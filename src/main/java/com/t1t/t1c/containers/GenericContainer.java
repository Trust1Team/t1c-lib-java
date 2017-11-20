package com.t1t.t1c.containers;

import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.rest.ContainerRestClient;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public abstract class GenericContainer<T extends GenericContainer> {
    public GenericContainer() {}
    public GenericContainer(String readerId, ContainerRestClient httpClient, String pin) { createInstance(readerId, httpClient, pin); }
    protected abstract T createInstance(String readerId, ContainerRestClient httpClient, String pin);
    protected abstract ContainerType getType();
    protected abstract List<String> getAllDataFilters();
    protected abstract List<String> getAllCertificateFilters();
    protected abstract String getTypeId();
    protected abstract String getReaderId();
    protected abstract AllData getAllData() throws GenericContainerException;
    protected abstract AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllData getAllData(boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates() throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(boolean... parseCertificates) throws GenericContainerException;
    protected abstract Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;
    protected abstract String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;
    protected abstract String sign(GclAuthenticateOrSignData data) throws GenericContainerException;
}
