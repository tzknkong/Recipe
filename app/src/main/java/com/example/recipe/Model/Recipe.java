package com.example.recipe.Model;

import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("img_url")
    private  String recipe_Name,category,ingredients,instruction,img_url;
    private Long time;



    public Recipe() {

    }



    public void setrecipe_Name(String recipe_Name) {
        this.recipe_Name = recipe_Name;
    }
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setIngredients(String ingredients){this.ingredients = ingredients;}
    public void setInstruction(String instruction){this.instruction = instruction;}


    public String getCategory() { return category; }
    public String getIngredients() {
        return ingredients;
    }
    public String getInstruction() {
        return instruction;
    }
    public Long getTime() {
        return time;
    }
    public String getImg_url() {
        return img_url;
    }
    public String getrecipe_Name() {
        return recipe_Name;
    }

}