package com.t1t.t1c.services;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclClientException;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.factories.ConnectionFactory;
import com.t1t.t1c.gcl.IGclClient;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.model.rest.GclCard;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.utils.ContainerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class GenericService implements IGenericService {

    private static final Logger log = LoggerFactory.getLogger(GenericService.class);

    @Override
    public List<ContainerType> getEligibleContainersTypesFor(String readerId) {
        return null;
    }

    @Override
    public List<GenericContainer> getGenericContainersFor(String readerId) {
        return null;
    }

    @Override
    public GenericContainer getGenericContainerFor(String readerId) {
        return null;
    }

    @Override
    public AllData dumpData(String readerId, String pin, List<String> filterParams) {
        return null;
    }

    @Override
    public AllData dumpData(String readerId, List<String> filterParams) {
        return null;
    }

    @Override
    public String authenticate(String readerId, GclAuthenticateOrSignData data, String... pin) {
        return null;
    }

    @Override
    public String sign(String readerId, GclAuthenticateOrSignData data, String... pin) {
        return null;
    }

    @Override
    public Boolean verifyPin(String readerId, String... pin) throws VerifyPinException {
        return null;
    }



/*    @Override
    public ContainerType getContainerTypeFor(String readerId) {
        return ContainerUtil.determineContainer(ConnectionFactory.getGclClient().getReader(readerId).getCard());
    }

    @Override
    public GenericContainer getGenericContainerFor(String readerId) {
        return ConnectionFactory.getGenericContainer(readerId);
    }

    @Override
    public AllData dumpData(String readerId, String pin, List<String> filterParams) {
        if (StringUtils.isNotEmpty(pin)) {
            return ConnectionFactory.getGenericContainer(readerId, pin).getAllData(filterParams, true);
        } else return ConnectionFactory.getGenericContainer(readerId).getAllData(filterParams, true);
    }

    @Override
    public AllData dumpData(String readerId, List<String> filterParams) {
        return dumpData(readerId, null, filterParams);
    }

    @Override
    public String getDownloadLink() {
        return ConnectionFactory.getDsClient().getDownloadLink(new PlatformInfo());
    }

    @Override
    public List<GclReader> getAuthenticationCapableReaders() {
        List<GclReader> returnValue = new ArrayList<>();
        IGclClient gcl = ConnectionFactory.getGclClient();
        List<GclContainer> availableContainers = gcl.getContainers();
        try {
            for (GclReader reader : gcl.getReadersWithInsertedCard()) {
                if (ContainerUtil.canAuthenticate(reader.getCard()) && ContainerUtil.isContainerAvailable(reader.getCard(), availableContainers)) {
                    returnValue.add(reader);
                }
            }
        } catch (GclClientException ex) {
            log.error("Could not retrieve list of readers: ", ex);
        }
        return returnValue;
    }

    @Override
    public List<GclReader> getSignCapableReaders() {
        List<GclReader> returnValue = new ArrayList<>();
        IGclClient gcl = ConnectionFactory.getGclClient();
        List<GclContainer> availableContainers = gcl.getContainers();
        try {
            for (GclReader reader : gcl.getReadersWithInsertedCard()) {
                if (ContainerUtil.canSign(reader.getCard()) && ContainerUtil.isContainerAvailable(reader.getCard(), availableContainers)) {
                    returnValue.add(reader);
                }
            }
        } catch (GclClientException ex) {
            log.error("Could not retrieve list of readers: ", ex);
        }
        return returnValue;
    }

    @Override
    public List<GclReader> getPinVerificationCapableReaders() {
        List<GclReader> returnValue = new ArrayList<>();
        IGclClient gcl = ConnectionFactory.getGclClient();
        try {
            List<GclContainer> availableContainers = gcl.getContainers();
            for (GclReader reader : gcl.getReadersWithInsertedCard()) {
                if (ContainerUtil.canVerifyPin(reader.getCard()) && ContainerUtil.isContainerAvailable(reader.getCard(), availableContainers)) {
                    returnValue.add(reader);
                }
            }
        } catch (GclClientException ex) {
            log.error("Could not retrieve list of readers: ", ex);
        }
        return returnValue;
    }

    @Override
    public String sign(String readerId, GclAuthenticateOrSignData data, String... pin) {
        GclCard card = ConnectionFactory.getGclClient().getReader(readerId).getCard();
        verifyAlgo(data, card);
        if (!ContainerUtil.canSign(card)) {
            throw ExceptionFactory.signingException("Card does not support signing");
        }
        return ConnectionFactory.getGenericContainer(readerId, pin).sign(data);
    }

    @Override
    public String authenticate(String readerId, GclAuthenticateOrSignData data, String... pin) {
        GclCard card = ConnectionFactory.getGclClient().getReader(readerId).getCard();
        verifyAlgo(data, card);
        if (!ContainerUtil.canAuthenticate(card)) {
            throw ExceptionFactory.authenticateException("Card does not support authentication");
        }
        return ConnectionFactory.getGenericContainer(readerId, pin).authenticate(data);
    }

    @Override
    public boolean verifyPin(String readerId, String... pin) throws VerifyPinException {
        return ConnectionFactory.getGenericContainer(readerId, pin).verifyPin(pin);
    }

    private void verifyAlgo(GclAuthenticateOrSignData data, GclCard card) {
        if (StringUtils.isBlank(data.getAlgorithmReference())) {
            String algo = ContainerUtil.determineDefaultAlgorithm(card);
            if (algo == null) {
                throw ExceptionFactory.authenticateException("No algorithm for authentication available");
            }
            data.setAlgorithmReference(algo);
        }
    }*/
}