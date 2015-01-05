package com.korqie.network.requests;

import com.korqie.models.user.UserApiResponse;
import com.korqie.network.endpoints.UsersEndpoint;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * HTTP request to get more info on the logged in {@code User}.
 */
public class GetUserRequest extends RetrofitSpiceRequest<UserApiResponse, UsersEndpoint> {

  private final String cookieValue;
  private final String userId;

  public GetUserRequest(String cookieValue, String userId) {
    super(UserApiResponse.class, UsersEndpoint.class);
    this.cookieValue = cookieValue;
    this.userId = userId;
  }

  @Override
  public UserApiResponse loadDataFromNetwork() {
    return getService().getUser(cookieValue, userId);
  }
}
