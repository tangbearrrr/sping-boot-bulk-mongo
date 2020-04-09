package com.tangbear.mongobulk.springmongobulk.repository;

import com.tangbear.mongobulk.springmongobulk.model.MobileSegmentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MobileSegmentRepository {

    private static final String MOBILE_SEGMENT_A = "mobileSegmentA";

    private final MongoTemplate mongoTemplate;


    public boolean bulkSave(List<MobileSegmentModel> segments) {
        mongoTemplate.insert(segments,MOBILE_SEGMENT_A);
        return true;
    }

    public boolean delete() {
        mongoTemplate.dropCollection(MOBILE_SEGMENT_A);
        return true;
    }
}
