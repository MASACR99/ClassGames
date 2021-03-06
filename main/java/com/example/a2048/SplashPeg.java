package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashPeg extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peg_splash); //activity_main
        ImageView letterO = (ImageView) findViewById(R.id.letter01);
        Animation animationO = AnimationUtils.loadAnimation(this,R.anim.intro_peg);
        animationO.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation){

            }

            @Override
            public void onAnimationRepeat(Animation animation){}

            @Override
            public void onAnimationEnd(Animation animation){
                try {
                    Thread.sleep(500);
                }catch(Exception ex){

                }
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),PegSolitaire.class);
                startActivity(intent);
            }
        });
        letterO.startAnimation(animationO);
    }
}
