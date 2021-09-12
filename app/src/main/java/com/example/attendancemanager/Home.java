package com.example.attendancemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addClass;
    Toolbar toolbar;
    EditText classname,subjectname;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        addClass = findViewById(R.id.btnAdd);
        bottomNavigationView = findViewById(R.id.bottom_nav);


        //add class onClick
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.class_dialog, null);
        builder.setView(view);
        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alert.show();

        Button btncancel = view.findViewById(R.id.btnCancel);
        Button btnadd = view.findViewById(R.id.btnAdd);
        classname = view.findViewById(R.id.etClass);
        subjectname = view.findViewById(R.id.etSubject);


        btncancel.setOnClickListener(view1 -> alert.dismiss());

        btnadd.setOnClickListener(view1 -> {addItem();
        alert.dismiss();});
    }

    private void addItem() {

        String inputClassname = classname.getText().toString().trim();
        String inputSubjectname = subjectname.getText().toString().trim();

        ClassItem classItem = new ClassItem(inputClassname,inputSubjectname);

        CollectionReference collectionReference = db.collection("Classes");
        collectionReference.add(classItem).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Class added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
