package com.t1t.t1c.model;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class PlatformInfo {

    private final OsInfo os = new OsInfo();
    private final JavaInfo java = new JavaInfo();

    public OsInfo getOs() {
        return os;
    }

    public JavaInfo getJava() {
        return java;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlatformInfo)) return false;

        PlatformInfo that = (PlatformInfo) o;

        if (!os.equals(that.os)) return false;
        return java.equals(that.java);
    }

    @Override
    public int hashCode() {
        int result = os.hashCode();
        result = 31 * result + java.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PlatformInfo{" +
                "os=" + os +
                ", java=" + java +
                '}';
    }
}