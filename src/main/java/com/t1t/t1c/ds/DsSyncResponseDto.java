package com.t1t.t1c.ds;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class DsSyncResponseDto {

    private String uuid;
    private Boolean activated;
    private String coreVersion;
    private String contextToken;
    private List<DsContainerResponse> containerResponses;
    private DsAtrList atrList;
    private String gclJwt;

    public DsSyncResponseDto(final Pair<DsRegistrationSyncResponse, String> response) {
        this.uuid = response.getLeft().getUuid();
        this.activated = response.getLeft().getActivated();
        this.coreVersion = response.getLeft().getCoreVersion();
        this.contextToken = response.getLeft().getContextToken();
        this.containerResponses = response.getLeft().getContainerResponses();
        this.atrList = response.getLeft().getAtrList();
        this.gclJwt = response.getRight();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(final Boolean activated) {
        this.activated = activated;
    }

    public String getCoreVersion() {
        return coreVersion;
    }

    public void setCoreVersion(final String coreVersion) {
        this.coreVersion = coreVersion;
    }

    public String getContextToken() {
        return contextToken;
    }

    public void setContextToken(final String contextToken) {
        this.contextToken = contextToken;
    }

    public List<DsContainerResponse> getContainerResponses() {
        return containerResponses;
    }

    public void setContainerResponses(final List<DsContainerResponse> containerResponses) {
        this.containerResponses = containerResponses;
    }

    public DsAtrList getAtrList() {
        return atrList;
    }

    public void setAtrList(final DsAtrList atrList) {
        this.atrList = atrList;
    }

    public String getGclJwt() {
        return gclJwt;
    }

    public void setGclJwt(final String gclJwt) {
        this.gclJwt = gclJwt;
    }
}