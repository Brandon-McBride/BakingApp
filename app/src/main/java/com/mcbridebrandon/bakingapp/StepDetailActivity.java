package com.mcbridebrandon.bakingapp;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.mcbridebrandon.bakingapp.fragments.VideoPlayerFragment;
import com.mcbridebrandon.bakingapp.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Step> stepList;
    private Step currentStep;
    private int stepPosition;
    private SimpleExoPlayer player;
    private static final String STEPLIST_KEY = "steplist";
    private static final String STEP_KEY = "step";
    private static final String STEP_POSITION_KEY = "stepposition";
    private static final String POSITION_KEY = "position";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        if (savedInstanceState != null) {

            stepList = savedInstanceState.getParcelableArrayList(STEPLIST_KEY);
            currentStep = savedInstanceState.getParcelable(STEP_KEY);
            stepPosition = savedInstanceState.getInt(STEP_POSITION_KEY);

        } else {

            //Get the bundle from StepDetailActivity
            Bundle bundle = getIntent().getExtras();
            //Get the step list
            stepList = bundle.getParcelableArrayList(STEPLIST_KEY);
            //Get the position clicked on in StepDetailActivity
            stepPosition = bundle.getInt(POSITION_KEY);


            //Set the current step
            currentStep = stepList.get(stepPosition);

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
                .add(R.id.video_container, videoFragment)
                .commit();
    }

        //get reference to the next/prev buttons
        Button prevButton = findViewById(R.id.btn_prev);
        Button nextButton = findViewById(R.id.btn_next);

        //set the onclick listener for the buttons
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int lastPosition = stepList.size() - 1;

        switch (v.getId()) {
            case R.id.btn_prev:
                //previous button was pressed
                //load previous step
                if (currentStep.getId() > 0) {
                    //dont think i need this here i beleve its don in the fragment
                    if (player != null) {
                        player.stop();
                    }
                    //update
                    //create a new instance of the ingredient fragment
                    VideoPlayerFragment videoFragment = new VideoPlayerFragment();

                    currentStep = stepList.get(stepPosition - 1);
                    stepPosition -= 1;
                    //update ingredient list
                    videoFragment.setVideoUrl(currentStep.getVideoURL());
                    videoFragment.setThumbnailUrl(currentStep.getThumbnailURL());
                    videoFragment.setRecipeDescription(currentStep.getDescription());

                    //use the fragment manager and transaction to add the fragment to the screen
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    //fragment transaction
                    fragmentManager.beginTransaction()
                            .replace(R.id.video_container, videoFragment)
                            .commit();

                } else {
                    Toast.makeText(this, "Already at the First Step", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_next:
                //next button was pressed
                //load next step
                if (currentStep.getId() < stepList.get(lastPosition).getId()) {
                    if (player != null) {
                        player.stop();
                    }
                    //update
                    //create a new instance of the ingredient fragment
                    VideoPlayerFragment videoFragment = new VideoPlayerFragment();

                    currentStep = stepList.get(stepPosition + 1);
                    stepPosition += 1;
                    //update ingredient list
                    videoFragment.setVideoUrl(currentStep.getVideoURL());
                    videoFragment.setThumbnailUrl(currentStep.getThumbnailURL());
                    videoFragment.setRecipeDescription(currentStep.getDescription());

                    //use the fragment manager and transaction to add the fragment to the screen
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    //fragment transaction
                    fragmentManager.beginTransaction()
                            .replace(R.id.video_container, videoFragment)
                            .commit();


                } else {
                    Toast.makeText(this, "Already at the Final Step", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(STEPLIST_KEY, stepList);
        outState.putParcelable(STEP_KEY, currentStep);
        outState.putInt(STEP_POSITION_KEY, stepPosition);
    }

}