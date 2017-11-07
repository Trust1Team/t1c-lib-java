package com.t1t.t1c.gcl;

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

    GclStatus getInfo();

    List<GclContainer> getContainers();

    GclReader getReader(String readerId);

    List<GclReader> getReaders();

    List<GclReader> getReadersWithInsertedCard();

    List<GclReader> getReadersWithoutInsertedCard();
}
