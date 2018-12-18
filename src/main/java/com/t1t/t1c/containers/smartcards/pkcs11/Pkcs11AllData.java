package com.t1t.t1c.containers.smartcards.pkcs11;


import com.t1t.t1c.model.AllData;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class Pkcs11AllData implements AllData {

    private List<GclPkcs11Slot> slots;

    public Pkcs11AllData(final List<GclPkcs11Slot> slots) {
        this.slots = slots;
    }

    public List<GclPkcs11Slot> getSlots() {
        return slots;
    }

    public void setSlots(final List<GclPkcs11Slot> slots) {
        this.slots = slots;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Pkcs11AllData)) return false;

        final Pkcs11AllData that = (Pkcs11AllData) o;

        return slots != null ? slots.equals(that.slots) : that.slots == null;
    }

    @Override
    public int hashCode() {
        return slots != null ? slots.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Pkcs11AllData{" +
                "slots=" + slots +
                '}';
    }
}