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
    private Boolean managed;
    private String coreVersion;
    private Long contextToken;
    private List<DsContainerResponse> containerResponses;
    private DsAtrList atrList;
    private String gclJwt;

    public DsSyncResponseDto(Pair<DsRegistrationSyncResponse, String> response) {
        this.uuid = response.getLeft().getUuid();
        this.activated = response.getLeft().getActivated();
        this.managed = response.getLeft().getManaged();
        this.coreVersion = response.getLeft().getCoreVersion();
        this.contextToken = response.getLeft().getContextToken();
        this.containerResponses = response.getLeft().getContainerResponses();
        this.atrList = response.getLeft().getAtrList();
        this.gclJwt = response.getRight();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean getManaged() {
        return managed;
    }

    public void setManaged(Boolean managed) {
        this.managed = managed;
    }

    public String getCoreVersion() {
        return coreVersion;
    }

    public void setCoreVersion(String coreVersion) {
        this.coreVersion = coreVersion;
    }

    public Long getContextToken() {
        return contextToken;
    }

    public void setContextToken(Long contextToken) {
        this.contextToken = contextToken;
    }

    public List<DsContainerResponse> getContainerResponses() {
        return containerResponses;
    }

    public void setContainerResponses(List<DsContainerResponse> containerResponses) {
        this.containerResponses = containerResponses;
    }

    public DsAtrList getAtrList() {
        return atrList;
    }

    public void setAtrList(DsAtrList atrList) {
        this.atrList = atrList;
    }

    public String getGclJwt() {
        return gclJwt;
    }

    public void setGclJwt(String gclJwt) {
        this.gclJwt = gclJwt;
    }
}