package korqie.korqie;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by qf26 on 12/7/14.
 */
public class KorqieApplication extends Application {
  private ObjectGraph graph;

  @Override public void onCreate() {
    super.onCreate();
    System.out.println("OnCreate for main application!");

    graph = ObjectGraph.create(getModules().toArray());
  }

  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new AppModule()
    );
  }

  public void inject(Object object) {
    graph.inject(object);
  }
}
