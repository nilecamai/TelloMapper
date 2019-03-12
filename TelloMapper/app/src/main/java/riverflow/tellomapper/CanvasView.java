package riverflow.tellomapper;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {
    Paint paint = new Paint();
    public static Canvas mCanvas;
    private int x;
    private int y;
    private int radius = 10;
    private boolean preview;

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

    public void drawCoordinate(boolean preview, Coordinate c) {
        this.preview = preview;
        x = (int)c.getX();
        y = (int)c.getY();

        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        super.onDraw(mCanvas);
        if (preview) {
            //paint.setColor(Color.parseColor("#6BB9F0")); // Malibu blue
            paint.setColor(Color.rgb(107, 185, 240)); // Malibu blue
        } else {
            //paint.setColor(Color.parseColor("7BEFB2")); // Aquamarine green
            paint.setColor(Color.rgb(123, 239, 178)); // Aquamarine green
        }
        mCanvas.drawCircle(x - (int)(radius / 2.0), y - (int)(radius / 2.0), radius, paint);
    }
}
