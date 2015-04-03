package com.shengkunwei.tictactoe.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ShengkunWei on 4/1/15.
 */
public class TicTacToeView extends View{
    private static final String TAG = TicTacToeView.class.getSimpleName();

    private final String KEY_CELLS = "cells";
    private final String KEY_GAME_GOING_ON = "game_going_on";

    private int mWidth;
    private int mHeight;
    private int mSize;

    private float DENSITY;

    private final int N = 3;
    private final int GRID_THICK = 7;
    private int mGridThick;

    private final int ROUND_R = 5;
    private int mRoundR;

    private int mUnitSize;

    private int START_MARGIN_X;
    private int START_MARGIN_Y;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintMark = new Paint(Paint.ANTI_ALIAS_FLAG);


    Cell[][] mCells = new Cell[N][N];
    private Rect[][] mGridRects = new Rect[N][N];

    private AIPlayer mAIPlayer;
    boolean mGameGoingOn = false;

    private int[] mWinPatterns = {0b111000000, 0b000111000, 0b000000111,
                                  0b100100100, 0b010010010, 0b001001001,
                                  0b100010001, 0b001010100};

    public TicTacToeView(Context context) {
        this(context, null, 0);
    }

    public TicTacToeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TicTacToeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGame(Cell.STATE_COMPUTER);
    }


    public void initGame(int whoFirst){
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[i].length; j++) {
                mCells[i][j] = new Cell(i, j);
                mCells[i][j].state = Cell.STATE_EMPTY;
            }
        }
        mAIPlayer = new AIPlayer(this, 4);
        if(whoFirst == Cell.STATE_COMPUTER){
            computerNextStep();
        }
        invalidate();
        mGameGoingOn = true;
    }

    public void saveGame(Bundle outState){
        int[] states = new int[N * N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                states[i * N + j] = mCells[i][j].state;
            }
        }
        outState.putIntArray(KEY_CELLS, states);
        outState.putBoolean(KEY_GAME_GOING_ON, mGameGoingOn);
    }

    public void restoreGame(Bundle savedInstanceState){
        int[] cells = savedInstanceState.getIntArray(KEY_CELLS);
        for (int i = 0; i < cells.length; i++) {
            int row = i / N;
            int col = i - row * N;
            mCells[row][col].state = cells[i];
            mCells[row][col].row = row;
            mCells[row][col].col = col;
        }
        mGameGoingOn = savedInstanceState.getBoolean(KEY_GAME_GOING_ON);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DENSITY = getResources().getDisplayMetrics().density;
        mGridThick = (int) (GRID_THICK * DENSITY);
        mRoundR = (int) (ROUND_R * DENSITY);

        mWidth = getWidth();
        mHeight = getHeight();

        mSize = Math.min(mWidth, mHeight);

        if(mWidth > mHeight){
            START_MARGIN_X = (Math.max(mWidth, mHeight) - mSize) / 2;
            START_MARGIN_Y = 0;
        } else {
            START_MARGIN_X = 0;
            START_MARGIN_Y = (Math.max(mWidth, mHeight) - mSize) / 2;
        }

        mUnitSize = (mSize - mGridThick * (N + 1)) / N;

        DENSITY = getResources().getDisplayMetrics().density;

        mGridThick = (int) (GRID_THICK * DENSITY);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        setBackgroundColor(Color.LTGRAY);

        //draw grids
        mPaint.setColor(Color.DKGRAY);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int x = (j + 1) * mGridThick + j * mUnitSize + START_MARGIN_X;
                int y = (i + 1) * mGridThick + i * mUnitSize + START_MARGIN_Y;
                mGridRects[i][j] = new Rect(x, y, x + mUnitSize, y + mUnitSize);
                canvas.drawRoundRect(new RectF(mGridRects[i][j]), mRoundR, mRoundR, mPaint);
            }
        }


        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[i].length; j++) {
                if(mCells[i][j] == null) {
                    continue;
                }
                Rect r;
                switch (mCells[i][j].state){
                    case Cell.STATE_PLAYER1:
                        r = mGridRects[i][j];
                        mPaintMark.setColor(Color.RED);
                        mPaintMark.setPathEffect(new CornerPathEffect(20));
                        mPaintMark.setStyle(Paint.Style.STROKE);
                        mPaintMark.setStrokeWidth(40);

                        Path p = new Path();
                        final int MARGIN = 50;
                        p.moveTo(r.left + MARGIN, r.top + MARGIN);
                        p.lineTo(r.right - MARGIN, r.bottom - MARGIN);
                        p.moveTo(r.left + MARGIN, r.bottom - MARGIN);
                        p.lineTo(r.right - MARGIN, r.top + MARGIN);

                        p.moveTo(r.left + MARGIN, r.bottom - MARGIN);
                        p.lineTo(r.right - MARGIN, r.top + MARGIN);

                        canvas.drawPath(p, mPaintMark);
                        break;
                    case Cell.STATE_COMPUTER:
                        r = mGridRects[i][j];
                        mPaintMark.setColor(Color.YELLOW);
                        mPaintMark.setPathEffect(new CornerPathEffect(20));
                        mPaintMark.setStyle(Paint.Style.STROKE);
                        mPaintMark.setStrokeWidth(30);

                        Path p2 = new Path();
                        p2.addCircle(r.left + (r.right - r.left) / 2, r.top + (r.bottom - r.top) / 2,
                                (r.right - r.left) / 3, Path.Direction.CW);

                        canvas.drawPath(p2, mPaintMark);
                        break;
                }
            }

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!mGameGoingOn){
            return false;
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int[] idx = getIndexOfTouchedGrid(event.getX(), event.getY());

            if(idx == null){
                return true;
            } else {
                int state = mCells[idx[0]][idx[1]].state;
                if(state == Cell.STATE_EMPTY){
                    mCells[idx[0]][idx[1]].state = Cell.STATE_PLAYER1;
                    invalidate();
                    if(!checkResult(Cell.STATE_PLAYER1)){
                        computerNextStep();
                    }
                }

            }
        }
        return true;

    }

    private boolean checkResult(int who){
        int pattern = 0;
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[i].length; j++) {
                pattern <<= 1;
                if(mCells[i][j].state == who){
                    pattern += 1;
                }
            }
        }
        if(doesMatchWinPatterns(pattern)){
            showFinishPrompt(who);
            return true;
        } else if(mAIPlayer.getEmptyCells().isEmpty()){
            showFinishPrompt(Cell.STATE_EMPTY);
            return true;
        }
        return false;
    }

    private boolean doesMatchWinPatterns(int pattern){
        for(int p : mWinPatterns){
            if((p & pattern) == p){
                return true;
            }
        }
        return false;
    }

    void showFinishPrompt(int winner){
        mGameGoingOn = false;
        String title = "";
        switch (winner){
            case Cell.STATE_PLAYER1:
                title = "You won!";

                break;
            case Cell.STATE_COMPUTER:
                title = "You lost!";
                break;

            case Cell.STATE_EMPTY:
                title = "Draw!";
                break;
        }
        Toast.makeText(getContext(), title, Toast.LENGTH_LONG).show();

    }

    private void computerNextStep(){
        int[] aiNextStep = mAIPlayer.getNextStep();

        if (aiNextStep[0] != -1) {
            mCells[aiNextStep[0]][aiNextStep[1]].state = Cell.STATE_COMPUTER;
            invalidate();
            checkResult(Cell.STATE_COMPUTER);
        } else {
            showFinishPrompt(Cell.STATE_EMPTY);
        }
    }

    private int[] getIndexOfTouchedGrid(float x, float y){
        for (int i = 0; i < mGridRects.length; i++) {
            for (int j = 0; j < mGridRects[i].length; j++) {
                if (mGridRects[i][j].contains((int)x, (int)y)){
                    return new int[]{i, j};
                }
            }

        }
        return null;
    }

}
