package com.example.recipe.Model;

public class Category {
    private String category_id,category_name,img_url;


    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setCategory_id(String caregory_id) {
        this.category_id = caregory_id;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getImg_url() {
        return img_url;
    }


}
