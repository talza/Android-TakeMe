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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_take_me);
        this.mApp =  (TakeMeApplication)getApplication();

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

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void onSignIn(View view)
    {

        EditText etEmail    = ((EditText)findViewById(R.id.etEmail));
        EditText etPassword = ((EditText)findViewById(R.id.etPassword));

        if (!isValidEmail(etEmail.getText().toString()) ||
            TextUtils.isEmpty(etPassword.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.msg_invalid_email_password),Toast.LENGTH_SHORT).show();
            return;
        }

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
        this.mApp.setCurrentUser(id.getId());
        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        startActivity(intentToMain);

        finish();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(SignInTakeMeActivity.this, getString(R.string.msg_invalid_email_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestCallError(Throwable t) {
        Toast.makeText(SignInTakeMeActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();

    }
}
