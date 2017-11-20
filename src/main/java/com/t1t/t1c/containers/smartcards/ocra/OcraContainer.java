package com.t1t.t1c.containers.smartcards.ocra;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.ocra.exceptions.OcraContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclOcraChallengeData;
import com.t1t.t1c.model.rest.GclVerifyPinRequest;
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
public class OcraContainer extends AbstractContainer implements IOcraContainer {

    private static final Logger log = LoggerFactory.getLogger(OcraContainer.class);

    public OcraContainer(String readerId, ContainerRestClient httpClient) {
        super(readerId, ContainerType.OCRA, httpClient);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return returnData(getHttpClient().getOcraAllData(getType().getId(), getReaderId(), createFilterParams(filterParams)));
            } else {
                return returnData(getHttpClient().getOcraAllData(getType().getId(), getReaderId()));
            }
        } catch (RestException ex) {
            throw ExceptionFactory.ocraContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("OCRA Container does not support retrieving certificates");
    }

    @Override
    public String getCounter(String... pin) throws OcraContainerException {
        try {
            String pinToUse = pin != null && pin.length > 0 ? pin[0] : null;
            GclVerifyPinRequest request = null;
            if (StringUtils.isNotEmpty(pinToUse)) {
                request = new GclVerifyPinRequest().withPin(pinToUse);
            }
            return returnData(getHttpClient().getOcraReadCounter(getTypeId(), getReaderId(), request));
        } catch (RestException ex) {
            checkPinExceptionMessage(ex);
            throw ExceptionFactory.ocraContainerException("Could not retrieve counter from container", ex);
        }
    }

    @Override
    public String challenge(GclOcraChallengeData data) throws OcraContainerException {
        try {
            return returnData(getHttpClient().ocraChallenge(getTypeId(), getReaderId(), data));
        } catch (RestException ex) {
            checkPinExceptionMessage(ex);
            throw ExceptionFactory.ocraContainerException("Could not get challenge from container", ex);
        }
    }
}