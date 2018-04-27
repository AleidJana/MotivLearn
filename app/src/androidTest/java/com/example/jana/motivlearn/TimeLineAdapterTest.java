package com.example.jana.motivlearn;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by reemaibrahim on 20/04/2018 AD.
 */
public class TimeLineAdapterTest {
    TimeLineAdapter TimeLineAdapter;
    @Test
    public void formatTimeTestcase1() throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Date postDate = dateFormat.parse("2018-04-26 11:02:11fh");
        Calendar c0 = Calendar.getInstance();
        c0.setTime(postDate);
        //add 10 hours (th difference between the local time and database time)
        c0.add(Calendar.HOUR_OF_DAY, -2);
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime(c0.toString());
        String expected = "2h";
        assertEquals(expected,actual);

    }
    @Test
    public void formatTimeTestcase2() throws Exception {
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime("2018-04-19 4:02:11fh");
        String expected = "2d";
        assertEquals(expected,actual);

    }

    @Test
    public void formatTimeTestcase3() throws Exception {
        TimeLineAdapter= new TimeLineAdapter();
        String actual =TimeLineAdapter.formatTime("2018-04-20 4:02:11fh");
        String expected = "1d";
        assertEquals(expected,actual);

    }

}
