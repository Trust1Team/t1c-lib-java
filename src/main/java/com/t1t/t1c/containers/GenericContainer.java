package com.t1t.t1c.containers;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 *
 * Virtual container.
 *
 * //TODO
 */
public abstract class GenericContainer<T extends GenericContainer, U> implements IGclContainer {
    /*Properties*/
    protected GclReader reader;
    protected U httpClient;
    protected transient String pin;
    protected LibConfig config;
    protected ContainerType type;
    /*Instantiation*/
    public GenericContainer() {}
    public GenericContainer(LibConfig config, GclReader reader, U httpClient, String pin) { createInstance(config, reader, httpClient, pin); }
    public abstract T createInstance(LibConfig config, GclReader reader, U httpClient, String pin);
    /*Data Related*/
    public abstract List<String> getAllDataFilters();
    public abstract List<String> getAllCertificateFilters();
    public abstract AllData getAllData() throws GenericContainerException;
    public abstract AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    public abstract AllData getAllData(Boolean... parseCertificates) throws GenericContainerException;
    public abstract AllCertificates getAllCertificates() throws GenericContainerException;
    public abstract AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    public abstract AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException;
    /*Token Functionality*/
    public abstract Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;
    public abstract String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;
    public abstract String sign(GclAuthenticateOrSignData data) throws GenericContainerException;

    protected String createFilterParams(List<String> params) {
        String returnValue = null;
        if (CollectionUtils.isNotEmpty(params)) {
            StringBuilder sb = new StringBuilder("");
            Iterator it = params.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext()) {
                    sb.append(",");
                }
            }
            String filter = sb.toString();
            if (StringUtils.isNotEmpty(filter)) {
                returnValue = filter;
            }
        }
        return returnValue;
    }
}
