package korqie.korqie;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import transport.JsonHttpClient;

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
  transport.HttpClient provideHttpClient() {
    return new JsonHttpClient(new OkHttpClient());
  }
}
