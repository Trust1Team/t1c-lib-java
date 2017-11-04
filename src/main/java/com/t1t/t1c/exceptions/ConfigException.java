package com.t1t.t1c.exceptions;

/**
 * Created by michallispashidis on 04/11/2017.
 */
public class ConfigException extends AbstractConsumerException {
    public ConfigException() {}

    public ConfigException(String message) {super(message);}

    @Override
    public int getHttpCode() {return 0;}

    @Override
    public int getErrorCode() {return 0;}

    @Override
    public String getMoreInfoUrl() {return null;}
}
