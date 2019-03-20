package riverflow.tellomapper;


import android.app.AlertDialog;
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
import android.widget.EditText;
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

import fly.Fly;


/**
 * A simple {@link Fragment} subclass.
 */
public class FlyFragment extends Fragment {


    public FlyFragment() {
        // Required empty public constructor
    }

    public static final String docPath = "tello_paths";
    Button loadButton;
    Button flyButton;
    Button deleteButton;
    Spinner fileSpinner;
    String data;
    List<String> fileNames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fly, container, false);
        loadButton = view.findViewById(R.id.load_button);
        flyButton = view.findViewById(R.id.fly_button);
        deleteButton = view.findViewById(R.id.delete_button);
        fileSpinner = view.findViewById(R.id.file_spinner);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(getView());
                updateButtons();
            }
        });

        loadButton.setEnabled(false);
        flyButton.setEnabled(false);
        deleteButton.setEnabled(false);
        updateSpinner();
        updateButtons();
        flyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data != null) {
                    fly.Fly.fly(data);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.delete_overwrite_dialog, null);
                Button cancelButton = mView.findViewById(R.id.cancel_delete_button);
                Button confirmButton = mView.findViewById(R.id.confirm_delete_button);
                TextView warningLabel = mView.findViewById(R.id.delete_overwrite_message);
                if (fileSpinner.getSelectedItem() != null) {
                    warningLabel.setText("Are you sure you want to delete \"" + fileSpinner.getSelectedItem().toString() + "\"?");
                }

                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(getView());
                        updateSpinner();
                        updateButtons();
                        dialog.cancel();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });


        return view;
    }

    public void updateSpinner() {
        File dir = getContext().getDir(docPath, Context.MODE_PRIVATE);
        // Toast.makeText(getContext(), dir.toString(), Toast.LENGTH_SHORT).show(); // /storage
        File[] files = dir.listFiles();
        fileNames = new ArrayList<String>();

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

    public void updateButtons() {
        if (data != null) {
            flyButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            flyButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
        if (fileNames != null) {
            if (fileNames.size() > 0) {
                loadButton.setEnabled(true);
            } else {
                loadButton.setEnabled(false);
            }
        } else {
            loadButton.setEnabled(false);
        }
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

            data = sb.toString();
            //Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();
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

    public void delete(View v) {
        try {
            String name = getContext().getDir(docPath, Context.MODE_PRIVATE) + "/" + fileSpinner.getSelectedItem().toString();
            File file = new File(name);
            file.delete();
            data = null;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public File getPublicDocStorageDir(String docName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), docName);
        if (!file.mkdirs()) {
            Log.e("Waba plorp", "Dirctory not created");
        }
        return file; //dab
    }

}
