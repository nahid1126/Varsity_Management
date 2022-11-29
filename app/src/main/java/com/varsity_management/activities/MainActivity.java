package com.varsity_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.varsity_management.R;
import com.varsity_management.utils.LocalStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layoutStudent)
    LinearLayout stuLay;

    @BindView(R.id.layoutTeacher)
    LinearLayout teaLay;

    @BindView(R.id.layoutAccount)
    LinearLayout accLay;

    @BindView(R.id.displayName)
    TextView displayName;

    @BindView(R.id.displayId)
    TextView displayId;

    @BindView(R.id.displayProf)
    TextView displayProf;

    @BindView(R.id.displayDept)
    TextView displayDept;


    @OnClick(R.id.layoutSubOffer)
    public void onSubOfferClicked() {
        startActivity(new Intent(MainActivity.this, SubjectOfferListActivity.class));
    }

    private LocalStorage localStorage;
    private FirebaseFirestore fireStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localStorage = new LocalStorage(this);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Dash Board");

        if (localStorage.getProfession().equalsIgnoreCase("")) {
            logOut();
        }
        setView();
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        if (localStorage.getProfession().equalsIgnoreCase("Teacher")) {
            stuLay.setVisibility(View.GONE);
            teaLay.setVisibility(View.VISIBLE);
            accLay.setVisibility(View.GONE);
        } else if (localStorage.getProfession().equalsIgnoreCase("Account")) {
            stuLay.setVisibility(View.GONE);
            teaLay.setVisibility(View.GONE);
            accLay.setVisibility(View.VISIBLE);
        } else if (localStorage.getProfession().equalsIgnoreCase("Student")) {
            stuLay.setVisibility(View.VISIBLE);
            teaLay.setVisibility(View.GONE);
            accLay.setVisibility(View.GONE);
        }
        displayProf.setText("Profession : " + localStorage.getProfession());
        displayId.setText("ID : " + localStorage.getID());
        displayName.setText("Name : " + localStorage.getName());
        displayDept.setText("Dept : " + localStorage.getDept());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logOut:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void logOut() {
        localStorage.putLoginResponse(false);
        localStorage.putProfession("");
        localStorage.putId("");
        localStorage.putName("");
        localStorage.putDept("");
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}