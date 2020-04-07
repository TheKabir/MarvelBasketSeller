package com.example.marvelbasket.bean;

import java.io.Serializable;

public class Category implements Serializable {
    int categoryId;
    String categoryName;

    public Category(){
        //Do nothing
    }
    public Category(int categoryId, String categoryName) {
        super();
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getcategoryId() {
        return categoryId;
    }
    public void setcategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getcategoryName() {
        return categoryName;
    }
    public void setcategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
