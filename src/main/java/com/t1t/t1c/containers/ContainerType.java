package com.t1t.t1c.containers;

import com.t1t.t1c.containers.readerapi.ReaderApiContainer;
import com.t1t.t1c.containers.smartcards.eid.be.BeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.dni.DnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.LuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.PtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.EmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.MobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.OcraContainer;
import com.t1t.t1c.containers.smartcards.piv.PivContainer;
import com.t1t.t1c.containers.smartcards.pkcs11.safenet.SafeNetContainer;
import com.t1t.t1c.containers.smartcards.pki.aventra.AventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.LuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.OberthurContainer;
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
            AventraContainer.class,
            null),
    BEID("beid",
            Collections.singletonList("Belgium Electronic ID card"),
            Arrays.asList("address", "rn", "picture", "root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate"),
            Arrays.asList("root-certificate", "authentication-certificate", "non-repudiation-certificate", "citizen-certificate", "rrn-certificate"),
            BeIdContainer.class,
            GclBeIdAllData.class),
    DNIE("dnie",
            Collections.singletonList(""),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            DnieContainer.class,
            null),
    EMV("emv",
            Collections.singletonList("Mastercard"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            EmvContainer.class,
            null),
    EST("esteid",
            Collections.singletonList("Estonian"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            BeIdContainer.class,
            null),
    LUXID("luxeid",
            Collections.singletonList("Grand Duchy of Luxembourg / Identity card with LuxTrust certificate (eID)"),
            Arrays.asList("authentication-certificate", "biometric", "non-repudiation-certificate", "picture", "root-certificates"),
            Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates"),
            LuxIdContainer.class,
            null),
    LUXTRUST("luxtrust",
            Collections.singletonList("LuxTrust card"),
            Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates"),
            Arrays.asList("authentication-certificate", "non-repudiation-certificate", "root-certificates"),
            LuxTrustContainer.class,
            null),
    MOBIB("mobib",
            Collections.singletonList("MOBIB"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            MobibContainer.class,
            null),
    OBERTHUR("oberthur",
            Collections.singletonList("Oberthur"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            OberthurContainer.class,
            null),
    OCRA("ocra",
            Collections.singletonList("Juridic Person's Token (PKI)"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            OcraContainer.class,
            null),
    PIV("piv",
            Arrays.asList("PIV", "CIV"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            PivContainer.class,
            null),
    PT("pteid",
            Collections.singletonList("Portuguese"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            PtEIdContainer.class,
            null),
    READER_API("readerapi",
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            ReaderApiContainer.class,
            null),
    SAFENET("safenet",
            Collections.singletonList("SafeNet"),
            Collections.EMPTY_LIST,
            Collections.EMPTY_LIST,
            SafeNetContainer.class,
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
