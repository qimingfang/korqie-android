package com.korqie.network.endpoints;

import com.korqie.models.user.Response;
import com.korqie.models.user.UserRegister;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * An API endpoint interface for Users.
 */
public interface UsersEndpoint {

  @POST("/users")
  Response register(@Body UserRegister userRegister);
}
