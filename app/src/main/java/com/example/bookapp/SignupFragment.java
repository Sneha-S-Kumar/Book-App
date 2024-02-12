package com.example.bookapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    Button signup;
    private String mParam2;
    DBHelper dbHelper;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

        // Inflate the layout for this
      View view=inflater.inflate(R.layout.fragment_signup,container,false);

      EditText name,username,email,password,confirmpassword,mobileno;
      name=view.findViewById(R.id.Name);
      username=view.findViewById(R.id.username);
      email=view.findViewById(R.id.email2);
      password=view.findViewById(R.id.password2);
      confirmpassword=view.findViewById(R.id.confirmPassword);
      mobileno=view.findViewById(R.id.mobileno);
      dbHelper=new DBHelper(getContext());
      signup=view.findViewById(R.id.signupbutton);
      signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String nameofP=name.getText().toString();
              String usernameofP=username.getText().toString();
              String emailofP=email.getText().toString();
              String passwordofP=password.getText().toString();
              String confirmpass=confirmpassword.getText().toString();
              String mobileofP=mobileno.getText().toString();

              if(!nameofP.isEmpty() && !usernameofP.isEmpty() && !emailofP.isEmpty() && !passwordofP.isEmpty() && !confirmpass.isEmpty() && !mobileofP.isEmpty())
              {
                  boolean a=dbHelper.userdataIn(nameofP,usernameofP,emailofP,passwordofP,confirmpass,mobileofP);
                  Toast.makeText(getContext(), " "+a, Toast.LENGTH_SHORT).show();
//                  Log.e("added", " ", );
//                  dialog.dismiss();


              }
              else {
                  Toast.makeText(getContext(), "Please enter all the data", Toast.LENGTH_SHORT).show();
                  return;

              }

              LoginFragment loginFragment=new LoginFragment();

              FragmentManager fragmentManager=getParentFragmentManager();

              FragmentTransaction transaction=fragmentManager.beginTransaction();

              transaction.replace(R.id.viewpagerlogin,loginFragment);

              transaction.addToBackStack(null);

              transaction.commit();

              TabLayout tabLayout = getActivity().findViewById(R.id.tablayoutlogin); // Replace with your actual TabLayout ID

              TabLayout.Tab loginTab = tabLayout.getTabAt(0); // Replace 1 with the index of your signup tab

              if (loginTab != null) {

                  loginTab.select();

              }

          }


      });
      return view;
}
}