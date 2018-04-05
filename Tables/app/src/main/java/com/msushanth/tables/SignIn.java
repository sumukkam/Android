package com.msushanth.tables;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        usernameET = (EditText) findViewById(R.id.usernameEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
    }

    public void signInButtonClicked(View v) {

        String print = "Signing on with Username: " + usernameET.getText().toString() + ", Passowrd: " + passwordET.getText().toString();
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();

        Intent mainActivity = new Intent(SignIn.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }


    public void createAccount(View v) {

        String print = "Create Account Clicked";
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();

        Intent createAccountIntent = new Intent(SignIn.this, CreateAccount.class);
        startActivity(createAccountIntent);
        finish();
    }

}
