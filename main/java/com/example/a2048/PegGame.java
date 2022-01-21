package com.example.a2048;

public class PegGame {
    private com.example.pegsolitaire.Square[][] board;
    private boolean[][] tableBool = {
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true},
            {false, false, true, true, true, false, false},
            {false, false, true, true, true, false, false}
    };
    private int centerX = 3;
    private int centerY = 3;

    public PegGame(){
        board = new com.example.pegsolitaire.Square[tableBool.length][tableBool[0].length];
        for(int i = 0; i< board.length;i++){
            for(int j=0;j< board[0].length;j++){
                board[i][j] = new com.example.pegsolitaire.Square(tableBool[i][j], tableBool[i][j]);
            }
        }
        board[centerX][centerY].setPegged(false);
    }

    public boolean move(int x1, int y1, int x2, int y2){
        if(!board[x2][y2].isPegged() && board[x1][y1].isPegged() && board[x2][y2].isValid()) {
            if (x1 == x2) {
                if (Math.abs(y2 - y1) == 2 && board[x1][(y1+y2)/2].isPegged()) {
                    board[x1][y1].setPegged(false);
                    board[x1][(y1+y2)/2].setPegged(false);
                    board[x2][y2].setPegged(true);
                    return true;
                }else{
                    return false;
                }
            } else if (y1 == y2) {
                if (Math.abs(x2 - x1) == 2 && board[(x1+x2)/2][y1].isPegged()) {
                    board[x1][y1].setPegged(false);
                    board[(x1+x2)/2][y1].setPegged(false);
                    board[x2][y2].setPegged(true);
                    return true;
                }else{
                    return false;
                }
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean checkWin(){
        int pegs = 0;
        for(int i = 0; i<board.length && pegs < 2;i++){
            for(int j = 0; j<board[0].length && pegs < 2;j++){
                if(board[i][j].isPegged()){
                    pegs++;
                }
            }
        }
        if(pegs > 1){
            return false;
        }else{
            return true;
        }
    }

    public void removePeg(int x, int y){
        board[x][y].setPegged(false);
    }

    public void addPeg(int x, int y){
        board[x][y].setPegged(true);
    }

    public boolean isPegged(int x, int y){
        return board[x][y].isPegged();
    }

    public boolean isValid(int x, int y){
        return board[x][y].isValid();
    }

    public int getLengthX(){
        return board.length;
    }

    public int getLengthY(){
        return board[0].length;
    }
}
