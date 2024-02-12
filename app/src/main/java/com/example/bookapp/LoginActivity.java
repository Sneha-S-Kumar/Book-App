package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadFragment(new LoginFragment());
        tabLayout=findViewById(R.id.tablayoutlogin);
        frameLayout=findViewById(R.id.viewpagerlogin);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab( tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               Fragment fragment=null;
               switch (tab.getPosition())
               {
                   case 0:
                       loadFragment(new LoginFragment());
                        break;
                   default:
                       loadFragment(new SignupFragment());
                       break;

               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
    }
    public void loadFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.viewpagerlogin,fragment).addToBackStack(null).commit();
    }
//    public void onBackPressed()
//    {
//        Log.e("loginActivity", "onBackPressed: " );
//        LoginFragment loginFragment=(LoginFragment) getSupportFragmentManager().findFragmentByTag("loginfragment");
//        if(loginFragment!=null && loginFragment.isVisible())
//        {
//            new AlertDialog.Builder(this).setTitle("exit app").setMessage("ARE YOU SURE YOU WANT TO EXIT").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    LoginActivity.super.onBackPressed();
//                }
//            }).setNegativeButton("No",null).show();
//        }
//        else {
//            super.onBackPressed();
//        }
//        finish();
//
//
//    }

}
