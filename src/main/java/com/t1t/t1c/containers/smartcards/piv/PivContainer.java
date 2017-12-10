package com.t1t.t1c.containers.smartcards.piv;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GenericContainerException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.rest.RestExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PivContainer extends GenericContainer<PivContainer, GclPivRestClient, GclPivAllData, AllCertificates> {

    public PivContainer(LibConfig config, GclReader reader, GclPivRestClient httpClient, String pin) {
        super(config, reader, httpClient, pin);
    }

    @Override
    public PivContainer createInstance(LibConfig config, GclReader reader, GclPivRestClient httpClient, String pin) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(pin), "PIN required to initialize PIV container");
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.PIV;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return null;
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return null;
    }

    @Override
    public GclPivAllData getAllData() throws GenericContainerException {
        return getAllData(null, null);
    }

    @Override
    public GclPivAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return RestExecutor.returnData(httpClient.getAllData(getTypeId(), reader.getId(), new GclVerifyPinRequest().withPin(this.pin)));
    }

    @Override
    public GclPivAllData getAllData(Boolean... parseCertificates) throws GenericContainerException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public AllCertificates getAllCertificates() throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws GenericContainerException {
        return null;
    }

    @Override
    public Boolean verifyPin(String... pin) throws GenericContainerException, VerifyPinException {
        return null;
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        return null;
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws GenericContainerException {
        return null;
    }

    @Override
    public ContainerType getType() {
        return type;
    }

    @Override
    public String getTypeId() {
        return type.getId();
    }

    @Override
    public Class<GclPivAllData> getAllDataClass() {
        return null;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
        return null;
    }
}
