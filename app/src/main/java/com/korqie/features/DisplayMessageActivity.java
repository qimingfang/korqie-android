package com.korqie.features;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.korqie.R;
import com.korqie.features.login.FacebookLoginActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayMessageActivity extends Activity {
    private TextView displayMessageText;
    private ImageView imageView;
    private Button newPictureButton;
    private Session fbSession;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        imageView = (ImageView) findViewById(R.id.imageViewFB);
        displayMessageText = (TextView) findViewById(R.id.display_message_text);
        newPictureButton = (Button) findViewById(R.id.newPictureButton);


        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(FacebookLoginActivity.EXTRA_MESSAGE);
        fbSession = (Session) intent.getSerializableExtra("facebookSession");


        newPictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeGraphAPIRequest(fbSession);
            }
        });

        makeGraphAPIRequest(fbSession);

    }


    private void makeGraphAPIRequest(Session session) {
/*
        Request rq = displayLikes(session);
*/
        Request rq = displayUserPhotos(session);
        rq.executeAsync();
    }

    private Request displayUserPhotos(Session session){
        Request rq = new Request(Session.getActiveSession(), "me/photos", null, HttpMethod.GET, new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                try{
                    String text = "";
                    text += "These are the url of your pictures: \n";
                    String urlToDisplay = "";
                    JSONArray photos = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
                    List<String> listOfImageURL = new ArrayList<String>();
                    for(int i = 0; i < photos.length(); i++){
                        //JSONObject is used when json start with {}
                        //JSONArray is used when json start with []
                        //http://stackoverflow.com/questions/12289844
                        JSONObject photo = photos.optJSONObject(i);
                        JSONArray images = photo.optJSONArray("images");
                        JSONObject largestImage = images.optJSONObject(0);
                        text += largestImage.optString("source") + "\n";
                        listOfImageURL.add(largestImage.optString("source"));
                    }
                    urlToDisplay = listOfImageURL.get((int) Math.floor(Math.random()*photos.length()));
                    //http://developer.android.com/guide/practices/screens_support.html
                    int dpi = getResources().getDisplayMetrics().densityDpi;
                    double dpToPixels = dpi / 160;
                    Picasso.with(getBaseContext())
                            .load(urlToDisplay)
                            .resize((int)(200 * dpToPixels) ,0)
                            .into(imageView);

                    displayMessageText.setText(text);
                    System.out.println(text);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return rq;
    }

    private Request displayLikes(Session session){

        Request rq = new Request(session, "me/likes", null, HttpMethod.GET, new Request.Callback() {
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
