package com.t1t.t1c.containers;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.core.GclReader;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.model.AllCertificates;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.DigestAlgorithm;
import com.t1t.t1c.rest.RestExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Guillaume Vandecasteele, Michallis
 * @since 2017
 * <p>
 * Virtual container.
 * <p>
 * //TODO
 */
public abstract class GenericContainer<T extends GenericContainer, U, V extends AllData, W extends AllCertificates> implements IGenericContainer<V, W> {
    /*Properties*/
    protected GclReader reader;
    protected U httpClient;
    protected transient String pin;
    protected LibConfig config;
    protected ContainerType type;

    /*Instantiation*/
    public GenericContainer() {
    }

    public GenericContainer(LibConfig config, GclReader reader, U httpClient, String pin) {
        createInstance(config, reader, httpClient, pin);
    }

    public abstract T createInstance(LibConfig config, GclReader reader, U httpClient, String pin);

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
