package com.elin.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnLongClickListener;

/**
 * Created by elin on 5/22/2015.
 */
public class Game extends View {
    private Cell[][] board = null;
    int x = 3, y = 3;
    private int gameW;
    private int gameH;
    private boolean playerTurn = true;
    private int flagWin = 3;
    private Paint mPaint;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    invalidate();
                    break;
                case 1:
                    Toast.makeText(getContext(), "You Win!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "Computer Win!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "Draw!",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public Game(Context context) {
        super(context);

        mPaint = new Paint();
        this.mPaint.setARGB(255, 0, 0, 0);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(5);
        gameW = this.getWidth();
        gameH = this.getHeight();
        board = new Cell[y][x];
        int xSingleSquare = gameW / x;
        int ySingleSquare = gameH / y;
        for (int n = 0; n < y; n++) {
            for (int m = 0; m < x; m++) {
                board[n][m] = new Empty(xSingleSquare * m, ySingleSquare * n);
                //TODO:setlongclick
                /*board[n][m].setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    protected Object clone() throws CloneNotSupportedException {
                        return super.clone();
                    }
                });
                */
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].draw(canvas, getResources(), j, i,
                        (this.getWidth() + 3) / board.length,
                        this.getHeight() / board[0].length);
            }
        }
        int xLine = this.getWidth() / x;
        int yLine = this.getHeight() / y;
        for (int i = 0; i <= x; i++) {
            canvas.drawLine(xLine * i, 0, xLine * i, this.getHeight(), mPaint);
        }
        for (int i = 0; i <= y; i++) {
            canvas.drawLine(0, yLine * i, this.getWidth(), yLine * i, mPaint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int xCurr = (int) (event.getX() / (this.getWidth() / x));
        int yCurr = (int) (event.getY() / (this.getHeight() / y));

        boolean temp = ifDrawn(xCurr, yCurr);
        while(temp){
            Toast.makeText(getContext(), "Already placed, try other squares!"
                    ,Toast.LENGTH_SHORT).show();
            return super.onTouchEvent(event);
        }
        drawImage(xCurr, yCurr);
        playerTurn = false;
        checkWinStatus();
        if (playerTurn == false) {
            computerPlay(xCurr, yCurr);
            playerTurn = true;
            checkWinStatus();
        }
        return super.onTouchEvent(event);
    }

    private boolean ifDrawn(int x, int y){
        if(board[y][x] instanceof Empty)
            return false;
        else
            return true;
    }

    private void checkWinStatus(){
        if (ifWin("X")) {
            System.out.println("You Win");
            handler.sendMessage(Message.obtain(handler, 1));
            restartGame();
        } else if (ifWin("O")) {
            System.out.println("Computer Win");
            handler.sendMessage(Message.obtain(handler, 2));
            restartGame();
        } else if (ifFull()) {
            System.out.println("Draw");
            handler.sendMessage(Message.obtain(handler, 3));
            restartGame();
        } else {
            System.out.println("Continue");
        }
    }

    private void drawImage(int xCurr, int yCurr) {
        Cell cel;
        if (playerTurn) {
            cel = new Cross(board[yCurr][xCurr].x, board[yCurr][xCurr].y);
        } else {
            cel = new Circle(board[yCurr][xCurr].x, board[yCurr][xCurr].y);
        }
        board[yCurr][xCurr] = cel;
        handler.sendMessage(Message.obtain(handler, 0));
    }

    private void computerPlay(int xCurr, int yCurr){
        Cell celWin = ifLastConnect("X");
        if (celWin != null){
            drawImage(celWin.x, celWin.y);
        } else {
            Cell celBlock = ifLastConnect("O");
            if (celBlock != null){
                drawImage(celBlock.x, celBlock.y);
            } else {
                Cell celFork = ifSecondConnect("X");
                if (celFork != null) {
                    drawImage(celFork.x, celFork.y);
                } else {
                    Cell celBlockFork = ifSecondConnect("O");
                    if (celBlockFork != null) {
                        drawImage(celBlockFork.x, celBlockFork.y);
                    } else {
                        Cell celCenter = new Cross(x / 2, y / 2);
                        if (board[y / 2][x / 2] != null) {
                            drawImage(celCenter.x, celCenter.y);
                        } else {
                            Cell celOpposite = new Cross(x - xCurr, y - yCurr);
                            if (board[y - yCurr][x - xCurr] != null) {
                                drawImage(celOpposite.x, celOpposite.y);
                            } else {
                                Cell celCorner = ifCornerEmpty();
                                if (celCorner != null) {
                                    drawImage(celCorner.x, celCorner.y);
                                } else {
                                    Cell celSide = ifSideEmpty();
                                    if (celSide != null) {
                                        drawImage(celSide.x, celSide.y);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean ifWin(String whoPlay) {
        int flagCurr;

        //all rows
        for (int n = 0; n < board.length; n++) {
            flagCurr = 0;
            for (int m = 0; m < board[0].length; m++) {
                System.out.print(board[n][m]);
                if (board[n][m].toString().equals(whoPlay)) {
                    flagCurr++;
                }
            }
            System.out.println("");
            if (flagCurr >= getPlayerWin()) {
                return true;
            }
        }

        //all columns
        for (int m = 0; m < board[0].length; m++) {
            flagCurr = 0;
            for (int n = 0; n < board.length; n++) {
                System.out.print(board[n][m]);
                if (board[n][m].toString().equals(whoPlay)) {
                    flagCurr++;
                }
            }
            System.out.println("");
            if (flagCurr >= getPlayerWin()) {
                return true;
            }
        }

        //all diagonal lines
        flagCurr = 0;
        for (int m = 0; m < board[0].length; m++) {
            int n = m;
            if (board[n][m].toString().equals(whoPlay)) {
                flagCurr++;
            }
        }
        System.out.println("");
        if (flagCurr >= getPlayerWin()) {
            return true;
        }

        flagCurr = 0;
        for (int m = 0; m < board[0].length; m++) {
            int n = board[0].length - m - 1;
            if (board[n][m].toString().equals(whoPlay)) {
                flagCurr++;
            }
        }
        System.out.println("");
        if (flagCurr >= getPlayerWin()) {
            return true;
        }

        return false;
    }

    public Cell ifLastConnect(String whoPlay){
        int flagCurr;
        Cell cel;

        //all rows
        int xTemp = 0, yTemp = 0;
        for (int n = 0; n < board.length; n++) {
            flagCurr = 0;
            for (int m = 0; m < board[0].length; m++) {
                System.out.print(board[n][m]);
                if (board[n][m].toString().equals(whoPlay)) {
                    flagCurr++;
                } else if (board[n][m] instanceof Empty){
                    xTemp = m;
                    yTemp = n;
                }
            }
            System.out.println("");
            if (flagCurr == getPlayerWin() - 1) {
                cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
                cel.x = xTemp;
                cel.y = yTemp;
                return cel;
            }
        }

        //all columns
        for (int m = 0; m < board[0].length; m++) {
            flagCurr = 0;
            for (int n = 0; n < board.length; n++) {
                System.out.print(board[n][m]);
                if (board[n][m].toString().equals(whoPlay)) {
                    flagCurr++;
                } else if(board[n][m] instanceof Empty){
                    xTemp = m;
                    yTemp = n;
                }
            }
            System.out.println("");
            if (flagCurr == getPlayerWin() - 1) {
                cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
                cel.x = xTemp;
                cel.y = yTemp;
                return cel;
            }
        }

        //all diagonal lines
        flagCurr = 0;
        for (int m = 0; m < board[0].length; m++) {
            int n = m;
            if (board[n][m].toString().equals(whoPlay)) {
                flagCurr++;
            } else if(board[n][m] instanceof Empty){
                xTemp = m;
                yTemp = n;
            }
        }
        System.out.println("");
        if (flagCurr == getPlayerWin() - 1) {
            cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
            cel.x = xTemp;
            cel.y = yTemp;
            return cel;
        }

        flagCurr = 0;
        for (int m = 0; m < board[0].length; m++) {
            int n = board[0].length - m - 1;
            if (board[n][m].toString().equals(whoPlay)) {
                flagCurr++;
            } else if(board[n][m] instanceof Empty){
                xTemp = m;
                yTemp = n;
            }
        }
        System.out.println("");
        if (flagCurr == getPlayerWin() - 1) {
            cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
            cel.x = xTemp;
            cel.y = yTemp;
            return cel;
        }

        return null;
    }

    public Cell ifSecondConnect(String whoPlay){
        int flagCurr;
        Cell cel;

        //all rows
        int xTemp = 0, yTemp = 0;
        for (int n = 0; n < board.length; n++) {
            flagCurr = 0;
            for (int m = 0; m < board[0].length; m++) {
                System.out.print(board[n][m]);
                if (board[n][m].toString().equals(whoPlay)) {
                    flagCurr++;
                } else if (board[n][m] instanceof Empty){
                    xTemp = m;
                    yTemp = n;
                }
            }
            System.out.println("");
            if (flagCurr == getPlayerWin() - 2) {
                cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
                cel.x = xTemp;
                cel.y = yTemp;
                return cel;
            }
        }

        //all columns
        for (int m = 0; m < board[0].length; m++) {
            flagCurr = 0;
            for (int n = 0; n < board.length; n++) {
                System.out.print(board[n][m]);
                if (board[n][m].toString().equals(whoPlay)) {
                    flagCurr++;
                } else if(board[n][m] instanceof Empty){
                    xTemp = m;
                    yTemp = n;
                }
            }
            System.out.println("");
            if (flagCurr == getPlayerWin() - 2) {
                cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
                cel.x = xTemp;
                cel.y = yTemp;
                return cel;
            }
        }

        //all diagonal lines
        flagCurr = 0;
        for (int m = 0; m < board[0].length; m++) {
            int n = m;
            if (board[n][m].toString().equals(whoPlay)) {
                flagCurr++;
            } else if(board[n][m] instanceof Empty){
                xTemp = m;
                yTemp = n;
            }
        }
        System.out.println("");
        if (flagCurr == getPlayerWin() - 2) {
            cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
            cel.x = xTemp;
            cel.y = yTemp;
            return cel;
        }

        flagCurr = 0;
        for (int m = 0; m < board[0].length; m++) {
            int n = board[0].length - m - 1;
            if (board[n][m].toString().equals(whoPlay)) {
                flagCurr++;
            } else if(board[n][m] instanceof Empty){
                xTemp = m;
                yTemp = n;
            }
        }
        System.out.println("");
        if (flagCurr == getPlayerWin() - 2) {
            cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
            cel.x = xTemp;
            cel.y = yTemp;
            return cel;
        }

        return null;
    }

    public Cell ifCornerEmpty(){
        int flagCurr;
        Cell cel;

        int xTemp = 0, yTemp = 0;
        for (int n = 0; n < board.length; n += board.length - 1) {
            flagCurr = 0;
            for (int m = 0; m < board[0].length; m += board[0].length - 1) {
                System.out.print(board[n][m]);
                if (board[n][m] instanceof Empty){
                    flagCurr = 1;
                    xTemp = m;
                    yTemp = n;
                }
            }
            System.out.println("");
            if (flagCurr == 1) {
                cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
                cel.x = xTemp;
                cel.y = yTemp;
                return cel;
            }
        }
        return null;
    }

    public Cell ifSideEmpty(){
        int flagCurr;
        Cell cel;

        int xTemp = 0, yTemp = 0;
        for (int n = 1; n < board.length - 1; n++) {
            flagCurr = 0;
            for (int m = 1; m < board[0].length - 1; m++) {
                System.out.print(board[n][m]);
                if (board[n][m] instanceof Empty){
                    flagCurr = 1;
                    xTemp = m;
                    yTemp = n;
                }
            }
            System.out.println("");
            if (flagCurr == 1) {
                cel = new Cross(board[yTemp][xTemp].x, board[yTemp][xTemp].y);
                cel.x = xTemp;
                cel.y = yTemp;
                return cel;
            }
        }
        return null;
    }

    public boolean ifFull() {
        for (int n = 0; n < board.length; n++) {
            for (int m = 0; m < board[0].length; m++) {
                if (board[n][m] instanceof Empty)
                    return false;
            }
        }
        return true;
    }

    private void restartGame() {
        playerTurn = true;
        board = new Cell[y][x];
        int xSingleSquare = gameW / x;
        int ySingleSquare = gameH / y;
        for (int n = 0; n < y; n++) {
            for (int m = 0; m < x; m++) {
                board[n][m] = new Empty(xSingleSquare * m, ySingleSquare * n);
            }
        }
        handler.sendMessage(Message.obtain(handler, 0));
    }

    private void resizeGame(int s) {
        x = s;
        y = s;
        playerTurn = true;
        board = new Cell[y][x];
        int xSingleSquare = gameW / x;
        int ySingleSquare = gameH / y;
        for (int n = 0; n < y; n++) {
            for (int m = 0; m < x; m++) {
                board[n][m] = new Empty(xSingleSquare * m, ySingleSquare * n);
            }
        }
        handler.sendMessage(Message.obtain(handler, 0));
    }

    public int getPlayerWin() {
        return flagWin;
    }
}


