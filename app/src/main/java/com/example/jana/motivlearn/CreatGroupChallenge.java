package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

public class CreatGroupChallenge extends AppCompatActivity implements ZoneRequestListener{
    private WarpClient theClient;
    TextView textView;
    private ProgressDialog progressDialog = null;
   int userType;
   Button button;
    String roomid;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_group_challenge);
        userType=getIntent().getIntExtra("usertype",0);
        button=(Button)findViewById(R.id.button);
        SharedPreferences sp1= CreatGroupChallenge.this.getSharedPreferences("Login", MODE_PRIVATE);
        username=sp1.getString("user_name","");
        textView=(TextView)findViewById(R.id.textViewnum3);
         init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinRoom(roomid);
            }
        });
    }
    private void init(){
        try {
            theClient = WarpClient.getInstance();
            theClient.addZoneRequestListener(this);
           if(userType==1) {
                theClient.createRoom("" + System.currentTimeMillis(), username, 4, null);
                progressDialog = ProgressDialog.show(CreatGroupChallenge.this, "", "Please wait...");
            }else {

           }
        } catch (Exception ex) {

        }
    }



    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent allRoomsEvent) {

    }

    @Override
    public void onCreateRoomDone(final RoomEvent roomEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
                    if(progressDialog!=null){
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    roomid=roomEvent.getData().getId();
                    textView.setText(roomid);
                }else{
                }
            }
        });
    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent allUsersEvent) {

    }

    @Override
    public void onGetLiveUserInfoDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onSetCustomUserDataDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onGetMatchedRoomsDone(MatchedRoomsEvent matchedRoomsEvent) {

    }

    @Override
    public void onGetAllRoomsCountDone(AllRoomsEvent allRoomsEvent) {

    }

    @Override
    public void onGetOnlineUsersCountDone(AllUsersEvent allUsersEvent) {

    }

    @Override
    public void onGetUserStatusDone(LiveUserInfoEvent liveUserInfoEvent) {

    }
    public void joinRoom(String roomId){
        if(roomId!=null && roomId.length()>0){
            goToGroupChallengeScreen(roomId);
        }else{
            Log.d("joinRoom", "failed:"+roomId);
        }
    }
    private void goToGroupChallengeScreen(String roomId){
        Intent intent = new Intent(this, GroupChallenge.class);
        intent.putExtra("roomId", roomId);
        startActivity(intent);    }
}
