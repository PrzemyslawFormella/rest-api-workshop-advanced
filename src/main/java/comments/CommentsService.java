package comments;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CommentsService implements ICommentsService {

    private static final String USERS_ENDPOINT = "http://localhost:3000/comments";

    RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(USERS_ENDPOINT)
            .setContentType(ContentType.JSON)
            .build();

    @Override
    public Response getComment(Integer commentId) {
        Response response = given()
                .spec(requestSpec)
                .pathParam("commentId", commentId)
                .when()
                .get("{commentId}");
        return response;
    }

    @Override
    public Response getCommentList() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get();
        return response;
    }

    @Override
    public Response addComment(Comment comment) {
        Response response = given()
                .spec(requestSpec)
                .body(comment)
                .when()
                .post();
        return response;
    }

    @Override
    public Response editComment(Integer commentId, Comment comment) {
        Response response = given()
                .spec(requestSpec)
                .pathParam("commentId", commentId)
                .body(comment)
                .when()
                .put("{commentId}");
        return response;
    }

    @Override
    public Response deleteComment(Integer commentId) {
        Response response = given()
                .spec(requestSpec)
                .pathParam("commentId", commentId)
                .when()
                .delete("{commentId}");
        return response;
    }
}
