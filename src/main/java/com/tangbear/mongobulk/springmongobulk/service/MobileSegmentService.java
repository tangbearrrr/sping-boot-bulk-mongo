package com.tangbear.mongobulk.springmongobulk.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.tangbear.mongobulk.springmongobulk.model.MobileSegmentModel;
import com.tangbear.mongobulk.springmongobulk.repository.MobileSegmentRepository;
import com.tangbear.mongobulk.springmongobulk.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MobileSegmentService {

    private final MobileSegmentRepository repository;

    @Async("asyncExecutor")
    public void processFile(MultipartFile file) throws IOException {
        long startTime = System.currentTimeMillis();
        log.info("*** Start upload segment file ***");
        log.info("file name : {}", file.getOriginalFilename());
        MappingIterator<MobileSegmentModel> mi = CsvUtil.reader(MobileSegmentModel.class, file.getInputStream());
        save(mi);
        long endTime = System.currentTimeMillis();
        log.info("*** End upload segment file ***");
        System.out.println("That took " + (endTime - startTime) / 60000 + " minutes " + (endTime - startTime) % 60 + " seconds");
    }

    public void save(MappingIterator<MobileSegmentModel> mi) throws IOException {
        int i = 1;
        int total = 0;
        List<MobileSegmentModel> mobileSegmentList = new ArrayList<>();
        while (i <= 10000 && mi.hasNextValue()) {
           mobileSegmentList.add(mi.nextValue());

           if (mobileSegmentList.size() == 10000) {
               repository.bulkSave(mobileSegmentList);
               total = total + mobileSegmentList.size();
               i = 1;
               mobileSegmentList.clear();
           }
        }
        System.out.println("size : " + total);
    }
}
