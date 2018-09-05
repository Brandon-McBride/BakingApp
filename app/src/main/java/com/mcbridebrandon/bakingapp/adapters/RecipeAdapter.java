package com.mcbridebrandon.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcbridebrandon.bakingapp.R;
import com.mcbridebrandon.bakingapp.model.Recipe;

import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private static final String TAG = "MOVIE ADAPTER";
    private List<Recipe> mRecipeList;
    private final ItemClickListener mClickListener;


    public RecipeAdapter(Context context, List<Recipe> itemList, ItemClickListener clickListener) {
        this.mRecipeList = itemList;
        this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Log.d(TAG, "#" + position + mRecipeList.get(position).getName());

        if (mRecipeList != null) {
            holder.tvRecipeTitle.setText(mRecipeList.get(position).getName());

        }
    }

    @Override
    public int getItemCount() {
        if(mRecipeList != null) {
            return this.mRecipeList.size();
        }else{
            return 1;
        }
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView tvRecipeTitle;
        //final ImageView recipeImageView;


        RecipeViewHolder(View itemView) {
            super(itemView);
            //recipeImageView = itemView.findViewById();
            tvRecipeTitle = itemView.findViewById(R.id.tv_recipe_title);


            //set onclick
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition());
        }
    }

    //click listener for recipe
    public interface ItemClickListener {
        void onItemClick(int position);

    }



    public void updateAdapter(List<Recipe> itemList){
        this.mRecipeList = itemList;
        notifyDataSetChanged();
    }

}
