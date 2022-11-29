package com.varsity_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.varsity_management.R;
import com.varsity_management.utils.VMConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubjectOfferListActivity extends AppCompatActivity {

    @BindView(R.id.subjectRecyclerView)
    RecyclerView subjectRecyclerView;


    @OnClick(R.id.btnAddSub)
    public void onAddBtnClicked() {
        Intent intent = new Intent(this, FragmentLoaderActivity.class);
        intent.putExtra(VMConstants.FRAGMENT_LOAD, VMConstants.SUBJECT_FRAGMENT);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_offer_list);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Subject Offer List");
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}