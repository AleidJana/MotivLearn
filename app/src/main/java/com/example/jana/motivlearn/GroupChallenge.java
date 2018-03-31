package com.example.jana.motivlearn;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import java.util.HashMap;

public class GroupChallenge extends AppCompatActivity  implements  RoomRequestListener, NotifyListener {
    private WarpClient theClient;
TextView textView0;
TextView textView1;
TextView textView2;
TextView textView3;
Button start;
String username;
String[] joinedUser=new String[4];
    private String roomId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_challenge);
        textView0=(TextView)findViewById(R.id.textView9);
        textView1=(TextView)findViewById(R.id.textView10);
        textView2=(TextView)findViewById(R.id.textView11);
        textView3=(TextView)findViewById(R.id.textView12);
        start=(Button) findViewById(R.id.button);
        SharedPreferences sp1= this.getSharedPreferences("Login", MODE_PRIVATE);
        username=sp1.getString("user_name","");
        try {
            theClient = WarpClient.getInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        roomId=getIntent().getStringExtra("roomId");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(roomId);
        /*mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        init(roomId);


    }

    private void init(String roomId){
        if(theClient!=null){
            theClient.addRoomRequestListener(this);
            theClient.addNotificationListener(this);
            theClient.joinRoom(roomId);

        }
    }

    @Override
    public void onRoomCreated(RoomData roomData) {

    }

    @Override
    public void onRoomDestroyed(RoomData roomData) {

    }

    @Override
    public void onUserLeftRoom(RoomData roomData, String s) {

    }

    @Override
    public void onUserJoinedRoom(final RoomData roomData, String s) {
         runOnUiThread(new Runnable() {
            @Override
            public void run() {
                theClient.getLiveRoomInfo(roomId);
            }
        });

    }

    @Override
    public void onUserLeftLobby(LobbyData lobbyData, String s) {

    }

    @Override
    public void onUserJoinedLobby(LobbyData lobbyData, String s) {

    }

    @Override
    public void onChatReceived(ChatEvent chatEvent) {

    }

    @Override
    public void onPrivateChatReceived(String s, String s1) {

    }

    @Override
    public void onPrivateUpdateReceived(String s, byte[] bytes, boolean b) {

    }

    @Override
    public void onUpdatePeersReceived(UpdateEvent updateEvent) {

    }

    @Override
    public void onUserChangeRoomProperty(RoomData roomData, String s, HashMap<String, Object> hashMap, HashMap<String, String> hashMap1) {

    }

    @Override
    public void onMoveCompleted(MoveEvent moveEvent) {

    }

    @Override
    public void onGameStarted(String s, String s1, String s2) {

    }

    @Override
    public void onGameStopped(String s, String s1) {

    }

    @Override
    public void onUserPaused(String s, boolean b, String s1) {

    }

    @Override
    public void onUserResumed(String s, boolean b, String s1) {

    }

    @Override
    public void onNextTurnRequest(String s) {

    }

    @Override
    public void onSubscribeRoomDone(final RoomEvent roomEvent) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
                    theClient.getLiveRoomInfo(roomId);
                    //Toast.makeText(GroupChallenge.this, "Room Subscribe done",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(GroupChallenge.this, "Room Subscribe failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent roomEvent) {

    }
    @Override
    public void onJoinRoomDone(final RoomEvent roomEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
                    theClient.subscribeRoom(roomId);
                    if(roomEvent.getData().getRoomOwner().equals(username))
                    {
                        start.setVisibility(View.VISIBLE);
                    }
                    //Toast.makeText(GroupChallenge.this, "Room joining done",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(GroupChallenge.this, "Room joining failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onLeaveRoomDone(RoomEvent roomEvent) {


    }
    @Override
    public void onGetLiveRoomInfoDone(final LiveRoomInfoEvent liveRoomInfoEvent) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(liveRoomInfoEvent.getResult()== WarpResponseResultCode.SUCCESS){
                        joinedUser = liveRoomInfoEvent.getJoinedUsers();
                        for(int i=0;i<joinedUser.length;i++){
                            switch (i)
                            {
                                case 0:
                                    textView0.setText(joinedUser[i]);
                                    break;
                                case 1:
                                    textView1.setText(joinedUser[i]);
                                    break;
                                case 2:
                                    textView2.setText(joinedUser[i]);
                                    break;
                                case 3:
                                    textView3.setText(joinedUser[i]);
                                    break;
                            }
                        }
                    }else{
                    }
                }
            });


    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent liveRoomInfoEvent) {

    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent liveRoomInfoEvent) {

    }

    @Override
    public void onLockPropertiesDone(byte b) {

    }

    @Override
    public void onUnlockPropertiesDone(byte b) {

    }

    @Override
    public void onJoinAndSubscribeRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {

    }
//    @Override
//    public void onBackPressed() {
//        theClient.leaveRoom(roomId);
//    }
}
