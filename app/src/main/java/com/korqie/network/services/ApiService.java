package com.korqie.network.services;

import com.korqie.BuildConfig;
import com.korqie.network.endpoints.UsersEndpoint;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Responsible for scheduling and executing API calls via RoboSpice.
 */
public class ApiService extends RetrofitGsonSpiceService {

  @Override
  public void onCreate() {
    super.onCreate();
    addRetrofitInterface(UsersEndpoint.class);
  }

  @Override
  protected String getServerUrl() {
    return BuildConfig.SEVER_URL;
  }
}
