package com.example.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends AppCompatActivity {

    ImageView back;
    Button btnLogout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        //Hooks
        back = findViewById(R.id.ivBack);
        btnLogout = findViewById(R.id.Logout);

        //Back Button part
        back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        });

        //logout Button
        btnLogout.setOnClickListener(view -> {
            firebaseAuth.signOut();
        });

    }
}