package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.AbstractContainer;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.rest.ContainerRestClient;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PtEIdContainer extends AbstractContainer implements IPtEIdContainer {


    public PtEIdContainer(LibConfig config, String readerId, ContainerRestClient httpClient) {
        super(config, readerId, ContainerType.PT, httpClient);
    }

    @Override
    public AllData getAllData(List<String> filterParams) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams) throws GenericContainerException {
        return null;
    }
}