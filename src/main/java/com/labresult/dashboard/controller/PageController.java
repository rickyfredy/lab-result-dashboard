package com.labresult.dashboard.controller;

import com.labresult.dashboard.entity.Patient;
import com.labresult.dashboard.service.PatientService;
import com.labresult.dashboard.service.TestResultService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final PatientService patientService;
    private final TestResultService testResultService;

    public PageController(PatientService patientService, TestResultService testResultService) {
        this.patientService = patientService;
        this.testResultService = testResultService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "home";
    }

    @GetMapping("/test-results")
    public String testResults(@RequestParam Integer patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            return "redirect:/";
        }
        model.addAttribute("patient", patient);
        model.addAttribute("patientId", patientId);
        model.addAttribute("groups", testResultService.getTestResultsGroupedByTitle(patientId));
        return "test-results";
    }

    @GetMapping("/historical")
    public String historical(@RequestParam Integer patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            return "redirect:/";
        }
        model.addAttribute("patient", patient);
        model.addAttribute("patientId", patientId);
        model.addAttribute("titles", testResultService.getDistinctTitles(patientId));
        return "historical";
    }
}
