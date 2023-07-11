package org.openmrs.module.Quiz.model;

import java.util.Date;

public class DeviceAnswers {
    private int AttributeName;
    private String AttributeValue;
    private String uuid;
    private int Creator;
    private Date CreateDate;

    public int getAttributeName() {
        return AttributeName;
    }

    public void setAttribute_Name(int attributeName) {
        AttributeName = attributeName;
    }

    public String getAttributeValue() {
        return AttributeValue;
    }

    public void setAttribute_Value(String attributeValue) {
        AttributeValue = attributeValue;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCreator() {
        return Creator;
    }

    public void setCreator(int creator) {
        Creator = creator;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }
}
