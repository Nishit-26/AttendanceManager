package com.example.attendancemanager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Button addclass, cancel, add;
    EditText classname, subjectname;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView.LayoutManager layoutManager;
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

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ClassAdapter classAdapter = new ClassAdapter(this,classItems);
        recyclerView.setAdapter(classAdapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        addclass.setOnClickListener(view -> showDialog());

    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        alertDialogBuilder.setView(view);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();

        classname = view.findViewById(R.id.etClass);
        subjectname = view.findViewById(R.id.etSubject);
        cancel = view.findViewById(R.id.btnCancel);
        add = view.findViewById(R.id.btnAdd);

        cancel.setOnClickListener(view1 -> dialog.dismiss());
        add.setOnClickListener(view1 -> {
            addclass();
            dialog.dismiss();
        });
    }

    private void addclass() {

        String inputclassname = classname.getText().toString().trim();
        String inputsubjectname = subjectname.getText().toString().trim();

        classItems.add(new ClassItem(inputclassname, inputsubjectname));

    }
}
