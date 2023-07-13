package org.openmrs.module.Quiz.model;

import java.util.Date;

public class MohDeviceInventory {
    private int inventory_id;
    private int device_id;
    private int device_status_id;
    private String name;
    private int created_by;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
