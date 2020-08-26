package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.dpkartavya.Common.CurrentUser;
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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Currency;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    private EditText name,rank,police;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri currentPhotoUri;
    private ImageView imageView;
    private String currentPhotoDownloadableUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imageView = findViewById(R.id.euserPhoto);
        name = findViewById(R.id.euserName);
        rank = findViewById(R.id.euserRank);
        police = findViewById(R.id.euserPoliceStation);
        Picasso.get().load(CurrentUser.currentUser.getPhoto()).into(imageView);
        name.setText(CurrentUser.currentUser.getName());
        rank.setText(CurrentUser.currentUser.getRank());
        police.setText(CurrentUser.currentUser.getPolice());
    }
    public void eonClickUploadProfilePhoto(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }
    public void eonClickRegisterNowDetails(View view){
        if(validate()){
            User user = new User(name.getText().toString(),rank.getText().toString(),currentPhotoDownloadableUrl,CurrentUser.currentUser.getMob(),
                    CurrentUser.currentUser.getPass(),police.getText().toString());
            databaseReference.child(CurrentUser.currentUser.getMob()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"PLEASE WAIT UNTIL YOUR SIGN UP REQUEST IS REVIEWED!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditProfileActivity.this,DashActivity.class);
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
        if (TextUtils.isEmpty(name.getText()) ||
                TextUtils.isEmpty(rank.getText() )|| TextUtils.isEmpty(police.getText())){
            Toast.makeText(getApplicationContext(),"FILEDS CANNOT BE EMPTY!",Toast.LENGTH_SHORT).show();
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

    private void setImageView(Uri currentPhotoUri) {
        ImageView imageView = findViewById(R.id.euserPhoto);
        imageView.setImageURI(currentPhotoUri);
    }
    public void onClickBackEditProfile(View view){
        Intent intent = new Intent(EditProfileActivity.this,MyProfile.class);
        startActivity(intent);
        finish();
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