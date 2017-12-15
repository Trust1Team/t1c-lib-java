package com.t1t.t1c.model;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class JavaInfo {

    private String version = System.getProperty("java.version");
    private String specificationVersion = System.getProperty("java.specification.version");

    public String getVersion() {
        return version;
    }

    public String getSpecificationVersion() {
        return specificationVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JavaInfo)) return false;

        JavaInfo javaInfo = (JavaInfo) o;

        if (version != null ? !version.equals(javaInfo.version) : javaInfo.version != null) return false;
        return specificationVersion != null ? specificationVersion.equals(javaInfo.specificationVersion) : javaInfo.specificationVersion == null;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (specificationVersion != null ? specificationVersion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JavaInfo{" +
                "version='" + version + '\'' +
                ", specificationVersion='" + specificationVersion + '\'' +
                '}';
    }
}