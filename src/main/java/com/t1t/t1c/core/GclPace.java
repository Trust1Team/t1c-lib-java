package com.t1t.t1c.core;

import java.io.Serializable;

/**
 * @author Guillaume Vandecasteele
 * @since 2018
 */
public class GclPace implements Serializable {

    private String pin;
    private String mrz;
    private String can;
    private String puk;

    public String getPin() {
        return pin;
    }

    public void setPin(final String pin) {
        this.pin = pin;
    }

    public GclPace withPin(final String pin) {
        this.pin = pin;
        return this;
    }

    public String getMrz() {
        return mrz;
    }

    public void setMrz(final String mrz) {
        this.mrz = mrz;
    }

    public GclPace withMrz(final String mrz) {
        this.mrz = mrz;
        return this;
    }

    public String getCan() {
        return can;
    }

    public void setCan(final String can) {
        this.can = can;
    }

    public GclPace withCan(final String can) {
        this.can = can;
        return this;
    }

    public String getPuk() {
        return puk;
    }

    public void setPuk(final String puk) {
        this.puk = puk;
    }

    public GclPace withPuk(final String puk) {
        this.puk = puk;
        return this;
    }

    @Override
    public String toString() {
        return "GclLuxIdPace{" +
                "pin='" + pin + '\'' +
                ", mrz='" + mrz + '\'' +
                ", can='" + can + '\'' +
                ", puk='" + puk + '\'' +
                '}';
    }
}