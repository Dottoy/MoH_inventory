package org.openmrs.module.Quiz.model;

public class DhisMohReportHeader {
 private Integer   id;
 private Integer   report_id;
 private String   header_description;
 private String   filter_query;
 private Integer   category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public String getHeader_description() {
        return header_description;
    }

    public void setHeader_description(String header_description) {
        this.header_description = header_description;
    }

    public String getFilter_query() {
        return filter_query;
    }

    public void setFilter_query(String filter_query) {
        this.filter_query = filter_query;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
