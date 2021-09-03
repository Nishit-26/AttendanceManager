package com.example.attendancemanager;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView forgot, txtsignup;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Hooks
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        forgot = findViewById(R.id.tvForgot);
        txtsignup = findViewById(R.id.tvSignup);

        //Check weather User is login or not
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            // User is signed in
            Intent i = new Intent(Login.this, Home.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        //Login button onClick
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();


                if (inputEmail.isEmpty()) {
                    email.setError("field can't be empty!");
                    email.requestFocus();
                } else if (inputPassword.isEmpty()) {
                    password.setError("field can't be empty!");
                    password.requestFocus();
                } else {

                    firebaseAuth.signInWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "username or password is incorrect! try again", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }

            }
        });
        //End of loginOnclick

        //signupText to signup page
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        });

    }
}