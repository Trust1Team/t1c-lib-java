package com.t1t.t1c.containers;

import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface GenericContainer {

    ContainerType getType();

    List<String> getAllDataFilters();

    List<String> getAllCertificateFilters();

    String getTypeId();

    String getReaderId();

    String getPin();

    AllData getAllData(List<String> filterParams) throws GenericContainerException;

    boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;

    String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;

    String sign(GclAuthenticateOrSignData data) throws GenericContainerException;
}
