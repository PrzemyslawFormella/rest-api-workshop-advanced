import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class StarWarsTest {

    private static RequestSpecification requestSpec;

    @BeforeAll
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://swapi.dev/api/people")
                .build();
    }


    @Test
    public void getPeopleTest() {
        given()
                .spec(requestSpec)
//                .queryParam("page", "2").
                .when()
                .get()
                .then()
//
                .body("results.findAll {it.height >'180'}.name", hasItems("Darth Vader", "Biggs Darklighter", "Obi-Wan Kenobi"))
                .body("results.findAll {it.gender == 'female'}.size()", equalTo(2))

                .statusCode(200)
//                .time(lessThan(2L), TimeUnit.SECONDS)
                .log().all();
    }
}
