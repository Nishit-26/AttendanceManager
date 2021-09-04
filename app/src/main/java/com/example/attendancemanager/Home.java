package com.example.attendancemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Home extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    Button addclass, cancel, add;
    EditText classname, subjectname;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ClassAdapter classAdapter;
    ArrayList<ClassItem> classItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //Hooks
        toolbar = findViewById(R.id.toolBar);
        recyclerView = findViewById(R.id.home_recyclerview);
        addclass = findViewById(R.id.btnAddclass);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //load data
        loadData();

        //code for set classAdapter in recyclerView
        classAdapter = new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);

        //code for toolBar sideMenu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        //Code of addClass
        addclass.setOnClickListener(view -> showDialog());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.Logout:

                        Toast.makeText(getApplicationContext(), "worked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

    }

    //showDialog method
    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        alertDialogBuilder.setView(view);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        classname = view.findViewById(R.id.etClass);
        subjectname = view.findViewById(R.id.etSubject);
        cancel = view.findViewById(R.id.btnCancel);
        add = view.findViewById(R.id.btnAdd);

        cancel.setOnClickListener(view1 -> dialog.dismiss());
        add.setOnClickListener(view1 -> {
            additem();
            dialog.dismiss();
            saveData();
        });
    }

    //save classitem of recyclerView
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferance",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(classItems);
        editor.putString("task list", json);
        editor.apply();
    }
    //method for load saved data
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferance",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type = new TypeToken<ArrayList<ClassItem>>() {}.getType();
        classItems = gson.fromJson(json,type);
        if (classItems == null){
            classItems = new ArrayList<>();
        }
    }

    private void additem() {

        String inputclassname = classname.getText().toString().trim();
        String inputsubjectname = subjectname.getText().toString().trim();

        if (inputclassname.isEmpty()) {
            classname.requestFocus();
        } else if (inputsubjectname.isEmpty()) {
            subjectname.requestFocus();
        } else {
            classItems.add(new ClassItem(inputclassname, inputsubjectname));
            classAdapter.notifyDataSetChanged();
        }
    }

}
