import comments.Comment;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class CommentTest {

    @Test
    public void addCommentTest() {
        Comment comment = new Comment();
        comment.setBody("tresc");
        comment.setPostId(1);

        Comment comment2 = new Comment();
        comment.setBody("tresc2");
        comment.setPostId(1);

        Integer addedCommentID = RestService.getCommentsService().addComment(comment)
                .then()
                .body("body", equalTo(comment.getBody()))
                .body("postId", equalTo(comment.getPostId()))
                .statusCode(201)
                .extract().path("id");

        RestService.getCommentsService().getComment(addedCommentID)
                .then()
                .body("id", equalTo(addedCommentID))
                .body("body", equalTo(comment.getBody()))
                .body("postId", equalTo(comment.getPostId()))
                .statusCode(200);

        RestService.getCommentsService().getCommentList()
                .then()
                .body("id", hasItems(addedCommentID))
                .statusCode(200);

        comment.setBody("nowe4");
        RestService.getCommentsService().editComment(addedCommentID, comment)
                .then()
                .body("body", equalTo(comment.getBody()))
                .statusCode(200);


        RestService.getCommentsService().deleteComment(addedCommentID)
                .then()
                .statusCode(200);

        RestService.getCommentsService().getCommentList()
                .then()
                .body("id", not(hasItems(addedCommentID)))
                .statusCode(200)
                .log().all();
    }


}
