package com.example.recipe;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DetailRecipe extends AppCompatActivity {

    String imageUrl="";
    TextView RecipeName,ingredients,instruction,category,time;
    ImageView foodImage,speechimage;
    TextToSpeech i;
    Button btnfav;

    private FirebaseAuth fAuth;
    FirebaseDatabase db;
    DatabaseReference refer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_content);
        RecipeName = findViewById(R.id.toolbar_image_title);
        category =  findViewById(R.id.toolbar_image_category);
        ingredients = findViewById(R.id.ingredients_detail);
        instruction = findViewById(R.id.recipe_detail_instruction_text);
        foodImage = findViewById(R.id.toolbar_imageview);
        time = findViewById(R.id.instruction_time);
        speechimage = findViewById(R.id.speech_image);





        Bundle mBundle = getIntent().getExtras();



        speechimage.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    speech();

                }
            });




        String t = String.valueOf(mBundle.getInt("time"));
        if(mBundle!=null){

            category.setText(mBundle.getString("category"));
            time.setText(t + "Min");
            RecipeName.setText(mBundle.getString("recipe_Name"));
            ingredients.setText(mBundle.getString("ingredients"));
            instruction.setText(mBundle.getString("instruction"));
            imageUrl = mBundle.getString("img_url");

            Glide.with(this)
                    .load(imageUrl)
                    .into(foodImage);


        }

    }


    private void speech(){
        i = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = i.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(DetailRecipe.this,"Language Not Support",Toast.LENGTH_SHORT );
                    } else {
                        String content ="";
                        if (!TextUtils.isEmpty(instruction.getText().toString())) {
                            content = instruction.getText().toString();
                            i.setSpeechRate(0.5f);
                            i.speak(content, TextToSpeech.QUEUE_FLUSH, null, null);
                        }


                    }
                }
            }
        });
    }




    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
