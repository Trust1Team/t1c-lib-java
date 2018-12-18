package com.t1t.t1c.model;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class OsInfo {

    private String name = System.getProperty("os.name");
    private String architecture = System.getProperty("os.arch");
    private String version = System.getProperty("os.version");

    public String getName() {
        return name;
    }

    public String getArchitecture() {
        return architecture;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof OsInfo)) return false;

        final OsInfo osInfo = (OsInfo) o;

        if (name != null ? !name.equals(osInfo.name) : osInfo.name != null) return false;
        if (architecture != null ? !architecture.equals(osInfo.architecture) : osInfo.architecture != null)
            return false;
        return version != null ? version.equals(osInfo.version) : osInfo.version == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (architecture != null ? architecture.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OSResponse{" +
                "name='" + name + '\'' +
                ", architecture='" + architecture + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

}