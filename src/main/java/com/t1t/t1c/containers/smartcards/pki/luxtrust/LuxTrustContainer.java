package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.google.common.base.Preconditions;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.exceptions.LuxTrustContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.T1cCertificate;
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
public class LuxTrustContainer extends GenericContainer<LuxTrustContainer> {

    private static final Logger log = LoggerFactory.getLogger(LuxTrustContainer.class);


    public LuxTrustContainer(String readerId, ContainerRestClient httpClient, String pin) {
        super(readerId, ContainerType.LUXTRUST, httpClient, pin);
        Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "PIN is required for LuxTrust Container");
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public boolean isActivated() throws LuxTrustContainerException {
        try {
            return isCallSuccessful(executeCall(getHttpClient().isLuxTrustActivated(getTypeId(), getReaderId(), getPin())));
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws LuxTrustContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new LuxTrustAllData(returnData(getHttpClient().getLuxTrustAllData(getTypeId(), getReaderId(), getPin(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new LuxTrustAllData(returnData(getHttpClient().getLuxTrustAllData(getTypeId(), getReaderId(), getPin())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws LuxTrustContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new LuxTrustAllCertificates(returnData(getHttpClient().getLuxTrustAllCertificates(getTypeId(), getReaderId(), getPin(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new LuxTrustAllCertificates(returnData(getHttpClient().getLuxTrustAllCertificates(getTypeId(), getReaderId(), getPin())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.luxTrustContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public List<T1cCertificate> getRootCertificates(boolean parse) throws LuxTrustContainerException {
        return super.getRootCertificates(parse);
    }

    @Override
    public T1cCertificate getAuthenticationCertificate(boolean parse) throws LuxTrustContainerException {
        return super.getAuthenticationCertificate(parse);
    }

    @Override
    public T1cCertificate getSigningCertificate(boolean parse) throws LuxTrustContainerException {
        return super.getSigningCertificate(parse);
    }
}