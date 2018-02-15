package com.example.jana.motivlearn;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.halilibo.bettervideoplayer.BetterVideoCallback;
import com.halilibo.bettervideoplayer.BetterVideoPlayer;

public class WatchVideo extends AppCompatActivity implements BetterVideoCallback {

    private static final String TEST_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private BetterVideoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video);

        // Grabs a reference to the player view
        player = (BetterVideoPlayer) findViewById(R.id.player);

        // Sets the callback to this Activity, since it inherits EasyVideoCallback
        player.setCallback(this);

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        player.setSource(Uri.parse(TEST_URL));
        player.disableSwipeGestures();
        // Hide the default controls and prevents them from being shown.
        player.disableControls();

// Defaults to false. Immediately starts playback when the player becomes prepared.
        player.setAutoPlay(true);
        // From here, the player view will show a progress indicator until the player is prepared.
        // Once it's prepared, the progress indicator goes away and the controls become enabled for the user to begin playback.
    }

    @Override
    public void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        player.pause();
    }

    // Methods for the implemented EasyVideoCallback

    @Override
    public void onStarted(BetterVideoPlayer player) {
        //Log.i(TAG, "Started");
    }

    @Override
    public void onPaused(BetterVideoPlayer player) {
        //Log.i(TAG, "Paused");
    }

    @Override
    public void onPreparing(BetterVideoPlayer player) {
        //Log.i(TAG, "Preparing");
    }

    @Override
    public void onPrepared(BetterVideoPlayer player) {
        //Log.i(TAG, "Prepared");
    }

    @Override
    public void onBuffering(int percent) {
        //Log.i(TAG, "Buffering " + percent);
    }

    @Override
    public void onError(BetterVideoPlayer player, Exception e) {
        //Log.i(TAG, "Error " +e.getMessage());
    }

    @Override
    public void onCompletion(BetterVideoPlayer player) {
        //Log.i(TAG, "Completed");
    }

    @Override
    public void onToggleControls(BetterVideoPlayer player, boolean isShowing) {
        //Log.i(TAG, "Controls toggled " + isShowing);
    }
}