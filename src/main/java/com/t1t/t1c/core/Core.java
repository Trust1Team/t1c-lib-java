package com.t1t.t1c.core;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.gcl.GclService;
import com.t1t.t1c.gcl.IGclClient;
import com.t1t.t1c.model.rest.GclCard;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michallispashidis on 31/10/2017.
 */
public class Core extends AbstractCore {

    private static final int DEFAULT_POLLING_INTERVAL = 30;

    public Core(LibConfig config) {
        super(config);
    }

    @Override
    protected boolean activate() {
        return GclService.getGclAdminClient().activate();
    }

    @Override
    protected String getPubKey() {
        return GclService.getGclAdminClient().getPublicKey();
    }

    @Override
    protected GclStatus getInfo() {
        return GclService.getGclClient().getInfo();
    }

    @Override
    protected List<GclContainer> getContainers() {
        return GclService.getGclClient().getContainers();
    }

    @Override
    protected GclReader pollCardInserted(Integer pollIntervalInSeconds) throws InterruptedException {
        List<GclReader> readers = pollReadersWithCards(pollIntervalInSeconds);
        return readers.get(0);
    }

    @Override
    protected List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws InterruptedException {
        List<GclReader> readers = new ArrayList<>();
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers)) {
            readers = GclService.getGclClient().getReadersWithInsertedCard();
            if (readers == null) {
                throw ExceptionFactory.gclClientException("GCL not found");
            }
            if (readers.isEmpty()) {
                Thread.sleep(pollInterval);
            }
        }
        return readers;
    }

    @Override
    protected List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws InterruptedException {
        List<GclReader> readers = new ArrayList<>();
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers)) {
            readers = GclService.getGclClient().getReaders();
            if (readers == null) {
                throw ExceptionFactory.gclClientException("GCL not found");
            }
            if (readers.isEmpty()) {
                Thread.sleep(pollInterval);
            }
        }
        return readers;
    }

    @Override
    protected GclReader getReader(String readerId) {
        return GclService.getGclClient().getReader(readerId);
    }

    @Override
    protected List<GclReader> getReaders() {
        return GclService.getGclClient().getReaders();
    }

    @Override
    protected List<GclReader> getReadersWithoutInsertedCard() {
        return GclService.getGclClient().getReadersWithoutInsertedCard();
    }

    @Override
    protected void setPubKey(String publicKey) {
        GclService.getGclAdminClient().setPublicKey(publicKey);
    }

    @Override
    protected String getUrl() {
        return config.getGclClientUri();
    }

    private int getPollingIntervalInMillis(Integer pollIntervalInSeconds) {
        return 1000 * (pollIntervalInSeconds != null ? pollIntervalInSeconds : config.getDefaultPollingInterval() != null ? config.getDefaultPollingInterval() : DEFAULT_POLLING_INTERVAL);
    }
}
