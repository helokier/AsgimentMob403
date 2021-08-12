package com.example.asgimentmob403.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asgimentmob403.DeleteActivity;
import com.example.asgimentmob403.Login.ChangePassActivity;
import com.example.asgimentmob403.Login.LoginActivity;
import com.example.asgimentmob403.R;
import com.example.asgimentmob403.database.Product;
import com.example.asgimentmob403.database.ProductAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;


public class AdminFragment extends Fragment {
    private TextView brands, desmon, name,price,url;
    private SharedPreferences pref;
    Button create,logout,back,delete;
    private View mView;
    FirebaseDatabase data;
    FirebaseAuth auth;
    FirebaseUser user;
    FloatingActionButton fab;
    ProgressDialog pd;
    DatabaseReference reference;

    public AdminFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_admin, container, false);
        initItem();
        // Inflate the layout for this fragment
        return mView;
    }
    private void createItem() {
        data = FirebaseDatabase.getInstance();
        //Get all values
        String brandl = brands.getText().toString();
        String desmonl = desmon.getText().toString();
        String namel = name.getText().toString();
        int pricel = Integer.parseInt(price.getText().toString());
        String urls = url.getText().toString();
        auth =FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance();
        reference = data.getReference("DbProduct");
        ProductAdmin admin = new ProductAdmin(brandl,desmonl,namel,pricel,urls);
          reference.child(brandl).setValue(admin);




    }
    private void initItem(){
        brands = mView.findViewById(R.id.brand);
        desmon = mView.findViewById(R.id.desmon);
        name =  mView.findViewById(R.id.name);
        price =  mView.findViewById(R.id.price);
        url =  mView.findViewById(R.id.url);
        logout =    mView.findViewById(R.id.logout);
        create =    mView.findViewById(R.id.create);
      delete = mView.findViewById(R.id.delete);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItem();
                Toast.makeText(getActivity(), "Add Product Succes", Toast.LENGTH_LONG).show();
                brands.setText("");
                desmon.setText("");
               name.setText("");
                price.setText("");
                url.setText("");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteActivity.class);
                startActivity(i);
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