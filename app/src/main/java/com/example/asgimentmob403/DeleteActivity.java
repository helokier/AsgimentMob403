package com.example.asgimentmob403;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asgimentmob403.Fragment.AdminFragment;
import com.example.asgimentmob403.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class DeleteActivity extends AppCompatActivity {
    private TextView brands;
    Button create, back,deletes;
    FirebaseDatabase data;

    ProgressDialog pd;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        brands = findViewById(R.id.brand);
        deletes = findViewById(R.id.deletes);
        deletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brandl = brands.getText().toString();
                if (!brandl.isEmpty()) {
                    Delete(brandl);
                }else {
                    Toast.makeText(getApplicationContext(),"Enter Your Brand",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void Delete(String brandl) {
        data = FirebaseDatabase.getInstance();
        reference = data.getReference("DbProduct");
        reference.child(brandl).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Delete Succesfuly",Toast.LENGTH_LONG).show();
                    brands.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"Delete Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}