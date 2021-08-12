package com.example.asgimentmob403.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asgimentmob403.Login.ChangePassActivity;
import com.example.asgimentmob403.Login.LoginActivity;
import com.example.asgimentmob403.MainActivity;
import com.example.asgimentmob403.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class UserFragment extends Fragment {
    private TextView tv_name, tv_email, tv_message,username,name2,phone;
    private SharedPreferences pref;
    Button btnupdate,logout,back,reset;
    private View mView;
    FirebaseDatabase data;
    FirebaseAuth auth;
    FirebaseUser user;
    FloatingActionButton fab;
    ProgressDialog pd;
    DatabaseReference reference;

    public UserFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);
        // init firebase
        auth  = FirebaseAuth.getInstance();
        user  = auth.getCurrentUser();
        DatabaseReference reference = data.getInstance().getReference("User");
        //init view
        pd = new ProgressDialog(getActivity());
        initItem();
        if(user != null){
            Query checkuser = reference.orderByChild("uid").equalTo(user.getUid());
            checkuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    //check id request data
                    for (DataSnapshot sd: snapshot.getChildren()){
                        String names = ""+sd.child("fullname").getValue();
                        String tvnames = ""+sd.child("fullname").getValue();
                        String emails = ""+sd.child("email").getValue();
                        String phones = ""+sd.child("phone").getValue();

                        //set data
                        username.setText(names);
                        tv_name.setText(tvnames);
                        tv_email.setText(emails);
                        phone.setText(phones);
                        try {

                        }catch (Exception e){

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
//fab bution
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ShowEditProfile();
//                }
//            });

        }


        // Inflate the layout for this fragment
        return mView;


    }
    public void ShowEditProfile(){
          String [] options ={"Edit email","Edit fullname","Edit phone"};
          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
          builder.setTitle("Chose Your Action");
          builder.setItems(options, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                 if (which == 0){
                      pd.setMessage("Updating Email Profile");
                      showemail("email");
                 }else if (which == 1){
                     pd.setMessage("Updating Fullname Profile");
                     showname("fullname");
                 }else if (which == 2){
                     pd.setMessage("Updating Phone Profile");
                     showphone("phone");
                 }
              }
          });
          //create show dialog
        builder.create().show();
    }
    private void showname(String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update "+key);
        LinearLayout ln = new LinearLayout(getActivity());
        ln.setOrientation(LinearLayout.VERTICAL);
        EditText edname = new EditText(getActivity());
        edname.setHint("Enter "+key);
        ln.addView(edname);
        builder.setView(ln);
        //add dialog bution
        builder.setPositiveButton("Update" ,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                     String value = edname.getText().toString().trim();
                     if (!TextUtils.isEmpty(value)){
                         pd.show();
                         HashMap<String,Object> resuit = new HashMap<>();
                         resuit.put(key,value);
                         data = FirebaseDatabase.getInstance();
                         reference = data.getReference("User");
                         reference.child(user.getUid()).updateChildren(resuit).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {
                                       pd.dismiss();
                                 Toast.makeText(getActivity()," Updated.... ",Toast.LENGTH_LONG).show();
                             }
                         }).addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull @NotNull Exception e) {
                                 pd.dismiss();
                                 Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                             }
                         });
                     }else {
                         Toast.makeText(getActivity()," Pleas Enter "+key,Toast.LENGTH_LONG).show();
                     }
            }
        });
        builder.setNegativeButton("Cancel" ,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    private void showemail(String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update"+key);
        LinearLayout ln = new LinearLayout(getActivity());
        ln.setOrientation(LinearLayout.VERTICAL);
        EditText edname = new EditText(getActivity());
        edname.setHint("Enter"+key);
        ln.addView(edname);
        builder.setView(ln);
        builder.setPositiveButton("Update" ,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value2 = edname.getText().toString().trim();
                if (!TextUtils.isEmpty(value2)){
                    pd.show();
                    HashMap<String,Object> resuit = new HashMap<>();
                    resuit.put(key,value2);
                    data = FirebaseDatabase.getInstance();
                    reference = data.getReference("User");
                    reference.child(user.getUid()).updateChildren(resuit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            pd.dismiss();
                            Toast.makeText(getActivity()," Updated.... ",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity()," Pleas Enter "+key,Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel" ,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    private void showphone(String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update"+key);
        LinearLayout ln = new LinearLayout(getActivity());
        ln.setOrientation(LinearLayout.VERTICAL);
        EditText edname = new EditText(getActivity());
        edname.setHint("Enter"+key);
        ln.addView(edname);
        builder.setView(ln);
        builder.setPositiveButton("Update" ,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value3 = edname.getText().toString().trim();
                if (!TextUtils.isEmpty(value3)){
                    pd.show();
                    HashMap<String,Object> resuit = new HashMap<>();
                    resuit.put(key,value3);
                    data = FirebaseDatabase.getInstance();
                    reference = data.getReference("User");
                    reference.child(user.getUid()).updateChildren(resuit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            pd.dismiss();
                            Toast.makeText(getActivity()," Updated.... ",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity()," Pleas Enter "+key,Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel" ,new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    private void initItem(){
        username = mView.findViewById(R.id.name);
        tv_email = mView.findViewById(R.id.email);
        tv_name =  mView.findViewById(R.id.name2);
        phone =  mView.findViewById(R.id.phone);
        logout =    mView.findViewById(R.id.logout);
        btnupdate =    mView.findViewById(R.id.update);
        reset = mView.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangePassActivity.class);
                startActivity(i);
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowEditProfile();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("checkBook", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                Toast.makeText(getActivity().getApplicationContext(),"UnChecked",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

    }
}