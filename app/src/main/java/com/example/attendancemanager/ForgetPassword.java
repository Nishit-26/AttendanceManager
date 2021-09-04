package com.example.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    EditText emailtoresetpass;
    Button sendink;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        emailtoresetpass = findViewById(R.id.etResetPassMail);
        sendink = findViewById(R.id.btnSendLink);

        sendink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputEmailResetPass = emailtoresetpass.getText().toString();

                if (inputEmailResetPass.isEmpty()){
                    emailtoresetpass.setError("enter Email");
                    emailtoresetpass.requestFocus();
                }else {
                    firebaseAuth.sendPasswordResetEmail(inputEmailResetPass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Check your Email", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed, try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}