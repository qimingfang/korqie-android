package transport;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Response;

/**
 * Client that issues POST and GET requests to a web service
 */
public interface HttpClient {

  /**
   * Issues an HTTP GET request on @param url
   *
   * @return {@link com.google.common.base.Optional#absent()} if error. {@link Response} otherwise
   */
  public void get(String url, Callback callback);
}
