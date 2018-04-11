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
    
    //URIs
    
    String LIB_URIS_AUTH = "t1c.uris.authtUri";
    String LIB_URIS_DS = "t1c.uris.dsUri";
    String LIB_URIS_GCL_CLIENT = "t1c.uris.gclClientUri";
    String LIB_URIS_OCV = "t1c.uris.ocvUri";
    String LIB_URIS_PROXYDOMAIN = "t1c.uris.proxyDomain";
    
    //Auth
    
    String LIB_AUTH_API_KEY = "t1c.auth.apikey";
    
    //General
    
    String LIB_GEN_FINGERPRINT_PATH = "t1c.general.";
    String LIB_GEN_CONTAINER_DOWNLOAD_TIMEOUT = "t1c.general.containerDownloadTimeoutInSeconds";
    String LIB_GEN_CONSENT_DURATION = "t1c.general.consentDurationInDays";
    String LIB_GEN_CONSENT_TIMEOUT = "t1c.general.consentTimeoutInSeconds";
    String LIB_GEN_POLLING_INTERVAL = "t1c.general.pollingIntervalInSeconds";
    String LIB_GEN_POLLING_TIMEOUT = "t1c.general.pollingTimeoutInSeconds";
    String LIB_GEN_HARDWARE_PINPAD_FORCED = "t1c.general.hardwarePinPadForced";
    String LIB_GEN_IMPLICIT_DOWNLOADS = "t1c.general.implicitDownloads";
    String LIB_GEN_OS_PIN_DIALOG = "t1c.general.osPinDialog";
    String LIB_GEN_SESSION_TIMEOUT = "t1c.general.sessionTimeoutInSeconds";
    String LIB_GEN_SYNC_MANAGED = "t1c.general.syncManaged";
    
    //Test

    String LIB_TEST_LOCAL_MODE = "t1c.testing.localTestMode";
    
    //PKCS

    String LIB_PKCS_PATH_MACOS = "t1c.pkcs11.modulePath.macos";
    String LIB_PKCS_PATH_WIN = "t1c.pkcs11.modulePath.win";
    String LIB_PKCS_PATH_LINUX = "t1c.pkcs11.modulePath.linux";
}
