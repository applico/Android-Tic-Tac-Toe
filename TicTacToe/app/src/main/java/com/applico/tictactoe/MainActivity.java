package com.applico.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by johntzan on 5/22/15.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private Game mGame;
    private Button[][] mTicTacToeButtons;
    private Button mNew_Game;
    private TextView mGameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize everything needed */
        this.mGame = new Game(this);
        this.mTicTacToeButtons = new Button[Game.NUM_ROWS][Game.NUM_COLUMNS];

        mGameState = (TextView) findViewById(R.id.game_state);

        mTicTacToeButtons[0][0] = (Button) findViewById(R.id.button00);
        mTicTacToeButtons[0][1] = (Button) findViewById(R.id.button01);
        mTicTacToeButtons[0][2] = (Button) findViewById(R.id.button02);
        mTicTacToeButtons[1][0] = (Button) findViewById(R.id.button10);
        mTicTacToeButtons[1][1] = (Button) findViewById(R.id.button11);
        mTicTacToeButtons[1][2] = (Button) findViewById(R.id.button12);
        mTicTacToeButtons[2][0] = (Button) findViewById(R.id.button20);
        mTicTacToeButtons[2][1] = (Button) findViewById(R.id.button21);
        mTicTacToeButtons[2][2] = (Button) findViewById(R.id.button22);

        mNew_Game = (Button) findViewById(R.id.newgame_btn);
        mNew_Game.setOnClickListener(this);

        /* give all TicTacToe Buttons on click listeners */
        for (int row = 0; row < Game.NUM_ROWS; row++) {
            for (int col = 0; col < Game.NUM_COLUMNS; col++) {
                mTicTacToeButtons[row][col].setOnClickListener(this);


            }
        }


        }


        @Override
        public void onClick(View v){


            if(v.getId() == R.id.newgame_btn){
                mGame.resetGame();
            }

            for (int row = 0; row < Game.NUM_ROWS; row++) {
                for (int col = 0; col < Game.NUM_COLUMNS; col++) {
                    if(mGame.gameState == mGame.gameState.X_TURN) {
                        if (v.getId() == mTicTacToeButtons[row][col].getId()) {
                            mGame.setMove(row, col);
                            
                        }
                    }
                    else if(mGame.gameState == mGame.gameState.O_TURN){
                        mGame.getAIMove();

                    }
                    mTicTacToeButtons[row][col].setText(mGame.setMoveText(row, col));


                }
            }
            mGameState.setText(mGame.stringForGameState());
        }

    }













