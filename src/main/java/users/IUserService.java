package users;

import io.restassured.response.Response;

public interface IUserService {
    Response getUser(Integer userId);

    Response getUserList(Integer pageId);


}
