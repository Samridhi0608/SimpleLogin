package com.example.simplelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton addNote;
    private RecyclerView recyclerview;
    static ArrayList<Item> itemList=new ArrayList<>();
    static ItemsAdapter mAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNote=findViewById(R.id.addNoteBtn);

        recyclerview=(RecyclerView)findViewById(R.id.itemsList);

        loadData();
        buildRecyclerView();

//        RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getApplicationContext());
//        recyclerview.setLayoutManager(mLayoutManger);
//        recyclerview.setItemAnimator(new DefaultItemAnimator());

//        sharedPreferences = getApplicationContext().getSharedPreferences("NotesDB", MODE_PRIVATE);
//        sharedPreferencesEditor = sharedPreferences.edit();
//
//
//
//        if(sharedPreferences!=null) {
//            String savedTitle = sharedPreferences.getString("title", "");
//            String savedDesc = sharedPreferences.getString("desc", "");
//            //Toast.makeText(this, "title "+savedTitle+" ,"+ "Desc: "+savedDesc, Toast.LENGTH_SHORT).show();
//
//            mAdapter = new ItemsAdapter(itemList);
//
////            Item item = new Item(savedTitle, savedDesc, null);
////            itemList.add(item);
//            mAdapter.notifyDataSetChanged();
//            recyclerview.setAdapter(mAdapter);
//        }

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("items", null);
        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        itemList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (itemList == null) {
            // if the array list is empty
            // creating a new array list.
            itemList = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        mAdapter = new ItemsAdapter(itemList, MainActivity.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //recyclerview.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        recyclerview.setLayoutManager(manager);

        // setting adapter to our recycler view.
        recyclerview.setAdapter(mAdapter);

    }

}
