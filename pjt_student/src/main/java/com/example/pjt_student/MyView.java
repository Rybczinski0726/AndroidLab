package com.example.pjt_student;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    Context context;
    int score;
    int color;

    public MyView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public MyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init(attributeSet);
    }

    public MyView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        this.context = context;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.AAA);
            color = array.getColor(R.styleable.AAA_scoreColor, Color.YELLOW);
        }
    }

    public void setScore(int score) {
        this.score = score;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.alpha((Color.CYAN)));

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);

        RectF rectF = new RectF(15, 15, 70, 70);
        canvas.drawArc(rectF, 0, 360, false, paint);

        float endAngle = (360 * score) / 100;
        paint.setColor(color);
        canvas.drawArc(rectF, -90, endAngle, false, paint);
    }
}
