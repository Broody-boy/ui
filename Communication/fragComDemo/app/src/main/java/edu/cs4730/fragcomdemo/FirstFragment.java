package edu.cs4730.fragcomdemo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link FirstFragment.OnFragmentInteractionListener1}
 * interface to handle interaction events. Use the
 * {@link FirstFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class FirstFragment extends Fragment {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";


	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener1 mListener;


	
	TextView tv1, tv2;
	Button btn1;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment FirstFragment.
	 */

	public static FirstFragment newInstance(String param1, String param2) {
		FirstFragment fragment = new FirstFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FirstFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View myView =inflater.inflate(R.layout.fragment_first, container, false);
		
		tv1 = (TextView) myView.findViewById(R.id.ff_tv1);
		tv1.setText("Parameter1: "+mParam1);
		tv2 = (TextView) myView.findViewById(R.id.ff_tv2);
		tv2.setText("Parameter2: "+mParam2);
		btn1 = (Button) myView.findViewById(R.id.ff_btn1);
		btn1.setOnClickListener( new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					//this is calling the interface, which call into the activity, so it 
					//can change to the first fragment and send a simple string as well.
					mListener.onFragmentInteraction1("Called by FirstFramgnet");
				}
			}
		});
		
		
		return myView;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Activity activity = getActivity();
		try {
			mListener = (OnFragmentInteractionListener1) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener1 {
		void onFragmentInteraction1(String Data);
	}

}
