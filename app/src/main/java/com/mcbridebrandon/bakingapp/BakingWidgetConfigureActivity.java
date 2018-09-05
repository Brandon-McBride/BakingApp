package com.mcbridebrandon.bakingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mcbridebrandon.bakingapp.adapters.RecipeAdapter;
import com.mcbridebrandon.bakingapp.model.Ingredient;
import com.mcbridebrandon.bakingapp.model.Recipe;
import com.mcbridebrandon.bakingapp.utilities.NetworkConfig;
import com.mcbridebrandon.bakingapp.utilities.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The configuration screen for the {@link BakingWidget BakingWidget} AppWidget.
 */
public class BakingWidgetConfigureActivity extends Activity implements RecipeAdapter.ItemClickListener {

    private static final String PREFS_NAME = "BakingApp";
    private static final String PREF_RECIPE_NAME_KEY = "recipeName";
    private static final String PREF_INGREDIENTS_KEY = "ingredients";
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private RecipeAdapter rAdapter;
    private RecyclerView mRecyclerView;
    private List<Recipe> mRecipeData;
    private List<Ingredient> mIngredientList;
    private String recipeName;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.baking_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }else {
            //continue

            //setup recyclerview
            // Set up the RecyclerView for displaying the list of movies in a grid
            mRecyclerView = findViewById(R.id.rv_recipe_list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            rAdapter = new RecipeAdapter(this, mRecipeData, this);

            //Make the network call to get the recipe data and update adapter data
            makeNetworkCall();
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

    }
    private void makeNetworkCall(){
        // Create a very simple REST adapter which points the GitHub API endpoint.
        NetworkService netService = NetworkConfig.getClient().create(NetworkService.class);

        // Fetch a list of the Github repositories.
        Call<List<Recipe>> call = netService.getRecipes();


        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                // The network call was a success and we got a re sponse
                // TODO: use the repository list and display it
                mRecipeData = response.body();
                rAdapter.updateAdapter(mRecipeData);
                mRecyclerView.setAdapter(rAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }});
    }

    @Override
    public void onItemClick(int position) {

        final Context context = BakingWidgetConfigureActivity.this;
        Log.d("WIDGETPOSITION","pos:"+ position);
        // When the recipe is clicked store
       recipeName = mRecipeData.get(position).getName();
       mIngredientList = mRecipeData.get(position).getIngredients();

        String ingredientListString = buildString(mIngredientList);

        if (context != null) {
            SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = pref.edit();


            editor.putString(PREF_RECIPE_NAME_KEY, recipeName);
            editor.putString(PREF_INGREDIENTS_KEY, ingredientListString);

            editor.apply();
        }
        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId, recipeName, ingredientListString);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
    //credit to Allen https://github.com/allensoberano/baking-app/blob/master/app/src/main/java/com/example/android/bakingapp/ui/RecipeDetailsFragment.java
    //Builds a string of ingredients with line breaks
    private String buildString(List<Ingredient> ingredients) {

        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            builder.append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).append(" ").append(ingredient.getIngredient()).append("\n");
        }
        return builder.toString();
    }
    //Reference https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    //Method to check if a network conenction is available
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public List<Ingredient> getmIngredientList() {
        return mIngredientList;
    }

    public String getRecipeName() {
        return recipeName;
    }
}


