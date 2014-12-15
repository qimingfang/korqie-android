package com.korqie;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Main Android application.
 */
public class KorqieApplication extends Application {
  private ObjectGraph graph;

  @Override public void onCreate() {
    super.onCreate();
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
