package com.t1t.t1c.core;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;

import java.util.List;

/**
 * Created by michallispashidis on 31/10/2017.
 */
public abstract class AbstractCore {

    protected LibConfig config;

    public AbstractCore(LibConfig config) {
        if (config == null) {
            throw ExceptionFactory.configException("Configuration is null");
        }
        this.config = config;
    }

    protected PlatformInfo getPlatformInfo() {
        return new PlatformInfo();
    }

    protected String getVersion() {
        return config.getVersion();
    }

    protected abstract boolean activate();
    protected abstract String getPubKey();
    protected abstract GclStatus getInfo();
    protected abstract List<GclContainer> getContainers();
    protected abstract GclReader pollCardInserted(Integer pollIntervalInSeconds) throws InterruptedException;
    protected abstract List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws InterruptedException;
    protected abstract List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws InterruptedException;
    protected abstract GclReader getReader(String readerId);
    protected abstract List<GclReader> getReaders();
    protected abstract List<GclReader> getReadersWithoutInsertedCard();
    protected abstract void setPubKey(String publicKey);
    protected abstract String getUrl();
}
