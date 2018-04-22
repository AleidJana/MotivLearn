package com.example.jana.motivlearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import static com.loopj.android.http.AsyncHttpClient.log;

public class GroupChallenge extends AppCompatActivity  implements  RoomRequestListener, NotifyListener, TurnBasedRoomListener {
    private WarpClient theClient;
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
boolean ressu=false;
String username;
int owner=0;
JSONArray fiveChallenges;
String[] joinedUser=new String[4];
String question;
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
            theClient.addNotificationListener(this);
            theClient.addRoomRequestListener(this);
            theClient.addTurnBasedRoomListener(this);
            theClient.joinRoom(roomId);

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
                    if(roomEvent.getData().getRoomOwner().equals(username))
                    {
                        owner=1;
                        start.setVisibility(View.VISIBLE);
                        start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              /*  theClient.sendChat("sentChat");
                                theClient.setCustomRoomData(roomId, "setCustomRoomData");
                                theClient.sendChat("Started");
                                theClient.startGame();
                                theClient.startGame(true, "");*/
                                getFiveChallenges();
                                //question ="{\"challenge_id\":5,\"type\":\"MC\",\"question\":\"When using the POST method, variables are displayed in the URL:\",\"answer\":\"{\\\"choice1\\\":\\\"True\\\",\\\"choice2\\\":\\\"False\\\",\\\"answer\\\":\\\"False\\\"}\",\"time\":10,\"field\":\"php\",\"rank\":5,\"coins\":5,\"challenge_title\":\"post method\",\"duration\":0,\"is_public\":false,\"user_id\":10,\"challenge_date\":\"2018-03-12 22:13:05.0\"}";
                              //  question = "{\"challenge_id\":1,\"type\":\"CO\",\"question\":\"\\t\\tfor(int i = 0; i<3; i++) {\\r\\n\\t\\t\\tSystem.out.println(\\\"Hello\\\");\\r\\n\\t\\t\\tbreak;\\r\\n\\t\\t}\",\"answer\":\"Hello\",\"time\":20,\"field\":\"java\",\"rank\":2,\"coins\":3,\"challenge_title\":\"simple java test\",\"duration\":0,\"is_public\":false,\"user_id\":2,\"challenge_date\":\"2018-03-12 09:58:36.0\"}";
                                // question = "{\"challenge_id\":6,\"type\":\"FB\",\"question\":\"The default connection type used by HTTP is\",\"answer\":\"{\\\"0\\\":\\\"Haifa\\\"}\",\"time\":15,\"field\":\"php\",\"rank\":14,\"coins\":5,\"challenge_title\":\"HTTP Request \",\"duration\":0,\"is_public\":false,\"user_id\":8,\"challenge_date\":\"2018-03-12 22:13:20.0\"}";
                               // question = "{\"challenge_id\":99,\"type\":\"PZ\",\"question\":\"[\\\"haifa\\\",\\\"ahmad\\\",\\\"zeyad\\\",\\\"bandar\\\"]\",\"answer\":\"\",\"time\":5,\"field\":\"java\",\"rank\":0,\"coins\":1,\"challenge_title\":\"test puzzle2\",\"duration\":0,\"is_public\":false,\"user_id\":2,\"challenge_date\":\"2018-03-30 10:44:10.0\"}";
                                /*theClient.setCustomRoomData(roomId, question);
                                theClient.sendChat("started");
                                start.setVisibility(View.INVISIBLE);*/
                                RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.relativeLayoutgroup);
                                relativeLayout.setVisibility(View.INVISIBLE);
                            }
                        });
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
                            if (challnum == 4)
                            {    intent = new Intent(GroupChallenge.this, finalResult.class);
                          /*  theClient.setCustomUserData(username, MyCoins+"");
                                HashMap<String, Object> content= new HashMap<>();
                                content.put(username, MyCoins);
                                theClient.updateRoomProperties(roomId, content, null);
                            String[] users = liveRoomInfoEvent.getJoinedUsers();
                            int[] scores = new int[users.length];
                            String s = liveRoomInfoEvent.getProperties().toString();
                            for(int i=0 ; i<users.length ; i++)
                              /*  scores[i] = theClient.ge
                                HashMap<String, Object> content= new HashMap<>();
                                content.put(users[i], username);*/
                                //theClient.getLiveRoomInfo(roomId);
                            /*    theClient.updateRoomProperties(roomId, content, null);
                                theClient.lockProperties(content);*/


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
      //  log.v("NOOOOOTTTTTEEEEE",liveRoomInfoEvent.getCustomData()+" "+username);
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
                   // progressDialog = ProgressDialog.show(GroupChallenge.this, "", "Please wait...");

                    int selectedId = radio.getCheckedRadioButtonId();
                    if(selectedId!=-1) {
                        RadioButton radioButton = findViewById(selectedId);
                        if (answer.equals(radioButton.getText())) {
                            //ll.selectRank(uid,challNum,"pass","gg",3, field);
                            HashMap<String, Object> content= new HashMap<>();
                            content.put("ch"+(challnum+1), username);
                            //theClient.getLiveRoomInfo(roomId);
                            theClient.updateRoomProperties(roomId, content, null);
                            theClient.lockProperties(content);
                           // theClient.getLiveRoomInfo(roomId);
                           /* if(winner.equals("")) {
                                theClient.sendChat(username);
                                winner=username;
                            }*/
                        } else {
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
        log.v("NOOOOOTTTTTEEEEE",liveRoomInfoEvent.getProperties()+" WINNER"+username);
        MyCoins+=coins;
       // theClient.sendChat(++challnum+"");
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
            log.v("CHAAAAAAAAAL", fiveChallenges.toString());
            theClient.setCustomRoomData(roomId, fiveChallenges.get(challnum).toString());
            theClient.sendChat("started");
            start.setVisibility(View.INVISIBLE);
        }catch (Exception e){}

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




