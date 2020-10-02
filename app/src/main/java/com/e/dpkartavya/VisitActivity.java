package com.e.dpkartavya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavya.Common.CurrentSenior;
import com.e.dpkartavya.Common.CurrentUser;
import com.e.dpkartavya.Common.CurrentVisit;
import com.e.dpkartavya.Model.LastVisit;
import com.e.dpkartavya.Model.Loc;
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

public class VisitActivity extends AppCompatActivity implements LocationListener {
    private Uri currentPhotoUri;
    private static final int LOCATION_REQ_CODE = 1021;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ImageView img;
    private EditText notes,complaint;
    private String plcStn;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private TextView name,mob;
    private String currentPhotoDownloadableUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("visits");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        plcStn = getIntent().getStringExtra("police");
        notes = findViewById(R.id.visitNotes);
        img = findViewById(R.id.visitImg);
        name = findViewById(R.id.visitName);
        complaint = findViewById(R.id.visitComplaint);
        mob = findViewById(R.id.visitMob);
        mob.setText( CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getMob());
        name.setText( CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getName());
        try {
            Picasso.get()
                    .load(CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getPhoto())
                    .into(img);
        }catch (Exception e){
            e.printStackTrace();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_REQ_CODE);
        } else {
            //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }
                break;
        }
    }
    public void onClickMarkThisVisit(View vie){
        if (TextUtils.isEmpty(notes.getText())){
            Toast.makeText(getApplicationContext(),"NOTES CANNOT BE EMPTY!",Toast.LENGTH_LONG).show();
        }
        else{
            Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            Loc loc = new Loc(String.valueOf(latitude),String.valueOf(longitude));
            String currentDate = year + "/" + (month+1) + "/" + day;
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            DatabaseReference databaseReference1 = firebaseDatabase.getReference("last_visited");
            DatabaseReference databaseReference2 = firebaseDatabase.getReference("my_visits");
            Visit visit = new Visit(CurrentUser.currentUser.getPolice(),name.getText().toString(),currentPhotoDownloadableUrl,notes.getText().toString(),complaint.getText().toString(), CurrentUser.currentUser.getMob(),CurrentUser.currentUser.getName(),currentDate,currentTime,CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getMob(),loc);
            databaseReference2.child(CurrentUser.currentUser.getMob()).child(String.valueOf(System.currentTimeMillis())).setValue(visit);
            LastVisit lastVisit = new LastVisit(name.getText().toString(),CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getMob(),currentDate,CurrentUser.currentUser.getPolice(), CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getPhoto());
            databaseReference1.child("+91"+CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getMob()).setValue(lastVisit);
            databaseReference.child("+91"+CurrentVisit.currentVisit.getBasicDetails().getPersonalDetails().getMob()).child(String.valueOf(System.currentTimeMillis())).setValue(visit).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"VISIT UPDATED",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(VisitActivity.this,MarkVisitActivity.class);
                    startActivity(intent);
                    finish();
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
        intent.putExtra("police",plcStn);
        startActivity(intent);
        finish();
    }
    private void setDownloadableUrl(Uri uri) {
        final StorageReference reference;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Photo....");
        progressDialog.show();
        reference = storageReference.child("VisitImages/"+ UUID.randomUUID().toString());
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}