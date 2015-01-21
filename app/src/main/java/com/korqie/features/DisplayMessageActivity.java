package com.korqie.features;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.korqie.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DisplayMessageActivity extends Activity {
    public static String FB_USER_PHOTO_ENDPOINT = "me/photos";
    public static String FB_USER_LIKE_ENDPOINT = "me/likes";

    @InjectView(R.id.display_message_text) TextView displayMessageText;
    @InjectView(R.id.imageViewFB) ImageView imageViewFB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        ButterKnife.inject(this);

        // Get the message from the intent
        Intent intent = getIntent();

        makeGraphAPIRequest();

    }

    @OnClick(R.id.newPictureButton)
    public void makeGraphAPIRequest() {
/*
        Request rq = displayLikes(session);
*/
        Request rq = displayUserPhotos();
        rq.executeAsync();
    }

    private Request displayUserPhotos(){
        Request rq = new Request(Session.getActiveSession(), FB_USER_PHOTO_ENDPOINT , null, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                try{
                    StringBuilder text = new StringBuilder();
                    String urlToDisplay = "";
                    JSONArray photos = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
                    List<String> listOfImageURL = new ArrayList<String>();

                    text.append("These are the url of your pictures: \n");
                    text.append("You have " + photos.length() + " pictures. \n");

                    for(int i = 0; i < photos.length(); i++){
                        //JSONObject is used when json start with {}
                        //JSONArray is used when json start with []
                        //http://stackoverflow.com/questions/12289844
                        JSONObject photo = photos.optJSONObject(i);
                        JSONArray images = photo.optJSONArray("images");
                        JSONObject largestImage = images.optJSONObject(0);
                        text.append(largestImage.optString("source") + "\n");
                        listOfImageURL.add(largestImage.optString("source"));
                    }
                    urlToDisplay = listOfImageURL.get((int) Math.floor(Math.random()*photos.length()));
                    //http://developer.android.com/guide/practices/screens_support.html
                    int dpi = getResources().getDisplayMetrics().densityDpi;
                    double dpToPixels = dpi / 160;
                    Picasso.with(getBaseContext())
                            .load(urlToDisplay)
                            .resize((int)(200 * dpToPixels) ,0)
                            .into(imageViewFB);

                    displayMessageText.setText(text);
                    System.out.println(text);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return rq;
    }

    private Request displayLikes(){
        Request rq = new Request(Session.getActiveSession(), FB_USER_LIKE_ENDPOINT, null, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                try{
                    StringBuilder text = new StringBuilder();
                    text.append("These are the things you like: \n");
                    JSONArray likes = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
                    for(int i = 0; i < likes.length(); i++){
                        JSONObject like = likes.optJSONObject(i);
                        text.append(like.optString("name") + "\n");
                    }
                    displayMessageText.setText(text);
                    /*System.out.println(text);*/
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return rq;
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
