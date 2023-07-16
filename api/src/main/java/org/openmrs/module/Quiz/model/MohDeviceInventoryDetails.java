package org.openmrs.module.Quiz.model;

import java.util.Date;

public class MohDeviceInventoryDetails {
    private String device_name;
    private String device_status;
    private String created_by;
    private String location_name;
    private Date created_at;
    private String uuid;
    private String device_category_name;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_status() {
        return device_status;
    }

    public void setDevice_status(String device_status) {
        this.device_status = device_status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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

    public String getDevice_category_name() {
        return device_category_name;
    }

    public void setDevice_category_name(String device_category_name) {
        this.device_category_name = device_category_name;
    }
}
