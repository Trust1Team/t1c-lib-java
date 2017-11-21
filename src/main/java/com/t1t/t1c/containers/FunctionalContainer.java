package com.t1t.t1c.containers;

/**
 * @Author Michallis Pashidis
 * @Since 2017
 */
public abstract class FunctionalContainer <T extends FunctionalContainer> implements GclContainer{
    /*Instantiation*/
    public FunctionalContainer(){createInstance();}
    protected abstract T createInstance();

}
