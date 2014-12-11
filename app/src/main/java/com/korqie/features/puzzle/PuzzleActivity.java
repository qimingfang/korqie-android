package com.korqie.features.puzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.korqie.KorqieApplication;
import com.korqie.models.user.Response;
import com.korqie.models.user.UserRegister;
import com.korqie.network.requests.UserRegisterRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import javax.inject.Inject;

import butterknife.ButterKnife;
import korqie.myapplication.R;

public class PuzzleActivity extends Activity {

  public static final String TAG = PuzzleActivity.class.getName();

  /**
   * A class to handle user registration events.
   */
  private final class RegisterUserRequestListener implements RequestListener<Response> {
    @Override
    public void onRequestFailure(SpiceException spiceException) {
      Toast.makeText(PuzzleActivity.this, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(Response response) {
      Toast.makeText(PuzzleActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }
  }

  @Inject SpiceManager spiceManager;

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

        UserRegister userRegister = new UserRegister("t5", "t5@gmail.com", "t5", "thumb5");
        spiceManager.execute(new UserRegisterRequest(userRegister),
            new RegisterUserRequestListener());
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

  @Override
  protected void onStart() {
    spiceManager.start(this);
    super.onStart();
  }

  @Override
  protected void onStop() {
    spiceManager.shouldStop();
    super.onStop();
  }
}
