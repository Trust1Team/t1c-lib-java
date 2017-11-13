package com.t1t.t1c.containers.smartcards.eid.esp;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.exceptions.DnieContainerException;
import com.t1t.t1c.model.rest.GclDnieInfo;
import com.t1t.t1c.model.rest.T1cCertificate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IDnieContainer extends GenericContainer {

    GclDnieInfo getInfo() throws DnieContainerException;

    T1cCertificate getIntermediateCertificate(boolean parse) throws DnieContainerException;

    T1cCertificate getAuthenticationCertificate(boolean parse) throws DnieContainerException;

    T1cCertificate getSigningCertificate(boolean parse) throws DnieContainerException;

}
