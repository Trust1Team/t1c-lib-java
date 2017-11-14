package com.t1t.t1c.containers.smartcards.eid.esp;

import com.t1t.t1c.containers.AbstractContainer;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.eid.pt.exceptions.PtIdContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclDnieInfo;
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
public class DnieContainer extends AbstractContainer implements IDnieContainer {

    private static final Logger log = LoggerFactory.getLogger(DnieContainer.class);

    public DnieContainer(String readerId, ContainerRestClient httpClient) {
        super(readerId, ContainerType.DNIE, httpClient);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws PtIdContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new DnieAllData(returnData(getHttpClient().getDnieAllData(getType().getId(), getReaderId(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new DnieAllData(returnData(getHttpClient().getDnieAllData(getType().getId(), getReaderId())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws PtIdContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return new DnieAllCertificates(returnData(getHttpClient().getDnieAllCertificates(getType().getId(), getReaderId(), createFilterParams(filterParams))), parseCertificates);
            } else {
                return new DnieAllCertificates(returnData(getHttpClient().getDnieAllCertificates(getType().getId(), getReaderId())), parseCertificates);
            }
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public GclDnieInfo getInfo() throws PtIdContainerException {
        try {
            return returnData(getHttpClient().getDnieInfo(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("Could not retrieve info from container", ex);
        }
    }

    @Override
    public T1cCertificate getIntermediateCertificate(boolean parse) throws PtIdContainerException {
        try {
            return CertificateUtil.createT1cCertificate(returnData(getHttpClient().getIntermediateCertificate(getTypeId(), getReaderId())), parse);
        } catch (RestException ex) {
            throw ExceptionFactory.dnieContainerException("Could not retrieve intermediate certificate from container", ex);
        }
    }

    @Override
    public T1cCertificate getAuthenticationCertificate(boolean parse) throws PtIdContainerException {
        return super.getAuthenticationCertificate(parse);
    }

    @Override
    public T1cCertificate getSigningCertificate(boolean parse) throws PtIdContainerException {
        return super.getSigningCertificate(parse);
    }
}