package com.t1t.t1c.containers;

import com.t1t.t1c.containers.remoteloading.belfius.IBelfiusContainer;
import com.t1t.t1c.containers.smartcards.eid.be.IBeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.esp.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.ISafeNetContainer;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.model.AllData;
import com.t1t.t1c.model.rest.GclBeIdAllData;

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public enum ContainerType {
    AVENTRA("aventra",
            Collections.singletonList("Aventra"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IAventraContainer.class,
            null),
    BEID("beid",
            Collections.singletonList("Belgium Electronic ID card"),
            Arrays.asList("address", "rn", "picture", "root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate"),
            Arrays.asList("root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate"),
            IBeIdContainer.class,
            GclBeIdAllData.class),
    DNIE("dnie",
            Collections.singletonList(""),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IDnieContainer.class,
            null),
    EMV("emv",
            Collections.singletonList("Mastercard"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IEmvContainer.class,
            null),
    EST("esteid",
            Collections.singletonList("Estonian"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IBeIdContainer.class,
            null),
    LUXID("luxeid",
            Collections.singletonList("Grand Duchy of Luxembourg / Identity card with LuxTrust certificate (eID)"),
            Arrays.asList("authentication-certificate", "biometric", "non-repudiation-certificate", "picture", "root-certificates"),
            Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates"),
            ILuxIdContainer.class,
            null),
    LUXTRUST("luxtrust",
            Collections.singletonList("LuxTrust card"),
            Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates"),
            Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates"),
            ILuxTrustContainer.class,
            null),
    MOBIB("mobib",
            Collections.singletonList("MOBIB"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IMobibContainer.class,
            null),
    OBERTHUR("oberthur",
            Collections.singletonList("Oberthur"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IOberthurContainer.class,
            null),
    OCRA("ocra",
            Collections.singletonList("Juridic Person's Token (PKI)"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IOcraContainer.class,
            null),
    PIV("piv",
            Arrays.asList("PIV", "CIV"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IPivContainer.class,
            null),
    PT("pteid",
            Collections.singletonList("Portuguese"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IPtEIdContainer.class,
            null),
    READER_API("readerapi",
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            IBelfiusContainer.class,
            null),
    SAFENET("safenet",
            Collections.singletonList("SafeNet"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            ISafeNetContainer.class,
            AllData.class);

    private static final Map<String, ContainerType> idMap;
    private static final Map<String, ContainerType> cardDescriptionMap;

    static {
        idMap = new HashMap<>();
        cardDescriptionMap = new HashMap<>();
        for (ContainerType type : ContainerType.values()) {
            idMap.put(type.id, type);
            for (String cardDescription : type.cardDescriptions) {
                cardDescriptionMap.put(cardDescription, type);
            }
        }
    }

    private String id;
    private List<String> cardDescriptions;
    private List<String> dataFilters;
    private List<String> certificateFilters;
    private Class containerClass;
    private Class allDataFormat;

    ContainerType(String id, List<String> descriptions, List<String> dataFilters, List<String> certificateFilters, Class containerClass, Class allDataFormat) {
        this.id = id;
        this.cardDescriptions = descriptions;
        this.dataFilters = dataFilters;
        this.certificateFilters = certificateFilters;
        this.containerClass = containerClass;
        this.allDataFormat = allDataFormat;
    }

    public static ContainerType valueOfId(String id) {
        return idMap.get(id);
    }

    public static ContainerType valueOfCardDescription(String description) {
        String keyToUse = null;
        for (String key : cardDescriptionMap.keySet()) {
            if (description.contains(key)) {
                keyToUse = key;
            }
        }
        return cardDescriptionMap.get(keyToUse);
    }

    @Override
    public String toString() {
        return "ContainerType{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public List<String> getCardDescriptions() {
        return cardDescriptions;
    }

    public List<String> getDataFilters() {
        return dataFilters;
    }

    public List<String> getCertificateFilters() {
        return certificateFilters;
    }

    public Class getContainerClass() {
        return containerClass;
    }

    public Class getAllDataFormat() {
        return allDataFormat;
    }
}
