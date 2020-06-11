package com.example.taskmassages.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.taskmassages.MainActivity;
import com.example.taskmassages.R;
import com.example.taskmassages.settings.MySharePreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FragmentStart extends Fragment {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button buttonLogin;
    private TextView registerTextView;
    private FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_start, container, false);

        findView(root);

        buttonLogin.setOnClickListener(btn_click_login);
        registerTextView.setOnClickListener(btn_click_register);
        mAuth = FirebaseAuth.getInstance();

        return root;
    }
    public void loginFunc(View view) {
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    }
                    // ...

                });


    }



    View.OnClickListener btn_click_register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoadSecFragment();
        }


    };
    View.OnClickListener btn_click_login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginFunc(v);
        }


    };


    View.OnClickListener btn_click = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            Intent intent;
            String choosenMode = view.getResources().getResourceEntryName(view.getId());
            switch(choosenMode) {
                case "button_firebase":
                    LoadSecFragment();
                    break;

                case "button_mongodb":
                    break;

                case "button_aws":
                    break;
            }
        }
    };


    private void LoadSecFragment() {

        FragmentSignUp second = new FragmentSignUp();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);


        transaction.replace(R.id.Main_FrameLayout, second, "1").addToBackStack(null).commit();

    }

    private void findView(View root){
        emailEditText = root.findViewById(R.id.email_start_editText);
        passwordEditText = root.findViewById(R.id.pass_main_editText);
        buttonLogin = root.findViewById(R.id.login_btn);
        registerTextView = root.findViewById(R.id.joinActivity_main_activity_text_view);
    }
}
