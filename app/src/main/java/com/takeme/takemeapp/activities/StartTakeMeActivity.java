package com.takeme.takemeapp.activities;

import android.app.Activity;
import android.content.Intent;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.takeme.models.UserToken;
import com.takeme.services.RegistrationDeviceUtil;
import com.takeme.services.UserGetByFacebookTask;
import com.takeme.services.UserSignViaFacebookTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * This class represent the Start activity of TakeMe application
 */
public class StartTakeMeActivity extends Activity implements
        UserSignViaFacebookTask.UserSignViaFacebookResponse,
        UserGetByFacebookTask.UserGetByFacebookResponse,
        RegistrationDeviceUtil.GetRegistrationDeviceIdCallBack{

    private CallbackManager callbackManager;

    private String email;
    private String firstName;
    private String lastName;

    private TakeMeApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mApp =  (TakeMeApplication)getApplication();

        // Set Facebook login button
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_start_take_me);

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setText("Facebook");
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
                // Set on Click Facebook button
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        // Tried to get user facebook data
                        GraphRequest graphRequestAsyncTask =
                                GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {

                                            @Override
                                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                                signWithFacebook(jsonObject);
                                            }
                                        }
                                );

                        // Get the first name , last name , email of user
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, email, first_name, last_name");
                        graphRequestAsyncTask.setParameters(parameters);
                        graphRequestAsyncTask.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
                    }
                });

        // If the application have user go to main activity
        if(mApp.getCurrentUser() != null){
            Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
            startActivity(intentToMain);
            finish();
        }

        // Tried to login via facebook
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

    /**
     * Go to sign in via Email
     * @param view
     */
    public void onSignIn(View view)
    {
        Intent intentToSignIn = new Intent(this, SignInTakeMeActivity.class);
        startActivity(intentToSignIn);
    }

    /**
     * Go to sign up via Email
     * @param view
     */
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

    /**
     * Sign Up via Facebook
     * @param jsonObject - Json data from facebook
     */
    private void signWithFacebook(JSONObject jsonObject)
    {
        try {
            mApp.showProgress(this);

            // Get email first & last name
            this.email = jsonObject.getString("email");
            this.firstName = jsonObject.getString("first_name");
            this.lastName = jsonObject.getString("last_name");

            // Get the registration device id.
            RegistrationDeviceUtil.getInstance().getRegId(this);

        } catch (JSONException e) {
            mApp.hideProgress();
            Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
            LoginManager.getInstance().logOut();
        }

    }

    /**
     * If the user already connect to application via facebook
     * tried to login to take me application via facebook token.
     * @param currentAccessToken
     */
    private void signViaFacebookToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
            mApp.showProgress(this);
            UserGetByFacebookTask userGetByFacebookTask = new UserGetByFacebookTask(currentAccessToken.getToken(),this);
            userGetByFacebookTask.getUserByFacbook();

        }
    }

    /**
     * Success to register to application via facebook
     * @param id
     */
    @Override
    public void onRegisterSuccess(UserToken id) {
        mApp.hideProgress();

        // Set the current user
        this.mApp.setCurrentUser(id.getId());
        Toast.makeText(StartTakeMeActivity.this, "Connected via Facebook", Toast.LENGTH_SHORT).show();

        // Go to main activity
        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        startActivity(intentToMain);
        finish();
    }

    /**
     * Failed to connect via facebook
     */
    @Override
    public void onRegisterFailed() {
        mApp.hideProgress();
        Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();
    }

    /**
     * Success to login via exist facebook user token
     * @param user
     */
    @Override
    public void onUserGetByFacebookSuccess(UserToken user) {
        mApp.hideProgress();

        // Set the current user
        this.mApp.setCurrentUser(user.getId());

        // Go to main activity
        Intent intentToMain = new Intent(StartTakeMeActivity.this, MainTakeMeActivity.class);
        startActivity(intentToMain);

        finish();
    }

    /**
     * Failed to sign in via exist facebook user token
     */
    @Override
    public void onUserGetByFacebookFailed() {
        mApp.hideProgress();
        Toast.makeText(StartTakeMeActivity.this, "Can't connected via Facebook", Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();
    }

    /**
     * Connection failed
     * @param t
     */
    @Override
    public void onRestCallError(Throwable t) {
        mApp.hideProgress();
        Toast.makeText(StartTakeMeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();

    }

    /**
     * Sucees to get registration id
     * @param regId - registration id
     */
    @Override
    public void onGetRegistrationDeviceId(String regId) {

        // Sign up via facebook
        UserSignViaFacebookTask userSignViaFacebookTask =
                new UserSignViaFacebookTask(
                        this.email,
                        this.firstName,
                        this.lastName,
                        regId,
                        AccessToken.getCurrentAccessToken().getToken(),
                        this);

        userSignViaFacebookTask.signViaFacebook();

    }
}
