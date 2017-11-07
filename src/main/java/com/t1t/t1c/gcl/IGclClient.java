package com.t1t.t1c.gcl;

import com.t1t.t1c.model.rest.GclContainerResponse;
import com.t1t.t1c.model.rest.GclReaderResponse;
import com.t1t.t1c.model.rest.GclStatusResponse;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGclClient {

    String getUrl();

    GclStatusResponse getInfo();

    List<GclContainerResponse> getContainers();

    GclReaderResponse getReader(String readerId);

    List<GclReaderResponse> getReaders();

    List<GclReaderResponse> getReadersWithInsertedCard();

    List<GclReaderResponse> getReadersWithoutInsertedCard();
}
