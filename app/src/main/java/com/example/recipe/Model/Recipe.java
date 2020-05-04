package com.example.recipe.Model;

import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("img_url")
    private  String recipe_Name,category,ingredients,instruction,img_uri,uid;
    private Integer time;



    public Recipe() {

    }



    public void setrecipe_Name(String recipe_Name) {
        this.recipe_Name = recipe_Name;
    }
    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }
    public void setTime(Integer time) {
        this.time = time;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setIngredients(String ingredients){this.ingredients = ingredients;}
    public void setInstruction(String instruction){this.instruction = instruction;}
    public void setUid(String uid) { this.uid = uid; }

    public String getCategory() { return category; }
    public String getIngredients() {
        return ingredients;
    }
    public String getInstruction() {
        return instruction;
    }
    public Integer getTime() {
        return time;
    }
    public String getImg_uri() {
        return img_uri;
    }
    public String getrecipe_Name() {
        return recipe_Name;
    }
    public String getUid() { return uid; }


}