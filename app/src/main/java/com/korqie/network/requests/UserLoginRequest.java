package com.korqie.network.requests;

import com.korqie.models.login.LoginApiResponse;
import com.korqie.models.login.UserLogin;
import com.korqie.network.endpoints.UsersEndpoint;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;

/**
 * A {@link RetrofitSpiceRequest} for user login.
 */
public class UserLoginRequest extends RetrofitSpiceRequest<LoginApiResponse, UsersEndpoint> {

  private final Converter converter;
  private final UserLogin userLogin;

  public UserLoginRequest(Converter converter, UserLogin userLogin) {
    super(LoginApiResponse.class, UsersEndpoint.class);
    this.converter = converter;
    this.userLogin = userLogin;
  }

  @Override
  public LoginApiResponse loadDataFromNetwork() {
    Response loginResponse = getService().login(userLogin);

    LoginApiResponse response = null;

    try {
      response = (LoginApiResponse) converter.fromBody(loginResponse.getBody(),
          LoginApiResponse.class);
      response.setHeaders(loginResponse.getHeaders());
    } catch (ConversionException e) {
      // TODO(qimingfang): handle errors better
      e.printStackTrace();
    }

    return response;
  }
}
