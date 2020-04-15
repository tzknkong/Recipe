package com.example.recipe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.recipe.FragmentRecipe;
import com.example.recipe.Model.Recipe;
import com.example.recipe.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.ViewHolder>{

    private Context context;
    private Recipe recipeList;

    ArrayList<Recipe> datasource = new ArrayList<>();
    ViewHolder viewHolder;
    int posit;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView title,time,category;

        public ViewHolder(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.toolbar_imageview);
            title = (TextView) itemView.findViewById(R.id.toolbar_image_title);
            time = (TextView) itemView.findViewById(R.id.instruction_time);
            category= (TextView) itemView.findViewById(R.id.toolbar_image_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    Intent ii = new Intent(context, FragmentRecipe.class);
                    ii.putExtra("wid",datasource.get(position).getRecipe_id());
                    ii.putExtra("title",datasource.get(position).getTitle());
                    ii.putExtra("img_url",datasource.get(position).getImg_url());
                    ii.putExtra("time",datasource.get(position).getTime());
                    ii.putExtra("steps",datasource.get(position).getSteps());
                    ii.putExtra("category",datasource.get(position).getCategory_name());
                    context.startActivity(ii);
                }
            });
        }
    }





    public AdapterRecipe(Context context,  ArrayList<Recipe> datasource,int position) {
        this.context = context;
        this.datasource = datasource;
        posit = position;
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
        if (datasource == null)
            return 0;
        else
            return datasource.size();
    }



    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_ATOP);
        }
    }


}
