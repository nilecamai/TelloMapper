package riverflow.tellomapper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button editButton = view.findViewById(R.id.edit_home_button);
        Button flyButton = view.findViewById(R.id.fly_home_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEdit(getView());
            }
        });
        flyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFly(getView());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void goToEdit(View v) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        fragment = new EditFragment();
        fragmentTransaction.replace(R.id.content_main, fragment, "Edit").commit();
    }
    public void goToFly(View v) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        fragment = new FlyFragment();
        fragmentTransaction.replace(R.id.content_main, fragment, "Fly").commit();
    }

}
