package com.example.infoloko.controller;


import com.example.infoloko.model.Summary;
import com.example.infoloko.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/summaries")
public class SummaryController {

    private final SummaryService summaryService;

    @Autowired
    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/getAll")
    public ResponseEntity<List<Summary>> getAllSummaryAsStream() {
        List<Summary> summaries = summaryService.getAllSummary();
        return ResponseEntity.ok(summaries);
    }
}
