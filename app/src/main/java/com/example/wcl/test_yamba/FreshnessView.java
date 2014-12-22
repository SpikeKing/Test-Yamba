package com.example.wcl.test_yamba;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 时间宽度
 * Created by wangchenlong on 14-12-22.
 */
@SuppressWarnings("all")
public class FreshnessView extends View {
    private static final int LINE_HEIGHT = 30;
    private long mTimestamp = -1;
    private Paint mPaint;

    public FreshnessView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setARGB(255, 0, 255, 0);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint.setStrokeWidth(LINE_HEIGHT);

        setMinimumHeight(LINE_HEIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mTimestamp == -1) return;

        long delta = System.currentTimeMillis() - mTimestamp;
        double hours = delta / 3600000.0;
        double multiplier = 1 - (Math.min(hours, 24) / 24.0);
        int width = (int) (getWidth() * multiplier);
        canvas.drawLine(0, 0, width, 0, mPaint);
    }

    public void setTimestamp(long timestamp) {
        this.mTimestamp = timestamp;
        this.invalidate();
    }
}
