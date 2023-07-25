package org.openmrs.module.Quiz.model;

public class DhisMohElement {
 private Integer   element_id;
 private String   element;
 private String   sql_query;
 private Integer   category;
 private String   data_element_uuid_dhis;

    public Integer getElement_id() {
        return element_id;
    }

    public void setElement_id(Integer element_id) {
        this.element_id = element_id;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getSql_query() {
        return sql_query;
    }

    public void setSql_query(String sql_query) {
        this.sql_query = sql_query;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getData_element_uuid_dhis() {
        return data_element_uuid_dhis;
    }

    public void setData_element_uuid_dhis(String data_element_uuid_dhis) {
        this.data_element_uuid_dhis = data_element_uuid_dhis;
    }
}
