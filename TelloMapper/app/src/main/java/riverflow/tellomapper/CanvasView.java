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
    private int radius = 10;

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

    public void drawCoordinate(Coordinate c, ArrayList<Coordinate> cs) {
        coordinates = new ArrayList<Coordinate>(cs);
        coordinate = new Coordinate(c);

        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        super.onDraw(mCanvas);
        // fencepost algorithm for drawing
        if (coordinates != null) {
            if (coordinates.size() > 0) {
                Coordinate tempCoordinate = coordinates.get(0);
                Coordinate prevCoordinate;
                mCanvas.drawCircle(tempCoordinate.getX() - (int)(radius / 2.0), tempCoordinate.getY() - (int)(radius / 2.0), radius, paint);
                for (int i = 1; i < coordinates.size(); i++) {
                    tempCoordinate = coordinates.get(i);
                    prevCoordinate = coordinates.get(i-1);
                    paint.setColor(Color.rgb(123, 239, 178)); // Aquamarine green
                    mCanvas.drawLine(prevCoordinate.getX(), prevCoordinate.getY(), tempCoordinate.getX(), tempCoordinate.getY(), paint);
                    mCanvas.drawCircle(tempCoordinate.getX() - (int)(radius / 2.0), tempCoordinate.getY() - (int)(radius / 2.0), radius, paint);
                }
                paint.setColor(Color.rgb(107, 185, 240)); // Malibu blue
                mCanvas.drawLine(tempCoordinate.getX(), tempCoordinate.getY(), coordinate.getX(), coordinate.getY(), paint);
            }
        }
        if (coordinate != null) {
            paint.setColor(Color.rgb(107, 185, 240)); // Malibu blue
            mCanvas.drawCircle(coordinate.getX() - (int)(radius / 2.0), coordinate.getY() - (int)(radius / 2.0), radius, paint);
        }
    }
}
