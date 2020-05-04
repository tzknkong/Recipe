package com.example.recipe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipe.DetailRecipe;
import com.example.recipe.Model.Recipe;
import com.example.recipe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdapterRecipe extends RecyclerView.Adapter<RecipeViewHolder>{

    private Context acontext;
    private int lastPosition = -1;
    FirebaseAuth fauth;
    FirebaseDatabase fDb;
    DatabaseReference databaseReference;
    DatabaseReference dburi;
    String currentuser;


    List<Recipe> recipeList = new ArrayList<>();




    public AdapterRecipe(Context context,  List<Recipe> arecipeList) {
        acontext = context;
        recipeList = arecipeList;
    }



    @NonNull
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_listitem, parent, false);
        fauth = FirebaseAuth.getInstance();
        fDb = FirebaseDatabase.getInstance();
        databaseReference = fDb.getReference("Recipe");
        dburi = fDb.getReferenceFromUrl("https://recipe-efc7f.firebaseio.com/Recipe");
       // currentuser = fauth.getCurrentUser().getUid();



        return new RecipeViewHolder(itemView);

    }



    public void onBindViewHolder( final RecipeViewHolder recipeViewHolder, int i) {




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Recipe recipe = dataSnapshot.getValue(Recipe.class);

                Picasso.with(acontext)
                        .load(recipe.getImg_uri())
                        .placeholder(R.drawable.placeholder)
                        .centerCrop()
                        .into(recipeViewHolder.imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        // foodViewHolder.imageView.setImageResource(myFoodList.get(i).getItemImage());
        recipeViewHolder.rname.setText(recipeList.get(i).getrecipe_Name());

        recipeViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(acontext, DetailRecipe.class);
                intent.putExtra("img_url",recipeList.get(recipeViewHolder.getAdapterPosition()).getImg_uri());
                intent.putExtra("recipe_Name",recipeList.get(recipeViewHolder.getAdapterPosition()).getrecipe_Name());
                intent.putExtra("time",recipeList.get(recipeViewHolder.getAdapterPosition()).getTime());
                intent.putExtra("instruction",recipeList.get(recipeViewHolder.getAdapterPosition()).getInstruction());
                intent.putExtra("ingredients",recipeList.get(recipeViewHolder.getAdapterPosition()).getIngredients());
                intent.putExtra("category",recipeList.get(recipeViewHolder.getAdapterPosition()).getCategory());

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
