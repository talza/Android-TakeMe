package com.takeme.takemeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignInTakeMeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_in_take_me);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
                finishSignIn(intent);
            }
        }.execute();
    }

    public void onSignUp(View view)
    {
        Intent intent = new Intent(this, SignUpTakeMeActivity.class);
        startActivity(intent);
    }

    private void finishSignIn(Intent intent) {
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

    private boolean isValidEmail(CharSequence target) {

        return !TextUtils.isEmpty(target) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }
}
