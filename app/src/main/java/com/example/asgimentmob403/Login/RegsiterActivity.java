package com.example.asgimentmob403.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asgimentmob403.R;
import com.example.asgimentmob403.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RegsiterActivity extends AppCompatActivity {
    FirebaseDatabase data;
    DatabaseReference reference;
    FirebaseFirestore storage;
     FirebaseAuth auth;
    Button btnregsiter;
    EditText names, pass, pass2, user2, emails, phones;
    TextView tx1, tx2;
    ImageView imageView;
    Animation top, bot, mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter);
        Hook();
        Animantion();
        Action();
    }

    public void Hook() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        emails = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass1);
        pass2 = (EditText) findViewById(R.id.pass2);
        btnregsiter = (Button) findViewById(R.id.btnregsiter);
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        imageView = findViewById(R.id.logo);
        top = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        mid = AnimationUtils.loadAnimation(this, R.anim.midde_anim);
        bot = AnimationUtils.loadAnimation(this, R.anim.botom_anim);

    }

    public void Animantion() {
        imageView.setAnimation(top);
        emails.setAnimation(bot);
        pass.setAnimation(bot);
        pass2.setAnimation(bot);
        tx1.setAnimation(mid);
        tx2.setAnimation(mid);
        btnregsiter.setAnimation(bot);
    }

    public void Action() {
        btnregsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regsiter();
            }
        });
    }

    public void Regsiter() {
        if (!validateEmail()|!validatePassword()){
            return;
        }
        Validate();
        data = FirebaseDatabase.getInstance();
        //Get all values
        String email = emails.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2s = pass2.getText().toString();
        auth =FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isComplete()){
                    //firebase instace
                    storage =FirebaseFirestore.getInstance();
                    data = FirebaseDatabase.getInstance();
                    //path to store User
                    reference = data.getReference("User");
                    //put hasmap  in database
                    FirebaseUser user = auth.getCurrentUser();
                    DocumentReference document = storage.collection("Users").document(user.getUid());
                    String e = user.getEmail();
                    String id = user.getUid();
                    //using hashmap
                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("email",e);
                    hashMap.put("uid",id);
                    hashMap.put("fullname","");
                    hashMap.put("phone","");
                    //user or admin
                    hashMap.put("isUser","1");
                    document.set(hashMap);
                    reference.child(id).setValue(hashMap);

                    Toast.makeText(RegsiterActivity.this, "SignIn Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegsiterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    };

    public void Validate() {


        String pass1 = pass.getText().toString();
        String pass2s = pass2.getText().toString();
        if (!pass1.equals(pass2s)) {
            pass2.setError("Passworld do not match");
            return;
        }
    }
//    public boolean validateName() {
//        String name = names.getText().toString();
//        if (name.isEmpty()) {
//            names.setError("Full Name cannot Empty");
//            return false;
//        } else {
//            names.setError("Full Name cannot Empty");
//            return true;
//        }
//    }
//    public boolean validateUserName() {
//
//        String username = user2.getText().toString();
//        String nowhitepasce = "(?=\\s+$)";
//        if (username.isEmpty()) {
//            user2.setError("User Name cannot Empty");
//            return false;
//        } else if (username.length() >= 15) {
//            user2.setError("User Name too long");
//        } else if (!username.matches(nowhitepasce)) {
//            user2.setError("White Space are not allowed");
//        }
//
//
//        return true;
//    }
    public boolean validateEmail() {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String email = emails.getText().toString();
        if (email.isEmpty()) {
            emails.setError("Email cannot Empty");
            return false;
        }else if(!email.matches(regex)){
            emails.setError("Invalid email");
            return false;
        }else {
            emails.setError(null);
            return true;
        }
    }
//    public boolean validatePhone() {
//        String phone = phones.getText().toString();
//        if (phone.isEmpty()) {
//            phones.setError("Phone cannot Empty");
//            return false;
//        }
//        phones.setError(null);
//        return true;
//    }
    public boolean validatePassword() {
        String pass1 = pass.getText().toString();
        if (pass1.isEmpty()) {
            pass.setError("Passworld cannot Empty");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }

}