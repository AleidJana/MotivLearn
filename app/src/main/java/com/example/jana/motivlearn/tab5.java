package com.example.jana.motivlearn;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jana.motivlearn.R;


public class tab5 extends Fragment {

    Button createChallenge ,sagestCallenge,sagestVedio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab5, container, false);
//        createChallenge = (Button) view.findViewById(R.id.button2);
//        createChallenge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(),createChallenge.class));
//
//            }
//        });
//        sagestCallenge = (Button) view.findViewById(R.id.button);
//        sagestCallenge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(),createChallenge.class));
//
//            }
//        });
//        sagestVedio = (Button) view.findViewById(R.id.vediobutton);
//        sagestVedio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(),suggestVedio.class));
//            }
//        });
        // Inflate the layout for this fragment
        return view;
    }


}
