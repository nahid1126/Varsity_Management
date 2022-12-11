package com.varsity_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.varsity_management.R;
import com.varsity_management.model.UserModel;
import com.varsity_management.utils.LocalStorage;
import com.varsity_management.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.txtName)
    EditText txtName;

    @BindView(R.id.txtId)
    EditText txtId;

    @BindView(R.id.txtMail)
    EditText txtMail;

    @BindView(R.id.txtProf)
    AutoCompleteTextView txtProf;

    @BindView(R.id.txtPass)
    EditText txtPass;

    @BindView(R.id.txtConfirmPass)
    EditText txtConfirmPass;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.txtProf)
    public void clickedProf() {
        txtProf.showDropDown();
    }

    @BindView(R.id.txtDept)
    AutoCompleteTextView txtDept;

    @OnClick(R.id.txtDept)
    public void clickDept() {
        txtDept.showDropDown();
    }

    @OnClick(R.id.btnSignUp)
    public void signUpClicked() {
        String name = txtName.getText().toString();
        String id = txtId.getText().toString();
        String email = txtMail.getText().toString();
        String profession = txtProf.getText().toString();
        String pass = txtPass.getText().toString();
        String confPass = txtConfirmPass.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        if (!NetworkUtils.isNetworkConnected(this)) {
            progressBar.setVisibility(View.GONE);
            Toasty.error(SignUpActivity.this, "Please Connect Internet!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name.isEmpty()) {
            txtName.requestFocus();
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        } else if (id.isEmpty()) {
            txtId.requestFocus();
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Please Enter ID", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            txtMail.requestFocus();
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmailId(email.trim())) {
            txtMail.requestFocus();
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Enter Varsity Mail", Toast.LENGTH_SHORT).show();
        } else if (profession.isEmpty()) {
            txtProf.requestFocus();
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Please Select Profession", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            txtPass.requestFocus();
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else if (confPass.isEmpty()) {
            txtConfirmPass.requestFocus();
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
        } else if (!confPass.equals(pass)) {
            progressBar.setVisibility(View.GONE);
            Toasty.warning(this, "Confirm password didn't match\nEnter same password", Toast.LENGTH_SHORT).show();
        } else {
            userModel = new UserModel(name, id, email, deptId, profId, pass);
            fAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(authResult -> {
                FirebaseUser user = fAuth.getCurrentUser();
                progressBar.setVisibility(View.GONE);
                Toasty.success(SignUpActivity.this, "Account Create Successful!", Toast.LENGTH_SHORT).show();
                DocumentReference df = fireStore.collection("Users").document(user.getUid());
                df.set(userModel);
                finish();
            }).addOnFailureListener(e -> {
                progressBar.setVisibility(View.GONE);
                Toasty.error(SignUpActivity.this, "Account Create Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.txtSignIn)
    public void signInClicked() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    private String profId;
    private String deptId;
    private List<String> professionList;
    private List<String> departmentList;
    private UserModel userModel;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fireStore;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        getSupportActionBar().hide();
        localStorage = new LocalStorage(this);
        fAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        setProfession();
        setDepartment();
        txtProf.setOnItemClickListener((adapterView, view, i, l) -> {
            profId = professionList.get(i);
        });
        txtDept.setOnItemClickListener((adapterView, view, i, l) -> {
            deptId = departmentList.get(i);
        });
    }

    private void setProfession() {
        professionList = new ArrayList<>();
        professionList.add("Account");
        professionList.add("Teacher");
        professionList.add("Student");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.prof_autocomplete, R.id.txtDropdown, professionList);
        txtProf.setAdapter(adapter);
    }

    private void setDepartment() {
        departmentList = new ArrayList<>();
        departmentList.add("BBA");
        departmentList.add("CSE");
        departmentList.add("ENGLISH");
        departmentList.add("EEE");
        departmentList.add("CE");
        departmentList.add("Architecture");
        departmentList.add("Law");
        departmentList.add("Islamic Studies");
        departmentList.add("Public Health");
        departmentList.add("Bangla");
        departmentList.add("Tourism & Hospitality Management");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.prof_autocomplete, R.id.txtDropdown, departmentList);
        txtDept.setAdapter(adapter);
    }

    private boolean isValidEmailId(String email) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@lus.ac.bd").matcher(email).matches();
    }
}