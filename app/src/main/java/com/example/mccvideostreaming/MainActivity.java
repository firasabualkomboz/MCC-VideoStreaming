package com.example.mccvideostreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class MainActivity extends AppCompatActivity {
    String VideoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    PlayerView playerView;
    SimpleExoPlayer player;

    private boolean playWhenReady       = true;
    private int     currentWindow       = 0;
    private long    playpackPosition    = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView.findViewById(R.id.video_view);
    }


            public void initVideo(){

            player = ExoPlayerFactory.newSimpleInstance(this);
            playerView.setPlayer(player);

            Uri uri =Uri.parse(VideoURL);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,"exoplayer-codelab"); // static val
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow,playpackPosition);
            player.prepare(mediaSource,false,false) ;

            }

            public void releaseVideo(){

            if (player != null){
                playWhenReady = player.getPlayWhenReady();
                playpackPosition = player.getCurrentPosition();
                currentWindow = player.getCurrentWindowIndex();
                player.release();
                player=null;

            }

            }

    @Override
    protected void onStart() {
        super.onStart();
        initVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(player!=null){
            initVideo();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseVideo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseVideo();
    }
}