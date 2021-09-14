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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addClass;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference classRef = db.collection("Classes");
    ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        toolbar = findViewById(R.id.toolbar);
        addClass = findViewById(R.id.btnAdd);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {

        Query query = classRef.orderBy("priority",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ClassModel> options = new FirestoreRecyclerOptions.Builder<ClassModel>()
                .setQuery(query,ClassModel.class)
                .build();

        adapter = new ClassAdapter(options);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
