package com.t1t.t1c.services;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.exceptions.ExceptionFactory;
import com.t1t.t1c.exceptions.GclClientException;
import com.t1t.t1c.gcl.IGclClient;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.GclAuthenticateOrSignData;
import com.t1t.t1c.model.rest.GclCard;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.utils.CardUtil;
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
    public ContainerType getContainerTypeFor(String readerId) {
        return ContainerUtil.determineContainer(FactoryService.getGclClient().getReader(readerId).getCard());
    }

    @Override
    public GenericContainer getGenericContainerFor(String readerId) {
        return FactoryService.getGenericContainer(readerId);
    }

    @Override
    public AllData dumpData(String readerId, String pin, List<String> filterParams) {
        if (StringUtils.isNotEmpty(pin)) {
            return FactoryService.getGenericContainer(readerId, pin).getAllData(filterParams);
        } else return FactoryService.getGenericContainer(readerId).getAllData(filterParams);
    }

    @Override
    public AllData dumpData(String readerId, List<String> filterParams) {
        return dumpData(readerId, null, filterParams);
    }

    @Override
    public String getDownloadLink() {
        return FactoryService.getDsClient().getDownloadLink(new PlatformInfo());
    }

    @Override
    public List<GclReader> getAuthenticationCapableReaders() {
        List<GclReader> returnValue = new ArrayList<>();
        IGclClient gcl = FactoryService.getGclClient();
        List<GclContainer> availableContainers = gcl.getContainers();
        try {
            for (GclReader reader : gcl.getReadersWithInsertedCard()) {
                if (CardUtil.canAuthenticate(reader.getCard()) && ContainerUtil.isContainerAvailable(reader.getCard(), availableContainers)) {
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
        IGclClient gcl = FactoryService.getGclClient();
        List<GclContainer> availableContainers = gcl.getContainers();
        try {
            for (GclReader reader : gcl.getReadersWithInsertedCard()) {
                if (CardUtil.canSign(reader.getCard()) && ContainerUtil.isContainerAvailable(reader.getCard(), availableContainers)) {
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
        IGclClient gcl = FactoryService.getGclClient();
        try {
            List<GclContainer> availableContainers = gcl.getContainers();
            for (GclReader reader : gcl.getReadersWithInsertedCard()) {
                if (CardUtil.canVerifyPin(reader.getCard()) && ContainerUtil.isContainerAvailable(reader.getCard(), availableContainers)) {
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
        GclCard card = FactoryService.getGclClient().getReader(readerId).getCard();
        verifyAlgo(data, card);
        if (!CardUtil.canSign(card)) {
            throw ExceptionFactory.signingException("Card does not support signing");
        }
        return FactoryService.getGenericContainer(readerId, pin).sign(data);
    }

    @Override
    public String authenticate(String readerId, GclAuthenticateOrSignData data, String... pin) {
        GclCard card = FactoryService.getGclClient().getReader(readerId).getCard();
        verifyAlgo(data, card);
        if (!CardUtil.canAuthenticate(card)) {
            throw ExceptionFactory.authenticateException("Card does not support authentication");
        }
        return FactoryService.getGenericContainer(readerId, pin).authenticate(data);
    }

    @Override
    public boolean verifyPin(String readerId, String... pin) {
        return FactoryService.getGenericContainer(readerId, pin).verifyPin(pin);
    }

    private void verifyAlgo(GclAuthenticateOrSignData data, GclCard card) {
        if (StringUtils.isBlank(data.getAlgorithmReference())) {
            String algo = CardUtil.determineDefaultAlgorithm(card);
            if (algo == null) {
                throw ExceptionFactory.authenticateException("No algorithm for authentication available");
            }
            data.setAlgorithmReference(algo);
        }
    }
}