package com.korqie.network.requests;

import com.korqie.models.login.UserLogin;
import com.korqie.models.user.ApiResponse;
import com.korqie.network.endpoints.UsersEndpoint;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;

/**
 * Created by qf26 on 12/11/14.
 */
public class UserLoginRequest extends RetrofitSpiceRequest<ApiResponse, UsersEndpoint> {

  private final Converter converter;
  private final UserLogin userLogin;

  public UserLoginRequest(Converter converter, UserLogin userLogin) {
    super(ApiResponse.class, UsersEndpoint.class);
    this.converter = converter;
    this.userLogin = userLogin;
  }

  @Override
  public ApiResponse loadDataFromNetwork() {
    Response loginResponse = getService().login(userLogin);

    ApiResponse response = null;

    try {
      response = (ApiResponse) converter.fromBody(loginResponse.getBody(), ApiResponse.class);
      response.setHeaders(loginResponse.getHeaders());
    } catch (ConversionException e) {
      // TODO(qimingfang): handle errors better
      e.printStackTrace();
    }

    return response;
  }
}
