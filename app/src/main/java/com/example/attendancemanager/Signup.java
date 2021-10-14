package com.example.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Signup extends AppCompatActivity {

    EditText username, email, password;
    Button signup;
    TextView txtlogin;
    RadioGroup radioGroup;
    RadioButton genderMale, genderFemale;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        genderFemale = findViewById(R.id.gender_female);
        genderMale = findViewById(R.id.gender_male);
        progressBar = findViewById(R.id.progressBar);

        //signup process
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputUsername = username.getText().toString().trim();
                String inputEmail = email.getText().toString().trim();
                String inputPass = password.getText().toString().trim();

                if (inputUsername.isEmpty()) {
                    username.setError("Field can't be empty");
                    username.requestFocus();
                } else if (inputEmail.isEmpty()) {
                    email.setError("Field can't be empty");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
                    email.setError("please enter valid email");
                    email.requestFocus();
                } else if (inputPass.isEmpty()) {
                    password.setError("Field can't be empty");
                    password.requestFocus();
                } else if (inputPass.length() < 6) {
                    password.setError("password is too short");
                    password.requestFocus();
                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User user = new User(inputUsername, inputEmail, inputPass);
                                        CollectionReference collectionReference = db.collection("Users");
                                        collectionReference.add(user)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Registeration Successful!", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), Home.class));
                                                            finish();
                                                            progressBar.setVisibility(View.GONE);

                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }
            }
        });

    }
}