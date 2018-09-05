package com.mcbridebrandon.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mcbridebrandon.bakingapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class VideoPlayerFragment extends Fragment{
    private ImageView ivThumbnail;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private Long playerPosition;
    private View rootView;
    private String videoURL;
    private String thumbURL;
    private String description;

    //mandatory constructor for instantiating the fragment
    public VideoPlayerFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //inflate the ingredient list layout
        View rootView = inflater.inflate(R.layout.fragment_video_player,container,false);
        playerView = rootView.findViewById(R.id.video_playerView);
        ivThumbnail = rootView.findViewById(R.id.iv_thumb);
        TextView recipeDescription = rootView.findViewById(R.id.tv_recipe_description);

        if(savedInstanceState != null){

            videoURL = savedInstanceState.getString("videoURL");
            thumbURL = savedInstanceState.getString("thumbURL");
            playerPosition = savedInstanceState.getLong("playerposition");
            description = savedInstanceState.getString("stepdescription");

        }
           //init player
            initializePlayer(videoURL);

            //initialize thumbnail
            initalizeThumbnail(thumbURL);

            //load recipe step into textview
            recipeDescription.setText(description);


        return rootView;
    }

    private void initalizeThumbnail(String thumbURL)
    {
        /*
        Not sure if this is the correct way of doing this maybe add a check for the file type
        before using picasso callback
         */
        if(!thumbURL.isEmpty()){
            Picasso.get()
                    .load(thumbURL)
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

    }

    private void initializePlayer(String videoURL){

       //check if there is a videoURL
        if(!videoURL.isEmpty()) {


            // 1. Create a default TrackSelector
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            //Initialize the Player View

            playerView.setPlayer(player);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "Recipe Step"));

            // Produces Extractor instances for parsing the media data.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // This is the MediaSource representing the media to be played.
            Uri videoUri = Uri.parse(videoURL);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);


            // Prepare the player with the source.
            player.prepare(videoSource);

            if(playerPosition != null){
                player.seekTo(playerPosition);
            }
        }else{
            //hide the playerview
            playerView.setVisibility(View.GONE);
        }
    }

    public void setVideoUrl(String videoURL){
        this.videoURL = videoURL;
    }
    public void setThumbnailUrl(String thumbURL){
        this.thumbURL = thumbURL;
    }
    public void setRecipeDescription(String description){
        this.description = description;
    }
    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (player == null) {
            // do nothing
        } else {
            playerPosition = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //release player
        releasePlayer();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(playerPosition != null){
            //save the position of the video player
            outState.putLong("playerposition", playerPosition);
        }

        //video url
        outState.putString("videoURL", videoURL);
        //thumurl
        outState.putString("thumbURL", thumbURL);
        //step description
        outState.putString("stepdescription", description);
    }
}
