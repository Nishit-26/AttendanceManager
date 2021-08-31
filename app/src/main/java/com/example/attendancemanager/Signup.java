package com.example.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Signup extends AppCompatActivity {

    EditText username, email, password;
    Button signup;
    TextView txtlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //Hooks
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        email = findViewById(R.id.etEmail);
        signup = findViewById(R.id.btnSignup);
        txtlogin = findViewById(R.id.tvLogin);


        //loginText onClick
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        //signup onClick
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();
                String inputEmail = email.getText().toString();

               if (inputUsername.isEmpty()){
                    username.setError("Username is required!");
                    username.requestFocus();
                }else if (inputPassword.isEmpty()){
                    password.setError("Password is required!");
                    password.requestFocus();
                }else if (inputEmail.isEmpty()){
                    email.setError("Email is required!");
                    email.requestFocus();
                }else if (inputEmail.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")){
                    email.setError("email is not valid");
                    email.requestFocus();
                }else {
                    startActivity(new Intent(getApplicationContext(),Home.class));
                }
            }
        });

    }
}