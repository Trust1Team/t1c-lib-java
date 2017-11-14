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

    GenericContainer getGenericContainerFor(String readerId);

    String getDownloadLink();

    AllData dumpData(String readerId, String pin, List<String> filterParams);

    AllData dumpData(String readerId, List<String> filterParams);

    List<GclReader> getAuthenticationCapableReaders();

    List<GclReader> getSignCapableReaders();

    List<GclReader> getPinVerificationCapableReaders();

    String authenticate(String readerId, GclAuthenticateOrSignData data, String... pin);

    String sign(String readerId, GclAuthenticateOrSignData data, String... pin);

    boolean verifyPin(String readerId, String... pin);
}
