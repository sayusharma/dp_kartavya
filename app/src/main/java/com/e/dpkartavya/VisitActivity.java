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
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Common.CurrentVisit;
import com.e.dpkartavya.Model.Visit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class VisitActivity extends AppCompatActivity {
    private String seniorMob;
    private Uri currentPhotoUri;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ImageView img;
    private EditText notes;
    private TextView name,mob;
    private String currentPhotoDownloadableUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        seniorMob = getIntent().getStringExtra("mob");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("visits");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        notes = findViewById(R.id.visitNotes);
        img = findViewById(R.id.visitImg);
        name = findViewById(R.id.visitName);
        mob = findViewById(R.id.visitMob);
        mob.setText( CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getMob());
        name.setText( CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getName());
        Picasso.get()
                .load(CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getPhoto())
                .into(img);

    }
    public void onClickMarkThisVisit(View vie){
        if (TextUtils.isEmpty(notes.getText())){
            Toast.makeText(getApplicationContext(),"NOTES CANNOT BE EMPTY!",Toast.LENGTH_LONG).show();
        }
        else if(currentPhotoDownloadableUrl.equals("")){
            Toast.makeText(getApplicationContext(),"PLEASE UPLOAD PHOTO",Toast.LENGTH_LONG).show();
        }
        else{
            Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            String currentDate = day + "/" + (month+1) + "/" + year;
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            Visit visit = new Visit(name.getText().toString(),CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getMob(),CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getAddress(),currentPhotoDownloadableUrl, CurrentUser.currentUser.getEmail(),currentDate,currentTime);
            databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(visit).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"VISIT UPDATED",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(VisitActivity.this,MarkVisitActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"PLEASE TRY AGAIN LATER",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void onClickAddRecentPhoto(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
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
        ImageView imageView = findViewById(R.id.visitRecentPhoto);
        imageView.setImageURI(currentPhotoUri);
    }
    public void onClickBackVisit(View view){
        Intent intent = new Intent(VisitActivity.this,MarkVisitActivity.class);
        startActivity(intent);
        finish();
    }
    private void setDownloadableUrl(Uri uri) {
        final StorageReference reference;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Photo....");
        progressDialog.show();
        reference = storageReference.child("SnrCznImages/"+ UUID.randomUUID().toString());
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