package com.example.jana.motivlearn.multiplayer;

import android.widget.Toast;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

/**
 * Created by jana on 3/30/2018 AD.
 */

public class MyConnectionListener implements ConnectionRequestListener {
    public  static boolean Multiplayer=false;
    @Override
    public void onConnectDone(ConnectEvent connectEvent) {

        if(connectEvent.getResult() == WarpResponseResultCode.SUCCESS){
            Multiplayer=true;
            System.out.println("yipee I have connected");
        }
    }


    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        System.out.println("On Disconnected invoked");
    }

    @Override
    public void onInitUDPDone(byte b) {

    }

}
