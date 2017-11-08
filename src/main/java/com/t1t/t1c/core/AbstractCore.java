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

    public PlatformInfo getPlatformInfo() {
        return new PlatformInfo();
    }

    public String getVersion() {
        return config.getVersion();
    }

    public abstract boolean activate();

    public abstract String getPubKey();

    public abstract void setPubKey(String publicKey);

    public abstract GclStatus getInfo();

    public abstract List<GclContainer> getContainers();

    public abstract GclReader pollCardInserted(Integer pollIntervalInSeconds) throws InterruptedException;

    public abstract List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds) throws InterruptedException;

    public abstract List<GclReader> pollReaders(Integer pollIntervalInSeconds) throws InterruptedException;

    public abstract GclReader getReader(String readerId);

    public abstract List<GclReader> getReaders();

    public abstract List<GclReader> getReadersWithoutInsertedCard();

    public abstract String getUrl();
}
