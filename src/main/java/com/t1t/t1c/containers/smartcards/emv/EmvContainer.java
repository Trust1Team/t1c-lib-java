package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclEmvAidRequest;
import com.t1t.t1c.model.rest.GclEmvApplication;
import com.t1t.t1c.model.rest.GclEmvApplicationData;
import com.t1t.t1c.model.rest.GclEmvCertificate;
import com.t1t.t1c.rest.ContainerRestClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class EmvContainer extends AbstractContainer implements IEmvContainer {

    private static final Logger log = LoggerFactory.getLogger(EmvContainer.class);

    public EmvContainer(String readerId, ContainerRestClient httpClient) {
        super(readerId, ContainerType.EMV, httpClient);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return returnData(getHttpClient().getEmvAllData(getType().getId(), getReaderId(), createFilterParams(filterParams)));
            } else {
                return returnData(getHttpClient().getEmvAllData(getType().getId(), getReaderId()));
            }
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("This container does not provide a list of all of its certificates");
    }

    @Override
    public List<GclEmvApplication> getApplications() {
        try {
            return returnData(getHttpClient().getEmvApplications(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("Could not retrieve list of applications from container", ex);
        }
    }

    @Override
    public GclEmvApplicationData getApplicationData() {
        try {
            return returnData(getHttpClient().getEmvApplicationData(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("Could not retrieve application data from container", ex);
        }
    }

    @Override
    public GclEmvCertificate getIccPublicKeyCertificate(String... aid) {
        try {
            return returnData(getHttpClient().getEmvIccPublicKeyCertificate(getTypeId(), getReaderId(), createAidRequest(aid)));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("Could not retrieve ICC public key certificate from container", ex);
        }
    }

    @Override
    public GclEmvCertificate getIssuerPublicKeyCertificate(String... aid) {
        try {
            return returnData(getHttpClient().getEmvIssuerPublicKeyCertificate(getTypeId(), getReaderId(), createAidRequest(aid)));
        } catch (RestException ex) {
            throw ExceptionFactory.emvContainerException("Could not retrieve issuer public key certificate from container", ex);
        }
    }

    private GclEmvAidRequest createAidRequest(String... aid) {
        GclEmvAidRequest request = null;
        if (aid != null && aid.length > 0 && StringUtils.isNotEmpty(aid[0])) {
            request = new GclEmvAidRequest().withAid(aid[0]);
        }
        return request;
    }
}