package com.t1t.t1c.containers.smartcards.pki.luxtrust;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.exceptions.LuxTrustContainerException;
import com.t1t.t1c.model.rest.T1cCertificate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ILuxTrustContainer extends GenericContainer {

    boolean isLuxTrustActivated() throws LuxTrustContainerException;

    T1cCertificate getRootCertificate(boolean parse) throws LuxTrustContainerException;

    T1cCertificate getAuthenticationCertificate(boolean parse) throws LuxTrustContainerException;

    T1cCertificate getSigningCertificate(boolean parse) throws LuxTrustContainerException;

}
