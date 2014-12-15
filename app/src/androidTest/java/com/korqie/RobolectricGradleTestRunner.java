package com.korqie;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

/**
 * Created by qf26 on 12/14/14.
 */
public class RobolectricGradleTestRunner extends RobolectricTestRunner {
  private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 20;

  public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  @Override
  protected AndroidManifest getAppManifest(Config config) {
    String manifestProperty = "../../../../../app/src/main/AndroidManifest.xml";
    String resProperty = "../../../../../app/src/main/res";
    return new AndroidManifest(Fs.fileFromPath(manifestProperty), Fs.fileFromPath(resProperty)) {
      @Override
      public int getTargetSdkVersion() {
        return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
      }
    };
  }
}
