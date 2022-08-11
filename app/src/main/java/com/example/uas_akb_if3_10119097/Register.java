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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

/**
 * 10119097
 * Ikhsan Nurul Rizki
 * IF-3 */
public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registeruser;
    private EditText nama_lngkp, eMail, pwd;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registeruser = (Button) findViewById(R.id.registerUsr);
        registeruser.setOnClickListener(this);

        nama_lngkp = (EditText) findViewById(R.id.nameReg);
        eMail = (EditText) findViewById(R.id.emailReg);
        pwd = (EditText) findViewById(R.id.passwordReg);

        progressbar = (ProgressBar) findViewById(R.id.progsBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case  R.id.registerUsr:
                registerUsr();
                        
        }
    }

    private void registerUsr() {
        String nama_lengkap = nama_lngkp.getText().toString().trim();
        String email = eMail.getText().toString().trim();
        String password = pwd.getText().toString().trim();

        if(nama_lengkap.isEmpty()){
            nama_lngkp.setError("Mohon tuliskan nama anda!!");
            nama_lngkp.requestFocus();
            return;
        }
        if(email.isEmpty()){
            eMail.setError("Mohon tuliskan email anda!!");
            eMail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eMail.setError("Mohon isi email dengan benar, contoh: 'budi.dibudi@gmail.com'");
            eMail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pwd.setError("Mohon tuliskan kata sandi anda!!");
            pwd.requestFocus();
            return;
        }
        if(password.length() < 6){
            pwd.setError("Kata sandi harus lebih dari 6 huruf!!");
            pwd.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user user = new user(nama_lengkap, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "Akun berhasil didaftarkan", Toast.LENGTH_LONG).show();
                                                progressbar.setVisibility(View.GONE);
                                            }
                                            else{
                                                Toast.makeText(Register.this, "Gagal membuat akun! Mohon coba lagi!!", Toast.LENGTH_LONG).show();
                                                progressbar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(Register.this, "Gagal membuat akun! Mohon coba lagi!!", Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}