package com.tangbear.mongobulk.springmongobulk.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.tangbear.mongobulk.springmongobulk.model.MobileSegmentModel;

import java.io.IOException;
import java.io.InputStream;

public class CsvUtil {

    private static final CsvMapper mapper = new CsvMapper();

    public static MappingIterator<MobileSegmentModel> reader(Class<MobileSegmentModel> model, InputStream file) throws IOException {
        CsvSchema schema = mapper.schemaFor(model).withHeader();
        MappingIterator<MobileSegmentModel> mi = mapper.readerFor(model).with(schema).readValues(file);
        return mi;
    }
}

