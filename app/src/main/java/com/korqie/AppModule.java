package com.korqie;

import com.google.gson.Gson;
import com.korqie.features.history.HistoryActivity;
import com.korqie.features.login.LoginActivity;
import com.korqie.network.services.ApiService;
import com.octo.android.robospice.SpiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

/**
 * Module that binds production dependencies.
 */
@Module (
  injects = {
      LoginActivity.class,
      HistoryActivity.class
  },
  library = true
)
public class AppModule {

  @Provides
  @Singleton
  SpiceManager provideUserService() {
    return new SpiceManager(ApiService.class);
  }

  @Provides
  Converter proideGSonConverter() {
    return new GsonConverter(new Gson());
  }
}
