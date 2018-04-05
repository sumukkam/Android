package com.msushanth.tables;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    EditText usernameET;
    EditText emailET;
    EditText passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        usernameET = (EditText) findViewById(R.id.usernameEditText);
        emailET = (EditText) findViewById(R.id.emailEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);
    }


    public void createAccountButtonClicked(View v) {
        String print = "Creating Account with Username: " + usernameET.getText().toString() + ", E-mail: " + emailET.getText().toString() + ", Passowrd: " + passwordET.getText().toString();
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();
    }

    public void signIn(View v) {
        String print = "Sign In Clicked";
        Toast.makeText(this, print, Toast.LENGTH_SHORT).show();

        Intent mainActivityIntent = new Intent(CreateAccount.this, SignIn.class);
        startActivity(mainActivityIntent);
        finish();
    }

}
