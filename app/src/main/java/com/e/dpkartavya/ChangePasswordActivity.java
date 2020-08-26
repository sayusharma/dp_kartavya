package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText old,newP,conNew;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        old = findViewById(R.id.oldPassword);
        newP = findViewById(R.id.newPassword);
        conNew = findViewById(R.id.conNewPassword);
    }
    public void onClickResetPassword(View view){
        if(validate()){
            User user = new User(CurrentUser.currentUser.getName(),CurrentUser.currentUser.getRank(),CurrentUser.currentUser.getPhoto(),
                    CurrentUser.currentUser.getMob(),newP.getText().toString(),CurrentUser.currentUser.getPolice());
            databaseReference.child(CurrentUser.currentUser.getMob()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"PASSWORD CHANGED!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ChangePasswordActivity.this,DashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"BAD DATABASE REQUEST. TRY AGAIN",Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(old.getText()) || TextUtils.isEmpty(newP.getText()) || TextUtils.isEmpty(conNew.getText())){
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!old.getText().toString().equals(CurrentUser.currentUser.getPass())){
            Toast.makeText(getApplicationContext(),"OLD PASSWORD IS INCORRECT",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!newP.getText().toString().equals(conNew.getText().toString())){
            Toast.makeText(getApplicationContext(),"PASSWORD DOESN'T MATCH",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    public void onClickBackChangePassword(View view){
        Intent intent = new Intent(ChangePasswordActivity.this,MyProfile.class);
        startActivity(intent);
        finish();
    }
}