package com.elin.tictactoe;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by elin on 5/22/2015.
 */
public class Cross extends Cell{
    public Cross(int x, int y){
        super(x, y);
    }
    public void draw(Canvas g, Resources res,int x, int y, int w, int h){
        Bitmap mPic = BitmapFactory.decodeResource(res, R.drawable.cross_grey);
        g.drawBitmap(mPic, null, new Rect(x * w, y * h, (x * w) + w, (y * h) + h), new Paint());
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof Circle;
    }

    @Override
    public String toString(){
        return "X";
    }
}
