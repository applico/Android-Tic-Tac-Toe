package com.tevinjeffrey.tictactoe.game.players;

import android.os.Parcel;
import android.os.Parcelable;

import com.orhanobut.hawk.Hawk;
import com.tevinjeffrey.tictactoe.game.board.Board;
import com.tevinjeffrey.tictactoe.game.cell.CellState;

import static com.tevinjeffrey.tictactoe.TTTApp.*;

public class Player implements Parcelable {

    private String playerName;
    private int playerScore;
    private CellState playerId;

    public void pick(Board board, int pickIndex) {
        board.invokeCell(getPlayerId(), pickIndex);
    }

    public CellState getPlayerId() {
        return playerId;
    }

    public void setPlayerId(CellState playerId) {
        this.playerId = playerId;
    }

    protected void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void savePlayerName() {
        if (getPlayerId().equals(CellState.PLAYER_ONE)) {
            Hawk.put(PREF_PLAYER_ONE_NAME, playerName);
        } else {
            Hawk.put(PREF_PLAYER_TWO_NAME, playerName);
        }
    }

    public String getPlayerName() {
        String name;
        if (getPlayerId().equals(CellState.PLAYER_ONE)) {
            name = Hawk.get(PREF_PLAYER_ONE_NAME, playerName);
        } else {
            name = Hawk.get(PREF_PLAYER_TWO_NAME, playerName);
        }
        return name;    
    }

    public int getPlayerScore() {
        int score;
        if (getPlayerId().equals(CellState.PLAYER_ONE)) {
            score = Hawk.get(PREF_PLAYER_ONE_SCORE, playerScore);
        } else {
            score = Hawk.get(PREF_PLAYER_TWO_SCORE, playerScore);
        }
        return score;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
        if (getPlayerId().equals(CellState.PLAYER_ONE)) {
            Hawk.put(PREF_PLAYER_ONE_SCORE, playerScore);
        } else {
            Hawk.put(PREF_PLAYER_TWO_SCORE, playerScore);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.playerName);
        dest.writeInt(this.playerScore);
        dest.writeInt(this.playerId == null ? -1 : this.playerId.ordinal());
    }

    protected Player() {
    }

    private Player(Parcel in) {
        this.playerName = in.readString();
        this.playerScore = in.readInt();
        int tmpPlayerId = in.readInt();
        this.playerId = tmpPlayerId == -1 ? null : CellState.values()[tmpPlayerId];
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}


