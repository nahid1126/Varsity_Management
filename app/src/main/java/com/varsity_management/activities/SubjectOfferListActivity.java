package com.varsity_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.varsity_management.R;
import com.varsity_management.model.SubjectModel;
import com.varsity_management.utils.VMConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SubjectOfferListActivity extends AppCompatActivity {
    private static final String TAG = "SubjectOfferListActivit";

    @BindView(R.id.subjectRecyclerView)
    RecyclerView subjectRecyclerView;


    @OnClick(R.id.btnAddSub)
    public void onAddBtnClicked() {
        Intent intent = new Intent(this, FragmentLoaderActivity.class);
        intent.putExtra(VMConstants.FRAGMENT_LOAD, VMConstants.SUBJECT_FRAGMENT);
        startActivity(intent);
    }

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_offer_list);
        ButterKnife.bind(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Subject Offer");
        getSupportActionBar().setTitle("Subject Offer List");
        setUpRecyclerView();
        setData();
    }

    private void setData() {
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.d(TAG, "onComplete: "+task.getResult());
            }
        });
    }

    private void setUpRecyclerView() {
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}