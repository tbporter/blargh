package com.travisbporter.blargh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class ChatFragment extends Fragment {

	Spinner _contactSpinner;
	List<Contact> _contacts;
	
	public ChatFragment(){
		_contacts = new ArrayList<Contact>();
		_contacts.add(new Contact("Derp","derp23","www.",null));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_main_chat,
				container, false);

		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle saved){
		super.onActivityCreated(saved);
		
		_contactSpinner = (Spinner) getActivity().findViewById(R.id.spin_contacts);
		_contactSpinner.setAdapter(new Contact.ContactAdapter(getActivity(),_contacts));
		
		
	}
	

}