package com.varsity_management.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.varsity_management.R;
import com.varsity_management.fragments.SubjectOfferFragment;
import com.varsity_management.utils.VMConstants;

public class FragmentLoaderActivity extends AppCompatActivity {
    private String loadFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_loader);

        if (getIntent().hasExtra(VMConstants.FRAGMENT_LOAD)) {
            loadFragment = getIntent().getStringExtra(VMConstants.FRAGMENT_LOAD);
            setUpFragment(loadFragment);
        }
    }

    private void setUpFragment(String loadFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (loadFragment.equalsIgnoreCase(VMConstants.SUBJECT_FRAGMENT)) {
            getSupportActionBar().setTitle("Add Subject");
            ft.replace(R.id.fragmentContainer, new SubjectOfferFragment());
        }
        ft.commit();
    }
}