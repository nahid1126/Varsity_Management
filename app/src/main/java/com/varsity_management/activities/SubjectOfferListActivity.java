package com.varsity_management.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.varsity_management.R;
import com.varsity_management.adapters.SubjectOfferAdapter;
import com.varsity_management.model.SubjectModel;
import com.varsity_management.model.SubjectName;
import com.varsity_management.utils.VMConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubjectOfferListActivity extends AppCompatActivity {
    private static final String TAG = "SubjectOfferListActivity";

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

    private List<SubjectModel> subjectModelList;
    private SubjectOfferAdapter subjectOfferAdapter;
    private List<SubjectName> subjectNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_offer_list);
        ButterKnife.bind(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Subject Offer");
        getSupportActionBar().setTitle("Subject Offer List");


        subjectOfferAdapter = new SubjectOfferAdapter();
        setUpRecyclerView();

    }

    private void getData() {
        subjectModelList = new ArrayList<>();

        databaseReference.get().addOnCompleteListener(task -> {
            for (DataSnapshot postSnapshot : task.getResult().getChildren()) {
                subjectNameList = new ArrayList<>();
                for (DataSnapshot snap : postSnapshot.getChildren()) {
                    subjectNameList.add(snap.getValue(SubjectName.class));
                }
                subjectModelList.add(new SubjectModel(postSnapshot.getKey(), subjectNameList));
            }
            setData();
        });
    }

    private void setData() {
        subjectOfferAdapter.setSubjectModelList(subjectModelList);
        subjectOfferAdapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectRecyclerView.setAdapter(subjectOfferAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }
}