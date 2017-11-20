package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.exceptions.SafeNetContainerException;
import com.t1t.t1c.model.rest.GclSafeNetInfo;
import com.t1t.t1c.model.rest.GclSafeNetSlot;
import com.t1t.t1c.model.rest.T1cCertificate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ISafeNetContainer extends GenericContainer {

    List<T1cCertificate> getCertificates(Integer slotId, String... pin) throws SafeNetContainerException;

    GclSafeNetInfo getInfo() throws SafeNetContainerException;

    List<GclSafeNetSlot> getSlots() throws SafeNetContainerException;

    List<GclSafeNetSlot> getSlotsWithToken() throws SafeNetContainerException;
}
