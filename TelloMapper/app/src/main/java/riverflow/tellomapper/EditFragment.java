package riverflow.tellomapper;


import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    // why do i have to put these up here again?
    int test;

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // variables n stuff
        Path path = new Path();
        final Coordinate tempCoordinate = new Coordinate();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        View canvasView = view.findViewById(R.id.canvasView);
        final TextView coordTextView = view.findViewById(R.id.coordTextView);
        final ToggleButton newNodeToggleButton = view.findViewById(R.id.newNodeToggleButton);

        // get rid of this one here and in xml code when done
        final TextView testTextView = view.findViewById(R.id.testTextView);
        testTextView.setText(test + " ");

        // set the edit box
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        canvasView.getLayoutParams().width = size.x * 7 / 8;
        if (canvasView.getLayoutParams().width <= size.y / 2) {
            canvasView.getLayoutParams().height = canvasView.getLayoutParams().width;
        } else {
            canvasView.getLayoutParams().height = size.y / 2;
        }
        final int canvasWidth = canvasView.getLayoutParams().width;
        final int canvasHeight = canvasView.getLayoutParams().height;

                canvasView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // get the coordinates
                /*
                x = event.getX();
                y = event.getY();
                */
                tempCoordinate.setX(event.getX());
                tempCoordinate.setY(event.getY());
                // display coordinates
                if (tempCoordinate.getX() >= 0 && tempCoordinate.getY() >= 0 && tempCoordinate.getX() <= canvasWidth && tempCoordinate.getY() <= canvasWidth) {
                    coordTextView.setText("Coordinates:\n" + tempCoordinate);
                }

                // let's draw pretty pictures
                // jk let's test something out
                testTextView.setText(test + " ");

                if (newNodeToggleButton.isChecked()) {
                    // coordTextView.setText("WAHOO\nx:\t" + (int)x + "\ny:\t" + (int)y);
                    coordTextView.setText("Coordinates:\n" + tempCoordinate);
                }
                return true;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        return view;
    }

}
