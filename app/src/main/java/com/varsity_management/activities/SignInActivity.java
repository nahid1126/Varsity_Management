package com.varsity_management.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.varsity_management.R;
import com.varsity_management.utils.LocalStorage;
import com.varsity_management.utils.NetworkUtils;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    @BindView(R.id.txtMail)
    EditText txtMail;

    @BindView(R.id.txtPass)
    EditText txtPass;

    @OnClick(R.id.btnSignIn)
    public void signBtnClicked() {
        if (!NetworkUtils.isNetworkConnected(this)) {
            Toasty.error(SignInActivity.this, "Please Connect Internet!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = txtMail.getText().toString();
        String pass = txtPass.getText().toString();
        if (email.isEmpty()) {
            txtMail.requestFocus();
            Toast.makeText(this, "Please Enter Varsity Mail", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmailId(email.trim())) {
            txtMail.requestFocus();
            Toast.makeText(this, "Enter Varsity Mail", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            txtPass.requestFocus();
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(authResult -> {
                localStorage.putLoginResponse(true);

                DocumentReference df = fireStore.collection("Users").document(authResult.getUser().getUid());
                df.get().addOnSuccessListener(documentSnapshot -> {
                    localStorage.putProfession(documentSnapshot.getString("prof"));
                    localStorage.putId(documentSnapshot.getString("id"));
                    localStorage.putName(documentSnapshot.getString("name"));
                    localStorage.putDept(documentSnapshot.getString("dept"));
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                });
                Toasty.success(SignInActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> Toasty.error(SignInActivity.this, "Login Failed! :" + e.getMessage(), Toast.LENGTH_SHORT).show());
        }

    }

    @OnClick(R.id.txtSignUp)
    public void signUpClicked() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private BroadcastReceiver broadcastReceiver;
    private LocalStorage localStorage;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        getSupportActionBar().hide();
        localStorage = new LocalStorage(this);
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private boolean isValidEmailId(String email) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@lus.ac.bd").matcher(email).matches();
    }
}