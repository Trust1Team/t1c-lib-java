package com.t1t.t1c.core;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.model.rest.GclConsent;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
    public boolean activate() {
        return FactoryService.getGclAdminClient().activate();
    }

    @Override
    public String getPubKey() {
        return FactoryService.getGclAdminClient().getPublicKey();
    }

    @Override
    public void setPubKey(String publicKey) {
        FactoryService.getGclAdminClient().setPublicKey(publicKey);
    }

    @Override
    public GclStatus getInfo() {
        return FactoryService.getGclClient().getInfo();
    }

    @Override
    public List<GclContainer> getContainers() {
        return FactoryService.getGclClient().getContainers();
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds) throws InterruptedException {
        List<GclReader> readers = pollReadersWithCards(pollIntervalInSeconds);
        return readers.get(0);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws InterruptedException {
        List<GclReader> readers = new ArrayList<>();
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers)) {
            readers = FactoryService.getGclClient().getReadersWithInsertedCard();
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
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws InterruptedException {
        List<GclReader> readers = new ArrayList<>();
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers)) {
            readers = FactoryService.getGclClient().getReaders();
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
    public GclReader getReader(String readerId) {
        return FactoryService.getGclClient().getReader(readerId);
    }

    @Override
    public List<GclReader> getReaders() {
        return FactoryService.getGclClient().getReaders();
    }

    @Override
    public List<GclReader> getReadersWithoutInsertedCard() {
        return FactoryService.getGclClient().getReadersWithoutInsertedCard();
    }

    @Override
    public String getUrl() {
        return config.getGclClientUri();
    }

    @Override
    public boolean getConsent(String title, String codeWord, Integer durationInDays) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(title), "Title is required!");
        Preconditions.checkArgument(StringUtils.isNotEmpty(codeWord), "Code word is required");
        return FactoryService.getGclClient().getConsent(new GclConsent().withTitle(title).withText(codeWord).withDurationInDays(durationInDays));
    }

    private int getPollingIntervalInMillis(Integer pollIntervalInSeconds) {
        return 1000 * (pollIntervalInSeconds != null ? pollIntervalInSeconds : config.getDefaultPollingInterval() != null ? config.getDefaultPollingInterval() : DEFAULT_POLLING_INTERVAL);
    }
}
