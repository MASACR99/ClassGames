package com.example.a2048;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameStarter extends AppCompatActivity {
    private GestureDetectorCompat mDetector;
    private final String DEBUG_TAG = "Oof";
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();
        game.random();
        GridLayout grid = findViewById(R.id.grid);
        View tv;
            for (int i = 0; i < grid.getRowCount() * grid.getColumnCount(); i++) {
                tv = grid.getChildAt(i);
                tv.setBackground(getDrawable(R.drawable.border));
            }
            mDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    float angle = (float) Math.toDegrees(Math.atan2(e1.getY() - e2.getY(), e1.getX() - e2.getX()));
                    boolean valid_move = false;
                    //Must check if move is possible
                    if (angle > -45 && angle <= 45) {
                        Log.d(DEBUG_TAG, "Right to Left swipe performed");
                        if (!game.check_moves(3)) {
                            game.move(3);
                            valid_move = true;
                        } else {
                            Log.d("Yeh: ", "No moves on that direction");
                            valid_move = false;
                        }
                    }
                    if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) {
                        Log.d(DEBUG_TAG, "Left to Right swipe performed");
                        if (!game.check_moves(1)) {
                            game.move(1);
                            valid_move = true;
                        } else {
                            Log.d("Yeh: ", "No moves on that direction");
                            valid_move = false;
                        }
                    }
                    if (angle < -45 && angle >= -135) {
                        Log.d(DEBUG_TAG, "Up to Down swipe performed");
                        if (!game.check_moves(2)) {
                            game.move(2);
                            valid_move = true;
                        } else {
                            Log.d("Yeh: ", "No moves on that direction");
                            valid_move = false;
                        }
                    }
                    if (angle > 45 && angle <= 135) {
                        Log.d(DEBUG_TAG, "Down to Up swipe performed");
                        if (!game.check_moves(0)) {
                            game.move(0);
                            valid_move = true;
                        } else {
                            Log.d("Yeh: ", "No moves on that direction");
                            valid_move = false;
                        }
                    }
                    if (valid_move) {
                        game.random();
                        printValues();
                    }

                    return valid_move;
                }
            });
        printValues();
    }

    private void printValues(){
        String[][] newVals = game.print();
        TextView tv;
        for(int i=0;i< newVals.length;i++){
            for(int j=0;j< newVals[0].length;j++){
                String cellName = "cell"+ i +""+ j;
                int id = getResources().getIdentifier(cellName,"id",getApplicationContext().getPackageName());
                tv = findViewById(id);
                if(newVals[i][j].equals(Integer.toString(-1))){
                    tv.setText("                ");
                }else{
                    tv.setText(newVals[i][j]);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return mDetector.onTouchEvent(motionEvent);
    }
}
