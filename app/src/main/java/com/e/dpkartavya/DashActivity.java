package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private customAdapter adapter;
    private Spinner locationspinner;
    private ImageView imageView;
    private ArrayList<CustomItem> customList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        locationspinner = findViewById(R.id.locationspinner);
        imageView = findViewById(R.id.profile_pic);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        final ProgressDialog p = new ProgressDialog(this);
        p.setMessage("Please Wait...");
        p.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CurrentUser.currentUser = dataSnapshot.child(SaveSharedPreference.getUserName(getApplicationContext())).getValue(User.class);
                Picasso.get()
                        .load(SaveSharedPreference.getPhoto(getApplicationContext()))
                        .into(imageView);
                p.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                p.dismiss();
                Toast.makeText(getApplicationContext(),"BAD DATABASE REQUEST",Toast.LENGTH_LONG).show();
            }
        });
        customList = getCustomList();
        adapter = new customAdapter(this, customList);
        locationspinner.setAdapter(adapter);
        locationspinner.setOnItemSelectedListener(this);
    }
    public void onClickProfilePhoto(View view){
        Intent intent = new Intent(DashActivity.this,MyProfile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        finish();
    }
    public void onClickVerify(View view)
    {
        if (!getLocation().equals("Select  Police Station")){
            Intent intent = new Intent(DashActivity.this, VerifyActivity.class);
            intent.putExtra("police",getLocation());
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
            intent.putExtra("police",getLocation());
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickUpdateSenior(View view){
        if (!getLocation().equals("Select  Police Station")) {
            Intent intent = new Intent(DashActivity.this, MyVerificationActivity.class);
            intent.putExtra("police",getLocation());
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