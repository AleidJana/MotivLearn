package com.example.jana.motivlearn;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by reemaibrahim on 20/04/2018 AD.
 */
// test the lunch of the login page succeefully
public class LoginTest {

    @Rule
    public ActivityTestRule<Login> TestActRule = new ActivityTestRule<Login>(Login.class);
    private Login TestAct=null;
    @Before
    public void setUp() throws Exception {
        //call before you exucat the test
        TestAct=TestActRule.getActivity();
    }

    @Test
    public void test(){
        View view = TestAct.findViewById(R.id.textView4);
        assertNotNull(view);

    }
    @After
    public void tearDown() throws Exception {
        //call after you exucat the test
        TestAct=null;
    }



}