package com.example.attendancemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button addclass, cancel, add;
    EditText classname, subjectname;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ClassAdapter classAdapter;
    TextView logout;
    ArrayList<ClassItem> classItems = new ArrayList<>();

    private int lastposition;


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
        logout = findViewById(R.id.Logout);

        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //retrieving last position from onDestroy
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        lastposition = sharedPreferences.getInt("lastposition",0);
        recyclerView.scrollToPosition(lastposition);

        //code for save recyclerView state
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastposition =  layoutManager.findFirstVisibleItemPosition();
            }
        });

        //code for set classAdapter in recyclerView
        ClassAdapter classAdapter = new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);

        //code for toolBar sideMenu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu);


        //Code of addClass
        addclass.setOnClickListener(view -> showDialog());


    }


    //saving lastPosition of recyclerView before onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastposition",lastposition);
        editor.apply();
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
        });
    }

    private void additem() {

        String inputclassname = classname.getText().toString().trim();
        String inputsubjectname = subjectname.getText().toString().trim();

        classItems.add(new ClassItem(inputclassname, inputsubjectname));

    }

}
