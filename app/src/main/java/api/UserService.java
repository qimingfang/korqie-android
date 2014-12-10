package api;

import api.model.Response;
import api.model.UserRegister;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by qf26 on 12/9/14.
 */
public interface UserService {

  @POST("/users")
  Response register(@Body UserRegister userRegister);
}
