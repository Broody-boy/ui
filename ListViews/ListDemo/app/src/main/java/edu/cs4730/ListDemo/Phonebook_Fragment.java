package edu.cs4730.ListDemo;

/*
 * This code is copied from http://techdroid.kbeanie.com/2009/07/custom-listview-for-android.html
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.Fragment;

public class Phonebook_Fragment extends Fragment {

    String TAG = "Phone_Fragment";

    public Phonebook_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.phonebook_fragment, container, false);

        ListView list = myView.findViewById(R.id.ListView01);
        list.setClickable(true);

        final List<Phonebook_DataModel> listOfPhonebook = new ArrayList<Phonebook_DataModel>();
        listOfPhonebook.add(new Phonebook_DataModel("Test", "9981728", "test@test.com"));
        listOfPhonebook.add(new Phonebook_DataModel("Test1", "1234455", "test1@test.com"));
        listOfPhonebook.add(new Phonebook_DataModel("Test2", "00000", "test2@test.com"));
        listOfPhonebook.add(new Phonebook_DataModel("Test3", "00000", "test3@test.com"));
        listOfPhonebook.add(new Phonebook_DataModel("Test4", "00000", "test4test.com"));
        listOfPhonebook.add(new Phonebook_DataModel("Test5", "00000", "test5@test.com"));
        listOfPhonebook.add(new Phonebook_DataModel("Test6", "00000", "test6@test.com"));

        Phonebook_myAdapter adapter = new Phonebook_myAdapter(requireContext(), listOfPhonebook);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                //showToast(listOfPhonebook.get(position).getName());
                Toast.makeText(requireContext(), listOfPhonebook.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });

        list.setAdapter(adapter);

        return myView;
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }


}

