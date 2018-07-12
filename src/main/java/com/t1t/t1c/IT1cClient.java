package com.t1t.t1c;

import com.t1t.t1c.containers.ContainerVersion;
import com.t1t.t1c.containers.IGenericContainer;
import com.t1t.t1c.containers.functional.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.ModuleConfiguration;
import com.t1t.t1c.containers.smartcards.pkcs11.Pkcs11Container;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
import com.t1t.t1c.core.GclPace;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.ICore;
import com.t1t.t1c.ds.IDsClient;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.ocv.IOcvClient;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IT1cClient {
    /*General*/

    /**
     * Get the GCL Core client. The core client contains administrative methods for the GCL as well as card-reader polling
     * @return the core client
     */
    ICore getCore();

    /**
     * Get the connection factory
     * @return the connection factory
     */
    ConnectionFactory getConnectionFactory();

    /*Clients*/

    /**
     * Returns a client for the Trust1Connector Distribution Service
     * @return
     */
    IDsClient getDsClient();

    /**
     * Returns a client for the Open Certificate Validation service. Requires a valid API key/JWT to address
     * @return the OCV client
     */
    IOcvClient getOcvClient();

    /*Containers*/

    /**
     * Get a  generic container. Functionalities are limited compared to when a container type is explicitly defined
     * @param reader the card reader
     * @param pace object containing credentials to unlock the PACE layer (can be PUK, PIN, MRZ or CAN)
     * @return the generic container
     */
    IGenericContainer getGenericContainer(GclReader reader, GclPace pace);

    /**
     * Get a  generic container. Functionalities are limited compared to when a container type is explicitly defined
     * @param reader the card reader
     * @return the generic container
     */
    IGenericContainer getGenericContainer(GclReader reader);

    /**
     * Returns a container client for the Belgian eID
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    BeIdContainer getBeIdContainer(GclReader reader, String version);

    /**
     * Returns a container client for the Luxembourgish eID
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @param pace object containing credentials to unlock the PACE layer. LuxId requires a CAN or PIN value
     * @return the container client
     */
    LuxIdContainer getLuxIdContainer(GclReader reader, String version, GclPace pace);

    /**
     * Returns a container client for the LuxTrust smartcard
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    LuxTrustContainer getLuxTrustContainer(GclReader reader, String version);

    /**
     * Returns a container client for the Spanish DNIE eID
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    DnieContainer getDnieContainer(GclReader reader, String version);

    /**
     * Returns a container client for EMV cards
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    EmvContainer getEmvContainer(GclReader reader, String version);

    /**
     * Returns a container client for MOBIB cards
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    MobibContainer getMobibContainer(GclReader reader, String version);

    /**
     * Returns a container client for OCRA
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    OcraContainer getOcraContainer(GclReader reader, String version);

    /**
     * Returns a container client for Aventra cards
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    AventraContainer getAventraContainer(GclReader reader, String version);

    /**
     * Returns a container client for Oberthur cards
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    OberthurContainer getOberthurContainer(GclReader reader, String version);

    /**
     * Returns a container client for PIV cards
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    PivContainer getPivContainer(GclReader reader, String version, String pacePin);

    /**
     * Returns a container client for Portuguese eID cards
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    PtEIdContainer getPtIdContainer(GclReader reader, String version);

    /**
     * Returns a container client for PKCS11 cards/tokens/keys
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    Pkcs11Container getPkcs11Container(GclReader reader, String version);

    /**
     * Returns a container client for EMV cards
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @param configuration the configuration to use, specifying the drivers
     * @return the container client
     */
    Pkcs11Container getPkcs11Container(GclReader reader, String version, ModuleConfiguration configuration);

    /*Functional containers*/
    /**
     * Returns a container client for the reader API
     * @param reader the card-reader containing the card
     * @param version the requested container version
     * @return the container client
     */
    ReaderApiContainer getReaderApiContainer(GclReader reader, String version);

    /*DS Functionality*/

    /**
     * Get a download link for the system-appropriate Trust1Connector installer.
     * @return the install link
     */
    String getDownloadLink();

    /**
     * Get a list of card-readers containing a card that is capable of authentication.
     * @return list of readers
     */
    List<GclReader> getAuthenticateCapableReaders();

    /**
     * Get a list of card-readers containing a card that is capable of signing.
     * @return list of readers
     */
    List<GclReader> getSignCapableReaders();

    /**
     * Get a list of card-readers containing a card that is capable of PIN verification.
     * @return list of readers
     */
    List<GclReader> getPinVerificationCapableReaders();

    /**
     * Get a list of container versions that are available for the current application (determined by the Distribution
     * Service response).
     * @return list of available container versions.
     */
    List<ContainerVersion> getAvailableContainerVersions();

    /**
     * Get a list of container versions that are installed locally. Some of these versions may not be available in the\
     * current application context.
     * @return list of installed container versions.
     */
    List<ContainerVersion> getInstalledContainerVersions();
}
