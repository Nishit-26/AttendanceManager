package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    EditText username, email, password;
    Button signup;
    TextView txtlogin;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

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
                startActivity(new Intent(getApplicationContext(), Login.class));
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

                if (inputUsername.isEmpty()) {
                    username.setError("Username is required!");
                    username.requestFocus();
                } else if (inputPassword.isEmpty()) {
                    password.setError("Password is required!");
                    password.requestFocus();
                } else if (inputEmail.isEmpty()) {
                    email.setError("Email is required!");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
                    email.setError("email is not valid");
                    email.requestFocus();
                } else {

                    User user = new User(inputUsername,inputEmail,inputPassword);
                    databaseReference.child(inputUsername).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(getApplicationContext(),"Registered successfully!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                    });

                }
            }
        });

    }
}