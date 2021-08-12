package com.example.asgimentmob403.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asgimentmob403.AdminActivity;
import com.example.asgimentmob403.MainActivity;
import com.example.asgimentmob403.R;
import com.example.asgimentmob403.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase data;
    FirebaseAuth auth;
    FirebaseFirestore storage;
    Button btnlogin,btnregsiter;
    EditText user,pass;
    TextView tvregis,forps,tx1,tx2;
    ImageView imageView;
    Animation top ,bot,mid;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hook();
        Animantion();
        Action();
        Login();
        checker();
    }
    public void Hook() {
        ActionBar  actionBar =getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = findViewById(R.id.logo);
        btnlogin = findViewById(R.id.btnlogin);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        tvregis = findViewById(R.id.tvregis);
        forps = findViewById(R.id.forps);
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        checkBox = findViewById(R.id.checkBox);
        top = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        mid = AnimationUtils.loadAnimation(this,R.anim.midde_anim);
        bot = AnimationUtils.loadAnimation(this,R.anim.botom_anim);
    }
    public void Animantion(){
        imageView.setAnimation(top);
        user.setAnimation(bot);
        pass.setAnimation(bot);
        tvregis.setAnimation(bot);
        forps.setAnimation(bot);
        tx1.setAnimation(mid);
        tx2.setAnimation(mid);
        btnlogin.setAnimation(bot);
    }
    public void Action(){
        tvregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegsiterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void Login(){
        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                     LoginUser();


            }
        });
    }
    public void LoginUser() {
        String users = user.getText().toString();
        String passworld = pass.getText().toString();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseFirestore.getInstance();
       auth.signInWithEmailAndPassword(users,passworld).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                checkAdmin(authResult.getUser().getUid());
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void checkAdmin(String uid){
        DocumentReference df = storage.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSucces"+documentSnapshot.getData());
                if (documentSnapshot.getString("isAdmin")!=null){
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    finish();
                }
                if (documentSnapshot.getString("isUser")!=null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });
    }
        //Rember User
        public void checker(){
            SharedPreferences preferences = getSharedPreferences("checkBook", MODE_PRIVATE);
            String checkbox = preferences.getString("remember", "false");
            if (checkbox.equals("true")) {
                Toast.makeText(getApplicationContext(),"Checked Succes",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else if (checkbox.equals("false")){
                Toast.makeText(getApplicationContext(),"Checked Failed",Toast.LENGTH_LONG).show();
            }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkBook", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Checked",Toast.LENGTH_LONG).show();
                }else {
                    SharedPreferences preferences = getSharedPreferences("checkBook", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"UnChecked",Toast.LENGTH_LONG).show();
                }
            }
        });
        }



    }
