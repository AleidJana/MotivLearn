package com.example.jana.motivlearn;

import com.example.jana.motivlearn.displayPuzzle;
import com.example.jana.motivlearn.model.displayPuzzleImp;
import com.example.jana.motivlearn.presenter.displayPuzzlePresenter;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by reemaibrahim on 20/04/2018 AD.
 */
public class MLJunitTest {
    displayPuzzleImp pre;
    displayPuzzle displayPuzzle;
    TimeLineAdapter TimeLineAdapter;
    @Test
    public void findBadgeTestcase1() throws Exception {

            pre = new displayPuzzleImp(displayPuzzle);
            int Expected  = pre.findBadge(5,"java");
            int Actual  = 17;
            assertEquals(Expected ,Actual );

    }
    @Test
    public void findBadgeTestcase2() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(10,"java");
        int Actual  = 18;
        assertEquals(Expected ,Actual );

    }
    @Test
    public void findBadgeTestcase3() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(20,"java");
        int Actual  = 19;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase4() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(5,"c");
        int Actual  = 1;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase5() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(10,"c");
        int Actual  = 2;
        assertEquals(Expected ,Actual );

    }
    @Test
    public void findBadgeTestcase6() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(20,"c");
        int Actual  = 3;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase7() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(5,"html");
        int Actual  = 23;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase8() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(10,"html");
        int Actual  = 24;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase9() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(20,"html");
        int Actual  = 25;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase10() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(5,"css");
        int Actual  = 14;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase11() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(10,"css");
        int Actual  = 15;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase12() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(20,"css");
        int Actual  = 16;
        assertEquals(Expected ,Actual );

    }
    @Test
    public void findBadgeTestcase13() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(5,"javascript");
        int Actual  = 20;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase14() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(10,"javascript");
        int Actual  = 21;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase15() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(20,"javascript");
        int Actual  = 22;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase16() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Actual  = pre.findBadge(5,"php");
        int Expected  = 26;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase17() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Actual  = pre.findBadge(10,"php");
        int Expected  = 27;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase18() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(20,"php");
        int Actual  = 28;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase19() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(50,"php");
        int Actual  = 12;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase20() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(2,"php");
        int Actual  = 0;
        assertEquals(Expected ,Actual );

    }


    @Test
    public void findBadgeTestcase21() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(13,"c#");
        int Actual  = 0;
        assertEquals(Expected ,Actual );

    }
    @Test
    public void formatTimeTestcase1() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date currentDate = dateFormat.parse(c);
        //  Date postDate = dateFormat.parse("2018-04-26 11:02:11fh");
        Calendar c0 = Calendar.getInstance();
        c0.setTime(currentDate);
        //add 10 hours (th difference between the local time and database time)
        c0.add(Calendar.HOUR_OF_DAY, -10);
        int year = c0.get(Calendar.YEAR);
        int month = c0.get(Calendar.MONTH) + 1;
        int day = c0.get(Calendar.DAY_OF_MONTH);
        int hour = c0.get(Calendar.HOUR_OF_DAY);
        int minute = c0.get(Calendar.MINUTE);
        int second = c0.get(Calendar.SECOND);
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"hh");
        String expected = "0s";
        assertEquals(expected,actual);

    }

    @Test
    public void formatTimeTestcase2() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date currentDate = dateFormat.parse(c);
        //  Date postDate = dateFormat.parse("2018-04-26 11:02:11fh");
        Calendar c0 = Calendar.getInstance();
        c0.setTime(currentDate);
        //add 10 hours (th difference between the local time and database time)
        c0.add(Calendar.HOUR_OF_DAY, -10);
        c0.add(Calendar.MINUTE, -30);
        int year = c0.get(Calendar.YEAR);
        int month = c0.get(Calendar.MONTH) + 1;
        int day = c0.get(Calendar.DAY_OF_MONTH);
        int hour = c0.get(Calendar.HOUR_OF_DAY);
        int minute = c0.get(Calendar.MINUTE);
        int second = c0.get(Calendar.SECOND);
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"hh");
        String expected = "30m";
        assertEquals(expected,actual);

    }
    @Test
    public void formatTimeTestcase3() throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date currentDate = dateFormat.parse(c);
      //  Date postDate = dateFormat.parse("2018-04-26 11:02:11fh");
        Calendar c0 = Calendar.getInstance();
        c0.setTime(currentDate);
        //add 10 hours (th difference between the local time and database time)
        c0.add(Calendar.HOUR_OF_DAY, -12);
        int year = c0.get(Calendar.YEAR);
        int month = c0.get(Calendar.MONTH) + 1;
        int day = c0.get(Calendar.DAY_OF_MONTH);
        int hour = c0.get(Calendar.HOUR_OF_DAY);
        int minute = c0.get(Calendar.MINUTE);
        int second = c0.get(Calendar.SECOND);
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"hh");
        String expected = "2h";
        assertEquals(expected,actual);

    }
    @Test
    public void formatTimeTestcase4() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date currentDate = dateFormat.parse(c);
        //  Date postDate = dateFormat.parse("2018-04-26 11:02:11fh");
        Calendar c0 = Calendar.getInstance();
        c0.setTime(currentDate);
        //add 10 hours (th difference between the local time and database time)
        c0.add(Calendar.DATE, -2);
        int year = c0.get(Calendar.YEAR);
        int month = c0.get(Calendar.MONTH) + 1;
        int day = c0.get(Calendar.DAY_OF_MONTH);
        int hour = c0.get(Calendar.HOUR_OF_DAY);
        int minute = c0.get(Calendar.MINUTE);
        int second = c0.get(Calendar.SECOND);
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"hh");
        String expected = "2d";
        assertEquals(expected,actual);

    }

    @Test
    public void formatTimeTestcase5() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date currentDate = dateFormat.parse(c);
        //  Date postDate = dateFormat.parse("2018-04-26 11:02:11fh");
        Calendar c0 = Calendar.getInstance();
        c0.setTime(currentDate);
        //add 10 hours (th difference between the local time and database time)
        c0.add(Calendar.DATE, -10);
        int year = c0.get(Calendar.YEAR);
        int month = c0.get(Calendar.MONTH) + 1;
        int day = c0.get(Calendar.DAY_OF_MONTH);
        int hour = c0.get(Calendar.HOUR_OF_DAY);
        int minute = c0.get(Calendar.MINUTE);
        int second = c0.get(Calendar.SECOND);
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"hh");
        String expected = (year+"-"+month+"-"+day).trim();
        assertEquals(expected,actual.trim());

    }



}