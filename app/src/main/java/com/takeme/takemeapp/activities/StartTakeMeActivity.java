package com.takeme.takemeapp.activities;

import android.app.Activity;
import android.content.Intent;


import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.takeme.takemeapp.R;


import org.json.JSONObject;

public class StartTakeMeActivity extends Activity {

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_start_take_me);

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setText("Facebook");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(StartTakeMeActivity.this, "Connected via Facebook", Toast.LENGTH_SHORT).show();


                GraphRequestAsyncTask graphRequestAsyncTask =
                        GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                        signWithFacebook();
                                    }
                                }).executeAsync();
            }

            @Override
            public void onCancel() {
                // Do Nothing
                // Toast.makeText(StartTakeMeActivity.this, "cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
        updateWithToken(AccessToken.getCurrentAccessToken());

//    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(
//                    AccessToken oldAccessToken,
//                    AccessToken currentAccessToken) {
//                // Set the access token using
//                // currentAccessToken when it's loaded or set.
//            }
//        };
//        // If the access token is available already assign it.
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //accessTokenTracker.stopTracking();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_take_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void onSignIn(View view)
    {
        Intent intentToSignIn = new Intent(this, SignInTakeMeActivity.class);
        startActivity(intentToSignIn);
    }

    public void onSignUp(View view)
    {
        Intent intent = new Intent(this, SignUpTakeMeActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void signWithFacebook()
    {
        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        startActivity(intentToMain);
    }

    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//
//                }
//            }, SPLASH_TIME_OUT);

            Intent i = new Intent(StartTakeMeActivity.this, MainTakeMeActivity.class);
            startActivity(i);

            finish();

        } else {
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    Intent i = new Intent(SplashScreen.this, Login.class);
//                    startActivity(i);
//
//                    finish();
//                }
//            }, SPLASH_TIME_OUT);
        }
    }
}
