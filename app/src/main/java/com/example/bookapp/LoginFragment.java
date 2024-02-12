package com.example.bookapp;

import android.content.Intent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;

import androidx.lifecycle.ViewTreeViewModelKt;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LoginFragment extends Fragment {

// TODO: Rename parameter arguments, choose names that match

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

// TODO: Rename and change types of parameters

    private String mParam1;

    private String mParam2;

    Button button;

    EditText username,password;

    TextView textViewsign;
    DBHelper dbHelper;

    TabLayout tabLayout;

    public LoginFragment() {

// Required empty public constructor

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginFragment.
     */

// TODO: Rename and change types and number of parameters

    public static LoginFragment newInstance(String param1, String param2) {

        LoginFragment fragment = new LoginFragment();

        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);

        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);

        return fragment;

    }
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);

            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_login,container,false);

// Inflate the layout for this fragment

        button=view.findViewById(R.id.loginbutton);

        username=view.findViewById(R.id.email);

        password=view.findViewById(R.id.password);

        textViewsign= view.findViewById(R.id.newuser);

        textViewsign.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                SignupFragment signupFragment=new SignupFragment();

                FragmentManager fragmentManager=getParentFragmentManager();

                FragmentTransaction transaction=fragmentManager.beginTransaction();

                transaction.replace(R.id.viewpagerlogin,signupFragment);

                transaction.addToBackStack(null);

                transaction.commit();

                TabLayout tabLayout = getActivity().findViewById(R.id.tablayoutlogin); // Replace with your actual TabLayout ID

                TabLayout.Tab signupTab = tabLayout.getTabAt(1); // Replace 1 with the index of your signup tab

                if (signupTab != null) {

                    signupTab.select();

                }

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                String user= String.valueOf(username.getText());

                String pass= String.valueOf(password.getText());
                dbHelper=new DBHelper(getContext());

                boolean valid=dbHelper.checkUser(user,pass);

                if(valid){
                Intent intent=new Intent(getActivity(),MainActivity.class);


                intent.putExtra("username",user);

                intent.putExtra("password",pass);

                startActivity(intent);

                getActivity().finish();

                        }else {
                              Toast.makeText(getContext(), "INVALID USER NAME OR PASSWORD", Toast.LENGTH_SHORT).show();
                              }

            }


        });

        return view;

    }
}