package com.rave.rave;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends ActionBarActivity {

    private final static String REGISTER_API_ENDPOINT_URL = "http://10.0.2.2:3000/api/v1/registrations";
    private SharedPreferences mPreferences;
    private String mUserEmail;
    private String mUserName;
    private String mUserPassword;
    private String mUserPasswordConfirmation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    public void registerNewAccount(View button) {
        EditText userEmailField = (EditText) findViewById(R.id.userEmail);
        mUserEmail = userEmailField.getText().toString();
        EditText userNameField = (EditText) findViewById(R.id.userName);
        mUserName = userNameField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
        mUserPassword = userPasswordField.getText().toString();
        EditText userPasswordConfirmationField = (EditText) findViewById(R.id.userPasswordConfirmation);
        mUserPasswordConfirmation = userPasswordConfirmationField.getText().toString();

        if (mUserEmail.length() == 0 || mUserName.length() == 0 || mUserPassword.length() == 0 || mUserPasswordConfirmation.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
            return;
        } else {
            if (!mUserPassword.equals(mUserPasswordConfirmation)) {
                // password doesn't match confirmation
                Toast.makeText(this, "Your password doesn't match confirmation, check again",
                        Toast.LENGTH_LONG).show();
                return;
            } else {
//                // everything is ok!
//                RegisterTask registerTask = new RegisterTask(RegisterActivity.this);
//                registerTask.setMessageLoading("Registering new account...");
//                registerTask.execute(REGISTER_API_ENDPOINT_URL);
           }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
