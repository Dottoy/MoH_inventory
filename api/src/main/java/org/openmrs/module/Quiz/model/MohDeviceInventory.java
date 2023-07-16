package org.openmrs.module.Quiz.model;

import java.util.Date;

public class MohDeviceInventory {
    private int inventory_id;
    private int device_id;
    private int device_status_id;
   private int created_by;

    public int getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(int current_location) {
        this.current_location = current_location;
    }

    private int current_location;
    private Date created_at;
    private String uuid;

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getDevice_status_id() {
        return device_status_id;
    }

    public void setDevice_status_id(int device_status_id) {
        this.device_status_id = device_status_id;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
