/*
 * MainActivity class
 * Initializes the UI elements
 * Initializes the game
 * Restarts game on menu item selection
 * Assigns click listeners to button
 */

package com.vivek.presentor;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.R;
import com.vivek.business.ComputerGame;
import com.vivek.global.EnvironmentVar;
import com.vivek.model.MODELButtonValue;


public class MainActivity extends Activity {

	ArrayList<MODELButtonValue>  gridList;
	Button [] gridButtons;
	int computerWins = 2;
	int humanWins = 1;
	int gameDrawn = 0;
	int humanFirst = 0;
	int compFirst = 0;
	TextView tvPlayerWins, tvCompWins, tvDrawnGames;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializers();
	}



	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	//--------------------------------------------------CUSTOM METHODS-----------------------------------------------------------------------------------------------------//
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------//	

	private void initializers(){	//Instantiate all the variables
		gridButtons = new Button[EnvironmentVar.boardSize];

		gridButtons[0] = (Button)findViewById(R.id.grid1);
		gridButtons[1] = (Button)findViewById(R.id.grid2);
		gridButtons[2] = (Button)findViewById(R.id.grid3);
		gridButtons[3] = (Button)findViewById(R.id.grid4);
		gridButtons[4] = (Button)findViewById(R.id.grid5);
		gridButtons[5] = (Button)findViewById(R.id.grid6);
		gridButtons[6] = (Button)findViewById(R.id.grid7);
		gridButtons[7] = (Button)findViewById(R.id.grid8);
		gridButtons[8] = (Button)findViewById(R.id.grid9);

		tvPlayerWins = (TextView)findViewById(R.id.tvPlayerWins);
		tvCompWins = (TextView)findViewById(R.id.tvCompWins);
		tvDrawnGames = (TextView)findViewById(R.id.tvDrawnGames);

		gameInitializer();

	}


	private void gameInitializer(){ //Initialize game data to default mode

		gridList = new ArrayList<MODELButtonValue>(EnvironmentVar.boardSize);
		tvPlayerWins.setText("Player: "+EnvironmentVar.playerWins);
		tvCompWins.setText("Computer: "+EnvironmentVar.compWins);
		tvDrawnGames.setText("Draws: "+EnvironmentVar.draws);

		for (int i = 0; i < EnvironmentVar.boardSize; i++) {
			MODELButtonValue modelButtonValue = new MODELButtonValue();
			modelButtonValue.setGridButton(gridButtons[i]); // Insert button object into the model class object
			modelButtonValue.setId(i); // set unique id to object
			modelButtonValue.setChecked(false); // set all button as unchecked
			modelButtonValue.getGridButton().setText(""); // set all button text to empty
			modelButtonValue.getGridButton().setId(i);
			modelButtonValue.getGridButton().setEnabled(true); // set all button to be clickable
			modelButtonValue.getGridButton().setOnClickListener(new GridClickLister()); // button onClick logic
			gridList.add(modelButtonValue);
		}

	}



	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	//--------------------------------------------------OVERRIDDEN METHODS-----------------------------------------------------------------------------------------------------//
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------//	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			gameInitializer();
		}
		return super.onOptionsItemSelected(item);
	}


	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	//--------------------------------------------------INNER CLASS--------------------------------------------------------------------------------------------------------//
	//---------------------------------------------------------------------------------------------------------------------------------------------------------------------//	

	private class GridClickLister implements View.OnClickListener{  //Grid button on click 

		@Override
		public void onClick(View view) {

			ComputerGame objComputerGame = new ComputerGame(gridList);

			int winnerType = objComputerGame.selectAndMark(((Button)view).getId(),R.string.player_human);
			if (winnerType == humanWins) {
				Toast.makeText(MainActivity.this, "Player Wins", Toast.LENGTH_SHORT).show();
				objComputerGame.setAlltoDisabled();
				EnvironmentVar.playerWins++;
				tvPlayerWins.setText("Player: "+EnvironmentVar.playerWins);
			}else if (winnerType == computerWins) {
				Toast.makeText(MainActivity.this, "Computer Wins", Toast.LENGTH_SHORT).show();
				objComputerGame.setAlltoDisabled();
				EnvironmentVar.compWins++;
				tvCompWins.setText("Computer: "+EnvironmentVar.compWins);
			}else if (winnerType == gameDrawn) {
				Toast.makeText(MainActivity.this, "Game Drawn", Toast.LENGTH_SHORT).show();
				objComputerGame.setAlltoDisabled();
				EnvironmentVar.draws++;
				tvDrawnGames.setText("Draws: "+EnvironmentVar.draws);
			}
		}

	}


}