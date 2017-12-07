package com.t1t.t1c.containers;

import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.DigestAlgorithm;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGenericContainer<V, W> extends IGclContainer {
    /**
     * Returns the implementation class of the AllData interface
     * @return the implementation class
     */
    Class<V> getAllDataClass();

    /**
     * Returns the implementation class of the AllCertificates interface
     * @return the implementation class
     */
    Class<W> getAllCertificatesClass();
    /*Data Related*/

    /**
     * Returns the list of available data filters for the getAllData() method
     * @return a List of available filters
     */
    List<String> getAllDataFilters();

    /**
     * Returns the list of available data filters for the getAllCertificates() method
     * @return a List of available filters
     */
    List<String> getAllCertificateFilters();

    /**
     * Dumps all the data on the card
     * @return AllData
     * @throws GenericContainerException: on failure
     */
    V getAllData() throws GenericContainerException;

    /**
     * Dumps all the data on the card
     * @param filterParams filter parameters to use (optional)
     * @param parseCertificates toggle to parse the certificates value (optional)
     * @return AllData
     * @throws GenericContainerException: on failure
     */
    V getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;

    /**
     * Dumps all the data on the card
     * @param parseCertificates toggle to parse the certificates value (optional
     * @return AllData
     * @throws GenericContainerException: on failure
     */
    V getAllData(Boolean... parseCertificates) throws GenericContainerException;

    /**
     * Dumps all the certificates on the card
     * @return AllCertificates
     * @throws GenericContainerException: on failure
     */
    W getAllCertificates() throws GenericContainerException;

    /**
     * Dumps all the certificates on the card
     * @param filterParams filter parameters to use (optional)
     * @param parseCertificates toggle to parse the certificates value (optional)
     * @return AllCertificates
     * @throws GenericContainerException: on failure
     */
    W getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;

    /**
     * Dumps all the certificates on the card
     * @param parseCertificates toggle to parse the certificates value (optional)
     * @return AllCertificates
     * @throws GenericContainerException: on failure
     */
    W getAllCertificates(Boolean... parseCertificates) throws GenericContainerException;

    /*Token Functionality*/
    /**
     * Verify a PIN
     * @param pin the PIN to verify (optional if hardware PIN pad is present)
     * @return true if PIN is correct
     * @throws GenericContainerException: on communication failure
     * @throws VerifyPinException: if PIN is incorrect
     */
    Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;

    /**
     * Signs a hash with the card's authentication certificate
     * @param data the authentication payload
     * @return a String representation of the signed hash
     * @throws GenericContainerException: on failure
     */
    String authenticate(String data, DigestAlgorithm algo,  String... pin) throws GenericContainerException;

    /**
     * Signs a hash with the card's signing (non-repudiation) certificate
     * @param data the signing payload
     * @return a String representation of the signed hash
     * @throws GenericContainerException: on failure
     */
    String sign(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException;
}
