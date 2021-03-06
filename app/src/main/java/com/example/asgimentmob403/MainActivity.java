package com.example.asgimentmob403;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.asgimentmob403.Fragment.CartFragment;
import com.example.asgimentmob403.Fragment.DetailProductFragment;
import com.example.asgimentmob403.Fragment.HistoryFragment;
import com.example.asgimentmob403.Fragment.OrderInfoFragment;
import com.example.asgimentmob403.Fragment.ProductFragment;
import com.example.asgimentmob403.Fragment.UserFragment;
import com.example.asgimentmob403.Login.LoginActivity;
import com.example.asgimentmob403.database.Order;
import com.example.asgimentmob403.database.Product;
import com.example.asgimentmob403.database.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private FragmentTransaction fragmentTransaction;
    // region Variable
    FirebaseDatabase data;
    private List<Product> listCartProduct;
    private List<User> listUser;
    DatabaseReference reference = data.getInstance().getReference("User");
    Query checkrole = reference.orderByChild("role").equalTo("User");
    // ?????m s??? s???n ph???m trong gi??? h??ng
    private int countProduct;

    private AHBottomNavigation ahBotNavHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2FBCC5")));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ab.setTitle("MOSI");
        ab.setSubtitle("Wellcome");
        ab.hide();
        Paper.init(this);
        initItem();
        setDataBotNavHome();

    }
    private void initItem() {
        ahBotNavHome = findViewById(R.id.ahbotnav_home);
        if(listCartProduct == null){
            listCartProduct = new ArrayList<>();
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new ProductFragment());

        fragmentTransaction.commit();
    }
    private void setDataBotNavHome() {

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_product, R.drawable.ic_baseline_home_24, R.color.teal_200);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_cart, R.drawable.ic_baseline_add_shopping_cart_24, R.color.gray);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_history, R.drawable.ic_baseline_history_24, R.color.red_300);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_profile, R.drawable.ic_profice, R.color.red_300);

        // Add items
        ahBotNavHome.addItem(item1);
        ahBotNavHome.addItem(item2);
        ahBotNavHome.addItem(item3);
        ahBotNavHome.addItem(item4);

        ahBotNavHome.setColored(false);

        // Set m??u nav
        ahBotNavHome.setDefaultBackgroundColor(getResources().getColor(R.color.white));

        // Khi click v??o c??c icon tr??n nav
        ahBotNavHome.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position){
                    case 0:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new ProductFragment());
                        fragmentTransaction.commit();
                        break;

                    case 1:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new CartFragment(listCartProduct));
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new HistoryFragment());
                        fragmentTransaction.commit();
                        break;
                    case 3:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.contet_frame, new UserFragment());
                        fragmentTransaction.commit();
                        break;
                }

                return true;
            }
        });
    }
    public static class Data{
        public static User user;
    }
    // M??? Fragment DetailProduct
    public void toDetailProductFragment(Product product){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new DetailProductFragment(product,listCartProduct));
        fragmentTransaction.commit();
    }
    // M??? Fragment OrderInfo
    public void toOrderInfoFragment(Order orderInfo){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contet_frame, new OrderInfoFragment(orderInfo));
        fragmentTransaction.addToBackStack(OrderInfoFragment.TAG);
        fragmentTransaction.commit();
    }

    // Th??m s???n ph???m ???? ch???n v??o gi??? h??ng
    public void addToListCartProdct(Product product){
        listCartProduct.add(product);
    }

    // L???y ra c??c s???n ph???m ???? th??m v??o gi??? h??ng
    public List<Product> getListCartProduct() {
        return listCartProduct;
    }

    // L???y ra s??? l?????ng c??c s???n ph???m ???? th??m v??o gi??? h??ng
    public int getCountProduct() {
        return countProduct;
    }

    // Set l???i s??? l?????ng c???a s???n ph???m khi mua nhi???u
    public void setCountForProduct(int possion, int countProduct){
        listCartProduct.get(possion).setNumProduct(countProduct);
    }
    // Set s??? l?????ng c??c s???n ph???m trong gi??? h??ng
    public void setCountProductInCart(int count){
        countProduct = count;
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(count))
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.teal_200))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .build();
        ahBotNavHome.setNotification(notification, 1);
    }

}
