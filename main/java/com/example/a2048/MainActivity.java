package com.example.a2048;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        lv=(ListView) findViewById(R.id.listView);
        menu0();

    }

    private void menu0(){
        String[] value = {"Choose game","See scores","Settings"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,android.R.id.text1,value);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                switch(position){
                    case 0:
                        //Change adapter for one to choose between 2048 or Peg solitaire
                        String[] value = new String[]{"2048","Peg solitaire","Go back"};
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,android.R.id.text1,value);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                                    long arg3) {
                                switch(position){
                                    case 0:
                                        //load 2048 (aka SplashTFE)
                                        Intent intent1 = new Intent();
                                        intent1.setClass(getApplicationContext(),SplashTFE.class);
                                        startActivity(intent1);
                                        break;
                                    case 1:
                                        //load PegSolitaire (aka SplashPeg)
                                        Intent intent2 = new Intent();
                                        intent2.setClass(getApplicationContext(),SplashPeg.class);
                                        startActivity(intent2);
                                        break;
                                    default:
                                        //Go back
                                        menu0(); //This method is sketchy AF, im probably gonna end up redoing it
                                        break;
                                }
                            }
                        });
                        break;
                    case 1:
                        //Will load a scores menu, probably will just have global scores
                        break;
                    case 2:
                        //Idk this is just filler material
                        break;
                    default:
                        //Something went either completely fucking sideways or idk what is going on
                        break;
                }
            }

        });
    }
}