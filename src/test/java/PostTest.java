import org.junit.jupiter.api.Test;
import posts.Post;

import static org.hamcrest.Matchers.equalTo;

public class PostTest {

    private final Post addPostBody = (Post) FileHelper.generateObjectFromResource("addPostBody.json", Post.class);


    private final Post editPostBody = (Post) FileHelper.generateObjectFromResource("editPostBody.json", Post.class);

    @Test
    public void addPostTest() {
        Post post = new Post();
        post.setTitle("Test123");
        post.setAuthor("Przemek");


        Integer addedPostID = RestService.getPostsService().addPost(post)
                .then()
                .body("author", equalTo(post.getAuthor()))
                .body("title", equalTo(post.getTitle()))
                .statusCode(201)
                .extract().path("id");

        RestService.getPostsService().getPost(addedPostID)
                .then()
                .body("id", equalTo(addedPostID))
                .body("author", equalTo(post.getAuthor()))
                .body("title", equalTo(post.getTitle()))
                .statusCode(200);

    }
}
