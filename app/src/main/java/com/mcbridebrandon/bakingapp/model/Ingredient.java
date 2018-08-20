package com.mcbridebrandon.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String quantity;
    private String measure;
    private String ingredient;

    public Ingredient() {
        // need for parser
    }

    public Ingredient(String quantity, String measure, String ingredient) {
        this.setQuantity(quantity);
        this.setMeasure(measure);
        this.setIngredient(ingredient);
    }
    //constructor used for parcel
    public Ingredient(Parcel parcel) {
        //read and set saved values from parcel
        quantity = parcel.readString();
        measure = parcel.readString();
        ingredient = parcel.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
