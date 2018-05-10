package com.example.jana.motivlearn;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jana.motivlearn.model.groupChallengeImp;
import com.example.jana.motivlearn.presenter.groupChallengePresenter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.TurnBasedRoomListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

import static com.example.jana.motivlearn.tab2.pres;
import static com.loopj.android.http.AsyncHttpClient.log;

public class GroupChallenge extends AppCompatActivity  implements  RoomRequestListener, NotifyListener, TurnBasedRoomListener, ZoneRequestListener {
    private WarpClient theClient = null;
TextView textView0;
TextView textView1;
TextView textView2;
TextView textView3;
int coins=0, MyCoins=0, time;
int challnum=0;
String winner="";
    RadioGroup radio;
ProgressDialog progressDialog;
String answer;
ProgressBar progressBar;
CountDownTimer countDownTimer;
int millisecondsleft;
Button start;
int[] scores;
String[] resultusernames;
int counter = 0;
boolean ressu=false;
String username;
boolean firstTime = true;
int owner=0;
JSONArray fiveChallenges;
String[] joinedUser=new String[4];
String question;
boolean roomowner=false;
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

            SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);
            username = sp1.getString("user_name", "");
            roomId = getIntent().getStringExtra("roomId");
            roomowner = getIntent().getBooleanExtra("roomowner",false);

        if(roomowner) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("your Room ID is: "+roomId+" Do you want to share it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            String shareBody = "The Room ID is : " + roomId;
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Room ID");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Room ID");
            alert.show();

        }
       /* Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
            try {
                theClient = WarpClient.getInstance();
                theClient.addNotificationListener(this);
                theClient.addRoomRequestListener(this);
                theClient.addTurnBasedRoomListener(this);
                theClient.addZoneRequestListener(this);
                theClient.joinRoom(roomId);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        log.v("RESUMMMMMMMED", username+"   "+MyCoins);
        if(owner == 1) {
            try {
                theClient.setCustomRoomData(roomId, fiveChallenges.get(++challnum).toString());
                theClient.sendChat("started");
            } catch (Exception e) {
            }
        }
        else
            challnum++;

        RelativeLayout mc = findViewById(R.id.multichoices);
        mc.setVisibility(View.INVISIBLE);
        RelativeLayout co = findViewById(R.id.codeoutput);
        co.setVisibility(View.INVISIBLE);
        RelativeLayout pz = findViewById(R.id.puzzlely);
        pz.setVisibility(View.INVISIBLE);


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
        log.v("NOOOOOTTTTTEEEEE",chatEvent.getMessage()+" "+username);
        if(chatEvent.getMessage().equals("started"))
            theClient.getLiveRoomInfo(roomId);
        else if(chatEvent.getMessage().startsWith("FinalResult"))
        {
            String[] temp = chatEvent.getMessage().split(":");
            resultusernames[counter] = temp[1];
            scores[counter++]= Integer.parseInt(temp[2]);
        }
  //      else
          //  challnum=Integer.parseInt(chatEvent.getMessage());
      /*  if(winner.equals("")&&!chatEvent.getMessage().equals("started")) {
            winner = chatEvent.getMessage();*
        }*/
        //theClient.getLiveRoomInfo(roomId);
    }

    @Override
    public void onPrivateChatReceived(String s, String s1) {

    }

    @Override
    public void onPrivateUpdateReceived(String s, byte[] bytes, boolean b) {
     //   log.v("NOOOOOTTTTTEEEEE",s+" "+username);

    }

    @Override
    public void onUpdatePeersReceived(UpdateEvent updateEvent) {
      //  log.v("NOOOOOTTTTTEEEEE",updateEvent.getUpdate()+" "+username);

    }

    @Override
    public void onUserChangeRoomProperty(RoomData roomData, String s, HashMap<String, Object> hashMap, HashMap<String, String> hashMap1) {
       // log.v("NOOOOOTTTTTEEEEE",s+" "+username);
    }

    @Override
    public void onMoveCompleted(MoveEvent moveEvent) {

    }

    @Override
    public void onGameStarted(String s, String s1, String s2) {
        ProgressWheel wheel = new ProgressWheel(GroupChallenge.this);
        wheel.setBarColor(Color.BLUE);
        log.v("NOOOOOTTTTTEEEEE", "onGameStarted "+username);
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
                    if(roomEvent.getData().getRoomOwner().equals(username) && firstTime)
                    {

                        firstTime = false;
                        owner=1;
                        start.setVisibility(View.VISIBLE);
                        start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                theClient.getOnlineUsersCount();

                            }
                        });
                    }
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
                        log.v("NOOOOOTTTTTEEEEE",liveRoomInfoEvent.getCustomData()+" "+username);
                        if(!liveRoomInfoEvent.getCustomData().equals(" ") && !liveRoomInfoEvent.getCustomData().equals(""))
                        {
                            String type="";
                            try {
                            JSONObject obj = new JSONObject(liveRoomInfoEvent.getCustomData());
                            type = obj.getString("type");
                            switch (type)
                            {
                                case "MC":
                                    displayMultiChoice(liveRoomInfoEvent.getCustomData());
                                    break;
                                case "CO":
                                    displayCodeOutput(liveRoomInfoEvent.getCustomData());
                                    break;
                                case "FB":
                                    displayFillBlank(liveRoomInfoEvent.getCustomData());
                                    break;
                                case "PZ":
                                    displayPuzzle(liveRoomInfoEvent.getCustomData());
                                    break;
                            }
                            log.v("TTTTTTYYYYPPPPEEE",type+" "+username);
                        }
                        catch(Exception e){
                            log.v("TTTTTTYYYYPPPPEEE",type+" "+username);
                        }


                        }
                        if(ressu) {
                            Intent intent;
                            ressu = false;
                            if (challnum == 2)
                            {   SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
                                int uid =sp1.getInt("user_id", 0);
                                groupChallengePresenter pre = new groupChallengeImp();
                                String[] users = liveRoomInfoEvent.getJoinedUsers();
                                scores = new int[users.length];
                                resultusernames = new String[users.length];
                                theClient.sendChat("FinalResult:"+username+":"+MyCoins);

                               while (counter<liveRoomInfoEvent.getJoinedUsers().length);
                               
                               orderWinners(resultusernames, scores);
                               if(resultusernames[0].equals(username) && MyCoins!=0)
                                   pre.updateCoinsWinner(uid, MyCoins+10);
                               else
                                   pre.updateCoins(uid, MyCoins);
                               intent = new Intent(GroupChallenge.this, finalResult.class);
                               intent.putExtra("users", resultusernames);
                               intent.putExtra("scores", scores);
                               startActivity(intent);
                            }
                            else
                            {
                                intent = new Intent(GroupChallenge.this, Result.class);
                                intent.putExtra("winners", liveRoomInfoEvent.getProperties().toString());
                                intent.putExtra("num", challnum+1);
                            }

                            startActivity(intent);
                        }
                    }else{
                    }
                }
            });
    }

    private void orderWinners(String[] resultusernames, int[] scores) {

        for (int i = 0; i < scores.length; i++) {
            for (int j = i + 1; j < scores.length; j++) {
                int tmp = 0;
                String tmp0="";
                if (scores[i] < scores[j]) {
                    tmp = scores[i];
                    scores[i] = scores[j];
                    scores[j] = tmp;

                    tmp0 = resultusernames[i];
                    resultusernames[i] = resultusernames[j];
                    resultusernames[j] = tmp0;
                }
            }
        }
    }

    private void displayMultiChoice(String res) {
       // progressBar.setProgress(0);
        theClient.setCustomRoomData(roomId, " ");
        try {
            JSONObject obj = new JSONObject(res);
            String qustion=obj.getString("question");
           // String challenge_title=obj.getString("challenge_title");
            String allChoise = obj.getString("answer");
            coins = obj.getInt("coins");
            time= obj.getInt("time");
           // field = obj.getString("field");
            JSONObject obj2 = new JSONObject(allChoise);
            int length= obj2.length();
            //TitleD.setText(challenge_title);
            progressBar = findViewById(R.id.progressBar2);
            progressBar.setMax(time);
            //progressBar.setProgress(10);
            progressBar.setProgress(time);
            time=time*1000;
            countDownTimer=new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    millisecondsleft=(int)millisUntilFinished;
                    progressBar.setProgress((int)(millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    ressu=true;
                    theClient.getLiveRoomInfo(roomId);
                    countDownTimer.cancel();

                   // ll.crrectAnswer(uid, challNum, "timeout", "gg", 0 , 0, 0);
                }
            }.start();

            TextView qustionD = findViewById(R.id.textViewTitle);
            qustionD.setText(qustion);
            radio = findViewById(R.id.radioG);
            radio.removeAllViews();
            for (int i = 1; i <= length-1 ; i++) {
                RadioButton rbn= new RadioButton(this);
                rbn.setId(i);
                String c=obj2.getString("choice"+i);
                rbn.setText(c);
                radio.addView(rbn);
            }

            answer = obj2.getString("answer");
            final Button submit = findViewById(R.id.button1);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // countDownTimer.cancel();
                    RelativeLayout mcl = findViewById(R.id.multichoices);
                    mcl.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);

                    int selectedId = radio.getCheckedRadioButtonId();//get the user choice
                    if(selectedId!=-1) {
                        RadioButton radioButton = findViewById(selectedId);
                        if (answer.equals(radioButton.getText())) {//compare the user choice eith the model answer
                            HashMap<String, Object> content= new HashMap<>();
                            //create a hashmap with the number of the challenge and the answerd user's username
                            content.put("ch"+(challnum+1), username);
                            //update the RoomProperties with the winner of the challenge
                            theClient.updateRoomProperties(roomId, content, null);
                            //lock this Property to not allow other players to overwrite the winner record
                            theClient.lockProperties(content);
                        }
                        else {
                            //ll.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0, 0);
                        }
                    }

                    log.v("NOOOOTEEEE", "I am "+username+" But the winner is "+winner);
                }
            });
            submit.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RelativeLayout mcl = findViewById(R.id.multichoices);
        mcl.setVisibility(View.VISIBLE);
    }
    private void displayCodeOutput(String res){
       // progressBar.setProgress(0);
        theClient.setCustomRoomData(roomId, " ");
        try {
            JSONObject obj = new JSONObject(res);
            String question = obj.getString("question");
            coins = obj.getInt("coins");
            time= obj.getInt("time");
            TextView Tqustion = findViewById(R.id.textViewTitle3);
            Tqustion.setText(question);
            EditText Uanswer = findViewById(R.id.editText);
            Uanswer.setText("");
            answer = obj.getString("answer");
            progressBar = findViewById(R.id.progressBar2);
            progressBar.setMax(time);
            progressBar.setProgress(time);
            time=time*1000;
            countDownTimer = new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    millisecondsleft=(int)millisUntilFinished;
                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    ressu=true;
                    theClient.getLiveRoomInfo(roomId);
                    countDownTimer.cancel();

                    //progressDialog.dismiss();
                   // displayCodeOutputP.crrectAnswer(uid, challNum, "timeout", "gg", 0 , 0, 0);
                }
            }.start();

            final Button submit = findViewById(R.id.button1);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout col = findViewById(R.id.codeoutput);
                    col.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                  //  countDownTimer.cancel();
                   // progressDialog = ProgressDialog.show(GroupChallenge.this, "", "Please wait...");
                    EditText Uanswer = findViewById(R.id.editText);
                    String userAnswer = Uanswer.getText().toString().trim();
                    log.v("NOOOOOTTTTTEEEEE",userAnswer+"  -  "+answer+" "+username);
                    if (userAnswer.equals(answer.trim())) {
                         /*   Toast.makeText(getApplicationContext(), "correct",
                                    Toast.LENGTH_LONG).show();*/
                       // displayCodeOutputP.selectRank(uid,challNum,"pass","gg",3, field);

                        HashMap<String, Object> content= new HashMap<>();
                        content.put("ch"+(challnum+1), username);
                        //theClient.getLiveRoomInfo(roomId);
                        theClient.updateRoomProperties(roomId, content, null);
                        theClient.lockProperties(content);


                    } else {
                        // Toast.makeText(getApplicationContext(), "not correct", Toast.LENGTH_LONG).show();
                       // displayCodeOutputP.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0, 0);
                    }
                }
            });
            submit.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RelativeLayout col = findViewById(R.id.codeoutput);
        col.setVisibility(View.VISIBLE);
    }
    private void displayFillBlank(String res) {
      //  progressBar.setProgress(0);
        theClient.setCustomRoomData(roomId, " ");
        try {
            JSONObject obj = new JSONObject(res);
            String question = obj.getString("question");
            time = obj.getInt("time");
            coins = obj.getInt("coins");
            TextView Tqustion = findViewById(R.id.textViewTitle3);
            Tqustion.setText(question);
            String Allanswer = obj.getString("answer");
            final EditText Uanswer = findViewById(R.id.editText);
            Uanswer.setText("");
            JSONObject obj2 = new JSONObject(Allanswer);
            int count = obj2.length();
            String hint = "answer";
            final String[] answer1 = new String[obj2.length()];
            for (int i = 1; i <= obj2.length(); i++) {
                int j = i - 1;
                answer1[j] = (obj2.getString("" + j)).trim().toLowerCase();
                hint = hint + i + ",";
            }
            Uanswer.setHint(hint);
            progressBar = findViewById(R.id.progressBar2);
            progressBar.setMax(time);
            progressBar.setProgress(time);
            time = time * 1000;
            countDownTimer = new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    // Toast.makeText(displayfillBlanck.this,"seconds remaining: " + millisUntilFinished / 1000,Toast.LENGTH_SHORT).show();
                    millisecondsleft = (int) millisUntilFinished;
                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                }

                public void onFinish() {
                    progressBar.setProgress(0);
                    ressu = true;
                    theClient.getLiveRoomInfo(roomId);
                    countDownTimer.cancel();

                }
            }.start();
            final Button submit = findViewById(R.id.button1);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout col = findViewById(R.id.codeoutput);
                    col.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                    //countDownTimer.cancel();
               //     progressDialog = ProgressDialog.show(displayfillBlanck.this, "", "Please wait...");
                    String userAnswer = Uanswer.getText().toString();
                    String[] answer = userAnswer.split(",");
                    if (answer.length == answer1.length) {
                        boolean flag = true;
                        for (int l = 0; l < answer.length; l++) {
                            if ((answer[l]).trim().toLowerCase().equals(answer1[l])){
                                flag = true;
                            }
                            else{
                                flag=false;
                                break;
                            }
                        }
                        if (flag) {
                            HashMap<String, Object> content= new HashMap<>();
                            content.put("ch"+(challnum+1), username);
                            //theClient.getLiveRoomInfo(roomId);
                            theClient.updateRoomProperties(roomId, content, null);
                            theClient.lockProperties(content);
                        }

                    }
                }
            });
            submit.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RelativeLayout col = findViewById(R.id.codeoutput);
        col.setVisibility(View.VISIBLE);
    }
    private void displayPuzzle(String res){
   //     progressBar.setProgress(0);
        theClient.setCustomRoomData(roomId, " ");
        try {
            JSONObject obj = new JSONObject(res);
            final String question = obj.getString("question");
            //log.d("HAIFA", question );
            DynamicListView listView = (DynamicListView) findViewById(R.id.dynamiclistview);
            ArrayAdapter<String> adapter = new MyListAdapter(this, question);

            listView.setAdapter(adapter);
            listView.enableDragAndDrop();
            listView.setDraggableManager(new TouchViewDraggableManager(R.id.list_row_draganddrop_textview));
            listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));

            time= obj.getInt("time");
            coins = obj.getInt("coins");
            progressBar = findViewById(R.id.progressBar2);
            progressBar.setMax(time);
            progressBar.setProgress(time);
            time=time*1000;
            countDownTimer= new CountDownTimer(time, 1000) {

                public void onTick(long millisUntilFinished) {
                    millisecondsleft=(int)millisUntilFinished;
                    progressBar.setProgress((int) (millisUntilFinished / 1000));
                }
                public void onFinish() {
                    progressBar.setProgress(0);
                    ressu = true;
                    theClient.getLiveRoomInfo(roomId);
                    countDownTimer.cancel();

                }
            }.start();


            final Button submit = findViewById(R.id.button1);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  countDownTimer.cancel();
                    RelativeLayout col = findViewById(R.id.puzzlely);
                    col.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                 //   progressDialog = ProgressDialog.show(displayPuzzle.this, "", "Please wait...");
                    DynamicListView listView = (DynamicListView) findViewById(R.id.dynamiclistview);
                    ListAdapter ll = listView.getAdapter();
                    try {
                        JSONArray jsonArray = new JSONArray(question);
                        boolean flag = true;
                        for (int i = 0; i < ll.getCount(); i++) {
                            if ((ll.getItem(i) + "").equals(jsonArray.get(i)))
                                continue;

                            flag = false;
                            //Toast.makeText(getApplicationContext(), "Wrong order", Toast.LENGTH_SHORT).show();
                          //  pre.crrectAnswer(uid, challNum, "fail", "gg", 0 , 0,0);
                            break;
                        }
                        if (flag)
                        {   HashMap<String, Object> content= new HashMap<>();
                            content.put("ch"+(challnum+1), username);
                            //theClient.getLiveRoomInfo(roomId);
                            theClient.updateRoomProperties(roomId, content, null);
                            theClient.lockProperties(content);
                        }
                    } catch (Exception e) {
                    }

                }
            });
            submit.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

        RelativeLayout col = findViewById(R.id.puzzlely);
        col.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        log.v("NOOOOOTTTTTEEEEE",liveRoomInfoEvent.getCustomData()+" "+username);
    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        MyCoins+=coins;
    }

    @Override
    public void onLockPropertiesDone(byte b) {

    }

    @Override
    public void onUnlockPropertiesDone(byte b) {

    }

    @Override
    public void onJoinAndSubscribeRoomDone(RoomEvent roomEvent) {
       // log.v("NOOOOOTTTTTEEEEE",roomEvent.getData()+" "+username);
    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onSendMoveDone(byte b) {

    }

    @Override
    public void onStartGameDone(byte b) {
        log.v("NOOOOOTTTTTEEEEE","onStartGameDone "+username);
    }

    @Override
    public void onStopGameDone(byte b) {

    }

    @Override
    public void onGetMoveHistoryDone(byte b, MoveEvent[] moveEvents) {

    }

    @Override
    public void onSetNextTurnDone(byte b) {

    }
//    @Override
//    public void onBackPressed() {
//        theClient.leaveRoom(roomId);
//    }

    public void getFiveChallenges() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestHandle requestHandle = client.get("https://api.appery.io/rest/1/apiexpress/api/" +
                "15_takeGroupChallenge/?apiKey=cb85dda5-927f-4408-844b-44bb99347ed4", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onGetFiveChallengesDone("fail");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
              //  responseString = responseString.substring(1, responseString.length() - 1);
                onGetFiveChallengesDone(responseString);
            }

        });
    }

    public void onGetFiveChallengesDone(String challenges)
    {
        //theClient.startGame(true);
        try {
            fiveChallenges = new JSONArray(challenges);
            theClient.setCustomRoomData(roomId, fiveChallenges.get(challnum).toString());
            theClient.sendChat("started");
            start.setVisibility(View.INVISIBLE);
        }catch (Exception e){}

    }

    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent allRoomsEvent) {

    }

    @Override
    public void onCreateRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent allUsersEvent) {
       // Log.v("UUUUUSSSSSEEEEERRRRRS",allUsersEvent.getUsersCount()+"");


    }

    @Override
    public void onGetLiveUserInfoDone(LiveUserInfoEvent liveUserInfoEvent) {


    }

    @Override
    public void onSetCustomUserDataDone(LiveUserInfoEvent liveUserInfoEvent) {
        resultusernames[counter]= liveUserInfoEvent.getName();
        scores[counter++] = Integer.parseInt(liveUserInfoEvent.getCustomData());
    }

    @Override
    public void onGetMatchedRoomsDone(MatchedRoomsEvent matchedRoomsEvent) {

    }

    @Override
    public void onGetAllRoomsCountDone(AllRoomsEvent allRoomsEvent) {

    }

    @Override
    public void onGetOnlineUsersCountDone(AllUsersEvent allUsersEvent) {
        if(allUsersEvent.getUsersCount() <2) {
            this.runOnUiThread(new Runnable() {
                public void run() {
                Toast.makeText(GroupChallenge.this,"the room has to contains 2 players at least", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
            this.runOnUiThread(new Runnable() {
                public void run() {
                    getFiveChallenges();                }
            });


    }

    @Override
    public void onGetUserStatusDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    ////////////////////////////////////////////////////////////
    private static class MyListAdapter extends ArrayAdapter<String>{

        private final Context mContext;

        MyListAdapter(final Context context, String question) {
            mContext = context;
            try {
                JSONArray jsonArray = new JSONArray(question);
                jsonArray = shuffleJsonArray(jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    add(jsonArray.get(i)+"");
                    // log.d("HAIFA", jsonArray.get(i)+"" );
                    // add(mContext.getString(R.string.row_number, i));
                }
            }
            catch(Exception e){}
        }

        @Override
        public long getItemId(final int position) {
            return getItem(position).hashCode();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.list_row_dynamiclistview, parent, false);
            }

            ((TextView) view.findViewById(R.id.list_row_draganddrop_textview)).setText(getItem(position));

            return view;
        }

        public static JSONArray shuffleJsonArray (JSONArray array) throws JSONException {
            // Implementing Fisher–Yates shuffle
            Random rnd = new Random();
            for (int i = array.length() - 1; i >= 0; i--)
            {
                int j = rnd.nextInt(i + 1);
                // Simple swap
                Object object = array.get(j);
                array.put(j, array.get(i));
                array.put(i, object);
            }
            return array;
        }
    }


    private class MyOnItemMovedListener implements OnItemMovedListener {

        private final ArrayAdapter<String> mAdapter;

        private Toast mToast;

        MyOnItemMovedListener(final ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onItemMoved(final int originalPosition, final int newPosition) {
            if (mToast != null) {
                mToast.cancel();
            }
            //  mToast = Toast.makeText(getApplicationContext(), getString(R.string.moved , mAdapter.getItem(newPosition), newPosition), Toast.LENGTH_SHORT);
            //  mToast.show();
        }
    }
}




