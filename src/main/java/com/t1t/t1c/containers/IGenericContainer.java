package com.t1t.t1c.containers;

import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;

import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IGenericContainer<V extends AllData, W extends AllCertificates> extends IGclContainer {
    /**
     * Returns the implementation class of the AllData interface
     *
     * @return the implementation class
     */
    Class<V> getAllDataClass();

    /**
     * Returns the implementation class of the AllCertificates interface
     *
     * @return the implementation class
     */
    Class<W> getAllCertificatesClass();
    /*Data Related*/

    /**
     * Returns the list of available data filters for the getAllData() method
     *
     * @return a List of available filters
     */
    List<String> getAllDataFilters();

    /**
     * Returns the list of available data filters for the getAllCertificates() method
     *
     * @return a List of available filters
     */
    List<String> getAllCertificateFilters();

    /**
     * Dumps all the data on the card
     *
     * @return AllData
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    V getAllData() throws RestException, NoConsentException;

    /**
     * Dumps all the data on the card
     *
     * @param parseCertificates toggle to parse the certificates value
     * @return AllData
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    V getAllData(Boolean parseCertificates) throws RestException, NoConsentException;

    /**
     * Dumps all the data on the card
     *
     * @param filterParams      filter parameters to use
     * @return AllData
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    V getAllData(List<String> filterParams) throws RestException, NoConsentException;

    /**
     * Dumps all the data on the card
     *
     * @param filterParams      filter parameters to use
     * @param parseCertificates toggle to parse the certificates value
     * @return AllData
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    V getAllData(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException;

    /**
     * Dumps all the certificates on the card
     *
     * @return AllCertificates
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    W getAllCertificates() throws RestException, NoConsentException;

    /**
     * Dumps all the certificates on the card
     *
     * @param parseCertificates toggle to parse the certificates value
     * @return AllCertificates
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    W getAllCertificates(Boolean parseCertificates) throws RestException, NoConsentException;

    /**
     * Dumps all the certificates on the card
     *
     * @param filterParams      filter parameters to use
     * @return AllCertificates
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    W getAllCertificates(List<String> filterParams) throws RestException, NoConsentException;

    /**
     * Dumps all the certificates on the card
     *
     * @param filterParams      filter parameters to use
     * @param parseCertificates toggle to parse the certificates value
     * @return AllCertificates
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    W getAllCertificates(List<String> filterParams, Boolean parseCertificates) throws RestException, NoConsentException;

    /*Token Functionality*/

    /**
     * Verify a PIN
     *
     * @param pin the PIN to verify
     * @return true if PIN is correct
     * @throws GenericContainerException: on communication failure
     * @throws VerifyPinException:        if PIN is incorrect
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    Boolean verifyPin(String pin) throws VerifyPinException, RestException, NoConsentException;

    /**
     * Verify a PIN
     *
     * @return true if PIN is correct
     * @throws GenericContainerException: on communication failure
     * @throws VerifyPinException:        if PIN is incorrect
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    Boolean verifyPin() throws VerifyPinException, RestException, NoConsentException;

    /**
     * Returns a list of digest algorithms supported by the container for signing
     *
     * @return digest algorithms
     * @throws RestException
     * @throws NoConsentException
     */
    List<DigestAlgorithm> getAvailableAuthenticationAlgorithms() throws RestException, NoConsentException;

    /**
     * Signs a hash with the card's authentication certificate
     *
     * @param data the authentication payload
     * @return a String representation of the signed hash
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    String authenticate(String data, DigestAlgorithm algo) throws VerifyPinException, RestException, NoConsentException;

    /**
     * Signs a hash with the card's authentication certificate
     *
     * @param data the authentication payload
     * @return a String representation of the signed hash
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    String authenticate(String data, DigestAlgorithm algo, String pin) throws VerifyPinException, RestException, NoConsentException;

    /**
     * Returns a list of digest algorithms supported by the container for signing
     *
     * @return digest algorithms
     * @throws RestException
     * @throws NoConsentException
     */
    List<DigestAlgorithm> getAvailableSignAlgorithms() throws RestException, NoConsentException;

    /**
     * Signs a hash with the card's signing (non-repudiation) certificate
     *
     * @param data the signing payload
     * @return a String representation of the signed hash
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    String sign(String data, DigestAlgorithm algo) throws VerifyPinException, RestException, NoConsentException;

    /**
     * Signs a hash with the card's signing (non-repudiation) certificate
     *
     * @param data the signing payload
     * @return a String representation of the signed hash
     * @throws GenericContainerException: on failure
     * @throws NoConsentException:        if consent is required but has not been granted (or is expired)
     */
    String sign(String data, DigestAlgorithm algo, String pin) throws VerifyPinException, RestException, NoConsentException;

    /**
     * Returns the certificate chain used for signing, if present
     *
     * @return An ordered map with the leaf certificate with key 0
     * @throws VerifyPinException: If a wrong PIN is provided
     * @throws RestException:      On communication failure with the GCL
     * @throws NoConsentException: if consent is required but has not been granted (or is expired)
     */
    Map<Integer, T1cCertificate> getSigningCertificateChain() throws VerifyPinException, RestException, NoConsentException;

    /**
     * Returns the certificate chain used for authentication, if present
     *
     * @return An ordered map with the leaf certificate with key 0
     * @throws VerifyPinException: If a wrong PIN is provided
     * @throws RestException:      On communication failure with the GCL
     * @throws NoConsentException: if consent is required but has not been granted (or is expired)
     */
    Map<Integer, T1cCertificate> getAuthenticationCertificateChain() throws VerifyPinException, RestException, NoConsentException;

    /**
     * Dumps the available container data
     *
     * @return the container data
     * @throws RestException:                 on communication failure
     * @throws UnsupportedOperationException: if the container has no data to dump
     * @throws NoConsentException:            if consent is required but has not been granted (or is expired)
     */
    ContainerData dumpData() throws RestException, UnsupportedOperationException, NoConsentException;

    /**
     * Dumps the available container data
     *
     * @param pin A PIN
     * @return the container data
     * @throws RestException:                 on communication failure
     * @throws UnsupportedOperationException: if the container has no data to dump
     * @throws NoConsentException:            if consent is required but has not been granted (or is expired)
     */
    ContainerData dumpData(String pin) throws RestException, UnsupportedOperationException, NoConsentException;
}
