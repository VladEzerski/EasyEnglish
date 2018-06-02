package com.ezerski.vladislav.easyenglish.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.ui.main.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity{

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private EditText etUsername, etPassword;

    private String email, password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFirebaseAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.et_sign_in_username);
        etPassword = findViewById(R.id.et_sign_in_password);

        email = etUsername.getText().toString();
        password = etPassword.getText().toString();

        Button btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
        Button btnCreateAccount = findViewById(R.id.btn_create_account);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "SignIn failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(){
        boolean valid = true;

        String email = etUsername.getText().toString();
        if (TextUtils.isEmpty(email)){
            etUsername.setError("Required");
            valid = false;
        } else {
            etUsername.setError(null);
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)){
            etPassword.setError("Required");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }
}