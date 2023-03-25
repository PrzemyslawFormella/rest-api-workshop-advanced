import comments.CommentsService;
import posts.PostsService;
import users.UserService;

public class RestService {

    public static UserService getUsersService() {
        return new UserService();
    }

    public static PostsService getPostsService() {
        return new PostsService();
    }

    public static CommentsService getCommentsService() {
        return new CommentsService();
    }
}
