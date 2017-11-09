package com.t1t.t1c.services;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.model.rest.GclReader;

import java.util.List;

/**
 * Created by michallispashidis on 05/11/2017.
 */
public interface IGenericService {

    ContainerType getContainerTypeFor(String readerId);

    <T extends GenericContainer> T getContainerFor(String readerId, ContainerType type, Class<T> containerIFace, String... pin);

    String getDownloadLink();

    AllData dumpData(String readerId, String pin, String... filterParams);

    List<GclReader> getAuthenticationCapableReaders();

    List<GclReader> getSignCapableReaders();

    List<GclReader> getPinVerificationCapableReaders();

    String authenticate(String readerId, GclAuthenticateOrSignData data);

    String sign(String readerId, GclAuthenticateOrSignData data);

    boolean verifyPin(String readerId, String... pin);
}
