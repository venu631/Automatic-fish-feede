package com.example.automaticfishfeeder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.automaticfishfeeder.databinding.ActivityFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class first extends AppCompatActivity {
    private android.widget.TextView VIEW;
    private android.widget.Button feed;

    ActivityFirstBinding binding;
    DatabaseReference databaseReference;
    String num = String.valueOf(1);
    private android.widget.ImageButton home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        feed = findViewById(R.id.feed);
        VIEW = findViewById(R.id.textView3);
        String num5 = getIntent().getStringExtra("value");
        VIEW.setText(num5);
        home=findViewById(R.id.imageButton4);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(first.this,loginactivity.class);
                startActivity(intent);
            }
        });
        binding.feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.textView3.getText().toString();
                String feed = String.valueOf(1);
                if (!username.isEmpty()) {
                    updateData(username, feed);
                } else {
                    Toast.makeText(first.this, "error in feeding", Toast.LENGTH_SHORT).show();
                }


            }
        });
        binding.cfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.textView3.getText().toString();
                String feed = String.valueOf(1);
                if (!username.isEmpty()) {
                    updateData1(username, feed);
                } else {
                    Toast.makeText(first.this, "error in cancelfeeding", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    private void updateData1(String userName, String feed) {
        HashMap User = new HashMap();
        User.put("c_FEED", feed);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userName).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    // binding.userName.setText("");
                    //binding.editTextTextPersonName2.setText("");
                    //binding.editTextTextPersonName3.setText("");
                    //binding.editTextTextPersonName4.setText("");
                    Toast.makeText(first.this, "FEED CANCELLED", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(first.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void updateData(String userName, String FEED) {
        HashMap User = new HashMap();
        User.put("feed", FEED);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userName).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    // binding.userName.setText("");
                    //binding.editTextTextPersonName2.setText("");
                    //binding.editTextTextPersonName3.setText("");
                    //binding.editTextTextPersonName4.setText("");
                    Toast.makeText(first.this, "FEEDED", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(first.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}