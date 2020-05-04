package com.example.recipe;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class DetailRecipe extends AppCompatActivity {

    String imageUrl="";
    TextView RecipeName,ingredients,instruction,category,time;
    ImageView foodImage,speechimage;
    TextToSpeech i;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_content);
        RecipeName = (TextView) findViewById(R.id.toolbar_image_title);
        category = (TextView) findViewById(R.id.toolbar_image_category);
        ingredients = (TextView)findViewById(R.id.ingredients_detail);
        instruction = (TextView)findViewById(R.id.recipe_detail_instruction_text);
        foodImage = (ImageView)findViewById(R.id.toolbar_imageview);
        time = (TextView)findViewById(R.id.instruction_time);
        speechimage = findViewById(R.id.speech_image);


        speechimage.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    i = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = i.setLanguage(Locale.ENGLISH);
                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Toast.makeText(DetailRecipe.this,"Language Not Support",Toast.LENGTH_SHORT );
                                } else {
                                    String content = "";
                                    if (!TextUtils.isEmpty(instruction.getText().toString())) {
                                        content = instruction.getText().toString();
                                    }
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        i.speak(content, TextToSpeech.QUEUE_FLUSH, null, null);
                                    } else {
                                        i.speak(content, TextToSpeech.QUEUE_FLUSH, null);
                                    }

                                }
                            }
                        }
                    });



                }
            });


        Bundle mBundle = getIntent().getExtras();

        String t = String.valueOf(mBundle.getInt("time"));
        if(mBundle!=null){

            category.setText(mBundle.getString("category"));
            time.setText(t + "Min");
            RecipeName.setText(mBundle.getString("recipe_Name"));
            ingredients.setText(mBundle.getString("ingredients"));
            instruction.setText(mBundle.getString("instruction"));


            Glide.with(this)
                    .load(imageUrl)
                    .into(foodImage);


        }

    }
}
