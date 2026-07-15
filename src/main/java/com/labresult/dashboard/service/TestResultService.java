package com.labresult.dashboard.service;

import com.labresult.dashboard.dto.HistoricalDataDTO;
import com.labresult.dashboard.dto.TestResultDTO;
import com.labresult.dashboard.dto.TestResultGroupDTO;
import com.labresult.dashboard.entity.TestResult;
import com.labresult.dashboard.repository.TestResultRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;

    private static final int INITIAL_PAGE_SIZE = 5;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Pattern NORMAL_RANGE_PATTERN = Pattern.compile("(\\d+\\.?\\d*)\\s*[-–]\\s*(\\d+\\.?\\d*)");

    public TestResultService(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    public List<TestResultGroupDTO> getTestResultsGroupedByTitle(Integer patientId) {
        List<String> titles = testResultRepository.findDistinctTitlesByPatientId(patientId);
        List<TestResultGroupDTO> groups = new ArrayList<>();

        for (String title : titles) {
            Page<TestResult> page = testResultRepository.findByPatientIdAndTitleOrderByTestDateDesc(
                    patientId, title, PageRequest.of(0, INITIAL_PAGE_SIZE));
            long totalCount = testResultRepository.countByPatientIdAndTitle(patientId, title);
            int loadedCount = page.getNumberOfElements();

            List<TestResultDTO> dtos = page.getContent().stream()
                    .map(this::toDTO)
                    .toList();

            groups.add(new TestResultGroupDTO(title, dtos, totalCount, loadedCount,
                    loadedCount < totalCount));
        }

        return groups;
    }

    public TestResultGroupDTO getMoreResults(Integer patientId, String title, int offset, int limit) {
        int page = offset / limit;
        Page<TestResult> pageResult = testResultRepository.findByPatientIdAndTitleOrderByTestDateDesc(
                patientId, title, PageRequest.of(page, limit));
        long totalCount = testResultRepository.countByPatientIdAndTitle(patientId, title);
        int loadedCount = offset + pageResult.getNumberOfElements();

        List<TestResultDTO> dtos = pageResult.getContent().stream()
                .map(this::toDTO)
                .toList();

        return new TestResultGroupDTO(title, dtos, totalCount, loadedCount,
                loadedCount < totalCount);
    }

    public List<String> getDistinctTitles(Integer patientId) {
        return testResultRepository.findDistinctTitlesByPatientId(patientId);
    }

    public List<String> getDistinctTestNames(Integer patientId, String title) {
        return testResultRepository.findDistinctTestNamesByPatientIdAndTitle(patientId, title);
    }

    public HistoricalDataDTO getHistoricalData(Integer patientId, String title,
                                               String testName, String period) {
        List<TestResult> results;

        if ("3y+".equals(period)) {
            LocalDateTime cutoffDate = LocalDateTime.now().minusYears(3);
            results = testResultRepository.findHistoricalBefore(patientId, title, testName, cutoffDate);
        } else {
            LocalDateTime startDate = calculateStartDate(period);
            results = testResultRepository.findHistoricalAfter(patientId, title, testName, startDate);
        }

        HistoricalDataDTO dto = new HistoricalDataDTO();
        dto.setTitle(title);
        dto.setTestName(testName);
        dto.setTotalCount(results.size());

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        List<String> abnormalFlags = new ArrayList<>();
        String unit = null;
        String normalRange = null;

        for (TestResult result : results) {
            Double numericValue = parseNumericValue(result.getResultValue());
            if (numericValue != null) {
                labels.add(result.getTestDate().format(DATE_FORMATTER));
                values.add(numericValue);
                abnormalFlags.add(Boolean.TRUE.equals(result.getIsAbnormal()) ? "abnormal" : "normal");

                if (unit == null && result.getResultUnit() != null) {
                    unit = result.getResultUnit();
                }
                if (normalRange == null && result.getNormalRange() != null) {
                    normalRange = result.getNormalRange();
                }
            }
        }

        dto.setLabels(labels);
        dto.setValues(values);
        dto.setAbnormalFlags(abnormalFlags);
        dto.setUnit(unit);
        dto.setNormalRange(normalRange);

        if (normalRange != null) {
            double[] range = parseNormalRange(normalRange);
            if (range != null) {
                dto.setNormalRangeMin(range[0]);
                dto.setNormalRangeMax(range[1]);
            }
        }

        return dto;
    }

    private LocalDateTime calculateStartDate(String period) {
        LocalDateTime now = LocalDateTime.now();
        return switch (period) {
            case "3m" -> now.minusMonths(3);
            case "6m" -> now.minusMonths(6);
            case "1y" -> now.minusYears(1);
            case "2y" -> now.minusYears(2);
            case "3y" -> now.minusYears(3);
            default -> now.minusYears(1);
        };
    }

    private Double parseNumericValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private double[] parseNormalRange(String normalRange) {
        Matcher matcher = NORMAL_RANGE_PATTERN.matcher(normalRange);
        if (matcher.find()) {
            try {
                double min = Double.parseDouble(matcher.group(1));
                double max = Double.parseDouble(matcher.group(2));
                return new double[]{min, max};
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private TestResultDTO toDTO(TestResult entity) {
        return new TestResultDTO(
                entity.getId(),
                entity.getTestName(),
                entity.getResultValue(),
                entity.getResultUnit(),
                entity.getIsAbnormal(),
                entity.getNormalRange(),
                entity.getTestDate()
        );
    }
}
