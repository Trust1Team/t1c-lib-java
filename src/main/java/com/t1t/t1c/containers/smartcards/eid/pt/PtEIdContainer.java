package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.configuration.LibConfig;
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
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PtEIdContainer extends AbstractContainer implements IPtEIdContainer {


    public PtEIdContainer(LibConfig config, String readerId, ContainerRestClient httpClient) {
        super(config, readerId, ContainerType.PT, httpClient);
    }

    @Override
    public AllData getAllData(List<String> filterParams) throws GenericContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return returnData(getHttpClient().getPtIdAllData(getType().getId(), getReaderId(), createFilterParams(filterParams)));
            } else {
                return returnData(getHttpClient().getPtIdAllData(getType().getId(), getReaderId()));
            }
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams) throws GenericContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return returnData(getHttpClient().getPtIdAllCertificates(getType().getId(), getReaderId(), createFilterParams(filterParams)));
            } else {
                return returnData(getHttpClient().getPtIdAllCertificates(getType().getId(), getReaderId()));
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
            return createT1cCertificate(returnData(getHttpClient().getRootAuthenticationCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.ptIdContainerException("Could not retrieve authentication certificate from container", ex);
        }
    }

    @Override
    public T1cCertificate getRootNonRepudiationCertificate(boolean parse) throws PtIdContainerException {
        try {
            return createT1cCertificate(returnData(getHttpClient().getRootNonRepudiationCertificate(getTypeId(), getReaderId())), parse);
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