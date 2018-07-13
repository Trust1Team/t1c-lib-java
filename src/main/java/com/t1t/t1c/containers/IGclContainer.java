package com.t1t.t1c.containers;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public interface IGclContainer {
    /*Type Related*/
    ContainerType getType();

    String getTypeId();

    String getContainerVersionId();

    String getContainerUrlId();
}
