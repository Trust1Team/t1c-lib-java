package com.t1t.t1c;

/**
 * Created by michallispashidis on 14/09/15.
 * Contains identifiers the services look for in the application.conf file.
 * Typesafe configuration concept.
 */
public interface IConfig {
    //Project specific
    String PROP_BUILD_DATE = "date";
    String PROP_FILE_VERSION = "version";

    //Lib specific
    String LIB_ENVIRONMENT = "t1c.environment";
    String LIB_GCL_CLIENT_URI = "t1c.uri";
}
