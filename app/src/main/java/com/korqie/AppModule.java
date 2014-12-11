package com.korqie;

import com.korqie.features.puzzle.PuzzleActivity;
import com.korqie.network.services.ApiService;
import com.octo.android.robospice.SpiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module that binds production dependencies.
 */
@Module (
  injects = PuzzleActivity.class,
  library = true
)
public class AppModule {

  @Provides
  @Singleton
  SpiceManager provideUserService() {
    return new SpiceManager(ApiService.class);
  }
}
