package com.takeme.takemeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.takeme.models.UserToken;
import com.takeme.services.RegistrationDeviceUtil;
import com.takeme.services.UserSignUpTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

/**
 * This class represent activity of sign up to application
 */
public class SignUpTakeMeActivity extends Activity implements
        UserSignUpTask.UserSignUpResponse,
        RegistrationDeviceUtil.GetRegistrationDeviceIdCallBack{

    private TakeMeApplication mApp;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_take_me);
        this.mApp =  (TakeMeApplication)getApplication();


        firstNameEditText   = (EditText)findViewById(R.id.etFirstName);
        lastNameEditText    = (EditText)findViewById(R.id.etLastName);
        phoneNumberEditText = (EditText)findViewById(R.id.etPhoneNumber);
        emailEditText       = (EditText)findViewById(R.id.etEmail);
        passwordEditText    = (EditText)findViewById(R.id.etPassword);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_sign_up_take_me, menu);
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
     * Sign up to application
     * @param view
     */
    public void onSignUp(View view)
    {

        // Check if the email is valid
        if (!isValidEmail(emailEditText.getText().toString()))
        {
            emailEditText.setError(getString(R.string.msg_invalid_email_format));
            return;
        }

        // Check Password validation
        if ( !isValidPassword(passwordEditText.getText().toString()))
        {
            passwordEditText.setError(getString(R.string.msg_invalid_password_format));
            return;
        }

        mApp.showProgress(this);


        // Get the registration device id.
        RegistrationDeviceUtil.getInstance().getRegId(this);

    }

    /**
     * Go to sign in activity
     * @param view
     */
    public void onSignIn(View view)
    {
        Intent intentToSignIn = new Intent(this, SignInTakeMeActivity.class);
        startActivity(intentToSignIn);

        finish();
    }

    /**
     * Check password in the format of at least 1 uppercase letter, 1 lowercase letter,
     * at least 1 digit , at least 8 letters.
     * @param target
     * @return
     */
    private  boolean isValidPassword(String target)
    {
        /*
        ^                 # start-of-string
        (?=.*[0-9])       # a digit must occur at least once
        (?=.*[a-z])       # a lower case letter must occur at least once
        (?=.*[A-Z])       # an upper case letter must occur at least once
        (?=.*[@#$%^&+=])  # a special character must occur at least once
        (?=\S+$)          # no whitespace allowed in the entire string
        .{8,}             # anything, at least eight places though
        $                 # end-of-string
         */
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        // Check Password validation
        return  !TextUtils.isEmpty(target) && target.matches(pattern);
    }

    /**
     * Check email format validation
     * @param target
     * @return
     */
    private boolean isValidEmail(CharSequence target) {

        return !TextUtils.isEmpty(target) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    /**
     * Success to sign up
     * @param id
     */
    @Override
    public void onRegisterSuccess(UserToken id) {
        mApp.hideProgress();

        // Save the current user
        this.mApp.setCurrentUser(id.getId());
        Toast.makeText(SignUpTakeMeActivity.this, "Sign up succeed", Toast.LENGTH_SHORT).show();

        // Go to main activity
        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        startActivity(intentToMain);

        finish();
    }

    /**
     * Failed to sign up
     */
    @Override
    public void onRegisterFailed() {
        mApp.hideProgress();
        Toast.makeText(SignUpTakeMeActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Error occurred when tried to sign up
     * @param t
     */
    @Override
    public void onRestCallError(Throwable t) {
        mApp.hideProgress();
        Toast.makeText(SignUpTakeMeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Success to get the registration device id
     * @param regId - registration device id
     */
    @Override
    public void onGetRegistrationDeviceId(String regId) {

        // Sign up to application
        UserSignUpTask userSignUpTask =
                new UserSignUpTask(
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        phoneNumberEditText.getText().toString(),
                        regId, this);

        userSignUpTask.signUp();

    }
}
