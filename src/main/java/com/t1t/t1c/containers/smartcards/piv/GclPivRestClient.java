package com.t1t.t1c.containers.smartcards.piv;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 * Specific GCL interface for PIV (Personal Identity Verification) Container
 */
public interface GclPivRestClient {

    String CERTIFICATES_PATH = "/certificates";
    String CONTAINER_AND_READER_CONTEXT_PATH = "{containerId}/{reader}";

}
