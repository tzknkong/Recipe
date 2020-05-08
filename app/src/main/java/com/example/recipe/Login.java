package com.example.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipe.Adapter.AdapterLogin;
import com.example.recipe.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Login extends AppCompatActivity  {

    private static final String TAG = "EmailPassword";
    private EditText email;
    private EditText password;
    private FirebaseAuth fAuth;
    private Button btnlogin,btnCreateAccount;
    private DatabaseReference fDatabase;
    RecyclerView recyclerView;
    AdapterLogin adapterLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.Email_login);
        password = findViewById(R.id.password_login);
        btnlogin = findViewById(R.id.btn_Login);
        btnCreateAccount = findViewById(R.id.btn_CreateAccount);
        fDatabase = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();



        if (fAuth.getCurrentUser() != null) {
            onAuthSuccess(fAuth.getCurrentUser());
        }


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAc.class);
                startActivity(intent);
            }
        });


    }

    private void signIn() {
        String loginEmail = email.getText().toString();
        String loginpw = password.getText().toString();

        if(TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginpw)){
            Toast.makeText(Login.this, "Field is empty" , Toast.LENGTH_LONG).show();
        }else {
            fAuth.signInWithEmailAndPassword(loginEmail, loginpw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                                Toast.makeText(Login.this, "Login Successful",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Login.this, "Login Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        fDatabase.child("users").child(userId).setValue(user);
    }




    }

