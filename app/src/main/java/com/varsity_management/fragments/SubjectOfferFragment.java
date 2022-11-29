package com.varsity_management.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.varsity_management.R;
import com.varsity_management.model.SubjectModel;
import com.varsity_management.model.SubjectName;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        if (TextUtils.isEmpty(txtSemester.getText().toString())) {
            Toast.makeText(getContext(), "Select Semester", Toast.LENGTH_SHORT).show();
        } else if (subLayout.getChildCount() == 0) {
            Toast.makeText(getContext(), "Add Subject", Toast.LENGTH_SHORT).show();
        } else {
            if (isValidData()) {
                subjectModel = new SubjectModel(semesterName, subjectNameList);
                subjectModelList.add(subjectModel);
               FirebaseDatabase.getInstance().getReference().child("Subject Offer").setValue(subjectModel);
            } else {
                Toast.makeText(getContext(), "Enter Subject Code And Name", Toast.LENGTH_SHORT).show();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subject_offer, container, false);
        ButterKnife.bind(this, view);
        setSemester();
        subjectNameList = new ArrayList<>();
        txtSemester.setOnItemClickListener((adapterView, view1, i, l) -> {
            semesterName = semesterList.get(i);
        });
        subjectModelList = new ArrayList<>();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.prof_autocomplete, R.id.txtDropdown, semesterList);
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
            subjectName = new SubjectName();
            if (!TextUtils.isEmpty(txtSubNameAndCode.getText().toString())) {
                subjectName.setSubCodeName(txtSubNameAndCode.getText().toString());
            } else {
                result = false;
                break;
            }
            subjectNameList.add(subjectName);
        }

        return result;
    }

}