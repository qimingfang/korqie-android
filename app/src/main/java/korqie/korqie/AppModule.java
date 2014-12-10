package korqie.korqie;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import api.UserService;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import transport.JsonHttpClient;

/**
 * Module that binds production dependencies.
 */
@Module (
  injects = PuzzleActivity.class,
  library = true
)
public class AppModule {

  private static final String KORQIE_ENDPOINT = "http://api.korqie.com";

  private final RestAdapter restAdapter;

  public AppModule() {
    this.restAdapter = new RestAdapter.Builder()
        .setEndpoint(KORQIE_ENDPOINT)
        .build();
  }

  @Provides
  @Singleton
  transport.HttpClient provideHttpClient() {
    return new JsonHttpClient(new OkHttpClient());
  }

  @Provides
  @Singleton
  UserService provideUserService() {
    return restAdapter.create(UserService.class);
  }
}
