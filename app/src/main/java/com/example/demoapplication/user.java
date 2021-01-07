package com.example.demoapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class user extends Fragment {
    DatabaseReference mDatabaseRef;
    ListView leave;
    List<profile> gd;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user1, container, false);
        leave = (ListView) v.findViewById(R.id.v1);
        gd = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gd.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    profile upload = postSnapshot.getValue(profile.class);
                    gd.add(upload);
                }
                ImageAdapter mAdapter = new ImageAdapter(getActivity(), gd);
                leave.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
