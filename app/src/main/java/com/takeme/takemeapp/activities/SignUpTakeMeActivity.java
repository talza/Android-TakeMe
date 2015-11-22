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
import com.takeme.services.UserSignUpTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

public class SignUpTakeMeActivity extends Activity implements UserSignUpTask.UserSignUpResponse {

    private TakeMeApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_take_me);
        this.mApp =  (TakeMeApplication)getApplication();

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

    public void onSignUp(View view)
    {
        String strFirstName       = ((EditText)findViewById(R.id.etFirstName)).getText().toString();
        String strLastName        = ((EditText)findViewById(R.id.etLastName)).getText().toString();
        String strPhoneNumber     = ((EditText)findViewById(R.id.etPhoneNumber)).getText().toString();
        EditText etEmail    = ((EditText)findViewById(R.id.etEmail));
        EditText etPassword = ((EditText)findViewById(R.id.etPassword));

        if (!isValidEmail(etEmail.getText().toString()))
        {
            etEmail.setError(getString(R.string.msg_invalid_email_format));
            return;
        }

        // Check Password validation
        if ( !isValidPassword(etPassword.getText().toString()))
        {
            etPassword.setError(getString(R.string.msg_invalid_password_format));
            return;
        }

        mApp.showProgress(this);
        UserSignUpTask userSignUpTask =
                new UserSignUpTask(etEmail.getText().toString(),
                                   etPassword.getText().toString(),
                                   strFirstName,
                                   strLastName,
                                   strPhoneNumber, this);

        userSignUpTask.signUp();

    }

    public void onSignIn(View view)
    {
        Intent intentToSignIn = new Intent(this, SignInTakeMeActivity.class);
        startActivity(intentToSignIn);

        finish();
    }

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

    private boolean isValidEmail(CharSequence target) {

        return !TextUtils.isEmpty(target) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    @Override
    public void onRegisterSuccess(UserToken id) {
        mApp.hideProgress();

        this.mApp.setCurrentUser(id.getId());
        Toast.makeText(SignUpTakeMeActivity.this, "Sign up succeed", Toast.LENGTH_SHORT).show();

        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        startActivity(intentToMain);

        finish();
    }

    @Override
    public void onRegisterFailed() {
        mApp.hideProgress();
        Toast.makeText(SignUpTakeMeActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestCallError(Throwable t) {
        mApp.hideProgress();
        Toast.makeText(SignUpTakeMeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
    }
}
