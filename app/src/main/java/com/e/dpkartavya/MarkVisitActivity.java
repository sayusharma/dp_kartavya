package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.dpkartavya.Common.CurrentVisit;
import com.e.dpkartavya.Interface.ItemClickListener;
import com.e.dpkartavya.Model.VerifySnr;
import com.e.dpkartavya.ViewHolder.VerirySnrViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
                    cancelName.setVisibility(View.INVISIBLE);
                }
                else
                    cancelName.setVisibility(View.VISIBLE);
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
                    cancelMob.setVisibility(View.INVISIBLE);
                }
                else
                    cancelMob.setVisibility(View.VISIBLE);
                filterResultsForMob(s);
            }
        });
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        databaseReference = firebaseDatabase.getReference("snr_czn");
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
            Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
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
    @Override
    public void onClick(int position) {
        Intent intent = new Intent(MarkVisitActivity.this,VisitActivity.class);
        CurrentVisit.currentVisit = cuurentArrayList.get(position);
        startActivity(intent);
    }
}