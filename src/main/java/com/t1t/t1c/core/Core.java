package com.t1t.t1c.core;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.gcl.FactoryService;
import com.t1t.t1c.model.DsPublicKeyEncoding;
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

    private static final int DEFAULT_POLLING_INTERVAL = 5;
    private static final int DEFAULT_POLLING_TIMEOUT = 30;

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
    public String getPubKey(DsPublicKeyEncoding encoding) {
        return FactoryService.getGclAdminClient().getPublicKey(encoding);
    }

    @Override
    public void setPubKey(String publicKey) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(publicKey), "Public key must be provided");
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
    public GclReader pollCardInserted() throws InterruptedException {
        return pollCardInserted(null, null);
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds) throws InterruptedException {
        return pollCardInserted(pollIntervalInSeconds, null);
    }

    @Override
    public GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws InterruptedException {
        List<GclReader> readers = pollReadersWithCards(pollIntervalInSeconds, pollTimeoutInSeconds);
        return CollectionUtils.isNotEmpty(readers) ? readers.get(0) : null;
    }

    @Override
    public List<GclReader> pollReadersWithCards() throws InterruptedException {
        return pollReadersWithCards(null, null);
    }

    @Override
    public List<GclReader> pollReaders() throws InterruptedException {
        return pollReaders(null, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws InterruptedException {
        return pollReadersWithCards(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws InterruptedException {
        return pollReaders(pollIntervalInSeconds, null);
    }

    @Override
    public List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws InterruptedException {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = FactoryService.getGclClient().getReadersWithInsertedCard();
            if (readers == null) {
                throw ExceptionFactory.gclClientException("GCL not found");
            }
            if (readers.isEmpty()) {
                Thread.sleep(pollInterval);
                totalTime += pollInterval;
            }
        }
        return readers;
    }

    @Override
    public List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds) throws InterruptedException {
        List<GclReader> readers = new ArrayList<>();
        int totalTime = 0;
        int pollTimeout = getPollingTimeoutInMillis(pollTimeoutInSeconds);
        int pollInterval = getPollingIntervalInMillis(pollIntervalInSeconds);
        while (CollectionUtils.isEmpty(readers) && totalTime < pollTimeout) {
            readers = FactoryService.getGclClient().getReaders();
            if (readers == null) {
                throw ExceptionFactory.gclClientException("GCL not found");
            }
            if (readers.isEmpty()) {
                Thread.sleep(pollInterval);
                totalTime += pollInterval;
            }
        }
        return readers;
    }

    @Override
    public GclReader getReader(String readerId) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(readerId), "Reader ID is required");
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
        Preconditions.checkArgument(StringUtils.isNotEmpty(codeWord), "Code word is required!");
        return FactoryService.getGclClient().getConsent(new GclConsent().withTitle(title).withText(codeWord).withDurationInDays(durationInDays));
    }

    private int getPollingIntervalInMillis(Integer pollIntervalInSeconds) {
        Preconditions.checkArgument(pollIntervalInSeconds == null || pollIntervalInSeconds > 0, "Polling interval must be greater than 0");
        return 1000 * (pollIntervalInSeconds != null ? pollIntervalInSeconds : config.getDefaultPollingIntervalInSeconds() != null ? config.getDefaultPollingIntervalInSeconds() : DEFAULT_POLLING_INTERVAL);
    }

    private int getPollingTimeoutInMillis(Integer pollTimeoutInSeconds) {
        Preconditions.checkArgument(pollTimeoutInSeconds == null || (pollTimeoutInSeconds > 0 && pollTimeoutInSeconds < 60), "Polling timout must be a value between 0 & 60");
        return 1000 * (pollTimeoutInSeconds != null ? pollTimeoutInSeconds : config.getDefaultPollingIntervalInSeconds() != null ? config.getDefaultPollingTimeoutInSeconds() : DEFAULT_POLLING_TIMEOUT);
    }
}
