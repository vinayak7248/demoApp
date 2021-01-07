package com.example.demoapplication;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.*;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class Enroll extends Fragment {
    View view;
    private TextView t;
    private ImageButton ProfileImage;
    private Button upload;
    private EditText Fname1,Lname1,DOB1,Gender1,country1,state1,Htown1,Mno1,Tno1;

    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private String profile_pic, Fname,Lname,DOB,Gender,country,state,Htown;
    private  long Mno,Tno;
    int y;
    Calendar myCalendar;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    ContentResolver cr;

    public Enroll() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_enroll, container, false);
        ProfileImage = view.findViewById(R.id.imageButton4);
        Fname1 = view.findViewById(R.id.name);
        Lname1 = view.findViewById(R.id.Lname);
        DOB1=    view.findViewById(R.id.DOB);
        Gender1 = view.findViewById(R.id.Gender);
        country1 = view.findViewById(R.id.Country);
        state1 = view.findViewById(R.id.state);
        Htown1 = view.findViewById(R.id.town);
        Mno1 = view.findViewById(R.id.Mno);
        Tno1 = view.findViewById(R.id.Tno);
        upload = view.findViewById(R.id.add);
        t=view.findViewById(R.id.textView);

        myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                y=year;
                updateLabel();
            }

        };

        DOB1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference("profile");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile");


        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fname=Fname1.getText().toString();
                Lname=Lname1.getText().toString();
                DOB=DOB1.getText().toString();
                Gender=Gender1.getText().toString();
                country=country1.getText().toString();
                state=state1.getText().toString();
                Htown=Htown1.getText().toString();

                if (imageUri == null) {
                    Toast.makeText(getActivity(), "please select profile picture", Toast.LENGTH_SHORT).show();
                }
                else if(Fname.isEmpty()){
                    Toast.makeText(getActivity(), "please Enter First Name", Toast.LENGTH_SHORT).show();
                }else if(Lname.isEmpty()){
                    Toast.makeText(getActivity(), "please Enter last Name", Toast.LENGTH_SHORT).show();
                }else if(DOB.isEmpty()){
                    Toast.makeText(getActivity(), "please Enter Date of Birth", Toast.LENGTH_SHORT).show();
                }else if(Gender.isEmpty()){
                    Toast.makeText(getActivity(), "please Enter Gender", Toast.LENGTH_SHORT).show();
                }else if(country.isEmpty()){
                    Toast.makeText(getActivity(), "please Enter country", Toast.LENGTH_SHORT).show();
                }else if(Htown.isEmpty()){
                    Toast.makeText(getActivity(), "please Enter Home town", Toast.LENGTH_SHORT).show();
                }else if(state.isEmpty()){
                    Toast.makeText(getActivity(), "please Enter state", Toast.LENGTH_SHORT).show();
                } else if(Mno1.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "please Enter Mobile no", Toast.LENGTH_SHORT).show();
                } else if(Tno1.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "please Enter Telephone no", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Tno = Long.parseLong(Mno1.getText().toString());
                    }
                    catch (Exception e){
                        Toast.makeText(getActivity(), "please Enter Mobile number properly", Toast.LENGTH_SHORT).show();
                    }

                    try {
                        Tno = Long.parseLong(Tno1.getText().toString());
                    }
                    catch (Exception e){
                        Toast.makeText(getActivity(), "please Enter Telephone number properly", Toast.LENGTH_SHORT).show();
                    }
                    uploadFile();
                }
                }
        });
        return view;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DOB1.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                cr=getActivity().getApplicationContext().getContentResolver();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr
                        , imageUri);
                ProfileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFile() {

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoLink = uri.toString();
                                    String uploadId = mDatabaseRef.push().getKey();
                                    profile prof=new profile(uploadId,photoLink, Fname,Lname,y,Gender,country,state,Htown,Mno,Tno);
                                    mDatabaseRef.child(uploadId).setValue(prof);
                                    Toast.makeText(getActivity(), "User is added", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Unable to User is add", Toast.LENGTH_SHORT).show();
                        }
                    });

        }

}