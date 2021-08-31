package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText username, password;
    CheckBox remember;
    Button login;
    TextView forgot,txtsignup;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Hooks
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        remember = findViewById(R.id.cbRemember);
        login = findViewById(R.id.btnLogin);
        forgot = findViewById(R.id.tvForgot);
        txtsignup = findViewById(R.id.tvSignup);


        //signuptext to signup page
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        });

        //Login button onClick
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputUsername = username.getText().toString().trim();
                String inputPassowrd = password.getText().toString().trim();

                if (inputUsername.isEmpty()) {
                    username.setError("Field can't be empty");
                    username.requestFocus();
                } else if (inputPassowrd.isEmpty()) {
                    password.setError("Field can't be empty");
                    password.requestFocus();
                } else{
                }

            }
        });

    }
}