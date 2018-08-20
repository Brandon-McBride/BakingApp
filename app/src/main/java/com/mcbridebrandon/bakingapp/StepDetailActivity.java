package com.mcbridebrandon.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.mcbridebrandon.bakingapp.fragments.VideoPlayerFragment;
import com.mcbridebrandon.bakingapp.model.Step;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class StepDetailActivity extends AppCompatActivity {
    private Step mStep;
    private SimpleExoPlayer player;
    private Long playerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Bundle bundle = getIntent().getExtras();
        mStep = bundle.getParcelable("step");


        if(!mStep.getVideoURL().isEmpty()){
            //create a new instance of the ingredient fragment
            VideoPlayerFragment videoFragment = new VideoPlayerFragment();

            //update ingredient list
            videoFragment.setVideoUrl(mStep.getVideoURL());

            //use the fragment manager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            //fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.video_container,videoFragment)
                    .commit();

        }else{
            //hide
        }

        //view for thumbnail image
        final ImageView ivThumbnail = findViewById(R.id.iv_thumb);
        /*
        Not sure if this is the correct way of doing this maybe add a check for the file type
        before using picasso callback
         */
        if(!mStep.getThumbnailURL().isEmpty()){
            Picasso.get()
                    .load(mStep.getThumbnailURL())
                    //.placeholder(R.drawable.user_placeholder)
                    //.error(R.drawable.user_placeholder_error)
                    .into(ivThumbnail, new Callback() {
                        @Override
                        public void onSuccess() {
                            //picture was loaded
                            ivThumbnail.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            //picture not loaded error
                            ivThumbnail.setVisibility(View.GONE);
                        }
                    });

        }else{
            ivThumbnail.setVisibility(View.GONE);
        }


        //load recipe step into textview
        TextView recipeDescription = findViewById(R.id.tv_recipe_description);

        recipeDescription.setText(mStep.getDescription());


    }

}
