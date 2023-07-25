package org.openmrs.module.Quiz.model;

public class OpdReport {
 private String   dataElement;
 private String   categoryOptionCombo;
 private String   value;
 private Integer  elementId;
 private Integer   headerId;

    public String getDataElement() {
        return dataElement;
    }

    public void setDataElement(String dataElement) {
        this.dataElement = dataElement;
    }

    public String getCategoryOptionCombo() {
        return categoryOptionCombo;
    }

    public void setCategoryOptionCombo(String categoryOptionCombo) {
        this.categoryOptionCombo = categoryOptionCombo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public Integer getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Integer headerId) {
        this.headerId = headerId;
    }
}
