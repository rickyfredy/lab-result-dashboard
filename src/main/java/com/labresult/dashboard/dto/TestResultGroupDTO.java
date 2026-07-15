package com.labresult.dashboard.dto;

import java.util.List;

public class TestResultGroupDTO {

    private String title;
    private List<TestResultDTO> results;
    private long totalCount;
    private int loadedCount;
    private boolean hasMore;

    public TestResultGroupDTO() {
    }

    public TestResultGroupDTO(String title, List<TestResultDTO> results, long totalCount,
                              int loadedCount, boolean hasMore) {
        this.title = title;
        this.results = results;
        this.totalCount = totalCount;
        this.loadedCount = loadedCount;
        this.hasMore = hasMore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TestResultDTO> getResults() {
        return results;
    }

    public void setResults(List<TestResultDTO> results) {
        this.results = results;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getLoadedCount() {
        return loadedCount;
    }

    public void setLoadedCount(int loadedCount) {
        this.loadedCount = loadedCount;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
