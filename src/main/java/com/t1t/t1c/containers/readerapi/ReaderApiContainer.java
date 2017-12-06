package com.t1t.t1c.containers.readerapi;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.FunctionalContainer;
import com.t1t.t1c.core.GclReader;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class ReaderApiContainer extends FunctionalContainer<ReaderApiContainer, GclReaderApiRestClient> {

    @Override
    public ReaderApiContainer createInstance(LibConfig config, GclReader reader, GclReaderApiRestClient httpClient, String pin) {
        return null;
    }

    @Override
    public ContainerType getType() {
        return null;
    }

    @Override
    public String getTypeId() {
        return null;
    }
}
