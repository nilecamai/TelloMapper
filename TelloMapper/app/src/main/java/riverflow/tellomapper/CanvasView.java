package riverflow.tellomapper;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CanvasView extends View {
    Paint paint = new Paint();
    public static Canvas mCanvas;
    ArrayList<Coordinate> coordinates;
    Coordinate coordinate;
    int scaling, increment;
    private static float mRadius = 20;
    private static float mStrokeWidth = 5;
    private static float mTextSize = 20;
    float radius;
    float strokeWidth;
    float textSize;
    private static final Paint.Align textAlign = Paint.Align.CENTER;
    private static final int lockedNodeColor = Color.rgb(123, 239, 178); // aquamarine green
    private static final int unlockedNodeColor = Color.rgb(107, 185, 240); // malibu blue
    private static final int nodeTextColor = Color.WHITE;

    public CanvasView(Context context) {
        super(context);

        init(null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

    }

    public void drawCoordinate(Coordinate c, ArrayList<Coordinate> cs, int scaling, int increment) {
        coordinates = new ArrayList<Coordinate>(cs);
        coordinate = new Coordinate(c);
        this.scaling = scaling;
        this.increment = increment;
        autoScale();

        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        paint.setStrokeWidth(strokeWidth);
        paint.setTextSize(textSize);
        paint.setTextAlign(textAlign);
        super.onDraw(mCanvas);
        // fencepost algorithm for drawing
        if (coordinates != null) {
            if (coordinates.size() > 0) {
                // draw the first one
                Coordinate tempCoordinate = coordinates.get(0);
                Coordinate prevCoordinate;
                /*
                paint.setColor(lockedNodeColor);
                mCanvas.drawCircle(tempCoordinate.getX(), tempCoordinate.getY(), radius, paint);
                mCanvas.drawText("1", tempCoordinate.getX(), tempCoordinate.getY(), paint);
                */
                drawNode(0, tempCoordinate, true);
                for (int i = 1; i < coordinates.size(); i++) {
                    tempCoordinate = coordinates.get(i);
                    prevCoordinate = coordinates.get(i-1);
                    /*
                    mCanvas.drawLine(prevCoordinate.getX(), prevCoordinate.getY(), tempCoordinate.getX(), tempCoordinate.getY(), paint);
                    mCanvas.drawCircle(tempCoordinate.getX(), tempCoordinate.getY(), radius, paint);
                    mCanvas.drawText(i + 1 + "", tempCoordinate.getX(), tempCoordinate.getY(), paint);
                    */
                    drawConnector(prevCoordinate, tempCoordinate, true);
                    drawNode(i, tempCoordinate, true);
                }
                /*
                paint.setColor(unlockedNodeColor);
                mCanvas.drawLine(tempCoordinate.getX(), tempCoordinate.getY(), coordinate.getX(), coordinate.getY(), paint);
                */
                drawConnector(tempCoordinate, coordinate, false);
            }
        }
        if (coordinate != null) {
            /*
            paint.setColor(unlockedNodeColor);
            mCanvas.drawCircle(coordinate.getX(), coordinate.getY(), radius, paint);
            */
            drawNode(-1, coordinate, false);
        }
    }

    public void drawNode(int index, Coordinate coordinate, boolean locked){
        int tempColor;
        String text;
        if (index != -1) {
            text = index + 1 + "";
        } else {
            text = "";
        }
        if (locked) {
            paint.setColor(lockedNodeColor);
            tempColor = lockedNodeColor;
        } else {
            paint.setColor(unlockedNodeColor);
            tempColor = unlockedNodeColor;
        }
        mCanvas.drawCircle(coordinate.getX(), coordinate.getY(), radius, paint);
        paint.setColor(nodeTextColor);
        mCanvas.drawText(text, coordinate.getX(), coordinate.getY() + (textSize * 0.35f), paint);
        paint.setColor(tempColor);
    }

    public void drawConnector(Coordinate c1, Coordinate c2, boolean locked) {
        if (locked) {
            paint.setColor(lockedNodeColor);
        } else {
            paint.setColor(unlockedNodeColor);
        }
        float x1 = c1.getX();
        float x2 = c2.getX();
        float y1 = c1.getY();
        float y2 = c2.getY();
        // INSANE TRIG
        double angle = Math.abs(Math.atan(((y2 - y1)/(x2 - x1))));
        if (y2 > y1 && x2 > x1) { // first quadrant
            angle += 0;
        } else if (y2 > y1 && x2 < x1) {
            angle = Math.PI - angle;
        } else if (y2 < y1 && x2 < x1) {
            angle += Math.PI;
        } else if (y2 < y1 && x2 > x1) {
            angle = 2.0 * Math.PI - angle;
        }
        mCanvas.drawLine((float)(x1 + radius * Math.cos(angle)), (float)(y1 + radius * Math.sin(angle)), (float)(x2 + radius * Math.cos(angle)), (float)(y2 + radius * Math.sin(angle)), paint);
    }

    public void autoScale() {
        float factor = (float)scaling / (float)increment;
        radius = mRadius * factor;
        strokeWidth = mStrokeWidth * factor;
        textSize = mTextSize * factor;
    }
}
