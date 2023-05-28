package com.example.automaticfishfeeder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class username extends AppCompatActivity {
    private EditText etName, etEmail, etPassword;
    private TextView tvNameError, tvEmailError, tvPasswordError, tvColor;
    private CardView frameOne, frameTwo, frameThree, frameFour;
    private CardView btnRegister;
    private boolean isAtLeast8 = false, hasUppercase = false, hasNumber = false, hasSymbol = false, isRegistrationClickable = false;
    String num = String.valueOf(0);
    private TextView t1;
    private ProgressDialog progressDialog;
    private Object firebaseAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        firebaseAuth = FirebaseAuth.getInstance();
        tvColor = findViewById(R.id.tvColor);
        tvNameError = findViewById(R.id.tvNameError);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        frameOne = findViewById(R.id.frameOne);
        frameTwo = findViewById(R.id.frameTwo);
        frameThree = findViewById(R.id.frameThree);
        frameFour = findViewById(R.id.frameFour);
        btnRegister = findViewById(R.id.btnRegister);


        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = etName.getText().toString(), email = etEmail.getText().toString(), password = etPassword.getText().toString();

                if (name.length() > 0 && email.length() > 0 && password.length() > 0) {
                    if (isRegistrationClickable) {
                        //Toast.makeText(MainActivity.this, "VENUUUU", Toast.LENGTH_SHORT).show();
                        readData(name);
                    }
                } else {
                    if (name.length() == 0) {
                        tvNameError.setVisibility(View.VISIBLE);
                    }
                    if (email.length() == 0) {
                        tvEmailError.setVisibility(View.VISIBLE);
                    }
                    if (password.length() == 0) {
                        tvPasswordError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        inputChange();
    }

    private void checkEmpty(String name, String email, String password) {
        if (name.length() > 0 && tvNameError.getVisibility() == View.VISIBLE) {
            tvNameError.setVisibility(View.GONE);
        }
        if (password.length() > 0 && tvPasswordError.getVisibility() == View.VISIBLE) {
            tvPasswordError.setVisibility(View.GONE);
        }
        if (email.length() > 0 && tvEmailError.getVisibility() == View.VISIBLE) {
            tvEmailError.setVisibility(View.GONE);
        }
    }

    @SuppressLint("ResourceType")
    private void checkAllData(String email) {
        if (isAtLeast8 && hasUppercase && hasNumber && hasSymbol && email.length() > 0) {
            isRegistrationClickable = true;
            tvColor.setTextColor(Color.WHITE);
            btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            isRegistrationClickable = false;
            btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
    }

    @SuppressLint("ResourceType")
    private void registrationDataCheck() {
        String password = etPassword.getText().toString(), email = etEmail.getText().toString(), name = etName.getText().toString();

        checkEmpty(name, email, password);

        if (password.length() >= 8) {
            isAtLeast8 = true;
            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            isAtLeast8 = false;
            frameOne.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("(.*[A-Z].*)")) {
            hasUppercase = true;
            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasUppercase = false;
            frameTwo.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("(.*[0-9].*)")) {
            hasNumber = true;
            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasNumber = false;
            frameThree.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        if (password.matches("^(?=.*[_.()]).*$")) {
            hasSymbol = true;
            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorAccent)));
        } else {
            hasSymbol = false;
            frameFour.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }

        checkAllData(email);
    }

    private void inputChange() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                registrationDataCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registrationDataCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void readData(String username) {
        DatabaseReference node = FirebaseDatabase.getInstance().getReference("users");
        node.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        Toast.makeText(username.this, "THIS USERNAME ALREADY USED" + " ASSIGN NEW USER NAME", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(username.this, "USERNAME SUCCESSFULLY ACCEPTED", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        t1 = findViewById(R.id.etName);
                        String t2 = etName.getText().toString();
                        String t3 = etPassword.getText().toString();
                        String t4 = etEmail.getText().toString();
                        String roll = t1.getText().toString().trim();
                        String FEED = num;
                        String user_name = t2;
                        String password = t3;
                        String email = t4;
                        String C_FEED = num;
                        dataholder obj = new dataholder(FEED, user_name, password, C_FEED);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference node = database.getReference("users");
                        node.child(roll).setValue(obj);
                        t1.setText("");

                        //Toast.makeText(username.this, "UPDATED", Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(username.this, username2.class);
                        i1.putExtra("value", username);
                        i1.putExtra("num", password);
                        i1.putExtra("email", email);
                        startActivity(i1);

                    }
                } else {
                    Toast.makeText(username.this, "FAILED TO READ DATA", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}





