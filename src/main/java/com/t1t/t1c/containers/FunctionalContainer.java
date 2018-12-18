package com.t1t.t1c.containers;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.core.GclReader;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public abstract class FunctionalContainer<T extends FunctionalContainer, U> implements IGclContainer {

    protected GclReader reader;
    protected U httpClient;
    protected transient String pin;
    protected LibConfig config;
    protected ContainerVersion containerVersion;

    public FunctionalContainer(final LibConfig config, final GclReader reader, final String containerVersion, final U httpClient, final String pin) {
        createInstance(config, reader, containerVersion, httpClient, pin);
    }

    public abstract T createInstance(LibConfig config, GclReader reader, String containerVersion, U httpClient, String pin);

    @Override
    public ContainerType getType() {
        return containerVersion.getType();
    }

    @Override
    public String getTypeId() {
        return getType().getId();
    }

    @Override
    public String getContainerVersionId() {
        return containerVersion.getId();
    }

    @Override
    public String getContainerUrlId() {
        return getContainerVersionId().replace("." , "-");
    }
}
