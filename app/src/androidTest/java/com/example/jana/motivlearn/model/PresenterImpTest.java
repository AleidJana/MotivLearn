package com.example.jana.motivlearn.model;

import com.example.jana.motivlearn.Register;
import com.example.jana.motivlearn.presenter.RegisterPresenter;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by reemaibrahim on 20/04/2018 AD.
 */
public class PresenterImpTest {
    RegisterPresenter RegisterP;
    Register Register;
    @Test
    public void isExist() throws Exception {
        RegisterP=new PresenterImp(Register);
       JSONObject obj = new JSONObject(RegisterP.isExist("435202907@student.ksu.edu.sa"));
       String name= obj.get("u_name").toString();
       String expected ="Nouf Alsedairy";
       assertEquals(expected,name);


    }

}