package riverflow.tellomapper;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FlyFragment extends Fragment {


    public FlyFragment() {
        // Required empty public constructor
    }

    TextView fileIndicator;
    Button loadButton;
    Button flyButton;
    Spinner fileSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fly, container, false);
        fileIndicator = view.findViewById(R.id.file_indicator);
        loadButton = view.findViewById(R.id.load_button);
        flyButton = view.findViewById(R.id.fly_button);
        fileSpinner = view.findViewById(R.id.file_spinner);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(getView());
            }
        });

        flyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "POOPITY SCOOP", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void load(View v) {
        FileInputStream fis = null;
        try {

            String path = Environment.getDataDirectory().toString(); // we need to get to /data/user/0/riverflow.tellomapper/files/test; fix .getDataDirectory()
            File directory = new File(path);
            File[] files = directory.listFiles();
            List<String> fileNames = new ArrayList<String>();
            for (File file: files) { // NullPointerException
                fileNames.add(file.toString());
                Toast.makeText(getContext(), file.toString(), Toast.LENGTH_SHORT).show();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, fileNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fileSpinner.setAdapter(adapter);

            Toast.makeText(getContext(), files.toString(), Toast.LENGTH_SHORT).show();

            fis = getContext().openFileInput("test"); // hardcoded for, well, testing

            // sb just makes a string to display, delete later
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_LONG).show();
            //

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
