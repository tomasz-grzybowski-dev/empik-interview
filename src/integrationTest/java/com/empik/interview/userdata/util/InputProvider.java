package com.empik.interview.userdata.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

public final class InputProvider {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private InputProvider() {
    }

    public static <T> T getInputDto(String path, Class<T> clazz) throws IOException {
        File resource = new ClassPathResource(path).getFile();
        String fileContent = FileUtils.readFileToString(resource, Charset.defaultCharset());
        return objectMapper.readValue(fileContent, clazz);
    }
}
