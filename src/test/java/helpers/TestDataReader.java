package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestDataReader {

    public static <T> T readTestData(String filePath, Class<T> clazz) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            File file = new File("src/test/resources/testdata/" + filePath);
            return mapper.readValue(file, clazz);

        } catch (IOException e) {
            throw new RuntimeException("Unable to read test data file: " + filePath);
        }
    }
}