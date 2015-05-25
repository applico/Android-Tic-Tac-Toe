package com.myapps.tushar.gametictactoe;


///////////////////////////////////////////////////////////////////////
// MainActivityFragment.java - GameTicTacToe                         //
// ver 1.0                                                           //
// Language:    Java, Android Studio 1.2.1.1                         //
// platform:    Dell Inspiron 15R, Windows 8                         //
// Author:      Tushar Bhatia                                        //
//              Graduate Student, Syracuse University                //
//              tbhatia@syr.edu                                      //
///////////////////////////////////////////////////////////////////////



import android.graphics.Color;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class MainActivityFragment extends Fragment {

    private TicTacToeGame mGame;
    private Button mGrid[];
    private TextView mStatusInfo;
    private TextView mComputerScore;
    private TextView mUserScore;
    private TextView mTies;

    private int mTieCount =0;
    private int mUserCount =0;
    private int mComputerCount =0;

    private boolean mUserFirst = true;
    private boolean mGameOver = false;
    private Button  mStartNewGame;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_main, container, false);


        int mGridSize = mGame.getGridSize();
        mGrid         = new Button[mGridSize];
        mGrid[0]      = (Button) myView.findViewById(R.id.button0);
        mGrid[1]      = (Button) myView.findViewById(R.id.button1);
        mGrid[2]      = (Button) myView.findViewById(R.id.button2);
        mGrid[3]      = (Button) myView.findViewById(R.id.button3);
        mGrid[4]      = (Button) myView.findViewById(R.id.button4);
        mGrid[5]      = (Button) myView.findViewById(R.id.button5);
        mGrid[6]      = (Button) myView.findViewById(R.id.button6);
        mGrid[7]      = (Button) myView.findViewById(R.id.button7);
        mGrid[8]      = (Button) myView.findViewById(R.id.button8);
        mStartNewGame = (Button) myView.findViewById(R.id.button9);


        mStatusInfo    = (TextView) myView.findViewById(R.id.statusInfo);
        mUserScore     = (TextView) myView.findViewById(R.id.userCount);
        mComputerScore = (TextView) myView.findViewById(R.id.computerScore);
        mTies          = (TextView) myView.findViewById(R.id.tieCount);

        mUserScore.setText(Integer.toString(mUserCount));
        mComputerScore.setText(Integer.toString(mComputerCount));
        mTies.setText(Integer.toString(mTieCount));


        mGame = new TicTacToeGame();
        startNewGame();

        return myView;
    }

    // ------------------------<Starts or restarts the game>--------------------------------------
    // Every time the user starts first. First turn toggles
    private void startNewGame(){

        mGame.ResetGrid();
        mGameOver = false;
        for(int i =0; i<mGrid.length;i++){
            mGrid[i].setText("");
            mGrid[i].setEnabled(true);
            mGrid[i].setOnClickListener(new ButtonClickListner(i));

        }

        mStartNewGame.setOnClickListener(new StartNewGameListner());

        if(mUserFirst){

            mStatusInfo.setText(R.string.user_turn);
            mUserFirst = false;
        }
        else{
            mStatusInfo.setText(R.string.computer_turn);
            int move = mGame.ComputerMove();
            setMove(mGame.COMPUTER,move);
            mUserFirst = true;
        }

    }

    // ----------------------<OnClickListener on Restart Game>---------------------------------
    private class StartNewGameListner implements View.OnClickListener{

        public void onClick(View view){
            startNewGame();

        }

    }

    //-------------------------<when any of the grid buttons are clicked>-------------------------------------
    private class ButtonClickListner implements View.OnClickListener{

        int location;

        public ButtonClickListner(int location){

            this.location = location;
        }

        public void onClick(View view){

            if(!mGameOver)
            {
                if(mGrid[location].isEnabled()){

                    setMove(mGame.USER,location);
                    int winner = mGame.checkForWin();
                    if(winner == 0) {
                        mStatusInfo.setText(R.string.computer_turn);
                        int move = mGame.ComputerMove();
                        setMove(mGame.COMPUTER, move);
                        winner = mGame.checkForWin();
                    }
                    if(winner == 0)
                        mStatusInfo.setText(R.string.user_turn);
                    else if(winner ==1){

                        mStatusInfo.setBackgroundColor(Color.YELLOW);
                        mStatusInfo.setText(R.string.result_tie);
                        mTieCount++;
                        mTies.setText(Integer.toString(mTieCount));
                        mGameOver = true;
                    }
                    else if (winner ==2){

                        mStatusInfo.setBackgroundColor(Color.GREEN);
                        mStatusInfo.setText(R.string.user_win);
                        mUserCount++;
                        mUserScore.setText(Integer.toString(mUserCount));
                        mGameOver = true;
                    }
                    else {
                        mStatusInfo.setBackgroundColor(Color.RED);
                        mStatusInfo.setText(R.string.computer_win);
                        mComputerCount++;
                        mComputerScore.setText(Integer.toString(mComputerCount));
                        mGameOver = true;
                    }


                }

            }
        }

    }
    // -----------------------------<Records the Players move>---------------------------------
    private void setMove(char player, int location){

        mGame.movePlayed(player,location);
        mGrid[location].setEnabled(false);

        mGrid[location].setText(String.valueOf(player));

        if(player == mGame.USER){
            mGrid[location].setTextColor(Color.RED);
        }

        if(player == mGame.COMPUTER){
            mGrid[location].setTextColor(Color.YELLOW);
        }


    }


}
