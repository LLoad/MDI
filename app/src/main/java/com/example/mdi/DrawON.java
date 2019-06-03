package com.example.mdi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class DrawON extends View { // 사각형 그리기
    public DrawON(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint pt = new Paint();
        pt.setColor(Color.GREEN);
        pt.setStrokeWidth(5);

        int width = getWidth();
        int height = getHeight();

        canvas.drawLine(width/5, height/4, width*4/5, height/4, pt);
        canvas.drawLine(width/5, height/4, width/5, height*3/4, pt);
        canvas.drawLine(width/5, height*3/4, width*4/5, height*3/4, pt);
        canvas.drawLine(width*4/5, height/4, width*4/5, height*3/4, pt);
    }
}