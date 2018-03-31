package com.example.jana.motivlearn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jana.motivlearn.multiplayer.MyConnectionListener;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

import static android.content.Context.MODE_PRIVATE;
import static com.example.jana.motivlearn.multiplayer.Constants.apiKey;
import static com.example.jana.motivlearn.multiplayer.Constants.secretKey;


public class StudentServices extends Fragment  implements ZoneRequestListener {
    Button RecordAchievement,WatchVideo,CreateGroupChallenge, SuggestChallenge,SuggestVideo,JoinGroupChallenge;
    private static final int CAMERA_REQUEST = 1888;
    WarpClient myGame;
    private WarpClient theClient;
    private ProgressDialog progressDialog = null;
    int uid;
    String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sp1= getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        username=sp1.getString("user_name","");
        WarpClient.initialize(apiKey,secretKey);
        View view = inflater.inflate(R.layout.fragment_student_services, container, false);
        myGame = null;
        try {
            myGame = WarpClient.getInstance();
            myGame.addConnectionRequestListener(new MyConnectionListener());
            WarpClient.getInstance().connectWithUserName(username);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecordAchievement = (Button) view.findViewById(R.id.RecordAchievement);
        RecordAchievement.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions( //Method of Fragment
                            new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST
                    );
                } else {
                    startActivity(new Intent(getActivity(), RecordAchievement.class));
                }


            }
        });

        WatchVideo = (Button) view.findViewById(R.id.WatchVideo);
        WatchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WatchVideo.class));

            }
        });

        CreateGroupChallenge = (Button) view.findViewById(R.id.CreateGroupChallenge);
        CreateGroupChallenge.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
              /*  Intent intent=new Intent(getActivity(), CreatGroupChallenge.class);
                intent.putExtra("usertype",1);
                startActivity(intent);*/
                theClient.createRoom("" + System.currentTimeMillis(), username, 4, null);
                progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            }
        });
        SuggestChallenge = (Button) view.findViewById(R.id.SuggestChallenge);
        SuggestChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), suggestQuestion.class));
            }
        });
        SuggestVideo = (Button) view.findViewById(R.id.SuggestVideo);
        SuggestVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), suggestVedio.class));
            }
        });

        JoinGroupChallenge = (Button) view.findViewById(R.id.JoinGroupChallenge);
        JoinGroupChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), JoinGroupChallenge.class));
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void init(){
        try {
            theClient = WarpClient.getInstance();
            theClient.addZoneRequestListener(this);
        } catch (Exception ex) {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST) {
            if (permissions[0].equals(Manifest.permission.CAMERA)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(getActivity(), RecordAchievement.class));
                //  proceedWithSdCard();
            }
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
       getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
                    if(progressDialog!=null){
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    joinRoom(roomEvent.getData().getId());
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
        Intent intent = new Intent(getActivity(), GroupChallenge.class);
        intent.putExtra("roomId", roomId);
        startActivity(intent);
    }
}
