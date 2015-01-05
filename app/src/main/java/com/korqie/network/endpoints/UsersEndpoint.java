package com.korqie.network.endpoints;

import com.korqie.models.login.UserLogin;
import com.korqie.models.user.UserApiResponse;
import com.korqie.models.user.UserRegister;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * An API endpoint interface for Users.
 */
public interface UsersEndpoint {

  @GET("/users/{id}")
  UserApiResponse getUser(@Header("cookie") String cookie, @Path("id") String id);

  @POST("/users")
  UserApiResponse register(@Body UserRegister userRegister);

  @POST("/auth/login")
  Response login(@Body UserLogin userLogin);
}
