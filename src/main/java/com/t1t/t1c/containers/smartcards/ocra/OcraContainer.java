package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.model.rest.GclOcraChallengeData;
import com.t1t.t1c.model.rest.GclVerifyPinRequest;
import com.t1t.t1c.containers.CommonContainerRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class OcraContainer extends GenericContainer<OcraContainer>{
    private static final Logger log = LoggerFactory.getLogger(OcraContainer.class);

    @Override
    protected OcraContainer createInstance(String readerId, CommonContainerRestClient httpClient, String pin) {
        return null;
    }

    @Override
    protected List<String> getAllDataFilters() {
        return null;
    }

    @Override
    protected List<String> getAllCertificateFilters() {
        return null;
    }

    @Override
    protected AllData getAllData() throws GenericContainerException {
        return null;
    }

    @Override
    protected AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected AllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected AllCertificates getAllCertificates() throws GenericContainerException {
        return null;
    }

    @Override
    protected AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    protected Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        return null;
    }

    @Override
    protected String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException {
        return null;
    }

    @Override
    protected String sign(GclAuthenticateOrSignData data) throws GenericContainerException {
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
}