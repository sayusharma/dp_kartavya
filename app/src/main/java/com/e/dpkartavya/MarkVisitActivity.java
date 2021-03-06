package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.dpkartavya.Adapter.SeniorAdapter;
import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Common.CurrentVisit;
import com.e.dpkartavya.Model.VerifySnr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MarkVisitActivity extends AppCompatActivity implements SeniorAdapter.OnItemClickListener{
    private FirebaseDatabase firebaseDatabase;
    private EditText mobEditText;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageView cancelName,cancelMob;
    private Context context;
    private ArrayList<VerifySnr> cuurentArrayList;
    private SeniorAdapter orderAdapter;
    private ArrayList<VerifySnr> arrayList;
    private boolean gps_enabled=false;
    private boolean network_enabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_visit);
        context = this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.snrRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editText = findViewById(R.id.searchByNameEditText);
        mobEditText = findViewById(R.id.searchByMobEditText);
        cancelMob = findViewById(R.id.cancelMob);
        cancelName = findViewById(R.id.cancelName);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    mobEditText.setEnabled(true);
                    cancelName.setVisibility(View.INVISIBLE);
                }
                else{
                    mobEditText.setEnabled(false);
                    cancelName.setVisibility(View.VISIBLE);
                }
                filterResults(s);


            }
        });
        mobEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    editText.setEnabled(true);
                    cancelMob.setVisibility(View.INVISIBLE);
                }
                else {
                    cancelMob.setVisibility(View.VISIBLE);
                    editText.setEnabled(false);
                }
                filterResultsForMob(s);
            }
        });
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        databaseReference = firebaseDatabase.getReference("snr_czn").child(CurrentUser.currentUser.getPolice());
        //Query query = databaseReference.orderByKey();
        databaseReference.orderByChild("basicDetails/personalDetails/name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    VerifySnr order = dataSnapshot1.getValue(VerifySnr.class);
                    arrayList.add(order);
                    //Toast.makeText(getContext(),"36",Toast.LENGTH_SHORT).show();
                }
                if (arrayList.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
                }
                else {
                    setAdapter();
                    //Toast.makeText(getContext(),"IN2",Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    public void onClickCancelName(View view){
        cancelName.setVisibility(View.INVISIBLE);
        editText.setText("");
    }
    public void onClickCancelMob(View view){
        cancelMob.setVisibility(View.INVISIBLE);
        mobEditText.setText("");
    }
    private void setAdapter() {
        orderAdapter = new SeniorAdapter(getApplicationContext(), arrayList,this);
        cuurentArrayList = arrayList;
        //Toast.makeText(getContext(),"IN3",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(orderAdapter);

    }

    private void filterResultsForMob(CharSequence s) {
        ArrayList<VerifySnr> filterList = new ArrayList<>();
        for(VerifySnr v:arrayList){
            if (v.getBasicDetails().getPersonalDetails().getMob().contains(s)){
                filterList.add(v);
            }
        }
        if (filterList.isEmpty()){
            cuurentArrayList = arrayList;
           // Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
        }
        else{
            cuurentArrayList = filterList;
        }
        SeniorAdapter seniorAdapter = new SeniorAdapter(getApplicationContext(),filterList,this);
        recyclerView.setAdapter(seniorAdapter);
    }

    private void filterResults(CharSequence s) {
        ArrayList<VerifySnr> filterList = new ArrayList<>();
        for(VerifySnr v:arrayList){
            if (v.getBasicDetails().getPersonalDetails().getName().toLowerCase().contains(s)){
                filterList.add(v);
            }
        }
        if (filterList.isEmpty()){
            cuurentArrayList = arrayList;
            //Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
        }
        else{
            cuurentArrayList = filterList;
        }
        SeniorAdapter seniorAdapter = new SeniorAdapter(getApplicationContext(),filterList,this);
        recyclerView.setAdapter(seniorAdapter);
    }
    public void onClickBackMarkVisit(View view){
        Intent intent = new Intent(MarkVisitActivity.this,DashActivity.class);
        startActivity(intent);
        finish();
    }
    private void locationEnabled () {

        LocationManager lm = (LocationManager)
                getSystemService(Context. LOCATION_SERVICE ) ;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(MarkVisitActivity.this )
                    .setMessage( "Please Enable GPS" )
                    .setPositiveButton( "Settings", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick (DialogInterface paramDialogInterface , int paramInt) {
                                    startActivity( new Intent(Settings. ACTION_LOCATION_SOURCE_SETTINGS )) ;
                                }
                            })
                    .show() ;

        }
    }
    @Override
    public void onClick(int position) {
        CurrentVisit.currentVisit = cuurentArrayList.get(position);
        LocationManager lm = (LocationManager)
                getSystemService(Context. LOCATION_SERVICE ) ;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        locationEnabled();
        if (gps_enabled){
            Intent intent = new Intent(MarkVisitActivity.this,VisitActivity.class);
            startActivity(intent);
        }
        else locationEnabled();
    }
}