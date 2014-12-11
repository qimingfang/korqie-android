package com.korqie.network.requests;

import com.korqie.models.user.Response;
import com.korqie.models.user.UserRegister;
import com.korqie.network.endpoints.UsersEndpoint;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by qf26 on 12/11/14.
 */
public class UserRegisterRequest extends RetrofitSpiceRequest<Response, UsersEndpoint> {

  private final UserRegister userRegister;

  public UserRegisterRequest(UserRegister userRegister) {
    super(Response.class, UsersEndpoint.class);
    this.userRegister = userRegister;
  }

  @Override
  public Response loadDataFromNetwork() {
    return getService().register(userRegister);
  }
}
