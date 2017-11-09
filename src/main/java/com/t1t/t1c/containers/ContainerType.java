package com.t1t.t1c.containers;

import com.t1t.t1c.containers.smartcards.eid.be.IBeIdContainer;
import com.t1t.t1c.containers.smartcards.eid.esp.IDnieContainer;
import com.t1t.t1c.containers.smartcards.eid.lux.ILuxIdContainer;
import com.t1t.t1c.containers.smartcards.eid.pt.IPtEIdContainer;
import com.t1t.t1c.containers.smartcards.emv.IEmvContainer;
import com.t1t.t1c.containers.smartcards.mobib.IMobibContainer;
import com.t1t.t1c.containers.smartcards.ocra.IOcraContainer;
import com.t1t.t1c.containers.smartcards.piv.IPivContainer;
import com.t1t.t1c.containers.smartcards.pki.aventra.IAventraContainer;
import com.t1t.t1c.containers.smartcards.pki.luxtrust.ILuxTrustContainer;
import com.t1t.t1c.containers.smartcards.pki.oberthur.IOberthurContainer;
import com.t1t.t1c.model.rest.GclBeIdAllData;

import java.util.*;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public enum ContainerType {
    AVENTRA("aventra", Collections.singletonList("Aventra"), IAventraContainer.class, null),
    BEID("beid", Collections.singletonList("Belgium Electronic ID card"), IBeIdContainer.class, GclBeIdAllData.class),
    DNIE("dnie", Collections.singletonList(""), IDnieContainer.class, null),
    EMV("emv", Collections.singletonList("Mastercard"), IEmvContainer.class, null),
    EST("esteid", Collections.singletonList("Estonian"), IBeIdContainer.class, null),
    LUXID("luxeid", Collections.singletonList("Grand Duchy of Luxembourg / Identity card with LuxTrust certificate (eID)"), ILuxIdContainer.class, null),
    LUXTRUST("luxtrust", Collections.singletonList("LuxTrust card"), ILuxTrustContainer.class, null),
    MOBIB("mobib", Collections.singletonList("MOBIB"), IMobibContainer.class, null),
    OBERTHUR("oberthur", Collections.singletonList("Oberthur"), IOberthurContainer.class, null),
    OCRA("ocra", Collections.singletonList("Juridic Person's Token (PKI)"), IOcraContainer.class, null),
    PIV("piv", Arrays.asList("PIV", "CIV"), IPivContainer.class, null),
    PT("pteid", Collections.singletonList("Portuguese"), IPtEIdContainer.class, null);

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
    private Class containerClass;
    private Class allDataFormat;

    ContainerType(String id, List<String> description, Class containerClass, Class allDataFormat) {
        this.id = id;
        this.cardDescriptions = description;
        this.containerClass = containerClass;
        this.allDataFormat = allDataFormat;
    }

    public static ContainerType valueOfId(String id) {
        return idMap.get(id);
    }

    public static ContainerType valueOfCardDescription(String description) {
        return cardDescriptionMap.get(description);
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

    public List<String> getCardDescription() {
        return cardDescriptions;
    }

    public Class getContainerClass() {
        return containerClass;
    }

    public Class getAllDataFormat() {
        return allDataFormat;
    }
}
