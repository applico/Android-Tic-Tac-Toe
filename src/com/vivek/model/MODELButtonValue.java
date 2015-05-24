/*
 * Custom object to hold button values:
 * 1) object checked/selected status
 * 2) button object
 * 3) id of the record
 * 4) player type of selection
 */

package com.vivek.model;

import android.widget.Button;

public class MODELButtonValue {
	
	Boolean checked; // True if button is selected
	Button gridButton;
	int id,playerType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public int getPlayerType() {
		return playerType;
	}
	public void setPlayerType(int playerType) {
		this.playerType = playerType;
	}
	public Button getGridButton() {
		return gridButton;
	}
	public void setGridButton(Button gridButton) {
		this.gridButton = gridButton;
	}

}
