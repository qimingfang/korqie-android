package transport;

import com.google.common.net.MediaType;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by qf26 on 12/7/14.
 */
public class JsonHttpClient implements HttpClient {
  private static final MediaType JSON = MediaType.JSON_UTF_8;

  private final OkHttpClient httpClient;

  public JsonHttpClient(OkHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public void get(String url, Callback callback) {
    Request request = new Request.Builder()
        .url(url)
        .build();

    httpClient.newCall(request).enqueue(callback);
  }
}
