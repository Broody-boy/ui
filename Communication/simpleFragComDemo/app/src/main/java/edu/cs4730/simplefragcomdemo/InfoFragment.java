package edu.cs4730.simplefragcomdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * a simple fragment to display data.
 */
public class InfoFragment extends Fragment {
    TextView label;
    int num = 0;

    public InfoFragment() {
        // Required empty public constructor
    }

    public void update(int i) {
        num = num + i;
        if (label != null)
            label.setText("Number of clicks: " + num);
        else
            Log.v("Info", "label is null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_info, container, false);

        label = myView.findViewById(R.id.numclick);
        label.setText("Number of clicks: " + num);

        return myView;
    }
}
