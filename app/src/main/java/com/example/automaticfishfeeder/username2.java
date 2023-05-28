package com.example.automaticfishfeeder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.automaticfishfeeder.databinding.ActivityUsername2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class username2 extends AppCompatActivity {
    private android.widget.EditText email;
    private android.widget.EditText password;
    private android.widget.Button login;
    private android.widget.Button work;
    private FirebaseAuth firebaseAuth;
    //private android.widget.ProgressBar progressDialog;
    //private android.widget.EditText user;
    private android.widget.TextView VIEW;
    private android.widget.Button police;
    ProgressDialog progressDialog;
    ActivityUsername2Binding binding;
    private android.widget.TextView t1;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username2);
        firebaseAuth = FirebaseAuth.getInstance();
        //progressDialog = findViewById(R.id.progressBar);
        email = findViewById(R.id.editTextTextPersonName3);
        password = findViewById(R.id.editTextTextPersonName4);
        work = findViewById(R.id.button4);
        VIEW = findViewById(R.id.textView);
        String num = getIntent().getStringExtra("value");
        VIEW.setText(num);
        t1=findViewById(R.id.textView);
        String num2=getIntent().getStringExtra("num");
        password.setText(num2);
        String num3=getIntent().getStringExtra("email");
        email.setText(num3);
        //progressDialog.setVisibility(View.INVISIBLE);
        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();

                if (!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strPassword)) {

                    //progressDialog.setVisibility(View.VISIBLE);
                    progressDialog = new ProgressDialog(username2.this);
                    progressDialog.setMessage("Account is creating wait for the magic ....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(username2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(username2.this, loginactivity.class);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(username2.this, "SUCCESSFULLY ACCOUNT CREATED" + "verification link send to your email", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(username2.this, "Error in sending link,Try again", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            } else {
                                Toast.makeText(username2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });


                } else {
                    Toast.makeText(username2.this, "Email or Password can not be empty", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}




