package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.rest.ContainerRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class BeIdContainer extends GenericContainer<BeIdContainer> {
    private static final Logger log = LoggerFactory.getLogger(BeIdContainer.class);
    private ContainerRestClient containerRestClient;
    public BeIdContainer(String readerId, ContainerRestClient httpClient) { }

    /*Dynamic instance creation*/
    @Override
    protected BeIdContainer createInstance(String readerId, ContainerRestClient httpClient, String pin) {
        return null;
    }

    @Override
    public ContainerType getType() {
        return null;
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
    public String getTypeId() {
        return null;
    }

    @Override
    public String getReaderId() {
        return null;
    }

    @Override
    public AllData getAllData() throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllData getAllData(boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates() throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        return null;
    }

    @Override
    public String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException {
        return null;
    }

    @Override
    public String sign(GclAuthenticateOrSignData data) throws GenericContainerException {
        return null;
    }
}