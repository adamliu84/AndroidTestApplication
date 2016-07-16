package com.example.adam.androidtestapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class LearnToPlayActivity extends YouTubeBaseActivity {

    private Button btnFly;
    private YouTubePlayerView ytpFly;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_to_play);

        ytpFly = (YouTubePlayerView) findViewById(R.id.ytpFly);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("1VQ_3sBZEm0");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("YoutubeError", youTubeInitializationResult.toString());
            }
        };

        btnFly = (Button) findViewById(R.id.btnFly);
        btnFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ytpFly.initialize(LearnToPlayAPI.api_key, onInitializedListener);
            }
        });

    }
}
