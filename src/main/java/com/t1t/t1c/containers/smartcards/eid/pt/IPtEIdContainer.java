package com.t1t.t1c.containers.smartcards.eid.pt;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.exceptions.PtIdContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.model.rest.GclPtIdData;
import com.t1t.t1c.model.rest.T1cCertificate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IPtEIdContainer extends GenericContainer {

    GclPtIdData getIdData() throws PtIdContainerException;

    GclPtIdData getIdDataWithOutPhoto() throws PtIdContainerException;

    String getPhoto() throws PtIdContainerException;

    T1cCertificate getRootCertificate(boolean parse) throws PtIdContainerException;

    T1cCertificate getRootAuthenticationCertificate(boolean parse) throws PtIdContainerException;

    T1cCertificate getRootNonRepudiationCertificate(boolean parse) throws PtIdContainerException;

    T1cCertificate getAuthenticationCertificate(boolean parse) throws PtIdContainerException;

    T1cCertificate getNonRepudiationCertificate(boolean parse) throws PtIdContainerException;
}
