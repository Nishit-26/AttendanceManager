package com.example.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {

    EditText username, email, password;
    Button signup;
    TextView txtlogin;
    RadioGroup radioGroup;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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
        radioGroup = findViewById(R.id.gender_group);
        LoadingDialog loadingDialog = new LoadingDialog(Signup.this);


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

                    loadingDialog.startLoadingDialog();
                    //Create User and save data
                    firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        User user = new User(inputUsername, inputEmail, inputPassword);
                                        databaseReference
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getApplicationContext(), "Register Successful!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), Home.class));
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error occured, try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

    }
}