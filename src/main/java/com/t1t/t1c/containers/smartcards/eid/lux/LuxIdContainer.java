package com.t1t.t1c.containers.smartcards.eid.lux;

import com.google.common.base.Preconditions;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.exceptions.LuxIdContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclLuxIdBiometric;
import com.t1t.t1c.model.rest.GclLuxIdPicture;
import com.t1t.t1c.model.rest.GclLuxIdSignatureImage;
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
public class LuxIdContainer extends GenericContainer<LuxIdContainer> {

    private static final Logger log = LoggerFactory.getLogger(LuxIdContainer.class);

    public LuxIdContainer(String readerId, ContainerRestClient httpClient, String pin) {
        super(readerId, ContainerType.LUXID, httpClient, pin);
        Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "PIN is required for Lux ID container");
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public GclLuxIdBiometric getBiometric() throws LuxIdContainerException {
        try {
            return returnData(getHttpClient().getLuxIdBiometric(getTypeId(), getReaderId(), getPin()));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve biometrics from container", ex);
        }
    }

    @Override
    public GclLuxIdPicture getPicture() throws LuxIdContainerException {
        try {
            return returnData(getHttpClient().getLuxIdPicture(getTypeId(), getReaderId(), getPin()));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve picture from container", ex);
        }
    }

    @Override
    public GclLuxIdSignatureImage getSignatureImage() throws LuxIdContainerException {
        try {
            return returnData(getHttpClient().getLuxIdSignatureImage(getTypeId(), getReaderId(), getPin()));
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve signature image from container", ex);
        }
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws LuxIdContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new LuxIdAllData(returnData(getHttpClient().getLuxIdAllData(getType().getId(), getReaderId(), getPin(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new LuxIdAllData(returnData(getHttpClient().getLuxIdAllData(getType().getId(), getReaderId(), getPin())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws LuxIdContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new LuxIdAllCertificates(returnData(getHttpClient().getLuxIdAllCertificates(getType().getId(), getReaderId(), getPin(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new LuxIdAllCertificates(returnData(getHttpClient().getLuxIdAllCertificates(getType().getId(), getReaderId(), getPin())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.luxIdContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public List<T1cCertificate> getRootCertificates(boolean parse) throws LuxIdContainerException {
        return super.getRootCertificates(parse);
    }

    @Override
    public T1cCertificate getAuthenticationCertificate(boolean parse) throws LuxIdContainerException {
        return super.getAuthenticationCertificate(parse);
    }

    @Override
    public T1cCertificate getNonRepudiationCertificate(boolean parse) throws LuxIdContainerException {
        return super.getNonRepudiationCertificate(parse);
    }
}