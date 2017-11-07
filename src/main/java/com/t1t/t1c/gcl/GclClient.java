package com.t1t.t1c.gcl;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.model.rest.GclContainerResponse;
import com.t1t.t1c.model.rest.GclReaderResponse;
import com.t1t.t1c.model.rest.GclStatusResponse;
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
    public GclStatusResponse getInfo() {
        return returnData(getHttpClient().getV1Status());
    }

    @Override
    public List<GclContainerResponse> getContainers() {
        return returnData(getHttpClient().getCointainers());
    }

    @Override
    public GclReaderResponse getReader(String readerId) {
        return returnData(getHttpClient().getCardReader(readerId));
    }

    @Override
    public List<GclReaderResponse> getReaders() {
        return returnData(getHttpClient().getCardReaders());
    }

    @Override
    public List<GclReaderResponse> getReadersWithInsertedCard() {
        return returnData(getHttpClient().getCardInsertedReaders(true));
    }

    @Override
    public List<GclReaderResponse> getReadersWithoutInsertedCard() {
        return returnData(getHttpClient().getCardInsertedReaders(false));
    }
}