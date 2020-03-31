package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private EditText email;
    private EditText password;
    private FirebaseAuth lAuth;
    private Button btnlogin,btnForgotPassword,btnCreateAccount;
    private FirebaseAuth.AuthStateListener lauthListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.Email_login);
        password = findViewById(R.id.password_login);
         btnlogin = findViewById(R.id.btn_Login);
         btnForgotPassword = findViewById(R.id.btn_ForgotPassword);
         btnCreateAccount = findViewById(R.id.btn_CreateAccount);
         lAuth = FirebaseAuth.getInstance();

        lauthListener = firebaseAuth -> {
            if(firebaseAuth.getCurrentUser() != null){
               startActivity(new Intent(login.this, MainActivity.class));
            }
        };


        btnlogin.setOnClickListener(view -> signIn());

    }

    private void signIn() {
        String loginEmail = email.getText().toString();
        String loginpw = password.getText().toString();

        if(TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginpw)){
                Toast.makeText(login.this, "Field is empty" , Toast.LENGTH_LONG).show();
        }else {

            lAuth.signInWithEmailAndPassword(loginEmail, loginpw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = lAuth.getCurrentUser();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }


}

