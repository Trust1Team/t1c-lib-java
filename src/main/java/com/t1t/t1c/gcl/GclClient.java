package com.t1t.t1c.gcl;

import com.t1t.t1c.core.GclContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclRestClient;
import com.t1t.t1c.core.GclStatus;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclAdminClientException;
import com.t1t.t1c.exceptions.GclClientException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.rest.RestExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GclClient implements IGclClient {

    private static final Logger log = LoggerFactory.getLogger(GclClient.class);

    private GclRestClient httpClient;

    public GclClient(GclRestClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public GclStatus getInfo() throws GclClientException {
        try {
            return RestExecutor.returnData(httpClient.getV1Status());
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve info", ex);
        }
    }

    @Override
    public List<GclContainer> getContainers() throws GclClientException {
        try {
            return RestExecutor.returnData(httpClient.getContainers());
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve containers", ex);
        }
    }

    @Override
    public GclReader getReader(String readerId) throws GclClientException {
        try {
            return RestExecutor.returnData(httpClient.getCardReader(readerId));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve reader", ex);
        }
    }

    @Override
    public List<GclReader> getReaders() throws GclClientException {
        try {
            return RestExecutor.returnData(httpClient.getCardReaders());
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve readers", ex);
        }
    }

    @Override
    public List<GclReader> getReadersWithInsertedCard() throws GclClientException {
        try {
            return RestExecutor.returnData(httpClient.getCardInsertedReaders(true));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve readers with inserted card", ex);
        }
    }

    @Override
    public List<GclReader> getReadersWithoutInsertedCard() throws GclClientException {
        try {
            return RestExecutor.returnData(httpClient.getCardInsertedReaders(false));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not retrieve readers without inserted card", ex);
        }
    }

    /*@Override
    public boolean getConsent(GclConsent consent) {
        try {
            return isCallSuccessful(executeCall(httpClient.getConsent(consent)));
        } catch (RestException ex) {
            throw ExceptionFactory.gclClientException("Could not get consent from GCL", ex);
        }
    }*/

    @Override
    public String getPublicKey() throws GclAdminClientException {
        return null;
    }
}