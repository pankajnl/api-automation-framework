package helpers;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidator {
    // This method validates the response body against a JSON schema located in the classpath.
   public static void validateSchema(Response response, String schemaFileName){
       response.then().assertThat()
               .body(matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
   }
}
