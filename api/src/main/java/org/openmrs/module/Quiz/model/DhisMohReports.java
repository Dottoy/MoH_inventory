package org.openmrs.module.Quiz.model;

public class DhisMohReports {
 private Integer   report_id;
 private String   report_title;
 private String   dataSet;
 private Integer   duration;

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public String getReport_title() {
        return report_title;
    }

    public void setReport_title(String report_title) {
        this.report_title = report_title;
    }

    public String getDataSet() {
        return dataSet;
    }

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
