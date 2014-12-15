package com.korqie.network.endpoints;

import com.korqie.models.user.ApiResponse;
import com.korqie.models.login.UserLogin;
import com.korqie.models.user.UserRegister;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * An API endpoint interface for Users.
 */
public interface UsersEndpoint {

  @POST("/users")
  ApiResponse register(@Body UserRegister userRegister);

  @POST("/auth/login")
  Response login(@Body UserLogin userLogin);
}
