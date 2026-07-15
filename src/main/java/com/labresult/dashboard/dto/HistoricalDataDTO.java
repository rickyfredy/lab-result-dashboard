package com.labresult.dashboard.dto;

import java.util.List;

public class HistoricalDataDTO {

    private String title;
    private String testName;
    private String unit;
    private String normalRange;
    private Double normalRangeMin;
    private Double normalRangeMax;
    private List<String> labels;
    private List<Double> values;
    private List<String> abnormalFlags;
    private int totalCount;

    public HistoricalDataDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public Double getNormalRangeMin() {
        return normalRangeMin;
    }

    public void setNormalRangeMin(Double normalRangeMin) {
        this.normalRangeMin = normalRangeMin;
    }

    public Double getNormalRangeMax() {
        return normalRangeMax;
    }

    public void setNormalRangeMax(Double normalRangeMax) {
        this.normalRangeMax = normalRangeMax;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public List<String> getAbnormalFlags() {
        return abnormalFlags;
    }

    public void setAbnormalFlags(List<String> abnormalFlags) {
        this.abnormalFlags = abnormalFlags;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
