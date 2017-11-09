package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclClientException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.rest.GclConsent;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import com.t1t.t1c.rest.AbstractRestClient;
import com.t1t.t1c.rest.GclRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclClient extends AbstractRestClient<GclRestClient> implements IGclClient {

    private static final Logger log = LoggerFactory.getLogger(GclClient.class);

    private LibConfig config;

    protected GclClient(LibConfig config, GclRestClient httpClient) {
        super(httpClient);
        this.config = config;
    }

    @Override
    public String getUrl() {
        return config.getGclClientUri();
    }

    @Override
    public GclStatus getInfo() throws GclClientException {
        try {
            return returnData(getHttpClient().getV1Status());
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve info", ex);
        }
    }

    @Override
    public List<GclContainer> getContainers() throws GclClientException {
        try {
            return returnData(getHttpClient().getContainers());
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve containers", ex);
        }
    }

    @Override
    public GclReader getReader(String readerId) throws GclClientException {
        try {
            return returnData(getHttpClient().getCardReader(readerId));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve reader", ex);
        }
    }

    @Override
    public List<GclReader> getReaders() throws GclClientException {
        try {
            return returnData(getHttpClient().getCardReaders());
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve readers", ex);
        }
    }

    @Override
    public List<GclReader> getReadersWithInsertedCard() throws GclClientException {
        try {
            return returnData(getHttpClient().getCardInsertedReaders(true));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve readers with inserted card", ex);
        }
    }

    @Override
    public List<GclReader> getReadersWithoutInsertedCard() throws GclClientException {
        try {
            return returnData(getHttpClient().getCardInsertedReaders(false));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve readers without inserted card", ex);
        }
    }

    @Override
    public boolean getConsent(GclConsent consent) {
        try {
            return isCallSuccessful(executeCall(getHttpClient().getConsent(consent)));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not get consent from GCL", ex);
        }
    }
}