package com.shengkunwei.tictactoe.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TicTacToeView gameView;
    private boolean autoRotate = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (TicTacToeView) findViewById(R.id.gameview);
    }

    public void startNewGame(View button){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("START NEW GAME")
                .setItems(new String[]{"You First", "Computer First"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                gameView.initGame(Cell.STATE_PLAYER1);
                                break;
                            case 1:
                                gameView.initGame(Cell.STATE_COMPUTER);
                                break;
                        }
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        gameView.saveGame(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameView.restoreGame(savedInstanceState);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            item.setChecked(!item.isChecked());
            autoRotate = item.isChecked();
            if(autoRotate){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
