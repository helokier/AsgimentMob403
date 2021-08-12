package com.example.asgimentmob403.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asgimentmob403.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ChangePassActivity extends AppCompatActivity {
    EditText pass1,pass,pass3;
    Button changepass;
    FirebaseDatabase data;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog pd;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        pass = findViewById(R.id.pass2);
        changepass = findViewById(R.id.changepass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }
        });

    }
    public void changePass(){
        final FirebaseUser user =
                FirebaseAuth.getInstance().getCurrentUser();
        if (user != null&& !pass.getText().toString().trim().equals("")){
            user.updatePassword(pass.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void>
                                                       task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePassActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(ChangePassActivity.this, "Failed to update password!",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        } else if (pass.getText().toString().trim().equals(""))
        {
            pass.setError("Enter email");
        }
    }

        }

