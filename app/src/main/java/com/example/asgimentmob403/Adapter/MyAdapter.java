
package com.example.asgimentmob403.Adapter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.asgimentmob403.MainActivity;
        import com.example.asgimentmob403.R;
        import com.example.asgimentmob403.database.User;
        import com.google.firebase.database.FirebaseDatabase;

        import org.jetbrains.annotations.NotNull;

        import java.text.DecimalFormat;
        import java.util.ArrayList;
        import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;

    ArrayList<User> list;


    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_profile,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.username.setText(user.getUsername());
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.phone.setText(user.getPhone());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
//        name, username, email,phone,password,role
        TextView username, name, email,phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.tvfirstName);
            name = itemView.findViewById(R.id.tvlastName);
            email = itemView.findViewById(R.id.tvage);
            phone = itemView.findViewById(R.id.tvphone);
        }
    }
}
