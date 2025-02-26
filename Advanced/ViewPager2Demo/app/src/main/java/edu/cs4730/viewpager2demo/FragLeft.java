package edu.cs4730.viewpager2demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * This is a simple fragment to display data and it the "left" most fragment in the viewpager.
 *   The code here is identical to the code in the right fragment.
*/


public class FragLeft extends Fragment {
    TextView tx;
    DataViewModel mViewModel;
    String TAG = "Left";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        if (savedInstanceState != null) {
            Log.d(TAG, "OnCreate savedInstanceState");
        }
        mViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Left", "OnCreateView");
        View view = inflater.inflate(R.layout.left, container, false);
        tx = view.findViewById(R.id.tvleft);


        mViewModel.getDataLeft().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String data) {
                tx.setText(data);
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "OnSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}
