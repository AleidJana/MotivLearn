package com.example.jana.motivlearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.emredavarci.circleprogressbar.CircleProgressBar;
import com.example.jana.motivlearn.model.multiChoicesImp;
import com.example.jana.motivlearn.model.watchVideoImp;
import com.example.jana.motivlearn.presenter.watchVideoPresenter;
import com.example.jana.motivlearn.view.watchVideoView;
import com.halilibo.bettervideoplayer.BetterVideoCallback;
import com.halilibo.bettervideoplayer.BetterVideoPlayer;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class WatchVideo extends AppCompatActivity implements BetterVideoCallback, watchVideoView {

    private String url;
    private int vidId;
    int uid;
    private  Handler updateHandler;
private boolean flag=false;
    private BetterVideoPlayer player;
    private watchVideoPresenter pres;
    private ImageButton imgbtn;
    private int seconds;
    private CircleProgressBar progressBar;
    private Timer t;
    private int currentposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video);
        SharedPreferences sp1= this.getSharedPreferences("Login", MODE_PRIVATE);
        uid =sp1.getInt("user_id", 0);
        pres = new watchVideoImp(WatchVideo.this);
        pres.getVideoLink(uid);
        t = new Timer();
        player = (BetterVideoPlayer) findViewById(R.id.player);
        player.setCallback(this);
        player.disableControls();

    }

    @Override
    public void setUrl(String res)
    {
      //  TEST_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        try {
            res = res.substring(1, res.length() - 1);
            JSONObject obj = new JSONObject(res);
            url = obj.getString("link");
            vidId = obj.getInt("video_id");
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                retriever.setDataSource(url, new HashMap<String, String>());
            else
                retriever.setDataSource(url);
            String mVideoDuration =  retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long mTimeInMilliseconds= Long.parseLong(mVideoDuration);
            seconds = (int)mTimeInMilliseconds/1000;

            imgbtn = findViewById(R.id.imageButton);
            imgbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    player.pause();
                    new TTFancyGifDialog.Builder(WatchVideo.this)
                            .setTitle("Are you sure you want close the video?")
                            .setMessage("Note that you will not be able to rewatch this video\n and you will not gain any coins")
                            .setPositiveBtnText("Yes")
                            .setPositiveBtnBackground("#9577bc")
                            .setNegativeBtnText("No")
                            .setNegativeBtnBackground("#c6c9ce")
                            .setGifResource(R.drawable.hgif5)      //pass your gif, png or jpg
                            .isCancellable(true)
                            .OnPositiveClicked(new TTFancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    //Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .OnNegativeClicked(new TTFancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    //Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                                    player.start();
                                }
                            })
                            .build();
                }
            });
          //  Toast.makeText(this,seconds+"", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {}
//String uu = "https://www.youtube.com/embed/_QElVrqBwnk";
        //Toast.makeText(this,url, Toast.LENGTH_SHORT).show();
        player.setSource(Uri.parse(url));
        player.disableSwipeGestures();
        player.setAutoPlay(true);
       // seconds = player.getDuration()/1000;
        progressBar = (CircleProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(seconds);
        progressBar.setMaxValue(seconds); 			// set progress max value
        progressBar.setStrokeWidth(5); 		// set stroke width
        progressBar.setBackgroundWidth(5); 		// set progress background width
        progressBar.setText(String.valueOf(seconds)); 	// set progress text
        progressBar.setTextColor("#FFFFFF"); 		// set text color
        //player.start();

    }

    @Override
    public void noVid() {
        new TTFancyGifDialog.Builder(WatchVideo.this)
                .setTitle("No Video to Watch")
                .setMessage("you have watched this week's video, come again next week")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(R.drawable.hgif2)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        //Toast.makeText(WatchVideo.this,"Ok",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .build();
    }

   /* @Override
    public void onPause() {
        player.pause();
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.


    }*/

    // Methods for the implemented EasyVideoCallback

    @Override
    public void onStarted(BetterVideoPlayer player) {
        Log.i("haifa", "Started");
    }

    @Override
    public void onPaused(BetterVideoPlayer player) {
        Log.i("haifa", "Paused");
    }

    @Override
    public void onPreparing(BetterVideoPlayer player) {
        Log.i("haifa", "Preparing");
    }



    @Override
    public void onPrepared(final BetterVideoPlayer player) {
        Log.i("haifa", "Prepared");

        updateHandler = new Handler();
        Runnable RecurringTask = new Runnable() {

            public void run() {
                // Do whatever you want
                int mm = player.getCurrentPosition()/1000;
                progressBar.setText(String.valueOf(seconds-mm));
                   progressBar.setProgress(seconds-mm);
                // Call this method again every 30 seconds
                updateHandler.postDelayed(this, 1000);
            }
        };
        // Do this first after 1 second
        updateHandler.postDelayed(RecurringTask, 1000);

    }


    @Override
    public void onBuffering(int percent) {

        Log.i("haifa", "Buffering " + percent);
    }

    @Override
    public void onError(BetterVideoPlayer player, Exception e) {
        Log.i("haifa", "Error " +e.getMessage());
    }

    @Override
    public void onCompletion(BetterVideoPlayer player) {
        if(updateHandler!=null) {
            updateHandler.removeCallbacksAndMessages(null);
        }
     //   Toast.makeText(this,"finished", Toast.LENGTH_SHORT).show();
       // super.onBackPressed();
        pres.updateUserCoins(uid, vidId);
        //Toast.makeText(this,"uid"+uid, Toast.LENGTH_SHORT).show();
    //    Toast.makeText(this,"vidId"+vidId, Toast.LENGTH_SHORT).show();
        new TTFancyGifDialog.Builder(WatchVideo.this)
                .setTitle("Congratulations")
                .setMessage("You Have got 10 Coins")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#9577bc")
                .setGifResource(R.drawable.hgif1)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        //Toast.makeText(WatchVideo.this,"Ok",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .build();


    }

    @Override
    public void onToggleControls(BetterVideoPlayer player, boolean isShowing) {
        Log.i("haifa", "Controls toggled " + isShowing);
    }

    @Override
    public void onBackPressed() {
       // Toast.makeText(this,"can't go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
if(player.isPlaying()) {
    player.pause();
    flag=true;
    currentposition=player.getCurrentPosition();
}

    }
    @Override
    public void onResume() {
        super.onResume();
        if(flag) {
            player.start();
            player.seekTo(currentposition);

        }
    }
}