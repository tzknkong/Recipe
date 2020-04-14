package com.example.recipe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipe.Model.RecipeList;
import com.example.recipe.R;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.ViewHolder>{

    private Context context;
    private List<RecipeList> arrayItemRecipesList;
    private RecipeList recipeList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;


        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.recipe_list_item_name);
            image = (ImageView) view.findViewById(R.id.recipe_list_item_image);


        }

    }

    public AdapterRecipe(Context context, List<RecipeList> arrayItemRecipesList) {
        this.context = context;
        this.arrayItemRecipesList = arrayItemRecipesList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, parent, false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
