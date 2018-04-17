package com.t1t.t1c.core;

import com.t1t.t1c.model.T1cPublicKey;
import com.t1t.t1c.utils.PkiUtil;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class T1cAdminPublicKeys {

    private T1cPublicKey ds;
    private T1cPublicKey device;
    private T1cPublicKey ssl;

    public T1cAdminPublicKeys(GclPublicKeys publicKeys, Boolean... parse) {
        if (publicKeys != null) {
            this.ds = PkiUtil.createT1cPublicKey(publicKeys.getDs(), parse);
            this.device = PkiUtil.createT1cPublicKey(publicKeys.getDevice(), parse);
            this.ssl = PkiUtil.createT1cPublicKey(publicKeys.getSsl(), parse);
        }
    }

    public T1cPublicKey getDs() {
        return ds;
    }

    public void setDs(T1cPublicKey ds) {
        this.ds = ds;
    }

    public T1cPublicKey getDevice() {
        return device;
    }

    public void setDevice(T1cPublicKey device) {
        this.device = device;
    }

    public T1cPublicKey getSsl() {
        return ssl;
    }

    public void setSsl(T1cPublicKey ssl) {
        this.ssl = ssl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof T1cAdminPublicKeys)) return false;

        T1cAdminPublicKeys that = (T1cAdminPublicKeys) o;

        if (ds != null ? !ds.equals(that.ds) : that.ds != null) return false;
        if (device != null ? !device.equals(that.device) : that.device != null) return false;
        return ssl != null ? ssl.equals(that.ssl) : that.ssl == null;
    }

    @Override
    public int hashCode() {
        int result = ds != null ? ds.hashCode() : 0;
        result = 31 * result + (device != null ? device.hashCode() : 0);
        result = 31 * result + (ssl != null ? ssl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "T1cAdminPublicKeys{" +
                "ds=" + ds +
                ", device=" + device +
                ", ssl=" + ssl +
                '}';
    }
}