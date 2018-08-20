package com.mcbridebrandon.bakingapp.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcbridebrandon.bakingapp.R;
import com.mcbridebrandon.bakingapp.adapters.IngredientAdapter;
import com.mcbridebrandon.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientListFragment extends Fragment {

    private View rootView;
    private IngredientAdapter mIngredientAdapter;
    private RecyclerView mIngredientRecyclerView;
    private List<Ingredient> ingredientList;

    //mandatory constructor for instantiating the fragment
    public IngredientListFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //inflate the ingredient list layout
        View rootView = inflater.inflate(R.layout.fragment_ingredient_list,container,false);

        //get a reference to the recyclerview
        mIngredientRecyclerView = rootView.findViewById(R.id.rv_ingredient_list);

        //set the layout manager
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //setup ingredient adapter
        mIngredientAdapter = new IngredientAdapter(getContext(), ingredientList);

        //set the adapter
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);


        return rootView;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientList = ingredientsList;
    }
}
