import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UserTest {

    private static RequestSpecification requestSpec;

    static Stream<Arguments> userListData() {
        return Stream.of(
                arguments(1, 6, 12, 2, 1, "george.bluth@reqres.in", "George", "Bluth", "https://reqres.in/img/faces/1-image.jpg"),
                arguments(2, 6, 12, 2, 7, "michael.lawson@reqres.in", "Michael", "Lawson", "https://reqres.in/img/faces/7-image.jpg")
        );
    }

    static Stream<Arguments> userData() {
        return Stream.of(
                arguments(1, "george.bluth@reqres.in", "George", "Bluth", "https://reqres.in/img/faces/1-image.jpg"),
                arguments(2, "janet.weaver@reqres.in", "Janet", "Weaver", "https://reqres.in/img/faces/2-image.jpg"),
                arguments(3, "emma.wong@reqres.in", "Emma", "Wong", "https://reqres.in/img/faces/3-image.jpg"),
                arguments(4, "eve.holt@reqres.in", "Eve", "Holt", "https://reqres.in/img/faces/4-image.jpg"),
                arguments(5, "charles.morris@reqres.in", "Charles", "Morris", "https://reqres.in/img/faces/5-image.jpg"),
                arguments(6, "tracey.ramos@reqres.in", "Tracey", "Ramos", "https://reqres.in/img/faces/6-image.jpg"),
                arguments(7, "michael.lawson@reqres.in", "Michael", "Lawson", "https://reqres.in/img/faces/7-image.jpg")
        );
    }


    @BeforeAll
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api/users")
                .build();
    }


    public void getUserListTest() {
        given()
                .spec(requestSpec)
                .queryParam("page", "2").
                when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .body(matchesJsonSchemaInClasspath("userList.json"))
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .log().all();
    }

    @ParameterizedTest
    @MethodSource("userListData")
    public void getUserTest(Integer pageId, Integer perPage, Integer total, Integer totalPages, Integer userId, String email, String fristName, String lastName, String avatar) {
       RestService.getUsersService().getUserList(pageId)
                .then()
                .body("page", equalTo(pageId))
                .body("per_page", equalTo(perPage))
                .body("total", equalTo(total))
                .body("total_pages", equalTo(totalPages))
                .rootPath("data")
                .body("id[0]", equalTo(userId))
                .body("email[0]", equalTo(email))
                .body("first_name[0]", equalTo(fristName))
                .body("last_name[0]", equalTo(lastName))
                .body("avatar[0]", equalTo(avatar))
                .contentType("application/json; charset=utf-8")
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .log().all();
    }


    @ParameterizedTest
    @MethodSource("userData")
    public void getSingleUserTest2(Integer id, String email, String firstName, String lastName, String avatar) {
        given()
                .spec(requestSpec)
                .pathParams("users", id).
                when()
                .get("/{users}")
                .then()
                .rootPath("data")
                .body("id", equalTo(id))
                .body("email", equalTo(email))
                .body("first_name", equalTo(firstName))
                .body("last_name", equalTo(lastName))
                .body("avatar", equalTo(avatar))
                .contentType("application/json; charset=utf-8")
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .log().all();
    }
}