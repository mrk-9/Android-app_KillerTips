package com.onlinecreativetraining.killertipsdavinciresolve.DataModel;

/**
 * Created by Alex on 8/25/2016.
 */
public class Category {
    String id, name = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameWithIndex(int index, String name){
        String temp = "";
        if(index < 10){
            temp = "0" + index + "." + name;
        }else{
            temp = index + "." + name;
        }
        this.name = temp;
    }
}
