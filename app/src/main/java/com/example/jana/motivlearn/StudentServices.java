package com.example.jana.motivlearn;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;


public class StudentServices extends Fragment  {
    Button RecordAchievement,WatchVideo,CreateGroupChallenge, SuggestChallenge,SuggestVideo,JoinGroupChallenge;
    private static final int CAMERA_REQUEST = 1888;
    private WarpClient theClient;
    private ProgressDialog progressDialog;
    private boolean isConnected = false;
    int uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_services, container, false);
        RecordAchievement = (Button) view.findViewById(R.id.RecordAchievement);
        RecordAchievement.setOnClickListener(new View.OnClickListener() {
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
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), CreateGroup.class));

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
               // startActivity(new Intent(getActivity(), suggestVedio.class));
            }
        });
        // Inflate the layout for this fragment
        return view;
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






}
