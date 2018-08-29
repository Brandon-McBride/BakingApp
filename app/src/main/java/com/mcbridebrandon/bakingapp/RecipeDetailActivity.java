package com.mcbridebrandon.bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import com.mcbridebrandon.bakingapp.adapters.IngredientAdapter;
import com.mcbridebrandon.bakingapp.adapters.StepAdapter;
import com.mcbridebrandon.bakingapp.fragments.IngredientListFragment;
import com.mcbridebrandon.bakingapp.fragments.StepListFragment;
import com.mcbridebrandon.bakingapp.fragments.VideoPlayerFragment;
import com.mcbridebrandon.bakingapp.model.Ingredient;
import com.mcbridebrandon.bakingapp.model.Recipe;
import com.mcbridebrandon.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity implements StepListFragment.onStepClickListener{
    private static final String TAG = "DETAIL";
    private Recipe mRecipe;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private RecyclerView mIngredientRecyclerView;
    private IngredientAdapter mIngredientAdapter;
    private RecyclerView mStepRecyclerView;
    private StepAdapter mStepAdapter;

    //video fragment
    private Step currentStep;
    private int stepPosition;


    //boolean to check if app is in two pane tablet mode
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(findViewById(R.id.two_pane_layout) != null){
            mTwoPane = true;

            if (savedInstanceState != null) {
                mRecipe = savedInstanceState.getParcelable("recipe");

            } else {
                Bundle bundle = getIntent().getExtras();
                mRecipe = bundle.getParcelable("recipe");
            }

            mIngredients = mRecipe.getIngredients();
            mSteps = mRecipe.getSteps();
            Log.d(TAG, "#" + mSteps);


            //create a new instance of the ingredient fragment
            IngredientListFragment ingredientFragment = new IngredientListFragment();

            //update ingredient list
            ingredientFragment.setIngredientsList(mIngredients);

            //use the fragment manager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            //fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();


            //create a new instance of the ingredient fragment
            StepListFragment stepFragment = new StepListFragment();

            //update ingredient list
            stepFragment.setStepList(mSteps);
            stepFragment.setTwoPane(mTwoPane);

            //fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();

            //load first step
            stepPosition = 0;
            currentStep = mSteps.get(stepPosition);
            //create a new instance of the ingredient fragment
            VideoPlayerFragment videoFragment = new VideoPlayerFragment();

            //update ingredient list
            videoFragment.setVideoUrl(currentStep.getVideoURL());
            videoFragment.setThumbnailUrl(currentStep.getThumbnailURL());
            videoFragment.setRecipeDescription(currentStep.getDescription());

            //fragment transaction
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, videoFragment)
                    .addToBackStack(null)
                    .commit();


        }
        else {
            mTwoPane = false;

            if (savedInstanceState != null) {
                mRecipe = savedInstanceState.getParcelable("recipe");

            } else {
                Bundle bundle = getIntent().getExtras();
                mRecipe = bundle.getParcelable("recipe");
            }

            mIngredients = mRecipe.getIngredients();
            mSteps = mRecipe.getSteps();
            Log.d(TAG, "#" + mSteps);


            //create a new instance of the ingredient fragment
            IngredientListFragment ingredientFragment = new IngredientListFragment();

            //update ingredient list
            ingredientFragment.setIngredientsList(mIngredients);

            //use the fragment manager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            //fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();


            //create a new instance of the ingredient fragment
            StepListFragment stepFragment = new StepListFragment();

            //update ingredient list
            stepFragment.setStepList(mSteps);
            stepFragment.setTwoPane(mTwoPane);

            //fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipe",mRecipe);

    }

    @Override
    public void onStepItemClick(int position) {
            currentStep = mSteps.get(position);
            //create a new instance of the ingredient fragment
            VideoPlayerFragment videoFragment = new VideoPlayerFragment();

            //Update the video fragment with data
            videoFragment.setVideoUrl(currentStep.getVideoURL());
            videoFragment.setThumbnailUrl(currentStep.getThumbnailURL());
            videoFragment.setRecipeDescription(currentStep.getDescription());

            //use the fragment manager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            //fragment transaction
            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, videoFragment)
                    .commit();

    }
}
