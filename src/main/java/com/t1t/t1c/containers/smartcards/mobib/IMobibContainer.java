package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.containers.smartcards.mobib.exceptions.MobibContainerException;
import com.t1t.t1c.model.rest.GclMobibCardIssuing;
import com.t1t.t1c.model.rest.GclMobibContract;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IMobibContainer extends GenericContainer {

    boolean isActivated() throws MobibContainerException;

    List<GclMobibContract> getContracts() throws MobibContainerException;

    GclMobibCardIssuing getCardIssuing() throws MobibContainerException;

    String getPicture() throws MobibContainerException;
}
