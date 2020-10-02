package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DashActivity extends AppCompatActivity  {
    private ImageView imageView;
    private boolean gps_enabled=false;
    private boolean network_enabled = false;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        imageView = findViewById(R.id.profile_pic);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        final ProgressDialog p = new ProgressDialog(this);
        p.setMessage("Please Wait...");
        p.show();
        p.setCancelable(false);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(SaveSharedPreference.getUserName(getApplicationContext())).getValue(User.class);
                CurrentUser.currentUser = user;
                try {
                    Picasso.get()
                            .load(CurrentUser.currentUser.getPhoto())
                            .into(imageView);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"FAILED TO LOAD PROFILE PHOTO",Toast.LENGTH_LONG).show();
                }
                p.dismiss();
                //Toast.makeText(getApplicationContext(),""+CurrentUser.currentUser.getName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                p.dismiss();
                Toast.makeText(getApplicationContext(),"BAD DATABASE REQUEST",Toast.LENGTH_LONG).show();
            }
        });

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
            new AlertDialog.Builder(DashActivity. this )
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
    public void onClickProfilePhoto(View view){
        Intent intent = new Intent(DashActivity.this,MyProfile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        finish();
    }
    public void onClickVerify(View view)
    {
        LocationManager lm = (LocationManager)
                getSystemService(Context. LOCATION_SERVICE ) ;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        locationEnabled();
        if (gps_enabled){
                Intent intent = new Intent(DashActivity.this, VerifyActivity.class);
                startActivity(intent);
        }
        else locationEnabled();

    }
    public void onClickMarkAVisit(View view)
    {
            Intent intent = new Intent(DashActivity.this, MarkVisitActivity.class);
            startActivity(intent);

    }
    public void onClickUpdateSenior(View view)
    {
        Intent intent = new Intent(DashActivity.this, MyVerificationActivity.class);
        startActivity(intent);

    }
}