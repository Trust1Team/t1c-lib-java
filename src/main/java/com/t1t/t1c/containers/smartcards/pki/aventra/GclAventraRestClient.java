package com.t1t.t1c.containers.smartcards.pki.aventra;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Aventra MyeID Container
 */
public interface GclAventraRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";
}
