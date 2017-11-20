package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.smartcards.mobib.exceptions.MobibContainerException;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclMobibCardIssuing;
import com.t1t.t1c.model.rest.GclMobibContract;
import com.t1t.t1c.rest.ContainerRestClient;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MobibContainer extends AbstractContainer implements IMobibContainer {

    private static final Logger log = LoggerFactory.getLogger(MobibContainer.class);

    public MobibContainer(String readerId, ContainerRestClient httpClient) {
        super(readerId, ContainerType.MOBIB, httpClient);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AllData getAllData(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        try {
            if (CollectionUtils.isNotEmpty(filterParams)) {
                return returnData(getHttpClient().getMobibAllData(getType().getId(), getReaderId(), createFilterParams(filterParams)));
            } else {
                return returnData(getHttpClient().getMobibAllData(getType().getId(), getReaderId()));
            }
        } catch (RestException ex) {
            throw ExceptionFactory.mobibContainerException("Could not retrieve all data from container", ex);
        }
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, boolean... parseCertificates) throws GenericContainerException {
        throw ExceptionFactory.unsupportedOperationException("MOBIB Container does not provide certificates");
    }

    @Override
    public boolean isActivated() throws MobibContainerException {
        try {
            return returnData(getHttpClient().getMobibStatus(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.mobibContainerException("Could not retrieve status", ex);
        }
    }

    @Override
    public List<GclMobibContract> getContracts() throws MobibContainerException {
        try {
            return returnData(getHttpClient().getMobibContracts(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.mobibContainerException("Could not retrieve status", ex);
        }
    }

    @Override
    public GclMobibCardIssuing getCardIssuing() throws MobibContainerException {
        try {
            return returnData(getHttpClient().getMobibCardIssuing(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.mobibContainerException("Could not retrieve status", ex);
        }
    }

    @Override
    public String getPicture() throws MobibContainerException {
        try {
            return returnData(getHttpClient().getMobibPicture(getTypeId(), getReaderId()));
        } catch (RestException ex) {
            throw ExceptionFactory.mobibContainerException("Could not retrieve status", ex);
        }
    }
}