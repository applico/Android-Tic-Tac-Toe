package com.myapps.tushar.gametictactoe;


///////////////////////////////////////////////////////////////////////
// MainActivity.java - GameTicTacToe                                 //
// ver 1.0                                                           //
// Language:    Java, Android Studio 1.2.1.1                         //
// platform:    Dell Inspiron 15R, Windows 8                         //
// Author:      Tushar Bhatia                                        //
//              Graduate Student, Syracuse University                //
//              tbhatia@syr.edu                                      //
///////////////////////////////////////////////////////////////////////

/*
*
*  The following is a simple android based game application based on the
*  Game of Tic Tac Toe.
*  For two players, X and O, who take turns marking the spaces in a 3×3 grid.
*  The player who succeeds in placing three respective marks in a horizontal,
*  vertical, or diagonal row wins the game.
*
*   For this game the 2nd player is Computer
*/

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment);

        if (fragment == null) {
            fragment = new MainActivityFragment();
            manager.beginTransaction().add(R.id.fragment, fragment)
                    .commit();
        }


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
