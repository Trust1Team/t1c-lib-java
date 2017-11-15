package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class SafeNetContainerConfiguration {

    private Path linux;
    private Path mac;
    private Path windows;

    public SafeNetContainerConfiguration() {
        this.linux = Paths.get("/usr/local/lib/libeTPkcs11.so");
        this.mac = Paths.get("/usr/local/lib/libeTPkcs11.dylib");
        this.windows = Paths.get("C:\\Windows\\System32\\eTPKCS11.dll");
    }

    public SafeNetContainerConfiguration(Path linux, Path mac, Path windows) {
        this.linux = linux;
        this.mac = mac;
        this.windows = windows;
    }

    public Path getLinux() {
        return linux;
    }

    public void setLinux(Path linux) {
        this.linux = linux;
    }

    public SafeNetContainerConfiguration withLinux(Path linux) {
        this.linux = linux;
        return this;
    }

    public Path getMac() {
        return mac;
    }

    public void setMac(Path mac) {
        this.mac = mac;
    }

    public SafeNetContainerConfiguration withMac(Path mac) {
        this.mac = mac;
        return this;
    }

    public Path getWindows() {
        return windows;
    }

    public void setWindows(Path windows) {
        this.windows = windows;
    }

    public SafeNetContainerConfiguration withWindows(Path windows) {
        this.windows = windows;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SafeNetContainerConfiguration)) return false;

        SafeNetContainerConfiguration that = (SafeNetContainerConfiguration) o;

        if (linux != null ? !linux.equals(that.linux) : that.linux != null) return false;
        if (mac != null ? !mac.equals(that.mac) : that.mac != null) return false;
        return windows != null ? windows.equals(that.windows) : that.windows == null;
    }

    @Override
    public int hashCode() {
        int result = linux != null ? linux.hashCode() : 0;
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + (windows != null ? windows.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SafenetContainerConfiguration{" +
                "linux=" + linux +
                ", mac=" + mac +
                ", windows=" + windows +
                '}';
    }
}