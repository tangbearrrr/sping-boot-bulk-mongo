package com.tangbear.mongobulk.springmongobulk.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.tangbear.mongobulk.springmongobulk.model.MobileSegmentModel;
import com.tangbear.mongobulk.springmongobulk.repository.MobileSegmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tangbear.mongobulk.springmongobulk.service.MobileSegmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MobileSegmentController {

    private final MobileSegmentService mobileSegmentService;

    private final MobileSegmentRepository repository;

    @GetMapping("/")
    public void export(HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String filename = "test.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<MobileSegmentModel> writer = new StatefulBeanToCsvBuilder<MobileSegmentModel>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        List<MobileSegmentModel> result = new ArrayList<>();
        for (int i = 1; i<= 10000000; i++) {
            result.add(new MobileSegmentModel("0970974974", "SEGMENT_" + i));
        }

        writer.write(result);

    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) throws IOException {
        mobileSegmentService.processFile(file);
        return ResponseEntity.ok().body("SUCCESS");
    }

    @DeleteMapping("/")
    public ResponseEntity delete() {
        repository.delete();
        return ResponseEntity.ok().body("DELETE SUCCESS");
    }
}
