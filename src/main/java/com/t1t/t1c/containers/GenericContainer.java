package com.t1t.t1c.containers;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.core.GclReader;
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
 * <p>
 * //TODO
 */
public abstract class GenericContainer<T extends GenericContainer, U, V extends AllData, W extends AllCertificates> implements IGenericContainer<V, W> {

    protected static final String ENCRYPTED_PIN_HEADER_NAME = "X-Encrypted-Pin";

    /*Properties*/
    protected GclReader reader;
    protected U httpClient;
    protected transient String pacePin;
    protected LibConfig config;
    protected ContainerType type;

    /*Instantiation*/
    public GenericContainer() {
    }

    public GenericContainer(LibConfig config, GclReader reader, U httpClient, String pacePin) {
        createInstance(config, reader, httpClient, pacePin);
    }

    public abstract T createInstance(LibConfig config, GclReader reader, U httpClient, String pacePin);

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
}
