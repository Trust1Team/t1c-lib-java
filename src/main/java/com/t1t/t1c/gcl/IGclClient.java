package com.t1t.t1c.gcl;

import com.t1t.t1c.exceptions.GclClientException;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGclClient {

    String getUrl();

    GclStatus getInfo() throws GclClientException;

    List<GclContainer> getContainers() throws GclClientException;

    GclReader getReader(String readerId) throws GclClientException;

    List<GclReader> getReaders() throws GclClientException;

    List<GclReader> getReadersWithInsertedCard() throws GclClientException;

    List<GclReader> getReadersWithoutInsertedCard() throws GclClientException;
}
