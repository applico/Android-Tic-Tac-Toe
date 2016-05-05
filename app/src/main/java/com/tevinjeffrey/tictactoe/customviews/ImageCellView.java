package com.tevinjeffrey.tictactoe.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.tevinjeffrey.tictactoe.R;
import com.tevinjeffrey.tictactoe.game.cell.Cell;
import com.tevinjeffrey.tictactoe.game.cell.CellState;

import static com.tevinjeffrey.tictactoe.game.cell.CellState.BLANK;
import static com.tevinjeffrey.tictactoe.game.cell.CellState.PLAYER_ONE;
import static com.tevinjeffrey.tictactoe.game.cell.CellState.PLAYER_TWO;

// ImageViews and like buttons but are a bit more flexible with it comes displaying the image.
// The image is also divorced from the background which is a big plus when it comes to animating the
// cell. For my purposes it's much better than drawing my own custom views.
public class ImageCellView extends ImageView implements Cell {

    private int value;
    private int index;
    private CellState state = BLANK;

    public ImageCellView(Context context) {
        super(context);
    }

    public ImageCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageCellView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int v) {
        value = v;
    }

    public boolean isTaken() {
        return  getState() != BLANK;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean isPlayerOne() {
        return getState().equals(PLAYER_ONE);
    }

    @Override
    public boolean isPlayerTwo() {
        return getState().equals(PLAYER_TWO);
    }

    @Override
    public boolean isBlank() {
        return getState() == BLANK;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState cellState) {
        // programmatically sets the default selectable background for the view.
        // TODO set the correct drawable for android versions lower than 21;
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        setBackgroundResource(outValue.resourceId);

        switch (cellState) {
            case BLANK:
                YoYo.with(Techniques.TakingOff).duration(250).interpolate(new DecelerateInterpolator()).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setImageResource(R.mipmap.ic_blank);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(this);
                break;
            case PLAYER_ONE:
                YoYo.with(Techniques.Landing).duration(250).interpolate(new DecelerateInterpolator()).playOn(this);
                setImageResource(R.mipmap.ic_cross);

                break;
            case PLAYER_TWO:
                YoYo.with(Techniques.Landing).duration(250).interpolate(new DecelerateInterpolator()).playOn(this);
                setImageResource(R.mipmap.ic_circle);
                break;
            default:
                break;

        }
        state = cellState;

    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt(Cell.INDEX_INSTANCE_STATE, index);
        bundle.putInt(Cell.VALUE_INSTANCE_STATE, value);
        bundle.putSerializable(Cell.STATE_INSTANCE_STATE, state);

        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("instanceState");
            this.index = bundle.getInt(Cell.INDEX_INSTANCE_STATE);
            this.value = bundle.getInt(Cell.VALUE_INSTANCE_STATE);
            setState((CellState) bundle.getSerializable(Cell.STATE_INSTANCE_STATE));

            super.onRestoreInstanceState(state);
        }
    }

    @Override
    public String toString() {
        return "(Index: "+ getIndex() + ", Score: " + getValue() + ", State: " + getState() + ")";
    }
}
