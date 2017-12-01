package com.t1t.t1c.services;

import com.t1t.t1c.containers.ContainerType;
import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.exceptions.VerifyPinException;
import com.t1t.t1c.model.AllData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public ContainerType getContainerTypeFor(String reader) {
        return ContainerUtil.determineContainer(ConnectionFactory.getGclClient().getReader(reader).getCard());
    }

    @Override
    public GenericContainer getGenericContainerFor(String reader) {
        return ConnectionFactory.getGenericContainer(reader);
    }

    @Override
    public AllData dumpData(String reader, String pin, List<String> filterParams) {
        if (StringUtils.isNotEmpty(pin)) {
            return ConnectionFactory.getGenericContainer(reader, pin).getAllData(filterParams, true);
        } else return ConnectionFactory.getGenericContainer(reader).getAllData(filterParams, true);
    }

    @Override
    public AllData dumpData(String reader, List<String> filterParams) {
        return dumpData(reader, null, filterParams);
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
    public String sign(String reader, GclAuthenticateOrSignData data, String... pin) {
        GclCard card = ConnectionFactory.getGclClient().getReader(reader).getCard();
        verifyAlgo(data, card);
        if (!ContainerUtil.canSign(card)) {
            throw ExceptionFactory.signingException("Card does not support signing");
        }
        return ConnectionFactory.getGenericContainer(reader, pin).sign(data);
    }

    @Override
    public String authenticate(String reader, GclAuthenticateOrSignData data, String... pin) {
        GclCard card = ConnectionFactory.getGclClient().getReader(reader).getCard();
        verifyAlgo(data, card);
        if (!ContainerUtil.canAuthenticate(card)) {
            throw ExceptionFactory.authenticateException("Card does not support authentication");
        }
        return ConnectionFactory.getGenericContainer(reader, pin).authenticate(data);
    }

    @Override
    public boolean verifyPin(String reader, String... pin) throws VerifyPinException {
        return ConnectionFactory.getGenericContainer(reader, pin).verifyPin(pin);
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