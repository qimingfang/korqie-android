package com.korqie.network.requests;

import com.korqie.models.ApiResponse;
import com.korqie.models.user.UserRegister;
import com.korqie.network.endpoints.UsersEndpoint;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Wrapper around HTTP request for user registration.
 */
public class UserRegisterRequest extends RetrofitSpiceRequest<ApiResponse, UsersEndpoint> {

  private final UserRegister userRegister;

  public UserRegisterRequest(UserRegister userRegister) {
    super(ApiResponse.class, UsersEndpoint.class);
    this.userRegister = userRegister;
  }

  @Override
  public ApiResponse loadDataFromNetwork() {
    return getService().register(userRegister);
  }
}
