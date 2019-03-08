package riverflow.tellomapper;


import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    Button addNodeButton;
    Button removeNodeButton;
    TextView coordTextView;
    TextView testTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // variables n stuff
        Path path = new Path();
        final Coordinate tempCoordinate = new Coordinate();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        View canvasView = view.findViewById(R.id.canvas_view);
        coordTextView = view.findViewById(R.id.coord_text_view);

        // get rid of this one here and in xml code when done
        testTextView = view.findViewById(R.id.test_text_view);
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

                return true;
            }
        });

        // button stuff
        ToggleButton editToggleButton = view.findViewById(R.id.edit_toggle_button);
        addNodeButton = view.findViewById(R.id.add_node_button);
        removeNodeButton = view.findViewById(R.id.remove_node_button);
        addNodeButton.setEnabled(false);
        removeNodeButton.setEnabled(false);

        return view;
    }

    public void changeEditState(View view) {
        ToggleButton toggleButton = (ToggleButton)view;
        boolean checked = toggleButton.isChecked();
        if (checked) {
            testTextView.setText("HLOLOLSDHFLIHESKJHDFLKUEHE");
        }
    }

}
