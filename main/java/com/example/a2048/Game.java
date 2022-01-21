package com.example.a2048;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int[][] board;
    private ArrayList<Integer> emptySpots = new ArrayList<>();
    private boolean[][] alreadyFused = new boolean[4][4];

    public Game(){
        board = new int[4][4];
        for(int i = 0; i<4;i++){
            for(int j=0;j<4;j++){
                board[i][j] = -1;
                emptySpots.add(i*4+j);
                alreadyFused[i][j] = false;
            }
        }
    }

    public void random(){
        Log.i("Random","Got into random");
        Random ran = new Random();
        int position;
        int random;
        if(emptySpots.size() != 0) {
            position = emptySpots.get(ran.nextInt(emptySpots.size()));
        }else{
            position = 0;
        }
        random = ran.nextInt(100);
        if(random <= 20) {
            board[(int)position/4][position%4] = 4;
        }else{
            board[(int)position/4][position%4] = 2;
        }
    }

    public String[][] print(){
        String[][] retVal = new String[4][4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(board[i][j] == -1){
                    retVal[i][j] = "-1";
                }else{
                    retVal[i][j] = Integer.toString(board[i][j]);
                }
            }
        }
        return retVal;
    }

    public void update_empty(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(board[i][j] == -1){
                    if(!emptySpots.contains(new Integer(i*4+j))){
                        emptySpots.add(i*4+j);
                    }
                }else{
                    if(emptySpots.contains(new Integer(i*4+j))){
                        emptySpots.remove(new Integer(i*4+j));
                    }
                }
            }
        }
    }

    //Up 0, right 1, down 2, left 3
    public void move(int direction){
        Log.i("Moving","Got into move");
        switch(direction){
            case 0:
                while(!check_moves(0)){
                    for(int i = 1; i < 4;i++){
                        for(int j = 0;j < 4;j++){
                            if(board[i][j] != -1 && board[i-1][j] == -1){
                                board[i-1][j] = board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i-1][j] = alreadyFused[i][j];
                            }else if((board[i][j] != -1 && board[i][j] == board[i-1][j]) && !alreadyFused[i][j] && !alreadyFused[i-1][j]){
                                board[i-1][j] += board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i-1][j] = true;
                            }
                        }
                    }
                }
                break;
            case 1:
                while(!check_moves(1)) {
                    for (int i = 0; i < 4; i++) {
                        for (int j = 2; j >= 0; j--) {
                            if(board[i][j] != -1 && board[i][j+1] == -1){
                                board[i][j+1] = board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i][j+1] = alreadyFused[i][j];
                            }else if(board[i][j] != -1 && board[i][j] == board[i][j+1] && !alreadyFused[i][j] && !alreadyFused[i][j+1]){
                                board[i][j+1] += board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i][j+1] = true;
                            }
                        }
                    }
                }
                break;
            case 2:
                while(!check_moves(2)) {
                    for (int i = 2; i >= 0; i--) {
                        for (int j = 3; j >= 0; j--) {
                            if(board[i][j] != -1 && board[i+1][j] == -1){
                                board[i+1][j] = board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i+1][j] = alreadyFused[i][j];
                            }else if(board[i][j] != -1 && board[i][j] == board[i+1][j] && !alreadyFused[i][j] && !alreadyFused[i+1][j]){
                                board[i+1][j] += board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i+1][j] = true;
                            }
                        }
                    }
                }
                break;
            case 3:
                while(!check_moves(3)) {
                    for (int i = 0; i < 4; i++) {
                        for (int j = 1; j < 4; j++) {
                            if(board[i][j] != -1 && board[i][j-1] == -1){
                                board[i][j-1] = board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i][j-1] = alreadyFused[i][j];
                            }else if(board[i][j] != -1 && board[i][j] == board[i][j-1] && !alreadyFused[i][j] && !alreadyFused[i][j-1]){
                                board[i][j-1] += board[i][j];
                                board[i][j] = -1;
                                alreadyFused[i][j-1] = true;
                            }
                        }
                    }
                }
                break;
            default:
                Log.wtf("nah dude", "get the fuck out of here");
                break;
        }
        setBoolsToFalse();
        update_empty();
    }

    public boolean check_moves(int direction){
        switch(direction){
            case 0:
                for(int i = 1; i < 4;i++){
                    for(int j = 0;j < 4;j++){
                        if((board[i][j] != -1 && board[i-1][j] == -1) || ((board[i][j] != -1 && board[i][j] == board[i-1][j]) && !alreadyFused[i][j] && !alreadyFused[i-1][j])){
                            return false;
                        }
                    }
                }
                return true;
            case 1:
                for(int i = 0; i < 4;i++){
                    for(int j = 0;j < 3;j++){
                        if((board[i][j] != -1 && board[i][j+1] == -1) || ((board[i][j] != -1 && board[i][j] == board[i][j+1]) && !alreadyFused[i][j] && !alreadyFused[i][j+1])){
                            return false;
                        }
                    }
                }
                return true;
            case 2:
                for(int i = 0; i < 3;i++){
                    for(int j = 0;j < 4;j++){
                        if((board[i][j] != -1 && board[i+1][j] == -1) || ((board[i][j] != -1 && board[i][j] == board[i+1][j]) && !alreadyFused[i][j] && !alreadyFused[i+1][j])){
                            return false;
                        }
                    }
                }
                return true;
            case 3:
                for(int i = 0; i < 4;i++){
                    for(int j = 1;j < 4;j++){
                        if((board[i][j] != -1 && board[i][j-1] == -1) || ((board[i][j] != -1 && board[i][j] == board[i][j-1]) && !alreadyFused[i][j] && !alreadyFused[i][j-1])){
                            return false;
                        }
                    }
                }
                return true;
            default:
                Log.wtf("Oh hell no", "How the fuck?");
                return false;
        }
    }

    private void setBoolsToFalse(){
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                alreadyFused[i][j] = false;
            }
        }
    }
}