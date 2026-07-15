package com.labresult.dashboard.controller;

import com.labresult.dashboard.dto.HistoricalDataDTO;
import com.labresult.dashboard.dto.TestResultGroupDTO;
import com.labresult.dashboard.service.TestResultService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final TestResultService testResultService;

    public ApiController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @GetMapping("/test-results/more")
    public ResponseEntity<TestResultGroupDTO> loadMoreResults(
            @RequestParam Integer patientId,
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(testResultService.getMoreResults(patientId, title, offset, limit));
    }

    @GetMapping("/titles")
    public ResponseEntity<List<String>> getTitles(@RequestParam Integer patientId) {
        return ResponseEntity.ok(testResultService.getDistinctTitles(patientId));
    }

    @GetMapping("/test-names")
    public ResponseEntity<List<String>> getTestNames(
            @RequestParam Integer patientId,
            @RequestParam String title) {
        return ResponseEntity.ok(testResultService.getDistinctTestNames(patientId, title));
    }

    @GetMapping("/historical")
    public ResponseEntity<HistoricalDataDTO> getHistoricalData(
            @RequestParam Integer patientId,
            @RequestParam String title,
            @RequestParam String testName,
            @RequestParam(defaultValue = "1y") String period) {
        return ResponseEntity.ok(testResultService.getHistoricalData(patientId, title, testName, period));
    }
}
