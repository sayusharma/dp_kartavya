package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.dpkartavya.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class RegisterActvity extends AppCompatActivity {
    private ImageView imageView;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri currentPhotoUri;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Spinner policeStation;
    private EditText name,mob,conMob,rank,pass,conPass;
    private String currentPhotoDownloadableUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actvity);
        imageView = findViewById(R.id.userPhoto);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("requests");
        name = findViewById(R.id.userName);
        mob = findViewById(R.id.userMob);
        conMob = findViewById(R.id.userMobConfirm);
        rank = findViewById(R.id.userRank);
        policeStation = findViewById(R.id.policeStation);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                R.array.police_station, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        policeStation.setAdapter(adapters);
    }
    public void onClickUploadProfilePhoto(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }
    public void onClickRegisterNowDetails(View view){
        if(validate()){

            User user = new User(name.getText().toString(),rank.getText().toString(),currentPhotoDownloadableUrl,"+91"+mob.getText().toString()
                    ,policeStation.getSelectedItem().toString());
            databaseReference.child("+91"+mob.getText().toString()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"PLEASE WAIT UNTIL YOUR SIGN UP REQUEST IS REVIEWED!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActvity.this,LoginActivity.class);
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
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(mob.getText()) || TextUtils.isEmpty(conMob.getText()) ||
                TextUtils.isEmpty(rank.getText())){
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(policeStation.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(currentPhotoDownloadableUrl.equals("")){
            Toast.makeText(getApplicationContext(),"PLEASE UPLOAD PHOTO",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!mob.getText().toString().equals(conMob.getText().toString())){
            Toast.makeText(getApplicationContext(),"MOBILE NUMBER DOES NOT MATCH!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                currentPhotoUri = result.getUri();
                setImageView(currentPhotoUri);
                setDownloadableUrl(currentPhotoUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void onClickCancelRegister(View view){
        Intent intent = new Intent(RegisterActvity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void setImageView(Uri currentPhotoUri) {
        ImageView imageView = findViewById(R.id.userPhoto);
        imageView.setImageURI(currentPhotoUri);
    }

    private void setDownloadableUrl(Uri uri) {
        final StorageReference reference;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Photo....");
        progressDialog.show();
        reference = storageReference.child("UserImages/"+ UUID.randomUUID().toString());
        reference.putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        progressDialog.dismiss();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                currentPhotoDownloadableUrl = uri.toString();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"ERROR UPLOADING PHOTO",Toast.LENGTH_LONG).show();
                    }
                });
    }

}