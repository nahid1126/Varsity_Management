package com.varsity_management.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.varsity_management.R;
import com.varsity_management.activities.SignUpActivity;
import com.varsity_management.model.SubjectModel;
import com.varsity_management.model.SubjectName;
import com.varsity_management.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SubjectOfferFragment extends Fragment {

    private static final String TAG = "SubjectOfferFragment";
    @BindView(R.id.subLayout)
    LinearLayout subLayout;

    @BindView(R.id.txtSemester)
    AutoCompleteTextView txtSemester;

    @OnClick(R.id.txtSemester)
    public void onSemesterClicked() {
        txtSemester.showDropDown();
    }

    @OnClick(R.id.btnAddSub)
    public void onAddBtnClicked() {
        addView();
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitBtnClicked() {

        if (!NetworkUtils.isNetworkConnected(getActivity())) {
            Toasty.error(getActivity(), "Please Connect Internet!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(txtSemester.getText().toString())) {
            Toasty.warning(getContext(), "Select Semester", Toast.LENGTH_SHORT).show();
        } else if (subLayout.getChildCount() == 0) {
            Toasty.warning(getContext(), "Add Subject", Toast.LENGTH_SHORT).show();
        } else {
            if (isValidData()) {
                databaseReference.child(semesterName).setValue(subjectNameList).addOnSuccessListener(unused -> {
                    Toasty.success(getActivity(), "Subject Added Successful", Toast.LENGTH_SHORT).show();
                    clearFiled();
                }).addOnFailureListener(e -> Toasty.error(getActivity(), "Subject Adding Field : " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                Toasty.warning(getContext(), "Enter Subject Code And Name or Credit", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public SubjectOfferFragment() {
        // Required empty public constructor
    }

    private List<SubjectName> subjectNameList;
    private List<String> semesterList;
    private String semesterName;
    private SubjectModel subjectModel;
    private SubjectName subjectName;
    private List<SubjectModel> subjectModelList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_offer, container, false);
        ButterKnife.bind(this, view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Subject Offer");
        setSemester();
        subjectNameList = new ArrayList<>();
        txtSemester.setOnItemClickListener((adapterView, view1, i, l) -> {
            semesterName = semesterList.get(i);
        });
        subjectModelList = new ArrayList<>();
        subjectModel = new SubjectModel();
        return view;
    }

    private void setSemester() {
        semesterList = new ArrayList<>();
        semesterList.add("1st Semester");
        semesterList.add("2nd Semester");
        semesterList.add("3rd Semester");
        semesterList.add("4th Semester");
        semesterList.add("5th Semester");
        semesterList.add("6th Semester");
        semesterList.add("7th Semester");
        semesterList.add("8th Semester");
        semesterList.add("9th Semester");
        semesterList.add("10th Semester");
        semesterList.add("11th Semester");
        semesterList.add("12th Semester");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.prof_autocomplete, R.id.txtDropdown, semesterList);
        txtSemester.setAdapter(adapter);
    }

    private void addView() {
        View subView = getLayoutInflater().inflate(R.layout.row_edit_text, null, false);

        EditText txtSubNameAndCode = subView.findViewById(R.id.txtSubCode);
        ImageView btnCancel = subView.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(view -> removeView(subView));
        subLayout.addView(subView);
    }

    private void removeView(View subView) {
        subLayout.removeView(subView);
    }

    // TODO: 11/27/2022 for subject offer list
    private boolean isValidData() {
        subjectNameList.clear();
        boolean result = true;
        for (int i = 0; i < subLayout.getChildCount(); i++) {
            View subView = subLayout.getChildAt(i);
            EditText txtSubNameAndCode = subView.findViewById(R.id.txtSubCode);
            EditText txtCredit = subView.findViewById(R.id.txtCredit);
            subjectName = new SubjectName();
            if (!TextUtils.isEmpty(txtSubNameAndCode.getText().toString()) || !TextUtils.isEmpty(txtCredit.getText().toString())) {
                subjectName.setSubCodeName(txtSubNameAndCode.getText().toString());
                subjectName.setCredit(Double.parseDouble(txtCredit.getText().toString()));
            } else {
                result = false;
                break;
            }
            subjectNameList.add(subjectName);
        }

        return result;
    }

    private void clearFiled() {

    }

}