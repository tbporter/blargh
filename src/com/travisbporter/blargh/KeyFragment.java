package com.travisbporter.blargh;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class KeyFragment extends Fragment {
	
	Button _gen;
	Button _beam;
	Activity _activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_main_keygen,
				container, false);

		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle saved){
		super.onActivityCreated(saved);
		_activity = getActivity();
		_gen = (Button) getActivity().findViewById(R.id.button_gen);
		_gen.setOnClickListener(_genKeyListener);
		
		_beam = (Button) getActivity().findViewById(R.id.button_beam);
		_beam.setOnClickListener(_beamListener);
	}
	
	private OnClickListener _genKeyListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
		    try {
		    	Crypt.genKey();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	};
	
	private OnClickListener _beamListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent i = new Intent(_activity, Beam.class);;
			startActivity(i);
		}
		
	};
}
