package com.example.demoapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<profile> {
    private Activity mContext;
    private List<profile> mUploads;
    String id;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;

    public ImageAdapter(Activity context, List<profile> uploads) {
        super(context,R.layout.content,uploads);
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        profile uploadCurrent = mUploads.get(position);
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listViewItem;
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile");
        TextView textViewName,textViewName1;
        ImageView imageView,delete;
        listViewItem = inflater.inflate(R.layout.content, null, true);
        textViewName = listViewItem.findViewById(R.id.text_view_name);
        textViewName1 = listViewItem.findViewById(R.id.textView3);
        delete=listViewItem.findViewById(R.id.delete);
        imageView = listViewItem.findViewById(R.id.image_view_upload);
        textViewName.setText(uploadCurrent.getFname()+" "+uploadCurrent.getLname());
        int m=Calendar.getInstance().get(Calendar.YEAR)-uploadCurrent.getDOB();
        textViewName1.setText(uploadCurrent.getGender()+" | "+m+" | "
        +uploadCurrent.getHtown());
        Picasso.get()
                .load(uploadCurrent.getProfile_pic())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageView);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference imageRef = mStorage.getReferenceFromUrl(uploadCurrent.getProfile_pic());
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabaseRef.child(uploadCurrent.getId()).removeValue();
                        Toast.makeText(getContext(), "User deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            ;
        });
        return listViewItem;
    }
}