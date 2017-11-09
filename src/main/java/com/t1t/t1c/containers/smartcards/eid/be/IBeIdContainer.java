package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.be.exceptions.BeIdContainerException;
import com.t1t.t1c.model.rest.GclBeIdAddress;
import com.t1t.t1c.model.rest.GclBeIdRn;
import com.t1t.t1c.model.rest.T1cCertificate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IBeIdContainer extends GenericContainer {

    GclBeIdRn getRnData() throws BeIdContainerException;

    GclBeIdAddress getAddress() throws BeIdContainerException;

    String getPicture() throws BeIdContainerException;

    T1cCertificate getRootCertificate(boolean parse) throws BeIdContainerException;

    T1cCertificate getCitizenCertificate(boolean parse) throws BeIdContainerException;

    T1cCertificate getAuthenticationCertificate(boolean parse) throws BeIdContainerException;

    T1cCertificate getNonRepudiationCertificate(boolean parse) throws BeIdContainerException;

    T1cCertificate getRrnCertificate(boolean parse) throws BeIdContainerException;
}
