package com.mcbridebrandon.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private int servings;
    private String image;
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public Recipe() {

    }

    public Recipe(String name, int servings, String image, List<Step> steps, List<Ingredient> ingredients) {
        this.setName(name);
        this.setServings(servings);
        this.setImage(image);
        this.setSteps(steps);
        this.setIngredients(ingredients);
    }

    public Recipe(int id, String name, int servings, String image, List<Step> steps, List<Ingredient> ingredients) {
        this.setId(id);
        this.setName(name);
        this.setServings(servings);
        this.setImage(image);
        this.setSteps(steps);
        this.setIngredients(ingredients);
    }


    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    //constructor used for parcel
    public Recipe(Parcel parcel) {
        //read and set saved values from parcel
        id = parcel.readInt();
        name = parcel.readString();
        image = parcel.readString();
        servings = parcel.readInt();
        steps = parcel.createTypedArrayList(Step.CREATOR);
        ingredients = parcel.createTypedArrayList(Ingredient.CREATOR);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeTypedList(steps);
        dest.writeTypedList(ingredients);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
