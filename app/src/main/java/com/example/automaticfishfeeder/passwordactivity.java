package com.example.automaticfishfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class passwordactivity extends AppCompatActivity {
    private EditText email;
    private Button resetpassword;
    private ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordactivity);
        email=findViewById(R.id.editTextTextPersonName2);
        resetpassword=findViewById(R.id.button);
        progressBar=findViewById(R.id.progressBar);
        auth= FirebaseAuth.getInstance();
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }


        });

    }
    private void resetpassword() {
        String email1=email.getText().toString().trim();
        if(email1.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("please provide a valid email");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(passwordactivity.this, "check your email to reset password", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(passwordactivity.this,loginactivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(passwordactivity.this, "Try again !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}