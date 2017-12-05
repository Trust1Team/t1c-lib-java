package com.t1t.t1c.containers;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.core.GclReader;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public abstract class FunctionalContainer <T extends FunctionalContainer, U> implements IGclContainer {

    protected GclReader reader;
    protected U httpClient;
    protected transient String pin;
    protected LibConfig config;
    protected ContainerType type;
    /*Instantiation*/
    public FunctionalContainer() {}
    public FunctionalContainer(LibConfig config, GclReader reader, U httpClient, String pin) { createInstance(config, reader, httpClient, pin); }
    public abstract T createInstance(LibConfig config, GclReader reader, U httpClient, String pin);

}
