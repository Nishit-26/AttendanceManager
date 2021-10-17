package com.example.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class StudentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_page);

        ImageView back;

        back = findViewById(R.id.ivBack);
        back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        });
    }
}