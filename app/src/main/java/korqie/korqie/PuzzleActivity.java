package korqie.korqie;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import api.UserService;
import api.model.Response;
import api.model.UserRegister;
import butterknife.ButterKnife;
import korqie.myapplication.R;
import service.RegisterUserTask;

public class PuzzleActivity extends Activity {

  public static final String TAG = PuzzleActivity.class.getName();

  @Inject transport.HttpClient httpClient;
  @Inject UserService userService;

  private KorqieApplication app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    app = (KorqieApplication) getApplication();
    app.inject(this);
    ButterKnife.inject(this);

    setContentView(R.layout.activity_puzzle);

    final Button button = (Button) findViewById(R.id.bt);
    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {

        AsyncTask<UserRegister, Integer, Response> task = new RegisterUserTask(userService).execute(
            new UserRegister("t4", "t4@gmail.com", "t4", "thumb4"));

        try {
          Response response = task.get();
          System.out.println(response);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
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
