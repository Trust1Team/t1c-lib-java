package com.t1t.t1c.containers.smartcards.pki.oberthur;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.DigestAlgorithm;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public class OberthurContainer extends GenericContainer<OberthurContainer, GclOberthurRestClient, AllData, AllCertificates> {

    public OberthurContainer(LibConfig config, GclReader reader, GclOberthurRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public OberthurContainer createInstance(LibConfig config, GclReader reader, GclOberthurRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.OBERTHUR;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return null;
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return null;
    }

    @Override
    public AllData getAllData() throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates() throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        return null;
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        return null;
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        return null;
    }

    @Override
    public ContainerType getType() {
        return null;
    }

    @Override
    public String getTypeId() {
        return null;
    }

    @Override
    public Class<AllData> getAllDataClass() {
        return null;
    }

    @Override
    public Class<AllCertificates> getAllCertificateClass() {
        return null;
    }
}
