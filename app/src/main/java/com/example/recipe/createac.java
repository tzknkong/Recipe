package com.example.recipe;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class createac extends AppCompatActivity {

    EditText Emailcreate,Password,Password2,name;
    Button register;
    private FirebaseAuth fauth;
    FirebaseFirestore fst;
    ProgressBar pb;
    String userID;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createac);

          Emailcreate = findViewById(R.id.Email_create);
          Password = findViewById(R.id.Password_Create);
          Password2 = findViewById(R.id.Password_2_Create);
          register = findViewById(R.id.Register_btn);
          name = findViewById(R.id.name_Create);
          pb = findViewById(R.id.progressBar);

        fauth = FirebaseAuth.getInstance();

        if(fauth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

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
                    Toast.makeText(createac.this, "User Created", Toast.LENGTH_LONG ).show();
                    userID = fauth.getCurrentUser().getUid();
                    DocumentReference dr = fst.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Name", n);
                    user.put("Email",email);
                    dr.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "OnSuccess: " + userID);
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    Toast.makeText(createac.this, "Error! "+ task.getException().getMessage(), Toast.LENGTH_LONG ).show();
                    pb.setVisibility(View.GONE);
                }
            });

        });



    }
}
