package comments;

import io.restassured.response.Response;
import posts.Post;

public interface ICommentsService {

    Response getComment(Integer commentId);

    Response getCommentList();

    Response addComment(Comment comment);

    Response editComment(Integer commentId, Comment comment);

    Response deleteComment(Integer commentId);
}


