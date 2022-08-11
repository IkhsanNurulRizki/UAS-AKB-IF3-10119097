package com.example.uas_akb_if3_10119097;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * 10119097
 * Ikhsan Nurul Rizki
 * IF-3 */
public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText emailLogin, passwordLogin;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.toRegister);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.loginUsr);
        signIn.setOnClickListener(this);

        emailLogin = (EditText) findViewById(R.id.emailLogn);
        passwordLogin = (EditText) findViewById(R.id.passwordLogn);

        progressBar = (ProgressBar) findViewById(R.id.progsBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toRegister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.loginUsr:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if(email.isEmpty()){
            emailLogin.setError("Mohon tuliskan email anda!!");
            emailLogin.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLogin.setError("Mohon isi email dengan benar, contoh: 'budi.dibudi@gmail.com'");
            emailLogin.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordLogin.setError("Mohon tuliskan kata sandi anda!!");
            passwordLogin.requestFocus();
            return;
        }
        if(password.length() < 6) {
            passwordLogin.setError("Kata sandi harus lebih dari 6 huruf!!");
            passwordLogin.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Login.this, MainActivity.class));
                }
                else{
                    Toast.makeText(Login.this, "Pastikan email dan password yang anda masukkan tidak salah!! Atau tekan Daftar Akun untuk daftar!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}