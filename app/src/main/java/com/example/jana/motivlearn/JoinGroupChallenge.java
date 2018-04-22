package com.example.jana.motivlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

public class JoinGroupChallenge extends AppCompatActivity implements ZoneRequestListener {
EditText editText;
Button button;
    String roomid;
    private WarpClient theClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group_challenge);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Join Group");
        mToolbar.setTitleTextColor(R.color.white);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.textView6);
        try {
            theClient = WarpClient.getInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 roomid = String.valueOf(editText.getText());
               if(!"".equals(roomid)&&roomid!=null)
               {
                   init();
               }
            }
        });
    }
    private void init(){
        if(theClient!=null){
            theClient.addZoneRequestListener(this);
            theClient.getAllRooms();

        }
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

    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetAllRoomsDone(final AllRoomsEvent allRoomsEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(allRoomsEvent.getResult()== WarpResponseResultCode.SUCCESS){
                    String[] RoomIds=allRoomsEvent.getRoomIds();
                    boolean flag=true;
                    for (int i=0;i<RoomIds.length;i++)
                    {
                        if(RoomIds[i].equals(roomid)) {
                            flag=true;
                            joinRoom(roomid);
                            break;
                        }
                        else
                            flag=false;

                    }
                    if(!flag)
                    {
                        Toast.makeText(JoinGroupChallenge.this, "Room is not found",Toast.LENGTH_LONG).show();

                    }
                }else{
                    //Toast.makeText(GroupChallenge.this, "Room Subscribe failed",Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    public void onCreateRoomDone(RoomEvent roomEvent) {

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
}
