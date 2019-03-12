package riverflow.tellomapper;


import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    // why do i have to put these up here again?

    public EditFragment() {
        // Required empty public constructor
    }

    CanvasView canvasView;
    Button addNodeButton;
    Button removeNodeButton;
    Button saveButton;
    TextView coordTextView;
    TextView previewTextView;
    ScrollView previewScrollView;
    Coordinate tempCoordinate = null;
    ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // variables n stuff
        Path path = new Path();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        canvasView = view.findViewById(R.id.canvas_view);
        coordTextView = view.findViewById(R.id.coord_text_view);


        previewScrollView = view.findViewById(R.id.preview_scroll_view);
        // preview text view is here to stay
        previewTextView = view.findViewById(R.id.preview_text_view);

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

                tempCoordinate = new Coordinate();
                tempCoordinate.setX(event.getX());
                tempCoordinate.setY(event.getY());
                // display coordinates
                if (tempCoordinate.getX() >= 0 && tempCoordinate.getY() >= 0 && tempCoordinate.getX() <= canvasWidth && tempCoordinate.getY() <= canvasWidth) {
                    coordTextView.setText("Coordinates:\n" + tempCoordinate);
                }

                // let's draw pretty pictures
                canvasView.drawCoordinate(tempCoordinate, coordinates);
                // jk let's test something out

                return true;
            }
        });

        // button stuff
        ToggleButton editToggleButton = view.findViewById(R.id.edit_toggle_button);
        editToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton toggleButton = (ToggleButton)v;
                boolean checked = toggleButton.isChecked();
                if (checked) {
                    // previewTextView.setText("HLOLOLSDHFLIHESKJHDFLKUEHE");
                    setNodeButtonState(true);
                } else {
                    setNodeButtonState(false);
                }

            }
        });
        addNodeButton = view.findViewById(R.id.add_node_button);
        removeNodeButton = view.findViewById(R.id.remove_node_button);
        setNodeButtonState(false);
        addNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempCoordinate != null) {
                    // draw STUFF
                    coordinates.add(new Coordinate(tempCoordinate));
                    // tempCoordinate = null;
                    displayCoordinatesPreview();
                } else {
                    previewTextView.setText("No coordinate specified.");
                }
                setNodeButtonState(true);
                autoSetSaveButtonState();

                canvasView.drawCoordinate(tempCoordinate, coordinates);
            }
        });
        removeNodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                // THIS IS NOT ACTUALLY WHAT IT'S SUPPOSED TO DO I just didn't want to make another button
                previewTextView.setText(coordinates + "");
                */
                    coordinates.remove(coordinates.size()-1);
                displayCoordinatesPreview();
                setNodeButtonState(true);
                autoSetSaveButtonState();

                canvasView.drawCoordinate(tempCoordinate, coordinates);
            }
        });
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public void setNodeButtonState(boolean bool) {
        addNodeButton.setEnabled(bool);
        if (!coordinates.isEmpty() && bool) {
            removeNodeButton.setEnabled(true);
        } else {
            removeNodeButton.setEnabled(false);
        }
    }

    public void autoSetSaveButtonState() {
        if (coordinates.isEmpty()) {
            saveButton.setEnabled(false);
        } else {
            saveButton.setEnabled(true);
        }
    }

    public void displayCoordinatesPreview() {
        String result = "Current coordinates:\n";
        for (Coordinate coordinate : coordinates) {
            result += coordinate + "\n";
        }
        previewTextView.setText(result);
    }

}
