package com.takeme.takemeapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.takeme.takemeapp.R;

public class SignUpTakeMeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_take_me);

        //Hide action bar
//        getActionBar().hide();
//        ActivitySignUpTakeMeBinding binding =
//                DataBindingUtil.setContentView(this, R.layout.activity_sign_up_take_me);

//        user = new User();
//        user.setEmail("zamirt@gmail.com");
//        binding.setUser(user);

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

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void onSignUp(View view)
    {
        String strFirstName       = ((EditText)findViewById(R.id.etFirstName)).getText().toString();
        String strLastName        = ((EditText)findViewById(R.id.etLastName)).getText().toString();
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

        new AsyncTask<Void, Void, Intent>() {
            @Override
            protected Intent doInBackground(Void... params) {
                //String authtoken = sServerAuthenticate.userSignIn(userName, userPass, mAuthTokenType);

                Bundle bndlData = new Bundle();
               // res.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
                //res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
               // res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
               // res.putExtra(PARAM_USER_PASS, userPass);
                final Intent res = new Intent();
                res.putExtras(bndlData);
                return res;
            }
            @Override
            protected void onPostExecute(Intent intent) {
                finishSignUp(intent);
            }
        }.execute();

    }

    public void onSignIn(View view)
    {
        Intent intentToSignIn = new Intent(this, SignInTakeMeActivity.class);
        startActivity(intentToSignIn);

        finish();
    }

    private void finishSignUp(Intent intent) {
//        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
//        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
//        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
//            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
//            String authtokenType = mAuthTokenType;
//            // Creating the account on the device and setting the auth token we got
//            // (Not setting the auth token will cause another call to the server to authenticate the user)
//            mAccountManager.addAccountExplicitly(account, accountPassword, null);
//            mAccountManager.setAuthToken(account, authtokenType, authtoken);
//        } else {
//            mAccountManager.setPassword(account, accountPassword);
//        }
//        setAccountAuthenticatorResult(intent.getExtras());
//        setResult(RESULT_OK, intent);
        Intent intentToMain = new Intent(this, MainTakeMeActivity.class);
        intentToMain.putExtras(intent.getExtras());
        startActivity(intentToMain);

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

}
