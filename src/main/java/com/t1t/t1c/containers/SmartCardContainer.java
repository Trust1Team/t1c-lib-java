package com.t1t.t1c.containers;

import com.google.common.base.Preconditions;
import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.containers.smartcards.ContainerData;
import com.t1t.t1c.core.GclPace;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.NoConsentException;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.model.T1cCertificate;
import com.t1t.t1c.utils.PkiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 * <p>
 * Virtual container.
 */
public abstract class SmartCardContainer<T extends SmartCardContainer, U, V extends AllData, W extends AllCertificates> implements IGenericContainer<V, W> {

    protected static final String ENCRYPTED_PIN_HEADER_NAME = "X-Encrypted-Pin";
    protected static final String ENCRYPTED_CAN_HEADER_NAME = "X-Encrypted-Can";
    protected static final String ENCRYPTED_MRZ_HEADER_NAME = "X-Encrypted-Mrz";
    protected static final String ENCRYPTED_PUK_HEADER_NAME = "X-Encrypted-Puk";

    /*Properties*/
    protected GclReader reader;
    protected U httpClient;
    protected transient GclPace pace;
    protected LibConfig config;
    protected ContainerVersion containerVersion;
    protected List<DigestAlgorithm> signAlgos;
    protected List<DigestAlgorithm> authenticateAlgos;

    /*Instantiation*/
    public SmartCardContainer() {
    }

    public SmartCardContainer(LibConfig config, GclReader reader, String containerVersion, U httpClient) {
        createInstance(config, reader, containerVersion, httpClient, new GclPace());
    }

    public SmartCardContainer(LibConfig config, GclReader reader, String containerVersion, U httpClient, GclPace pace) {
        createInstance(config, reader, containerVersion, httpClient, pace);
    }

    public abstract T createInstance(LibConfig config, GclReader reader, String containerVersion, U httpClient, GclPace pace);

    @Override
    public V getAllData() throws RestException, NoConsentException {
        return getAllData(null, null);
    }

    @Override
    public V getAllData(Boolean parseCertificates) throws RestException, NoConsentException {
        return getAllData(null, parseCertificates);
    }

    @Override
    public V getAllData(List<String> filterParams) throws RestException, NoConsentException {
        return getAllData(filterParams, null);
    }

    @Override
    public W getAllCertificates() throws RestException, NoConsentException {
        return getAllCertificates(null, null);
    }

    @Override
    public W getAllCertificates(Boolean parseCertificates) throws RestException, NoConsentException {
        return getAllCertificates(null, parseCertificates);
    }

    @Override
    public W getAllCertificates(List<String> filterParams) throws RestException, NoConsentException {
        return getAllCertificates(filterParams, null);
    }

    @Override
    public Boolean verifyPin() throws VerifyPinException, RestException, NoConsentException {
        return verifyPin(null);
    }

    @Override
    public String authenticate(String data, DigestAlgorithm algo) throws VerifyPinException, RestException, NoConsentException {
        return authenticate(data, algo, null);
    }

    @Override
    public String sign(String data, DigestAlgorithm algo) throws VerifyPinException, RestException, NoConsentException {
        return sign(data, algo, null);
    }

    @Override
    public ContainerData dumpData() throws RestException, UnsupportedOperationException, NoConsentException {
        return dumpData(null);
    }

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


    protected List<DigestAlgorithm> getAlgorithms(List<String> algoRefs) {
        List<DigestAlgorithm> returnValue = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(algoRefs)) {
            for (String algoRef : algoRefs) {
                returnValue.add(DigestAlgorithm.getAlgoForRef(algoRef));
            }
        }
        return returnValue;
    }

    protected Map<Integer, T1cCertificate> orderCertificates(T1cCertificate... certs) {
        if (certs == null || certs.length == 0) {
            return Collections.emptyMap();
        } else return orderCertificates(Arrays.asList(certs));
    }

    protected Map<Integer, T1cCertificate> orderCertificates(List<T1cCertificate> certs) {
        return PkiUtil.orderCertificates(certs);
    }

    protected void isSignAlgorithmSupported(DigestAlgorithm selectedAlgorithm) {
        isSupported(selectedAlgorithm, getAvailableSignAlgorithms());
    }

    protected void isAuthenticateAlgorithmSupported(DigestAlgorithm selectedAlgorithm) {
        isSupported(selectedAlgorithm, getAvailableAuthenticationAlgorithms());
    }

    private void isSupported(DigestAlgorithm selectedAlgorithm, List<DigestAlgorithm> supported) {
        Preconditions.checkNotNull(selectedAlgorithm, "digest algorithm must not be null");
        if (!supported.contains(selectedAlgorithm)) {
            throw ExceptionFactory.unsupportedDigestAlgorithm(selectedAlgorithm, supported);
        }
    }

    @Override
    public ContainerType getType() {
        return containerVersion.getType();
    }

    @Override
    public String getTypeId() {
        return getType().getId();
    }

    @Override
    public String getContainerVersionId() {
        return this.containerVersion.getId();
    }

    @Override
    public String getContainerUrlId() {
        return getContainerVersionId().replace(".", "-");
    }
}
