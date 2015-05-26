package com.tictactoe.marcel.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;


//===================Tic Tac Toe Game=================//
//===================Marcelino Yax===================//
//===================Me vs. Android===================//
/**
 * =====TicTacToe app does the following=====
 * 1. Show instructions when app launched for the first time
 * 2. Retrieve, display and save players scores
 * 3. Show players move: Android move is shown after 1 second delay during the game
 * 5. Check and Display the winner
 * 6. Clear all: set score and board to zero
 *
 * =====Step Required to Complete the App=====
 * 1. Design the game: select a simple interface design
 * 2. Implement the game
 * 3. Test the game: test it with various devices and conditions
 * 4. Fix the game: fix logo, pick different color, clean code
 * 5. Test it again
 */


public class TicTacToeMainActivity extends Activity {

    private Button[] button; //Array holding each button reference
    private int[] enableButtons;//Array enabling buttons
    private TextView scoreAndroidDisplay, scoreMeDisplay; //Reference to textView

    private TicTacToeEngine tictactoe;

    private final static int numberOfButton=9;//The number of buttons on screen
    private final static int addAPoint=1;
    //private static final String TAG = "TicTacToe Error"; // for logging errors

    private Handler handler; //Used to delay displaying ANDROID_PLAYER move
    private boolean checkRunnableAdded; //Check Runnable is added to the message queue

    //Using SharedPreferences for storing
    private SharedPreferences saveGameScore;
    // Key used to persistently store the value of the game score
    private static final String storeAndroidScore = "com.tictactoe.marcel.tictactoe.storeAndroidScore";
    private static final String storeMeScore = "com.tictactoe.marcel.tictactoe.storeMeScore";
    private static final String checkFirsTime = "com.tictactoe.marcel.tictactoe.checkFirstTimes";
    private int androidPlayerScore,mePlayerScore; //Holding scores value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get reference to each button
        referenceToButton();

        //Get reference to each textView
        scoreAndroidDisplay=(TextView)findViewById(R.id.displayScoreAndroid);
        scoreMeDisplay=(TextView)findViewById(R.id.displayScoreMe);

        //Create an Instance of TicTacToeEngine
        tictactoe =new TicTacToeEngine(numberOfButton);

        //An instance of Handler
        handler=new Handler();
        checkRunnableAdded=false;

        //Get reference to SharedPreferences
        //Get score value from SharedPreferences
        saveGameScore=getSharedPreferences(storeAndroidScore,0);
        androidPlayerScore=saveGameScore.getInt(storeAndroidScore,0);
        mePlayerScore=saveGameScore.getInt(storeMeScore,0);

        //Display Score in TextView
        displayScore();

        //Show the instruction if app is launched for the first time
        if(firstTimeShowInstruction()){

            showHowToPlay();//Show the instruction
        }

        //Start a new game
        startNewGame();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        switch (item.getItemId()){

            case R.id.action_info:

                //Help overlay to show game instruction
                showHowToPlay();
                return true;

            case R.id.action_reset:
                //Clear all
                //Refreash TextView
                androidPlayerScore=0;
                mePlayerScore=0;
                displayScore();//Refresh TextView

                //Update score in Sharepreference
                storeWinnerScore(storeAndroidScore ,androidPlayerScore);
                storeWinnerScore(storeMeScore,mePlayerScore);

                //Start a new Game
                startNewGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch

    }

    //Get reference to each button and put it into array
    private void referenceToButton(){

        button=new Button[numberOfButton];

        //First Row
        button[0] = (Button) findViewById(R.id.entry1);
        button[1] = (Button) findViewById(R.id.entry2);
        button[2] = (Button) findViewById(R.id.entry3);

        //Second Row
        button[3] = (Button) findViewById(R.id.entry4);
        button[4] = (Button) findViewById(R.id.entry5);
        button[5] = (Button) findViewById(R.id.entry6);

        //Third Row
        button[6] = (Button) findViewById(R.id.entry7);
        button[7] = (Button) findViewById(R.id.entry8);
        button[8]= (Button) findViewById(R.id.entry9);

    }

    //Display score in TextView
    private void displayScore(){
        scoreAndroidDisplay.setText(String.valueOf(androidPlayerScore));
        scoreMeDisplay.setText(String.valueOf(mePlayerScore));
    }

    //Start a new Game
    private void startNewGame(){

        int k;

        //Clear all previous values
        tictactoe.clearBoard();

        //Reset and Enable all buttons
        for(k=0; k<numberOfButton; k++){
            button[k].setText(" ");
            button[k].setEnabled(true);
            //Applied click listener to each button
            button[k].setOnClickListener(new EntryButtonListener(k));

        }

    }

    //Called when an entry button is pressed
    private class EntryButtonListener implements View.OnClickListener {

        int mePosition;

        public EntryButtonListener(int mePosition){

            this.mePosition=mePosition;

        }

        @Override
        public void onClick(View v) {

            //Display and tore ME_PLAYER move
            button[mePosition].setText("x");
            button[mePosition].setTextColor(getResources().getColor(R.color.black));
            tictactoe.storePlayerMove(mePosition, tictactoe.mePlayer());

            //Disable ME_PLAYER button
            button[mePosition].setEnabled(false);

            //Disable all enable buttons
            disableButtons();

            //Display ANDROID player move after 1 second
            //using Handler
            checkRunnableAdded=handler.postDelayed(displayAndroidMove,1000);

        }
    } //end class EntryButtonListener

    //Display android move after 1 second delay
    private Runnable displayAndroidMove=new Runnable() {
        @Override
        public void run() {


            int checkWinner;

            //Check Winner game case
            checkWinner= tictactoe.checkWinner();

            //Check if there is no winner yet
            //and store ANDROID_PLAYER move

            if(checkWinner== tictactoe.noWinnerYet()) {
                //Get and store ANDROID_PLAYER move
                int androidPosition = tictactoe.findAndroidMove();

                //Display ANDROID_PLAYER move
                button[androidPosition].setText("o");
                button[androidPosition].setTextColor(getResources().getColor(R.color.black));

                //Check Winner game case again
                checkWinner= tictactoe.checkWinner();
            }

            //Check for winner
            if(checkWinner== tictactoe.mePlayerWin()){
                //ME Player wins

                //Update ME player score
                mePlayerScore=mePlayerScore+addAPoint;

                //Refresh Me TextView
                scoreMeDisplay.setText(String.valueOf(mePlayerScore));

                //Store ME score
                storeWinnerScore(storeMeScore,mePlayerScore);

                //Show Dialog
                showGameOverDialog(R.string.meWinner);


            }else if(checkWinner== tictactoe.androidPlayerWin()){
                //ANDROID player wins

                //Update Android  player score
                androidPlayerScore=androidPlayerScore+addAPoint;

                //Refresh Android TextView
                scoreAndroidDisplay.setText(String.valueOf(androidPlayerScore));

                //Store ANDROID  score
                storeWinnerScore(storeAndroidScore ,androidPlayerScore);

                //Show Dialog
                showGameOverDialog(R.string.androidWinner);

            }else if(checkWinner== tictactoe.thereIsTie()){
                //No winner, there is a tie game

                showGameOverDialog(R.string.tiedGame);//Show Dialog

            }else{

                //There is no winner yet and
                //Enable only empty buttons
                enableButtons=tictactoe.getAllEntry();
                for(int k=0; k<numberOfButton; k++){

                    if(enableButtons[k]==tictactoe.emptyBoard()){

                        //Enable empty button
                        button[k].setEnabled(true);
                    }

                }

            }


        }
    };


    //Disable all buttons
    private void disableButtons(){

        for(int k=0; k<numberOfButton; k++){

            if(button[k].isEnabled()){

                //Disable button
                button[k].setEnabled(false);
            }

        }
    }

    //Store winner score in Sharepreference
    private void storeWinnerScore(String scoreKey, int scoreValue){

        SharedPreferences.Editor preferenceEditior=saveGameScore.edit();
        preferenceEditior.putInt(scoreKey,scoreValue);
        preferenceEditior.apply();
    }

    //To notify human player
    //about the winner and start
    //a new game
    private void showGameOverDialog(final int messageId){

        //Create a new AlertDialog Builder
        AlertDialog.Builder builder=new AlertDialog.Builder(TicTacToeMainActivity.this);

        //Set dialog's tittle
        builder.setTitle(getResources().getString(R.string.gameTittle));

        //Set message to display
        builder.setMessage(getResources().getString(messageId));

        //Provide an OK button that dismisses the dialog
        builder.setPositiveButton(R.string.OK,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Start a new Game
                startNewGame();
            }
        });

        //Create and show AlertDialog
        AlertDialog winnerNotify=builder.create();
        winnerNotify.show();

    }


    //Show game Instruction
    private void showHowToPlay(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.pop_window_instruction);
        dialog.setCanceledOnTouchOutside(true);

        //For dismissing anywhere you touch
        View masterView = dialog.findViewById(R.id.popWindow);

        //Set a click listener to the dialog
        masterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    //Show the instruction if app is launched for the first time
    private boolean firstTimeShowInstruction(){


        boolean ranBeforeLaunch=saveGameScore.getBoolean(checkFirsTime, false);

        if (!ranBeforeLaunch) {

            //This is the first time app is launched
            SharedPreferences.Editor editor = saveGameScore.edit();
            editor.putBoolean(checkFirsTime, true);
            editor.apply();

        }

        return !ranBeforeLaunch;
    }


    //Method onClick newGame
    public void newGame(View v){

        //Stop postDelay
        //Only stop if Runnable is added to the message queue
        if(checkRunnableAdded) {
            handler.removeCallbacks(displayAndroidMove);//Stop postDelay
            checkRunnableAdded=false;
        }
        //Start a new game
        startNewGame();

    }

} //end class TicTacToeMainActivity
