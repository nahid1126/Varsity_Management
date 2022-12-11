package com.varsity_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.varsity_management.R;
import com.varsity_management.adapters.SubjectOfferAdapter;
import com.varsity_management.adapters.SubjectRegistrationAdapter;
import com.varsity_management.model.SubjectModel;
import com.varsity_management.model.SubjectName;
import com.varsity_management.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SubjectRegistrationActivity extends AppCompatActivity {

    @BindView(R.id.subjectRegistrationRecyclerView)
    RecyclerView subjectRegistrationRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<SubjectModel> subjectModelList;
    private SubjectRegistrationAdapter subjectRegistrationAdapter;
    private List<SubjectName> subjectNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_registration);
        ButterKnife.bind(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Subject Offer");
        getSupportActionBar().setTitle("Subject Registration List");

        progressBar.setProgress(100);
        progressBar.setVisibility(View.VISIBLE);
        subjectRegistrationAdapter = new SubjectRegistrationAdapter();
        setUpRecyclerView();
    }

    private void getData() {
        if (!NetworkUtils.isNetworkConnected(this)) {
            progressBar.setVisibility(View.GONE);
            Toasty.error(SubjectRegistrationActivity.this, "Please Connect Internet!!", Toast.LENGTH_SHORT).show();
            return;
        }
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
            progressBar.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            Toasty.warning(SubjectRegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        });
    }

    private void setData() {
        subjectRegistrationAdapter.setSubjectModelList(subjectModelList);
        subjectRegistrationAdapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        subjectRegistrationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectRegistrationRecyclerView.setAdapter(subjectRegistrationAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }
}