package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.displayPuzzle;
import com.example.jana.motivlearn.presenter.displayPuzzlePresenter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by reemaibrahim on 20/04/2018 AD.
 */
public class displayPuzzleImpTest2 {
    displayPuzzleImp pre;
    displayPuzzle displayPuzzle;
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
        int Expected  = pre.findBadge(5,"php");
        int Actual  = 26;
        assertEquals(Expected ,Actual );

    }

    @Test
    public void findBadgeTestcase17() throws Exception {

        pre = new displayPuzzleImp(displayPuzzle);
        int Expected  = pre.findBadge(10,"php");
        int Actual  = 27;
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
        int Expected  = pre.findBadge(13,"c#");
        int Actual  = 0;
        assertEquals(Expected ,Actual );

    }


}