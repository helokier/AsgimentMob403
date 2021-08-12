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

import com.example.asgimentmob403.Login.LoginActivity;
import com.example.asgimentmob403.R;
import com.example.asgimentmob403.database.ProductAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;


public class DeleteProductFragment extends Fragment {
    private TextView brands, desmon, name, price, url;
    private SharedPreferences pref;
    Button create, logout, back, delete;
    private View mView;
    FirebaseDatabase data;
    FirebaseAuth auth;
    FirebaseUser user;
    FloatingActionButton fab;
    ProgressDialog pd;
    DatabaseReference reference;


    public DeleteProductFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_delete_product, container, false);
        initItem();
        // Inflate the layout for this fragment
        return mView;
    }
    private void DeleteItem() {
        data = FirebaseDatabase.getInstance();
        //Get all values
        String brandl = brands.getText().toString();
        String namel = name.getText().toString();
        auth =FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance();
        reference = data.getReference("DbProduct");
        reference.child(brandl).child(namel).removeValue();




    }
    private void initItem() {
        brands = mView.findViewById(R.id.brand);
        name = mView.findViewById(R.id.name);
        logout = mView.findViewById(R.id.logout);
        delete = mView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteItem();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("checkBook", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                Toast.makeText(getActivity().getApplicationContext(), "UnChecked", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), AdminFragment.class);
                startActivity(i);
            }
        });


    }
}