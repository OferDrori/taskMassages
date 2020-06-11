package com.example.taskmassages.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.taskmassages.MainActivity;
import com.example.taskmassages.R;
import com.example.taskmassages.classes.User;
import com.example.taskmassages.settings.MySharePreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.taskmassages.settings.Keys.KEY_NAME;
import static com.example.taskmassages.settings.Keys.KEY_PHONE;

public class FragmentSignUp extends Fragment {
    private Button SignUp_BTN_signup;
    private TextView SignUp_TXT_Email, SignUp_TXT_FullName, SignUp_TXT_phoneNumber,SignUp_TXT_pasword;
    private MySharePreferences msp;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        findViews(root);
        msp=new MySharePreferences(getContext());

        SignUp_BTN_signup.setOnClickListener(signUpListener);


        return root;



    }
    public void register(View view) {
        mAuth.createUserWithEmailAndPassword(SignUp_TXT_Email.getText().toString(), SignUp_TXT_pasword.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            User user = new User(SignUp_TXT_FullName.getText().toString(), SignUp_TXT_phoneNumber.getText().toString(), SignUp_TXT_Email.getText().toString());
                            FirebaseUser userUID = FirebaseAuth.getInstance().getCurrentUser();
                            msp.putString(KEY_PHONE,user.getPhoneNumber());

                            myRef.child("Users").child((userUID.getUid())).setValue(user);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    }
                });
    }


    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mobileNumber = SignUp_TXT_phoneNumber.getText().toString().trim();
            String fullName = SignUp_TXT_FullName.getText().toString().trim();
            String email = SignUp_TXT_Email.getText().toString().trim();

            if (fullName.isEmpty()) {
                SignUp_TXT_FullName.setError("Enter fullName name");
                SignUp_TXT_FullName.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                SignUp_TXT_Email.setError("Enter last name");
                SignUp_TXT_Email.requestFocus();
                return;
            }
            if (mobileNumber.isEmpty() || mobileNumber.length() < 10) {
                SignUp_TXT_phoneNumber.setError("Enter a valid mobile");
                SignUp_TXT_phoneNumber.requestFocus();
                return;
            }
        msp=new MySharePreferences(getContext());
            msp.putString(KEY_NAME,fullName);
            msp.putString(KEY_PHONE,mobileNumber);
            register(v);
        }
    };






    /**
     *
     */
    private void findViews(View root) {
        SignUp_BTN_signup = root.findViewById(R.id.SignUp_BTN_signup);
        SignUp_TXT_FullName = root.findViewById(R.id.SignUp_TXT_fullName);
        SignUp_TXT_Email = root.findViewById(R.id.SignUp_TXT_Email);
        SignUp_TXT_phoneNumber = root.findViewById(R.id.SignUp_TXT_phoneNumber);
        SignUp_TXT_pasword=root.findViewById(R.id.SignUp_TXT_password);
    }

    private void LoadNextFragment() {



    }


}
