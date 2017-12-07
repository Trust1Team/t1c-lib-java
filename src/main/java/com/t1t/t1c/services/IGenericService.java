package com.t1t.t1c.services;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllData;

import java.util.List;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public interface IGenericService {
    /**
     * Get eligible containers for target token.
     *
     * @param readerId
     * @return
     */
    List<ContainerType> getEligibleContainersTypesFor(String readerId);

    /**
     * Get generic containers for target token.
     *
     * @param readerId
     * @return
     */
    List<GenericContainer> getGenericContainersFor(String readerId);

    /**
     * Get first generic container - default - for target token.
     *
     * @param readerId
     * @return
     */
    GenericContainer getGenericContainerFor(String readerId);

    /**
     * Return all data available on the token.
     * The exposed filters can be used in order to refine the response.
     * Tokens with NFC interface, requires PIN (ex. PACE) in order to access data.
     *
     * @param readerId
     * @param pin
     * @param filterParams
     * @return
     */
    AllData dumpData(String readerId, String pin, List<String> filterParams);

    AllData dumpData(String readerId, List<String> filterParams);

    /**
     * Authenticate using target token.
     *
     * @param readerId
     * @param data
     * @param pin
     * @return
     */
    String authenticate(String readerId, GclAuthenticateOrSignData data, String... pin);

    /**
     * Digitally sign using target token.
     *
     * @param readerId
     * @param data
     * @param pin
     * @return
     */
    String sign(String readerId, GclAuthenticateOrSignData data, String... pin);

    /**
     * PIN verification using target token.
     *
     * @param readerId
     * @param pin
     * @return
     * @throws VerifyPinException
     */
    Boolean verifyPin(String readerId, String... pin) throws VerifyPinException;
}
