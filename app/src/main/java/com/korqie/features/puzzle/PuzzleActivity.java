package com.korqie.features.puzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.korqie.KorqieApplication;
import com.korqie.R;
import com.korqie.models.login.UserLogin;
import com.korqie.models.user.ApiResponse;
import com.korqie.network.requests.UserLoginRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.converter.Converter;

public class PuzzleActivity extends Activity {

  public static final String TAG = PuzzleActivity.class.getName();

  /**
   * A class to handle user registration events.
   */
  private final class RegisterUserRequestListener implements RequestListener<ApiResponse> {
    @Override
    public void onRequestFailure(SpiceException spiceException) {
      Toast.makeText(PuzzleActivity.this, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(ApiResponse apiResponse) {
      Toast.makeText(PuzzleActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }
  }

  private final class LoginUserRequestListener implements RequestListener<ApiResponse> {
    @Override
    public void onRequestFailure(SpiceException spiceException) {
      Toast.makeText(PuzzleActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(ApiResponse apiResponse) {
      Toast.makeText(PuzzleActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
    }
  }

  @Inject SpiceManager spiceManager;
  @Inject Converter converter;

  @InjectView(R.id.emailField) TextView emailField;
  @InjectView(R.id.passwordField) TextView passwordField;
  @InjectView(R.id.loginButton) Button button;

  private KorqieApplication app;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_puzzle);
    ButterKnife.inject(this);

    app = (KorqieApplication) getApplication();
    app.inject(this);

    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(password)) {
          Toast.makeText(PuzzleActivity.this, "Email and Password cannot be blank",
              Toast.LENGTH_SHORT).show();
          return;
        }

        UserLogin userLogin = new UserLogin(email, password);
        spiceManager.execute(new UserLoginRequest(converter, userLogin),
            new LoginUserRequestListener());
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
