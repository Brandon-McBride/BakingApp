package com.mcbridebrandon.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcbridebrandon.bakingapp.IdlingResource.SimpleIdlingResource;
import com.mcbridebrandon.bakingapp.adapters.RecipeAdapter;
import com.mcbridebrandon.bakingapp.model.Recipe;
import com.mcbridebrandon.bakingapp.utilities.NetworkConfig;
import com.mcbridebrandon.bakingapp.utilities.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {
    private RecipeAdapter rAdapter;
    private RecyclerView mRecyclerView;
    private List<Recipe> mRecipeData;
    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);


        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            /*
      Whether or not the activity is in two-pane mode, i.e. running on a tablet
      device.
     */
            boolean mTwoPane = true;
        }

        // Set up the RecyclerView for displaying the list of movies in a grid
        mRecyclerView = findViewById(R.id.rv_recipe_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rAdapter = new RecipeAdapter(this, mRecipeData, this);
        // rAdapter.setClickListener(this);


        //Make the network call to get the recipe data and update adapter data
        makeNetworkCall();

        // Get the IdlingResource instance
        getIdlingResource();
        mIdlingResource.setIdleState(false);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {


    }

    private void makeNetworkCall() {
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

                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        // need to launch movie detail activity
        launchDetailsActivity(position);
    }

    private void launchDetailsActivity(int position) {
        //Send Recipe to DetailsActivity
        Recipe recipeToSend;
        recipeToSend = this.mRecipeData.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipeToSend);

        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
    /** Reference tea time udacity nanodegree
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}