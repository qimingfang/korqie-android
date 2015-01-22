package com.korqie.features.history;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.korqie.BuildConfig;
import com.korqie.KorqieApplication;
import com.korqie.R;
import com.korqie.features.SharedPrefKeys;
import com.korqie.models.user.User;
import com.korqie.models.user.UserApiResponse;
import com.korqie.network.requests.GetUserRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Displays the history of users this user encountered
 */
public class HistoryActivity extends Activity {

  private final class UserHistoryRequestListener implements RequestListener<UserApiResponse> {

    @Override
    public void onRequestFailure(SpiceException spiceException) {
      Toast.makeText(HistoryActivity.this, "Failed to get history", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(UserApiResponse apiResponse) {
      User user = apiResponse.getFirstValue();

      listAdapter.add("success!");

      if (user != null) {
        for (String favorite : user.getFavorites()) {
          listAdapter.add(favorite);
        }
      }
    }
  }

  @Inject SpiceManager spiceManager;
  @InjectView(R.id.historyListView) ListView historyListView;

  private KorqieApplication app;
  private ArrayAdapter<String> listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_history);
    ButterKnife.inject(this);

    app = (KorqieApplication) getApplication();
    app.inject(this);

    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, new ArrayList<String>());
    listAdapter.add("Before fetch");

    historyListView.setAdapter(listAdapter);

    fetchHistoryList();
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

  private void fetchHistoryList() {
    SharedPreferences settings = getSharedPreferences(BuildConfig.SHARED_PREFS_FILE, 0);
    String cookieValue = settings.getString(SharedPrefKeys.COOKIE.name(), null);
    String userId = settings.getString(SharedPrefKeys.ID.name(), null);

    if (cookieValue != null && userId != null) {
      spiceManager.execute(new GetUserRequest(cookieValue, userId),
          new UserHistoryRequestListener());
    }
  }
}
