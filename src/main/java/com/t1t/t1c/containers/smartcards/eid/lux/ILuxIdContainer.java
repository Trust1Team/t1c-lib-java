package com.t1t.t1c.containers.smartcards.eid.lux;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.exceptions.LuxIdContainerException;
import com.t1t.t1c.model.rest.GclLuxIdBiometric;
import com.t1t.t1c.model.rest.GclLuxIdPicture;
import com.t1t.t1c.model.rest.GclLuxIdSignatureImage;
import com.t1t.t1c.model.rest.T1cCertificate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ILuxIdContainer extends GenericContainer {

    GclLuxIdBiometric getBiometric() throws LuxIdContainerException;

    GclLuxIdPicture getPicture() throws LuxIdContainerException;

    GclLuxIdSignatureImage getSignatureImage() throws LuxIdContainerException;

    List<T1cCertificate> getRootCertificates(boolean parse) throws LuxIdContainerException;

    T1cCertificate getAuthenticationCertificate(boolean parse) throws LuxIdContainerException;

    T1cCertificate getNonRepudiationCertificate(boolean parse) throws LuxIdContainerException;
}
