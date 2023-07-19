package org.openmrs.module.Quiz.model;

import java.util.Date;

public class deviceMaintenance {
    private String reported_issue;
    private int reported_by;
    private String fault_found;
    private  String creator;
    private Date date_reported;
    private String uuid;
    private  String attribute_name;
    private String action_taken;

    public String getReported_issue() {
        return reported_issue;
    }

    public void setReported_issue(String reported_issue) {
        this.reported_issue = reported_issue;
    }

    public int getReported_by() {
        return reported_by;
    }

    public void setReported_by(int reported_by) {
        this.reported_by = reported_by;
    }

    public String getFault_found() {
        return fault_found;
    }

    public void setFault_found(String fault_found) {
        this.fault_found = fault_found;
    }


    public Date getDate_reported() {
        return date_reported;
    }

    public void setDate_reported(Date date_reported) {
        this.date_reported = date_reported;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
    }

    public String getAction_taken() {
        return action_taken;
    }

    public void setAction_taken(String action_taken) {
        this.action_taken = action_taken;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
