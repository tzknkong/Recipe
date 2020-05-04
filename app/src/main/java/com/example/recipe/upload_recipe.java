package com.example.recipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipe.Model.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class upload_recipe extends AppCompatActivity {
    ImageView recipeImage;
    Uri uri;
    EditText recipename, recipecategory, recipetime, recipeingredients, recipeinstruction;
    String imageUrl;
    Recipe recipe;
    DatabaseReference reff;
    StorageReference rst;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addrecipe);
        recipe = new Recipe();
        reff = FirebaseDatabase.getInstance().getReference().child("Recipe");
        rst = FirebaseStorage.getInstance().getReference().child("RecipeImage");


        recipeImage = (ImageView) findViewById(R.id.iv_foodImage);
        recipename = (EditText) findViewById(R.id.recipe_name);
        recipecategory = (EditText) findViewById(R.id.recipe_category);
        recipetime = (EditText) findViewById(R.id.recipe_time);
        recipeingredients = (EditText) findViewById(R.id.recipe_ingredients_txt);
        recipeinstruction = (EditText) findViewById(R.id.recipe_instruction_txt);
        upload = findViewById(R.id.btn_uploadR);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer t = Integer.parseInt(recipetime.getText().toString().trim());

                if (recipename != null && recipecategory != null && recipetime != null && recipeingredients != null && recipeinstruction != null ) {
                    recipe.setrecipe_Name(recipename.getText().toString().trim());
                    recipe.setCategory(recipecategory.getText().toString().trim());
                    recipe.setIngredients(recipeingredients.getText().toString());
                    recipe.setInstruction(recipeinstruction.getText().toString());
                    recipe.setTime(t);


                    uploadfile();
                    finish();
                } else {
                    Toast.makeText(upload_recipe.this, "you have input all information", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void btnSelectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            recipeImage.setImageURI(uri);

        } else Toast.makeText(this, "Please pick a image", Toast.LENGTH_SHORT).show();

    }

    public void uploadfile() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("RecipeImage").child(uri.getLastPathSegment());

        if (uri != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());


            // adding listeners on upload
            // or failure of image
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    // Image uploaded successfully
                    // Dismiss dialog

                    recipe.setImg_uri(uri.toString());

                    String uploadid = reff.push().getKey();


                    reff.child(uploadid).setValue(recipe);
                    Toast.makeText(upload_recipe.this, "upload successful", Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(upload_recipe.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(
                        UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });


        }
    }
}







