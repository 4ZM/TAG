/**
 * Copyright (c) 2013 Anders Sundman <anders@4zm.org>
 * 
 * This file is part of TAG.
 * 
 * TAG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * TAG is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with TAG.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sparvnastet.tag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    final static int cells = 32;
    byte[] data = new byte[cells * cells / 8];
    private Rect mRect = new Rect();

    private Paint mGridPaint = new Paint();
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(Color.DKGRAY);

        fgPaint.setStyle(Style.FILL);
        fgPaint.setColor(Color.RED);

        bgPaint.setStyle(Style.FILL);
        bgPaint.setColor(Color.LTGRAY);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] newData) {
        if (data.length != newData.length)
            return;

        for (int i = 0; i < data.length; ++i)
            data[i] = newData[i];
    }

    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        setData(x, y, true);

        // Force redraw
        invalidate();

        return true;
    }

    public void onDraw(Canvas c) {

        int w = getWidth();
        int h = getHeight();

        final float cellHeight = (float) h / cells;
        final float cellWidth = (float) w / cells;

        for (int xc = 0; xc < cells; ++xc) {
            for (int yc = 0; yc < cells; ++yc) {
                int tlx = (int) (cellWidth * xc);
                int tly = (int) (cellHeight * yc);
                mRect.set(tlx + 1, tly + 1, tlx + (int) cellWidth + 1, tly + (int) cellHeight + 1);
                c.drawRect(mRect, getData(xc, yc) ? fgPaint : bgPaint);
            }
        }

        drawGrid(c);

        super.onDraw(c);
    }

    private void setData(float x, float y, boolean set) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())
            return;

        int xCell = (int) (x / getWidth() * cells);
        int yCell = (int) (y / getHeight() * cells);
        Log.i("TAG", "X: " + x + " Y: " + y);
        Log.i("TAG", "Xi: " + xCell + " Yi: " + yCell);

        int bitIndex = yCell * cells + xCell; // Total bit index
        int byteIndex = bitIndex / 8;
        int bitOffset = bitIndex % 8;

        byte mask = (byte) ((byte) 1 << (byte) bitOffset);

        if (set)
            data[byteIndex] = (byte) (data[byteIndex] | mask);
        else
            data[byteIndex] = (byte) (data[byteIndex] & ~mask);
    }

    private boolean getData(int xCell, int yCell) {
        int bitIndex = yCell * cells + xCell; // Total bit index
        int byteIndex = bitIndex / 8;
        int bitOffset = bitIndex % 8;

        byte mask = (byte) ((byte) 1 << (byte) bitOffset);

        return (data[byteIndex] & mask) != 0;
    }

    private void drawGrid(Canvas c) {
        int w = getWidth();
        int h = getHeight();

        // Draw outline
        mRect.set(0, 0, w - 1, h - 1);
        c.drawRect(mRect, mGridPaint);

        // Draw grid lines
        final float cellHeight = (float) h / cells;
        final float cellWidth = (float) w / cells;
        for (int i = 1; i < cells; ++i) {
            c.drawLine(0.0f, i * cellHeight, w, i * cellHeight, mGridPaint);
            c.drawLine(i * cellWidth, 0.0f, i * cellWidth, h, mGridPaint);
        }

    }
}
