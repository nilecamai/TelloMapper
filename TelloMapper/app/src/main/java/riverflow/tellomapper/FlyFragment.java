package riverflow.tellomapper;


import android.content.Context;
import android.drm.DrmInfoRequest;
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

    public static final String docPath = "tello_paths";
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
                //testLoad();
            }
        });

        flyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "POOPITY SCOOP", Toast.LENGTH_SHORT).show();
            }
        });

        updateSpinner();

        return view;
    }

    public void updateSpinner() {
        File dir = getContext().getDir(docPath, Context.MODE_PRIVATE);
        // Toast.makeText(getContext(), dir.toString(), Toast.LENGTH_SHORT).show(); // /storage
        File[] files = dir.listFiles();
        List<String> fileNames = new ArrayList<String>();

        for (File file: files) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
            // Toast.makeText(getContext(), file.toString(), Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, fileNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileSpinner.setAdapter(adapter);
    }

    public void testLoad() {
        Toast.makeText(getContext(), getContext().getDir(docPath, Context.MODE_PRIVATE) + fileSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    public void load(View v) {
        FileInputStream fis = null;

        try {
            String name = getContext().getDir(docPath, Context.MODE_PRIVATE) + "/" + fileSpinner.getSelectedItem().toString();
            File file = new File(name);
            //fis = getContext().openFileInput(fileSpinner.getSelectedItem().toString());
            //fis = getContext().openFileInput("test.dat"); // hardcoded for, well, testing
            fis = new FileInputStream(file);

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

    public void fly() {
        // call go code
    }

    public File getPublicDocStorageDir(String docName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), docName);
        if (!file.mkdirs()) {
            Log.e("Waba plorp", "Dirctory not created");
        }
        return file; //dab
    }

}
