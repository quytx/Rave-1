package com.rave.rave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class WelcomeActivity extends ActionBarActivity {
    // file: WelcomeActivity.java
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if(isLoggedIn()){
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }

        findViewById(R.id.registerButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // No account, load new account view
                        Intent intent = new Intent(WelcomeActivity.this,
                                RegisterActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

        findViewById(R.id.loginButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Existing Account, load login view
                        Intent intent = new Intent(WelcomeActivity.this,
                                LoginActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }

    public boolean isLoggedIn(){
        SharedPreferences mPreference = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        if(mPreference.contains("AuthToken")){
            return true;
        }
        else{
            return false;
        }
    }
}
