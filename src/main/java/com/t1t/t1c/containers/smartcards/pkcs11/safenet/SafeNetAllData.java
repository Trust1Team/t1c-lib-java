package com.t1t.t1c.containers.smartcards.pkcs11.safenet;

import com.t1t.t1c.model.AllData;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class SafeNetAllData implements AllData {

    private List<GclSafeNetSlot> slots;

    public List<GclSafeNetSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<GclSafeNetSlot> slots) {
        this.slots = slots;
    }

    public SafeNetAllData withSlots(List<GclSafeNetSlot> slots) {
        this.slots = slots;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SafeNetAllData)) return false;

        SafeNetAllData that = (SafeNetAllData) o;

        return slots != null ? slots.equals(that.slots) : that.slots == null;
    }

    @Override
    public int hashCode() {
        return slots != null ? slots.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SafeNetAllData{" +
                "slots=" + slots +
                '}';
    }
}