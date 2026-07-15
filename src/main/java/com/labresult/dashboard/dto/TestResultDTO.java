package com.labresult.dashboard.dto;

import java.time.LocalDateTime;

public class TestResultDTO {

    private Integer id;
    private String testName;
    private String resultValue;
    private String resultUnit;
    private Boolean isAbnormal;
    private String normalRange;
    private LocalDateTime testDate;

    public TestResultDTO() {
    }

    public TestResultDTO(Integer id, String testName, String resultValue, String resultUnit,
                         Boolean isAbnormal, String normalRange, LocalDateTime testDate) {
        this.id = id;
        this.testName = testName;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.isAbnormal = isAbnormal;
        this.normalRange = normalRange;
        this.testDate = testDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }

    public Boolean getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(Boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }
}
