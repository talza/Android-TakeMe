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
import com.takeme.services.UserSignInTask;
import com.takeme.takemeapp.R;
import com.takeme.takemeapp.TakeMeApplication;

public class SignInTakeMeActivity extends Activity implements UserSignInTask.UserLoginResponse{

    private TakeMeApplication mApp;
    private  EditText etEmail;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_take_me);
        this.mApp =  (TakeMeApplication)getApplication();

        etEmail    = ((EditText)findViewById(R.id.etEmail));
        etPassword = ((EditText)findViewById(R.id.etPassword));

        etEmail.setText("test1@gmail.com");
        etPassword.setText("Aa123456");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_take_me, menu);
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

        if (!isValidEmail(etEmail.getText().toString()) ||
            TextUtils.isEmpty(etPassword.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.msg_invalid_email_password),Toast.LENGTH_SHORT).show();
            return;
        }

        mApp.showProgress(this);
        UserSignInTask userSignInTask = new UserSignInTask(etEmail.getText().toString(),etPassword.getText().toString(),this);
        userSignInTask.signIn();
    }

    public void onSignUp(View view)
    {
        Intent intent = new Intent(this, SignUpTakeMeActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isValidEmail(CharSequence target) {

        return !TextUtils.isEmpty(target) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    @Override
    public void onLoginSuccess(UserToken id) {
        mApp.hideProgress();
        this.mApp.setCurrentUser(id.getId());
        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        startActivity(intentToMain);
        finish();

    }

    @Override
    public void onLoginFailed() {
        mApp.hideProgress();
        Toast.makeText(SignInTakeMeActivity.this, getString(R.string.msg_invalid_email_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestCallError(Throwable t) {
        mApp.hideProgress();
        Toast.makeText(SignInTakeMeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();

    }
}
