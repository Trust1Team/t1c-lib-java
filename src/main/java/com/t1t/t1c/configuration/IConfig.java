package com.t1t.t1c.configuration;

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
    String LIB_API_KEY = "t1c.apikey";
    String LIB_GCL_CLIENT_URI = "t1c.gcl_uri";
    String LIB_GATEWAY_URI = "t1c.gateway_uri";
    String LIB_DS_CONTEXT_PATH = "t1c.ds_context_path";
    String LIB_DS_DOWNLOAD_CONTEXT_PATH = "t1c.ds_download_context_path";
}
