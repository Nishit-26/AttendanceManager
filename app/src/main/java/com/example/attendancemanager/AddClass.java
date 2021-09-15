package com.example.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddClass extends AppCompatActivity {

    EditText className, subjectName;
    Button add;
    NumberPicker priority;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);

        //hooks
        className = findViewById(R.id.etClass);
        subjectName = findViewById(R.id.etSubject);
        priority = findViewById(R.id.npPriority);
        back = findViewById(R.id.ivBack);
        add = findViewById(R.id.btnAddclass);

        priority.setMinValue(1);
        priority.setMaxValue(10);

        //Back onClick
        back.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        });

        //add onClick
        add.setOnClickListener(view -> {addClass();
        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
        });
    }

    //Methods

    private void addClass() {

        String inputClassname = className.getText().toString().trim();
        String inputSubjectname = subjectName.getText().toString().trim();
        int inputPriority = priority.getValue();

        if (inputClassname.isEmpty() || inputSubjectname.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter all details!", Toast.LENGTH_SHORT).show();
            return;
        }
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Class");
        ClassModel classModel = new ClassModel(inputClassname,inputSubjectname,inputPriority);
        collectionReference.add(classModel);
        Toast.makeText(getApplicationContext(), "Class added successfully!", Toast.LENGTH_SHORT).show();

    }
}