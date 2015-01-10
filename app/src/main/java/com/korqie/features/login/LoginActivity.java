package com.korqie.features.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.korqie.BuildConfig;
import com.korqie.KorqieApplication;
import com.korqie.R;
import com.korqie.features.SharedPrefKeys;
import com.korqie.features.history.HistoryActivity;
import com.korqie.models.ApiResponse;
import com.korqie.models.login.LoginApiResponse;
import com.korqie.models.login.UserLogin;
import com.korqie.network.requests.UserLoginRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.converter.Converter;

public class LoginActivity extends Activity {

  public static final String TAG = LoginActivity.class.getName();

  /**
   * A class to handle user registration events.
   */
  private final class RegisterUserRequestListener implements RequestListener<ApiResponse> {
    @Override
    public void onRequestFailure(SpiceException spiceException) {
      Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(ApiResponse apiResponse) {
      Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }
  }

  private final class LoginUserRequestListener implements RequestListener<LoginApiResponse> {
    @Override
    public void onRequestFailure(SpiceException spiceException) {
      spiceException.printStackTrace();
      Toast.makeText(LoginActivity.this, "Login failed. Invalid email/password",
          Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(LoginApiResponse apiResponse) {

      String cookieValue = apiResponse.getCookie();
      String id = apiResponse.getFirstValue().getUser();

      if (cookieValue != null && id != null) {
        SharedPreferences settings = getSharedPreferences(BuildConfig.SHARED_PREFS_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(SharedPrefKeys.ID.name(), id);
        editor.putString(SharedPrefKeys.COOKIE.name(), cookieValue);

        // Commit the edits
        editor.apply();

        // Navigate to the history screen.
        Intent intent = new Intent(LoginActivity.this, HistoryActivity.class);
        startActivity(intent);
        return;
      }

      // Server sent down malformed response?
      Toast.makeText(LoginActivity.this, "Login failed. Please update your client",
          Toast.LENGTH_SHORT).show();
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
    setContentView(R.layout.activity_login);
    ButterKnife.inject(this);

    app = (KorqieApplication) getApplication();
    app.inject(this);

    emailField.setText(BuildConfig.DEFAULT_EMAIL);
    passwordField.setText(BuildConfig.DEFAULT_PWD);

    button.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {

        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (email == null || email.length() == 0 || password == null || password.length() == 0) {
          Toast.makeText(LoginActivity.this, "Email and Password cannot be blank",
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
