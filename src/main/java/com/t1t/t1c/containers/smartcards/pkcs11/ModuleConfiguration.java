package com.t1t.t1c.containers.smartcards.pkcs11;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class ModuleConfiguration {

    private Path linux;
    private Path mac;
    private Path windows;

    public ModuleConfiguration() {
        this.linux = Paths.get("/usr/local/lib/libeTPkcs11.so");
        this.mac = Paths.get("/usr/local/lib/libeTPkcs11.dylib");
        this.windows = Paths.get("C:\\Windows\\System32\\eTPKCS11.dll");
    }

    public ModuleConfiguration(final Path linux, final Path mac, final Path windows) {
        this.linux = linux;
        this.mac = mac;
        this.windows = windows;
    }

    public Path getLinux() {
        return linux;
    }

    public void setLinux(final Path linux) {
        this.linux = linux;
    }

    public ModuleConfiguration withLinux(final Path linux) {
        this.linux = linux;
        return this;
    }

    public Path getMac() {
        return mac;
    }

    public void setMac(final Path mac) {
        this.mac = mac;
    }

    public ModuleConfiguration withMac(final Path mac) {
        this.mac = mac;
        return this;
    }

    public Path getWindows() {
        return windows;
    }

    public void setWindows(final Path windows) {
        this.windows = windows;
    }

    public ModuleConfiguration withWindows(final Path windows) {
        this.windows = windows;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ModuleConfiguration)) return false;

        final ModuleConfiguration that = (ModuleConfiguration) o;

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