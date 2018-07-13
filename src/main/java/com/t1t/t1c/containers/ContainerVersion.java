package com.t1t.t1c.containers;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class ContainerVersion {

    private static final String VERSION_SEPARATOR = "-";

    private ContainerType type;
    private String version;

    public ContainerVersion(ContainerType type, String version) {
        this.type = type;
        this.version = version;
    }

    public ContainerVersion(String containerVersionId) {
        setTypeAndVersionFromIdString(containerVersionId);
    }

    public ContainerType getType() {
        return type;
    }

    public void setType(ContainerType type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return type.getId() + VERSION_SEPARATOR + version;
    }

    public void setId(String id) {
        setTypeAndVersionFromIdString(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContainerVersion)) return false;

        ContainerVersion that = (ContainerVersion) o;

        if (type != that.type) return false;
        return version != null ? version.equals(that.version) : that.version == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContainerVersion{" +
                "type=" + type +
                ", version='" + version + '\'' +
                '}';
    }

    private void setTypeAndVersionFromIdString(String id) {
        if (StringUtils.isNotEmpty(id) && id.contains(VERSION_SEPARATOR)) {
            this.type = ContainerType.valueOfId(id.substring(0, id.lastIndexOf(VERSION_SEPARATOR)));
            this.version = id.substring(id.lastIndexOf(VERSION_SEPARATOR) + 1);
        }
    }
}