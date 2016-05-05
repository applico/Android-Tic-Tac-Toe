package com.tevinjeffrey.tictactoe.game.cell;

//A cell interface. To support different types of cells depending on how the layout was constructed.
// e.g ImageViews, Buttons, GridView, Custom Views.
public interface Cell {

    String INDEX_INSTANCE_STATE = "INDEX_INSTANCE_STATE";
    String VALUE_INSTANCE_STATE = "VALUE_INSTANCE_STATE";
    String STATE_INSTANCE_STATE = "STATE_INSTANCE_STATE";

    boolean isTaken();

    int getIndex();

    void setIndex(int index);

    int getValue();

    void setValue(int value);

    boolean isPlayerOne();

    boolean isPlayerTwo();

    boolean isBlank();

    void setState(CellState cellState);

    CellState getState();

}
