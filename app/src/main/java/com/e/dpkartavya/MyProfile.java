package com.e.dpkartavya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MyProfile extends Activity {
    private Button logout,edit,change,cancel;
    private TextView name,design;
    private ImageView imageView;
    private String string;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
       // firebaseDatabase = FirebaseDatabase.getInstance();
       // databaseReference = firebaseDatabase.getReference("users");
        logout = findViewById(R.id.btnLogout);
       // edit = findViewById(R.id.btnEditProfile);
        name = findViewById(R.id.profile_name);
        design = findViewById(R.id.designation);
        imageView = findViewById(R.id.profilePhoto);
        //Toast.makeText(getApplicationContext(),""+CurrentUser.currentUser.getName()+CurrentUser.currentUser.getPhoto(),Toast.LENGTH_LONG).show();
        try {
            Picasso.get().load(CurrentUser.currentUser.getPhoto()).into(imageView);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"FAILED TO LOAD PROFILE PHOTO",Toast.LENGTH_LONG).show();
        }
        name.setText(CurrentUser.currentUser.getName());
        design.setText(CurrentUser.currentUser.getRank());

    }
    public void onClickLogout(View view){
        //SaveSharedPreference.setUserName(getApplicationContext(),"null");
        SaveSharedPreference.clearPreference(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        CurrentUser.currentUser = null;
        Intent intent = new Intent(MyProfile.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClickCancelProfile(View view){
        Intent intent = new Intent(MyProfile.this,DashActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        finish();
    }
}
