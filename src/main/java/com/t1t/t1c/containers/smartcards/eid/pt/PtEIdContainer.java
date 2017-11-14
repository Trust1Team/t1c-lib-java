package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.containers.AbstractContainer;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.pt.exceptions.PtIdContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclPtIdData;
import com.t1t.t1c.model.rest.T1cCertificate;
import com.t1t.t1c.rest.ContainerRestClient;
import com.t1t.t1c.utils.CertificateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PtEIdContainer extends AbstractContainer implements IPtEIdContainer {

    private static final Logger log = LoggerFactory.getLogger(PtEIdContainer.class);

    public PtEIdContainer(String readerId, ContainerRestClient httpClient) {
        super(readerId, ContainerType.PT, httpClient);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new PtIdAllData(returnData(getHttpClient().getPtIdAllData(getType().getId(), getReaderId(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new PtIdAllData(returnData(getHttpClient().getPtIdAllData(getType().getId(), getReaderId())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new PtIdAllCertificates(returnData(getHttpClient().getPtIdAllCertificates(getType().getId(), getReaderId(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new PtIdAllCertificates(returnData(getHttpClient().getPtIdAllCertificates(getType().getId(), getReaderId())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public GclPtIdData getIdData() throws PtIdContainerException {
        return getPtIdData(true);
    }

    @Override
    public GclPtIdData getIdDataWithOutPhoto() throws PtIdContainerException {
        return getPtIdData(false);
    }

    @Override
    public String getPhoto() throws PtIdContainerException {
        try {
            return returnData(getHttpClient().getPtIdPhoto(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve ID data from container", ex);
        }
    }

    @Override
    public T1cCertificate getRootCertificate(boolean parse) throws PtIdContainerException {
        return super.getRootCertificate(parse);
    }

    @Override
    public T1cCertificate getRootAuthenticationCertificate(boolean parse) throws PtIdContainerException {
        try {
            return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getRootAuthenticationCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    @Override
    public T1cCertificate getRootNonRepudiationCertificate(boolean parse) throws PtIdContainerException {
        try {
            return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getRootNonRepudiationCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    @Override
    public T1cCertificate getAuthenticationCertificate(boolean parse) throws PtIdContainerException {
        return super.getAuthenticationCertificate(parse);
    }

    @Override
    public T1cCertificate getNonRepudiationCertificate(boolean parse) throws PtIdContainerException {
        return super.getNonRepudiationCertificate(parse);
    }

    private GclPtIdData getPtIdData(boolean includePhoto) {
        try {
            return returnData(getHttpClient().getPtIdData(getTypeId(), getReaderId(), includePhoto));
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve ID data from container", ex);
        }
    }
}