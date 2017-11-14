package com.t1t.t1c.core;

import com.t1t.t1c.model.PlatformInfo;
import com.t1t.t1c.model.rest.GclContainer;
import com.t1t.t1c.model.rest.GclReader;
import com.t1t.t1c.model.rest.GclStatus;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface ICore {

    PlatformInfo getPlatformInfo();

    String getVersion();

    boolean activate();

    String getPubKey();

    void setPubKey(String publicKey);

    GclStatus getInfo();

    List<GclContainer> getContainers();

    GclReader pollCardInserted();

    GclReader pollCardInserted(Integer pollIntervalInSeconds);

    GclReader pollCardInserted(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds);

    List<GclReader> pollReadersWithCards();

    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds);

    List<GclReader> pollReadersWithCards(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds);

    List<GclReader> pollReaders();

    List<GclReader> pollReaders(Integer pollIntervalInSeconds);

    List<GclReader> pollReaders(Integer pollIntervalInSeconds, Integer pollTimeoutInSeconds);

    List<GclReader> getReaders();

    List<GclReader> getReadersWithoutInsertedCard();

    List<GclReader> getReadersWithInsertedCard();

    GclReader getReader(String readerId);

    String getUrl();

    boolean getConsent(String title, String codeWord, Integer durationInDays);
}
