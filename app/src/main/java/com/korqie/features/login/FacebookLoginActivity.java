/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.korqie.features.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.korqie.R;
import com.korqie.features.DisplayMessageActivity;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FacebookLoginActivity extends FragmentActivity {

    private static final String PERMISSION = "publish_actions";
    private static final Location SEATTLE_LOCATION = new Location("") {
        {
            setLatitude(47.6097);
            setLongitude(-122.3331);
        }
    };

    private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.features.login.facebooklogin:PendingAction";
    public final static String EXTRA_MESSAGE = "blah";
    private boolean loggedIn = true;

    private LoginButton loginButton;
    private ProfilePictureView profilePictureView;
    private TextView greeting;
    private TextView extraText;
    private ImageView imageView;
    private PendingAction pendingAction = PendingAction.NONE;
    private ViewGroup controlsContainer;
    private GraphUser user;
    private GraphPlace place;
    private List<GraphUser> tags;

    // Declare Variables
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private int[] flag;
    private CirclePageIndicator mIndicator;

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("FacebookLoginActivity", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("FacebookLoginActivity", "Success!");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }

        setContentView(R.layout.activity_facebook_login);

        flag = new int[] { R.drawable.intro1, R.drawable.intro2,
                R.drawable.intro3 };

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(FacebookLoginActivity.this, flag);
        viewPager.setAdapter(adapter);

        // ViewPager Indicator
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);

        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions(Arrays.asList("user_location", "user_birthday", "user_likes", "user_photos"));
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                FacebookLoginActivity.this.user = user;
                if (user != null){
                    startDisplayMessageActivity();
                }
                updateUI();
            }
        });
/*
        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        greeting = (TextView) findViewById(R.id.greeting);
        extraText = (TextView) findViewById(R.id.extra_text);
        imageView = (ImageView) findViewById(R.id.imageViewFB);
*/

        controlsContainer = (ViewGroup) findViewById(R.id.main_ui_container);
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be launched into.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (pendingAction != PendingAction.NONE &&
                (exception instanceof FacebookOperationCanceledException ||
                        exception instanceof FacebookAuthorizationException)) {
            new AlertDialog.Builder(FacebookLoginActivity.this)
                    .setTitle(R.string.cancelled)
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null)
                    .show();
            pendingAction = PendingAction.NONE;
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {
         /*   handlePendingAction();*/
        }
        updateUI();
    }

    private void startDisplayMessageActivity(){
        Session session = Session.getActiveSession();
        Intent intent = new Intent(FacebookLoginActivity.this, DisplayMessageActivity.class);
        String message = "What's up Tim?";
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra("facebookSession", session);
        startActivity(intent);
    }
    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());

        if (enableButtons && user != null) {
           /* profilePictureView.setProfileId(user.getId());
            greeting.setText(getString(R.string.hello_user, user.getFirstName()) + " Your birthday is " + user.getBirthday());*/
/*
            makeGraphAPIRequest(session);
*/
            loggedIn = true;
        } else {

    /*        profilePictureView.setProfileId(null);
            greeting.setText(null);
            extraText.setText(null);
            imageView.setImageBitmap(null);*/
            loggedIn = false;
        }
    }


    private void makeGraphAPIRequest(final Session session) {
/*
        Request rq = displayUserPhotos(session);
*/
        Request rq = displayLikes(session);
        rq.executeAsync();
    }

    private Request displayUserPhotos(final Session session){
        Request rq = new Request(Session.getActiveSession(), "me/photos", null, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                try{
                    String text = "";
                    text += "These are the url of your pictures: \n";
                    String urlToDisplay = "";
                    JSONArray photos = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
                    for(int i = 0; i < photos.length(); i++){
                        //JSONObject is used when json start with {}
                        //JSONArray is used when json start with []
                        //http://stackoverflow.com/questions/12289844
                        JSONObject photo = photos.optJSONObject(i);
                        JSONArray images = photo.optJSONArray("images");
                        JSONObject largestImage = images.optJSONObject(0);
                        text += largestImage.optString("source") + "\n";
                        urlToDisplay = largestImage.optString("source");
                    }
                    //http://developer.android.com/guide/practices/screens_support.html
                    int dpi = getResources().getDisplayMetrics().densityDpi;
                    double dpToPixels = dpi / 160;
                    Picasso.with(getBaseContext())
                            .load(urlToDisplay)
                            .resize((int)(200 * dpToPixels) ,0)
                            .into(imageView);

                    extraText.setText(text);
                    System.out.println(text);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return rq;
    }
    private Request displayLikes(final Session session){
        Request rq = new Request(Session.getActiveSession(), "me/likes", null, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                try{
                    String text = "";
                    text += "These are the things you like: \n";
                    JSONArray likes = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
                    for(int i = 0; i < likes.length(); i++){
                        JSONObject like = likes.optJSONObject(i);
                        text += like.optString("name") + "\n";
                    }
                    extraText.setText(text);
                    System.out.println(text);
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return rq;
    }

    private interface GraphObjectWithId extends GraphObject {
        String getId();
    }

}
