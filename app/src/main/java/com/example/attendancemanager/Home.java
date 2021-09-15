package com.example.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton addClass;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference classRef = db.collection("Class");
    ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //hooks
        toolbar = findViewById(R.id.toolbar);
        addClass = findViewById(R.id.btnAdd);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        //recyclerSetup
        setUpRecyclerView();

        //floatingButton onClick
        addClass.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddClass.class)));

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Profile_nav:
                        startActivity(new Intent(getApplicationContext(),UserProfile.class));
                        return;
                }
            }
        });
    }

    //Methods
    private void setUpRecyclerView() {

        Query query = classRef.orderBy("priority", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ClassModel> options = new FirestoreRecyclerOptions.Builder<ClassModel>()
                .setQuery(query, ClassModel.class)
                .build();

        adapter = new ClassAdapter(options);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteClass(viewHolder.getAbsoluteAdapterPosition());
                Toast.makeText(getApplicationContext(), "class deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
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
