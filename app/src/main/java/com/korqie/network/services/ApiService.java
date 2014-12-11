package com.korqie.network.services;

import com.google.common.annotations.VisibleForTesting;
import com.korqie.network.endpoints.UsersEndpoint;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Responsible for scheduling and executing API calls via RoboSpice.
 */
public class ApiService extends RetrofitGsonSpiceService {

  @VisibleForTesting
  static final String KORQIE_ENDPOINT = "http://api.korqie.com";

  @Override
  public void onCreate() {
    super.onCreate();
    addRetrofitInterface(UsersEndpoint.class);
  }

  @Override
  protected String getServerUrl() {
    return KORQIE_ENDPOINT;
  }
}
