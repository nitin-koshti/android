package com.ntncorp.amitos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView mainListView;
    private ArrayList<String> utilityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initilised all UI items here
        initialisedUIItems();

        // Display Business Items on Main List
        displayBusinessItemOnMainList();

        //On click of items on utility list view
        onClickOfUtilityListItems();
    }

    private void initialisedUIItems() {
        mainListView = findViewById(R.id.mainlistview);
    }


    private void displayBusinessItemOnMainList() {
        utilityList.add("Today");
        utilityList.add("Yesterday");
        utilityList.add("Current Month");
        utilityList.add("Month Summary");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, utilityList);
        mainListView.setAdapter(arrayAdapter);
    }

    private void onClickOfUtilityListItems() {

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UtilityActivity.class);
                intent.putExtra("index", position);
                intent.putStringArrayListExtra("utilityItemsList", utilityList);
                startActivity(intent);
            }
        });
    }

}
