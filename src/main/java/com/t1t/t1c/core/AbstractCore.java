package com.t1t.t1c.core;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.model.PlatformInfo;

/**
 * Created by michallispashidis on 31/10/2017.
 */
public abstract class AbstractCore implements ICore {

    protected LibConfig config;

    public AbstractCore(LibConfig config) {
        if (config == null) {
            throw ExceptionFactory.configException("Configuration is null");
        }
        this.config = config;
    }

    @Override
    public PlatformInfo getPlatformInfo() {
        return new PlatformInfo();
    }

    @Override
    public String getVersion() {
        return config.getVersion();
    }

}
