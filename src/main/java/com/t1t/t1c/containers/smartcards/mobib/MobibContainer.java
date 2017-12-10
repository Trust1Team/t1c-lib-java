package com.t1t.t1c.containers.smartcards.mobib;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.rest.RestExecutor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MobibContainer extends GenericContainer<MobibContainer, GclMobibRestClient, GclMobibAllData, AllCertificates> {

    public MobibContainer(LibConfig config, GclReader reader, GclMobibRestClient httpClient) {
        super(config, reader, httpClient, null);
    }

    @Override
    public MobibContainer createInstance(LibConfig config, GclReader reader, GclMobibRestClient httpClient, String pin) {
        this.config = config;
        this.reader = reader;
        this.httpClient = httpClient;
        this.pin = pin;
        this.type = ContainerType.MOBIB;
        return this;
    }

    @Override
    public List<String> getAllDataFilters() {
        return Arrays.asList("active", "card-issuing", "contracts", "picture");
    }

    @Override
    public List<String> getAllCertificateFilters() {
        return Collections.emptyList();
    }

    @Override
    public GclMobibAllData getAllData() throws RestException {
        return getAllData(null, null);
    }

    @Override
    public GclMobibAllData getAllData(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        return RestExecutor.returnData(httpClient.getMobibAllData(getTypeId(), reader.getId(), createFilterParams(filterParams)));
    }

    @Override
    public GclMobibAllData getAllData(Boolean... parseCertificates) throws RestException {
        return getAllData(null, null);
    }

    @Override
    public AllCertificates getAllCertificates() throws RestException {
        return getAllCertificates(null, null);
    }

    @Override
    public AllCertificates getAllCertificates(List<String> filterParams, Boolean... parseCertificates) throws RestException {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    @Override
    public AllCertificates getAllCertificates(Boolean... parseCertificates) throws RestException {
        return getAllCertificates(null, null);
    }

    @Override
    public Boolean verifyPin(String... pin) throws RestException, VerifyPinException {
        throw ExceptionFactory.unsupportedOperationException("container has no verify PIN capabilities");
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo, String... pin) throws RestException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
    }

    @Override
    public String sign(String data, DigestAlgorithm algo, String... pin) throws RestException {
        throw ExceptionFactory.unsupportedOperationException("container has no authentication capabilities");
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
    public Class<GclMobibAllData> getAllDataClass() {
        return GclMobibAllData.class;
    }

    @Override
    public Class<AllCertificates> getAllCertificatesClass() {
        throw ExceptionFactory.unsupportedOperationException("container has no certificate dump implementation");
    }

    public Boolean getStatus() throws RestException {
        return RestExecutor.returnData(httpClient.getMobibStatus(getTypeId(), reader.getId()));
    }

    public String getPicture() throws RestException {
        return RestExecutor.returnData(httpClient.getMobibPicture(getTypeId(), reader.getId()));
    }

    public GclMobibCardIssuing getCardIssuing() throws RestException {
        return RestExecutor.returnData(httpClient.getMobibCardIssuing(getTypeId(), reader.getId()));
    }

    public List<GclMobibContract> getContracts() throws RestException {
        return RestExecutor.returnData(httpClient.getMobibContracts(getTypeId(), reader.getId()));
    }
}