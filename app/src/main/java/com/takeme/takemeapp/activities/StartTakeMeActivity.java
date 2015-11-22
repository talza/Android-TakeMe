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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.takeme.models.UserToken;
import com.takeme.services.UserGetByFacebookTask;
import com.takeme.services.UserSignViaFacebookTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;


import org.json.JSONException;
import org.json.JSONObject;

public class StartTakeMeActivity extends Activity implements
        UserSignViaFacebookTask.UserSignViaFacebookResponse,
        UserGetByFacebookTask.UserGetByFacebookResponse{

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    private TakeMeApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mApp =  (TakeMeApplication)getApplication();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_start_take_me);

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setText("Facebook");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest graphRequestAsyncTask =
                        GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {

                                    @Override
                                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                        signWithFacebook(jsonObject);
                                    }
                                }
                        );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email");
                graphRequestAsyncTask.setParameters(parameters);
                graphRequestAsyncTask.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
            }
        });

//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
//                if(oldAccessToken.equals(newAccessToken)) {
//      //             updateWithToken(newAccessToken);
//                }
//            }
//        };

        signViaFacebookToken(AccessToken.getCurrentAccessToken());

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

    private void signWithFacebook(JSONObject jsonObject)
    {
        try {

            UserSignViaFacebookTask userSignViaFacebookTask =
                    new UserSignViaFacebookTask(
                            jsonObject.getString("email"),
                            jsonObject.getString("first_name"),
                            jsonObject.getString("last_name"),
                            null,
                            AccessToken.getCurrentAccessToken().getToken(),
                            this);

            userSignViaFacebookTask.signViaFacebook();

        } catch (JSONException e) {
            Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
        }

    }

    private void signViaFacebookToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {

            UserGetByFacebookTask userGetByFacebookTask = new UserGetByFacebookTask(currentAccessToken.getToken(),this);
            userGetByFacebookTask.getUserByFacbook();

        }
    }

    @Override
    public void onRegisterSuccess(UserToken id) {

        this.mApp.setCurrentUser(id.getId());
        Toast.makeText(StartTakeMeActivity.this, "Connected via Facebook", Toast.LENGTH_SHORT).show();

        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        startActivity(intentToMain);
    }

    @Override
    public void onRegisterFailed() {
        Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();
    }

    @Override
    public void onUserGetByFacebookSuccess(UserToken user) {
        this.mApp.setCurrentUser(user.getId());
        Intent intentToMain = new Intent(StartTakeMeActivity.this, MainTakeMeActivity.class);
        startActivity(intentToMain);

        finish();
    }

    @Override
    public void onUserGetByFacebookFailed() {
        Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
      //  LoginManager.getInstance().logOut();
    }

    @Override
    public void onRestCallError(Throwable t) {
        Toast.makeText(StartTakeMeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();

    }
}
