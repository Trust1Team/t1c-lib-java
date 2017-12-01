package com.t1t.t1c.containers;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
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
    protected U httpCient;
    protected transient String pin;
    protected LibConfig config;
    /*Instantiation*/
    public GenericContainer() {}
    public GenericContainer(LibConfig config, GclReader reader, U httpClient, String pin) { createInstance(config, reader, httpClient, pin); }
    protected abstract T createInstance(LibConfig config, GclReader reader, U httpClient, String pin);
    /*Data Related*/
    protected abstract List<String> getAllDataFilters();
    protected abstract List<String> getAllCertificateFilters();
    protected abstract AllData getAllData() throws GenericContainerException;
    protected abstract AllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllData getAllData(Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates() throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException;
    protected abstract AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException;
    /*Token Functionality*/
    protected abstract Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException;
    protected abstract String authenticate(GclAuthenticateOrSignData data) throws GenericContainerException;
    protected abstract String sign(GclAuthenticateOrSignData data) throws GenericContainerException;

    protected String createFilterParams(List<String> params) {
        StringBuilder sb = new StringBuilder("");
        Iterator it = params.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(",");
            }
        }
        return StringUtils.isEmpty(sb.toString()) ? null : sb.toString();
    }
}
