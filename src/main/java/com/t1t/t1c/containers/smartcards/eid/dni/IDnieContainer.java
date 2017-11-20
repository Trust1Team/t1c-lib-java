package com.t1t.t1c.containers.smartcards.eid.dni;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.exceptions.PtIdContainerException;
import com.t1t.t1c.model.rest.GclDnieInfo;
import com.t1t.t1c.model.rest.T1cCertificate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IDnieContainer extends GenericContainer {

    GclDnieInfo getInfo() throws PtIdContainerException;

    T1cCertificate getIntermediateCertificate(boolean parse) throws PtIdContainerException;

    T1cCertificate getAuthenticationCertificate(boolean parse) throws PtIdContainerException;

    T1cCertificate getSigningCertificate(boolean parse) throws PtIdContainerException;

}
