package com.t1t.t1c.containers.smartcards.pki.oberthur;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for Oberthur Cosmo Container
 */
public interface GclOberthurRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

}
