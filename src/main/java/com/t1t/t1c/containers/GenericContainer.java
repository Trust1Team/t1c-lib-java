package com.t1t.t1c.containers;

import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface GenericContainer {

    ContainerType getType();

    String getTypeId();

    String getReaderId();

    String getPin();

    AllData getAllData(String... filterParams) throws GenericContainerException;

    boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;

    String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;

    String sign(GclAuthenticateOrSignData data) throws GenericContainerException;
}
