package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.containers.CommonContainerRestClient;
import com.t1t.t1c.rest.RestExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;


/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class BeIdContainer extends GenericContainer<BeIdContainer> {

    private final ContainerType type = ContainerType.BEID;
    private static final List<String> certificateFilters = Arrays.asList("address", "rn", "picture", "root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    private static final Logger log = LoggerFactory.getLogger(BeIdContainer.class);
    private GclBeidRestClientCommon client;

    public BeIdContainer(String readerId, GclBeidRestClientCommon gclBeidRestClient) {
        this.readerId = readerId;
        this.client = gclBeidRestClient;
    }

    /*Dynamic instance creation*/
    @Override
    protected BeIdContainer createInstance(String readerId, CommonContainerRestClient commonClient, String pin) {
        this.readerId = readerId;
        this.commonClient = commonClient;
    }

    @Override
    protected List<String> getAllDataFilters() {
        return Arrays.asList("address", "rn", "picture", "root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    protected List<String> getAllCertificateFilters() {
        return Arrays.asList("root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate");
    }

    @Override
    protected AllData getAllData() throws GenericContainerException {
        return getAllData(null, null);
    }

    @Override
    protected AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        try {
            GclBeIdAllData data = RestExecutor.returnData(client.getBeIdAllData(readerId, type.getId(), createFilterParams(filterParams)));
            return new BeIdAllData(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    protected AllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    protected AllCertificates getAllCertificates() throws GenericContainerException {
        return getAllCertificates(null, null);
    }

    @Override
    protected AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        try {
            GclBeIdAllCertificates data = RestExecutor.returnData(client.getBeIdAllCertificates(readerId, type.getId(), createFilterParams(filterParams)));
            return new BeIdAllCertificates(data, parseCertificates);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("could not retrieve all data", ex);
        }
    }

    @Override
    protected AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    protected Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        try {

        }
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