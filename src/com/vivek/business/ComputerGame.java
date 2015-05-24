/*
 * 1) Assigns user clicks to the button
 * 2) Makes random computer selection
 * 3) Checks for winner/draw/game not completed after selection is marked
 */

package com.vivek.business;

import java.util.ArrayList;
import java.util.Random;

import com.vivek.R;
import com.vivek.global.EnvironmentVar;
import com.vivek.model.MODELButtonValue;

public class ComputerGame {

	ArrayList<MODELButtonValue> gridList;
	int computerWins = 2;
	int humanWins = 1;
	int gameDrawn = 0;
	int gameNotOver = 3;


	public ComputerGame(ArrayList<MODELButtonValue> buttonValue){
		this.gridList = buttonValue;
	}

	/*
	 * Calculates a random position to mark the selection. [Computer mind]	
	 */
	private void makeCompMove(){

		Random randomNumber = new Random();
		int counter = 0;
		int  mark;
		do  {  
			mark = randomNumber.nextInt(EnvironmentVar.boardSize);  
			counter++;
			if (counter == EnvironmentVar.boardSize) {
				break;
			}
		} while (gridList.get(mark).getChecked() == true); 

		selectAndMark(mark,R.string.player_comp);

	}

	/*
	 * Make the selection of desired grid button	
	 */
	public int selectAndMark(int loc , int playerType){

		if (playerType == R.string.player_human) {
			for (int i = 0; i < gridList.size(); i++) {
				if (gridList.get(i).getId() == loc) { //check for desired button from the grid button list
					gridList.get(i).getGridButton().setTextSize(50);
					gridList.get(i).getGridButton().setText(R.string.human_move); //Mark the selected button
					gridList.get(i).setPlayerType(playerType); // Set player type to the gridList object
					gridList.get(i).getGridButton().setEnabled(false); // Disable the button
					gridList.get(i).setChecked(true); // set the checked boolean true
				}
			}
			if (winnerType() == humanWins) { // Check if player won
				return humanWins;
			}
			makeCompMove();		// Call computer to make next selection	

		}else if(playerType == R.string.player_comp){ //check for desired button from the grid button list
			for (int i = 0; i < gridList.size(); i++) {
				if (gridList.get(i).getId() == loc) {
					if (gridList.get(i).getChecked() != true) {//Check if checked
						gridList.get(i).getGridButton().setTextSize(50);
						gridList.get(i).getGridButton().setText(R.string.comp_move); //Mark the selected button
						gridList.get(i).setPlayerType(playerType); // Set player type to the gridList object
						gridList.get(i).getGridButton().setEnabled(false); // Disable the button
						gridList.get(i).setChecked(true); // set the checked boolean true
					}
				}
			}

			if (winnerType() == computerWins) { // Check if computer won
				return computerWins;
			}
		}

		return winnerType();
	}


	/*
	 * Check for the winner or draw or not complete	
	 */
	private int winnerType(){

		for (int i = 0; i <= 6; i+=3) { 
			if (gridList.get(i).getPlayerType() == R.string.player_human && gridList.get(i+1).getPlayerType() == R.string.player_human 
					&& gridList.get(i+2).getPlayerType() == R.string.player_human ) { // Check horizontally if player won
				return humanWins;
			}

			if (gridList.get(i).getPlayerType() == R.string.player_comp && gridList.get(i+1).getPlayerType() == R.string.player_comp 
					&& gridList.get(i+2).getPlayerType() == R.string.player_comp ) { // Check horizontally if computer won
				return computerWins;
			}
		}

		for (int i = 0; i <= 2; i++) {

			if (gridList.get(i).getPlayerType() == R.string.player_human && gridList.get(i+3).getPlayerType() == R.string.player_human 
					&& gridList.get(i+6).getPlayerType() == R.string.player_human ) { // Check vertically if player won
				return humanWins;
			}

			if (gridList.get(i).getPlayerType() == R.string.player_comp && gridList.get(i+3).getPlayerType() == R.string.player_comp 
					&& gridList.get(i+6).getPlayerType() == R.string.player_comp ) { // Check vertically if computer won
				return computerWins;
			}
		}

		if ((gridList.get(0).getPlayerType() == R.string.player_human 
				&& gridList.get(4).getPlayerType() == R.string.player_human 
				&& gridList.get(8).getPlayerType() == R.string.player_human) 
				|| 
				gridList.get(2).getPlayerType() == R.string.player_human 
				&& gridList.get(4).getPlayerType() == R.string.player_human 
				&& gridList.get(6).getPlayerType() == R.string.player_human)  // Check both diagonals, if player won
			return humanWins;

		if ((gridList.get(0).getPlayerType() == R.string.player_comp 
				&& gridList.get(4).getPlayerType() == R.string.player_comp 
				&& gridList.get(8).getPlayerType() == R.string.player_comp) 
				||  
				gridList.get(2).getPlayerType() == R.string.player_comp 
				&& gridList.get(4).getPlayerType() == R.string.player_comp 
				&& gridList.get(6).getPlayerType() == R.string.player_comp)  // Check both diagonals, if computer won
			return computerWins;		

		for (int i = 0; i < gridList.size(); i++)  
		{  
			if (gridList.get(i).getPlayerType() != R.string.player_human && 
					gridList.get(i).getPlayerType() != R.string.player_comp) // Check for open grid buttons : game not over  
				return gameNotOver;  
		}  

		return gameDrawn; // Return game as drawn
	}

	/*
	 * Disable all the buttons after game is concluded
	 */
	public void setAlltoDisabled(){ 
		for (int i = 0; i < gridList.size(); i++) {
			gridList.get(i).getGridButton().setEnabled(false);
		}
	}

}