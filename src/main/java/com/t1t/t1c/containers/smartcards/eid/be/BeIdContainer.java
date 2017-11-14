package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.containers.AbstractContainer;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.be.exceptions.BeIdContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclBeIdAddress;
import com.t1t.t1c.model.rest.GclBeIdRn;
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
public class BeIdContainer extends AbstractContainer implements IBeIdContainer {

    private static final Logger log = LoggerFactory.getLogger(BeIdContainer.class);

    public BeIdContainer(String readerId, ContainerRestClient httpClient) {
        super(readerId, ContainerType.BEID, httpClient);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public GclBeIdRn getRnData() throws BeIdContainerException {
        try {
            return returnData(getHttpClient().getRnData(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve RnData from BeId container", ex);
        }
    }


    @Override
    public GclBeIdAddress getAddress() throws BeIdContainerException {
        try {
            return returnData(getHttpClient().getBeIdAddress(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve address data from BeId container", ex);
        }
    }


    @Override
    public String getPicture() throws BeIdContainerException {
        try {
            return returnData(getHttpClient().getBeIdPicture(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve picture data from BeId container", ex);
        }
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws BeIdContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new BeIdAllData(returnData(getHttpClient().getBeIdAllData(getType().getId(), getReaderId(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new BeIdAllData(returnData(getHttpClient().getBeIdAllData(getType().getId(), getReaderId())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws BeIdContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new BeIdAllCertificates(returnData(getHttpClient().getBeIdAllCertificates(getType().getId(), getReaderId(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new BeIdAllCertificates(returnData(getHttpClient().getBeIdAllCertificates(getType().getId(), getReaderId())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public T1cCertificate getRootCertificate(boolean parse) throws BeIdContainerException {
        return super.getRootCertificate(parse);
    }

    @Override
    public T1cCertificate getCitizenCertificate(boolean parse) throws BeIdContainerException {
        try {
            return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getCitizenCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve citizen certificate from container", ex);
        }
    }

    @Override
    public T1cCertificate getAuthenticationCertificate(boolean parse) throws BeIdContainerException {
        return super.getAuthenticationCertificate(parse);
    }

    @Override
    public T1cCertificate getNonRepudiationCertificate(boolean parse) throws BeIdContainerException {
        return super.getNonRepudiationCertificate(parse);
    }

    @Override
    public T1cCertificate getRrnCertificate(boolean parse) throws BeIdContainerException {
        try {
            return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getRrnCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.beIdContainerException("Could not retrieve RRN certificate from container", ex);
        }
    }
}