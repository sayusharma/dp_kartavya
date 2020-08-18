package com.e.dpkartavya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        if (!getLocation().equals("Select  Police Station")){
            Intent intent = new Intent(DashActivity.this, VerifyActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_LONG).show();
        }
    }
    public String getLocation(){
        TextView textView = findViewById(R.id.tvsniperlayout);
        return textView.getText().toString();
    }
    public void onClickMarkAVisit(View view)
    {
        if (!getLocation().equals("Select  Police Station")) {
            Intent intent = new Intent(DashActivity.this, MarkVisitActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickUpdateSenior(View view){
        if (!getLocation().equals("Select  Police Station")) {
            Intent intent = new Intent(DashActivity.this, MyVerificationActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_LONG).show();
        }
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