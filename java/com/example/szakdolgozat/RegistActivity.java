package com.example.szakdolgozat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistActivity.class.getName();
    private static final String PREF_KEY = RegistActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    EditText etName, etEmail, etPhone, etDate, etPassword, etReentertPassword;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        mAuth = FirebaseAuth.getInstance();
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etDate = findViewById(R.id.etDate);
        etPassword = findViewById(R.id.etPassword);
        etReentertPassword = findViewById(R.id.etReentertPassword);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        etEmail.setText(email);
        etPassword.setText(password);
        etReentertPassword.setText(password);
        Log.i(LOG_TAG, "onCreate");
    }

    public void save (View view) {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String birthofdate = etDate.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String reenterpassword = etReentertPassword.getText().toString().trim();



        if (!password.equals((reenterpassword))) {
            Toast.makeText(this, "Password is not match!", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password is wrong. Must have 6 or more charackters.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!email.contains("@")) {
            Toast.makeText(this, "It's not an e-mail.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!email.contains(".")) {
            Toast.makeText(this, "It's not an e-mail.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            registertUser(name, email, phone, birthofdate, password);
        }


        Log.i(LOG_TAG, "Regisztrált: " + email + ", password: " + password);


        }
    private void registertUser (String name, String email, String phone, String birthofdate, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification();
                    startStore();
                } else {
                    Toast.makeText(RegistActivity.this, "User was't created successfully:", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void startStore(/* registered used class */) {
        Intent intent = new Intent(this, StoreActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }


    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}