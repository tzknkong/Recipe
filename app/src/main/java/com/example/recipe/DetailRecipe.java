package com.example.recipe;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class DetailRecipe extends AppCompatActivity {

    String imageUrl="";
    TextView RecipeName,ingredients,instruction,category,time;
    ImageView foodImage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_content);
        RecipeName = (TextView) findViewById(R.id.toolbar_image_title);
        category = (TextView) findViewById(R.id.toolbar_image_category);
        ingredients = (TextView)findViewById(R.id.ingredients_detail);
        instruction = (TextView)findViewById(R.id.recipe_detail_instruction_text);
        foodImage = (ImageView)findViewById(R.id.toolbar_imageview);
        time = (TextView)findViewById(R.id.instruction_time);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){

            category.setText(mBundle.getString("category"));
            time.setText(mBundle.getString("time"));
            imageUrl = mBundle.getString("img_url");
            RecipeName.setText(mBundle.getString("recipe_Name"));
            ingredients.setText(mBundle.getString("ingredients"));
            instruction.setText(mBundle.getString("instruction"));


            Glide.with(this)
                    .load(mBundle.getString("img_url"))
                    .into(foodImage);


        }

    }
}
