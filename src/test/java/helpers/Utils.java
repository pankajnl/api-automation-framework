package helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    private static RequestSpecification request;
    private static PrintStream log;
    private static Properties properties = new Properties();
    /*Below method is used to create a request specification that can be reused across multiple test cases.
    It sets the base URI and content type for the requests.
    The method checks if the request specification has already been created (i.e., if it's null) and only creates it once, ensuring that the same configuration is used throughout the tests.
    Also logs request and response details to a file named "logging.txt" for better debugging and traceability of API interactions.
    */
    public static RequestSpecification requestSpecification() {
        if(log==null) {
            try {
                log = new PrintStream(new FileOutputStream("logging.txt"));
            } catch (FileNotFoundException e) {
                // Fallback to console logging if file cannot be created
                log = System.out;
            }
        }
        if(request==null) {
            request = new RequestSpecBuilder().setBaseUri(getBaseUrl())
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return request;
    }

    // This method loads the global properties from a file and retrieves the value associated with the provided key.
    public static String getGlobalValue(String key) {
        // This method loads the global properties from a file and retrieves the value associated with the provided key.
        if (properties.isEmpty()) {
            try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/config/global.properties")) {
                properties.load(fileInputStream);
            } catch (IOException e) {
                // Log the exception and return null to indicate failure
                throw new RuntimeException("Error loading global properties", e);
            }
        }
        return properties.getProperty(key);
    }

    // This method retrieves the base URL for the API from the global properties file based on the environment specified as a system property.
    public static String getBaseUrl() {
        String env = System.getProperty("env");

        if (env == null || env.trim().isEmpty()) {
            throw new RuntimeException("Environment is not specified. Please run with a valid Maven profile such as -Pdev, -Ptst, -Pqa or -Pprd.");
        }

        String baseUrl = getGlobalValue(env + ".baseUrl");

        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new RuntimeException("Base URL is not configured for environment: " + env);
        }

        return baseUrl;
    }
}
