package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText email, password;
    CheckBox remember;
    Button login;
    TextView forgot,txtsignup;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Hooks
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        remember = findViewById(R.id.cbRemember);
        login = findViewById(R.id.btnLogin);
        forgot = findViewById(R.id.tvForgot);
        txtsignup = findViewById(R.id.tvSignup);
        LoadingDialog loadingDialog = new LoadingDialog(Login.this);

        //Login button onClick
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();



                if (inputEmail.isEmpty()){
                    email.setError("field can't be empty!");
                    email.requestFocus();
                }else if (inputPassword.isEmpty()){
                    password.setError("field can't be empty!");
                    password.requestFocus();
                }else{

                    firebaseAuth.signInWithEmailAndPassword(inputEmail,inputPassword)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   startActivity(new Intent(getApplicationContext(),Home.class));
                                } else {
                                  Toast.makeText(getApplicationContext(),"username or password is incorrect! try again",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                }

            }
        });


        //signuptext to signup page
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        });

    }
}