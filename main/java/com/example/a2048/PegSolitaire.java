package com.example.a2048;


import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class PegSolitaire extends AppCompatActivity {

    private ImageView [][]images = new ImageView[7][7];
    private int originalId = 0;
    private int x1 = -1;
    private int y1 = -1;
    private int x2 = -1;
    private int y2 = -1;
    private int originalX = -1;
    private int originalY = -1;
    private boolean found1 = false;
    private boolean found2 = false;
    private boolean first_drag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peg_game);
        PegGame game = new PegGame();

        GridLayout grid = findViewById(R.id.grid);
        for(int i = 0; i < 7;i++){
            for(int j = 0; j < 7;j++){
                images[i][j] = (ImageView) grid.getChildAt(i*7+j);
                ImageView image = (ImageView) grid.getChildAt(i*7+j);
                if (game.isValid(i,j)){
                    if(game.isPegged(i,j)){
                        image.setImageResource(R.drawable.pan_blue_circle);
                        image.setTag("blue");
                    }else{
                        image.setImageResource(R.drawable.daco_4431349);
                        image.setTag("yellow");
                    }
                }else {
                    image.setImageResource(android.R.color.transparent);
                    image.setTag("no touch");
                }
                image.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(image);
                            image.startDrag(null, shadowBuilder, image, 0);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                image.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View view, DragEvent dragEvent) {
                        ImageView original = (ImageView) dragEvent.getLocalState();
                        Rect impact;
                        if(!original.getTag().equals("no touch")){
                            switch (dragEvent.getAction()) {
                                case DragEvent.ACTION_DRAG_STARTED:
                                    if(first_drag) {
                                        originalX = (int)dragEvent.getX();
                                        originalY = (int)dragEvent.getY();
                                        if (original.getTag() == "yellow") {
                                            originalId = R.drawable.daco_4431349;
                                        } else if (original.getTag() == "blue") {
                                            originalId = R.drawable.pan_blue_circle;
                                        }
                                        impact = new Rect();
                                        for (int i = 0; i < 7 && !found1; i++) {
                                            for (int j = 0; j < 7 && !found1; j++) {
                                                images[i][j].getHitRect(impact);
                                                if (impact.contains((int) dragEvent.getX(), (int) dragEvent.getY())) {
                                                    found1 = true;
                                                    x1 = i;
                                                    y1 = j;
                                                }
                                            }
                                        }
                                        first_drag = false;
                                        original.setImageResource(android.R.color.transparent);
                                    }
                                    break;
                                case DragEvent.ACTION_DROP:
                                    //Dropped image will be var image

                                    first_drag = true;
                                    for (int i = 0; i < 7 && !found2; i++) {
                                        for (int j = 0; j < 7 && !found2; j++) {
                                            if(images[i][j].getId() == image.getId()){
                                                x2 = i;
                                                y2 = j;
                                                found2 = true;
                                            }
                                        }
                                    }
                                    if (found1 && found2) {
                                        if (game.move(x1, y1, x2, y2)) {
                                            images[x1][y1].setImageResource(R.drawable.daco_4431349);
                                            images[x1][y1].setTag("yellow");
                                            images[x2][y2].setImageResource(R.drawable.pan_blue_circle);
                                            images[x2][y2].setTag("blue");
                                            if(x1 == x2){
                                                images[x1][(y1+y2)/2].setImageResource(R.drawable.daco_4431349);
                                                images[x1][(y1+y2)/2].setTag("yellow");
                                            }else{
                                                images[(x1+x2)/2][y1].setImageResource(R.drawable.daco_4431349);
                                                images[(x1+x2)/2][y1].setTag("yellow");
                                            }
                                            if(game.checkWin()){
                                                Toast.makeText(getApplicationContext(),"You won bitch", Toast.LENGTH_LONG).show();
                                                //prompt that the user won and go back to a main menu or something
                                            }
                                        }else{
                                            original.setImageResource(originalId);
                                        }
                                    }else{
                                        original.setImageResource(originalId);
                                    }
                                    found1 = false;
                                    found2 = false;
                                    break;
                            }
                        }
                        return true;
                    }
                });
            }
        }
    }
}