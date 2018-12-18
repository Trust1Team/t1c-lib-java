package com.t1t.t1c.containers;

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public enum ContainerType {
    AVENTRA("aventra",
            Collections.singletonList("Aventra")),
    BEID("beid",
            Collections.singletonList("Belgium Electronic ID card")),
    DNIE("dnie",
            Collections.singletonList("DNI electronico")),
    EMV("emv",
            Collections.singletonList("Mastercard")),
    EST("esteid",
            Collections.singletonList("Estonian")),
    LUXID("luxeid",
            Collections.singletonList("Grand Duchy of Luxembourg / Identity card with LuxTrust certificate (eID)")),
    LUXTRUST("luxtrust",
            Collections.singletonList("LuxTrust card")),
    MOBIB("mobib",
            Collections.singletonList("MOBIB")),
    OBERTHUR("oberthur",
            Collections.singletonList("Oberthur")),
    OCRA("ocra",
            Collections.singletonList("Juridic Person's Token (PKI)")),
    PIV("piv",
            Arrays.asList("PIV", "CIV")),
    PT("pteid",
            Arrays.asList("Portuguese", "Portugese")),
    READER_API("readerapi",
            Collections.<String>emptyList()),
    PKCS11("pkcs11",
            Arrays.asList("Pkcs11", "SafeNet")),
    FILE_EXCHANGE("file-exchange",
            Collections.<String>emptyList()),
    JAVA_KEYTOOL("java-keytool",
            Collections.<String>emptyList());

    private static final Map<String, ContainerType> idMap;
    private static final Map<String, ContainerType> cardDescriptionMap;

    static {
        idMap = new HashMap<>();
        cardDescriptionMap = new HashMap<>();
        for (final ContainerType type : ContainerType.values()) {
            idMap.put(type.id, type);
            for (final String cardDescription : type.cardDescriptions) {
                cardDescriptionMap.put(cardDescription, type);
            }
        }
    }

    private String id;
    private List<String> cardDescriptions;

    ContainerType(final String id, final List<String> descriptions) {
        this.id = id;
        this.cardDescriptions = descriptions;
    }

    public static ContainerType valueOfId(final String id) {
        return idMap.get(id);
    }

    public static ContainerType valueOfCardDescription(final String description) {
        String keyToUse = null;
        for (final String key : cardDescriptionMap.keySet()) {
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
}
