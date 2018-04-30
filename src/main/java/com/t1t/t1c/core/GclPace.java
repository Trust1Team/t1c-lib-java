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

    public void setPin(String pin) {
        this.pin = pin;
    }

    public GclPace withPin(String pin) {
        this.pin = pin;
        return this;
    }

    public String getMrz() {
        return mrz;
    }

    public void setMrz(String mrz) {
        this.mrz = mrz;
    }

    public GclPace withMrz(String mrz) {
        this.mrz = mrz;
        return this;
    }

    public String getCan() {
        return can;
    }

    public void setCan(String can) {
        this.can = can;
    }

    public GclPace withCan(String can) {
        this.can = can;
        return this;
    }

    public String getPuk() {
        return puk;
    }

    public void setPuk(String puk) {
        this.puk = puk;
    }

    public GclPace withPuk(String puk) {
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