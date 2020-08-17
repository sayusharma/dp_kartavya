package com.e.dpkartavya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

public class DashActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private customAdapter adapter;
    private Spinner locationspinner;
    private ArrayList<CustomItem> customList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        locationspinner = findViewById(R.id.locationspinner);
        customList = getCustomList();
        adapter = new customAdapter(this, customList);
        locationspinner.setAdapter(adapter);
        locationspinner.setOnItemSelectedListener(this);
    }
    public void onClickVerify(View view)
    {
        Intent intent = new Intent(DashActivity.this,VerifyActivity.class);
        startActivity(intent);
    }
    public void onClickMarkAVisit(View view)
    {
        Intent intent = new Intent(DashActivity.this,MarkVisitActivity.class);
        startActivity(intent);
    }
    public void onClickUpdateSenior(View view){
        Intent intent = new Intent(DashActivity.this,UpdateSeniorActivity.class);
        startActivity(intent);
    }
    private ArrayList<CustomItem> getCustomList() {
        customList = new ArrayList<>();
        customList.add(new CustomItem("Select  Police Station"));
        customList.add(new CustomItem("Vasant Vihar"));
        return customList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}