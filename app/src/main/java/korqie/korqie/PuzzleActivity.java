package korqie.korqie;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;

import korqie.myapplication.R;

public class PuzzleActivity extends Activity {

  public static final String TAG = PuzzleActivity.class.getName();

  @Inject transport.HttpClient httpClient;

  private KorqieApplication app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    app = (KorqieApplication) getApplication();
    app.inject(this);

    setContentView(R.layout.activity_puzzle);

    final Button button = (Button) findViewById(R.id.bt);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        httpClient.get("https://api.korqie.com/", new Callback() {
          @Override
          public void onFailure(Request request, IOException e) {
            // TODO(qimingfang): better logging
            System.out.println("request failed");
          }

          @Override
          public void onResponse(final Response response) throws IOException {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                TextView textView = (TextView) findViewById(R.id.textView);
                try {
                  textView.setText(response.body().string());
                } catch (IOException e) {

                }
              }
            });

          }
        });
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_puzzle, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
