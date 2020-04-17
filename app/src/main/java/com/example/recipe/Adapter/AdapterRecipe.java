package com.example.recipe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.recipe.DetailRecipe;
import com.example.recipe.FragmentRecipe;
import com.example.recipe.Model.Recipe;
import com.example.recipe.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterRecipe extends RecyclerView.Adapter<RecipeViewHolder>{

    private Context acontext;
    private int lastPosition = -1;


    List<Recipe> recipeList = new ArrayList<>();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();



    public AdapterRecipe(Context context,  List<Recipe> arecipeList) {
        acontext = context;
        recipeList = arecipeList;
    }



    @NonNull
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_listitem, parent, false);


        return new RecipeViewHolder(itemView);

    }



    public void onBindViewHolder( final RecipeViewHolder recipeViewHolder, int i) {
        //String uriimage = recipeList.get(i).getImg_url().toString();
        Uri uri = Uri.parse(recipeList.get(i).getImg_url());

        Glide.with(acontext)
                .load(uri)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(recipeViewHolder.imageView);

        // foodViewHolder.imageView.setImageResource(myFoodList.get(i).getItemImage());
        recipeViewHolder.rname.setText(recipeList.get(i).getrecipe_Name());
        recipeViewHolder.imageView.setImageURI(uri);

        recipeViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(acontext, DetailRecipe.class);
                intent.putExtra("RecipeImage",recipeList.get(recipeViewHolder.getAdapterPosition()).getImg_url());
                intent.putExtra("RecipeName",recipeList.get(recipeViewHolder.getAdapterPosition()).getrecipe_Name());


                acontext.startActivity(intent);


            }
        });


        setAnimation(recipeViewHolder.itemView,i);

    }

    public void setAnimation(View viewToAnimate, int position ){

        if(position> lastPosition){

            ScaleAnimation animation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                    Animation.RELATIVE_TO_SELF,0.5f,
                    Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(500);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;


        }



    }

    @Override
    public int getItemCount() { return recipeList.size(); }


    public void filteredList(ArrayList<Recipe> filterList) {

        recipeList = filterList;
        notifyDataSetChanged();
    }
}

class RecipeViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView rname;

    public RecipeViewHolder( View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.recipe_list_item_image);
        rname = itemView.findViewById(R.id.recipe_list_item_name);


    }

}
