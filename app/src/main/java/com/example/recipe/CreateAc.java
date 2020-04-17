package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recipe.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAc extends AppCompatActivity {
    EditText Emailcreate,Password,Password2,name;
    Button register;
    private FirebaseAuth fauth;
    ProgressBar pb;
    String userID;
    private DatabaseReference lDatabase;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createac);

        Emailcreate = findViewById(R.id.Email_create);
        Password = findViewById(R.id.Password_Create);
        Password2 = findViewById(R.id.Password_2_Create);
        register = findViewById(R.id.btn_register);
        pb = findViewById(R.id.progressBar);
        lDatabase = FirebaseDatabase.getInstance().getReference();

        fauth = FirebaseAuth.getInstance();


        register.setOnClickListener ((v)->{

            String email = Emailcreate.getText().toString().trim();
            String pw = Password.getText().toString().trim();
            String n = name.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Emailcreate.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(pw)){
                Password.setError("Password is required");
                return;
            }

            if (pw.length() < 6){
                Password.setError("Password must be > 6 characters");
                return;
            }

            pb.setVisibility(View.VISIBLE);

            fauth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener((task)->{
                if(task.isSuccessful()){
                    Toast.makeText(CreateAc.this, "User Created", Toast.LENGTH_LONG ).show();
                    userID = fauth.getCurrentUser().getUid();

                    onAuthSuccess(task.getResult().getUser());

                    startActivity(new Intent(getApplicationContext(),Login.class));
                }else{
                    Toast.makeText(CreateAc.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_LONG ).show();

                    pb.setVisibility(View.GONE);
                }
            });

        });

    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(CreateAc.this, Login.class));
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

        lDatabase.child("users").child(userId).setValue(user);
    }
}

