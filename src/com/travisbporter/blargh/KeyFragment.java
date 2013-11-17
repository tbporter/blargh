package com.travisbporter.blargh;

import java.io.UnsupportedEncodingException;
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
import android.widget.Toast;

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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == 1337 && data != null){
			String result=data.getStringExtra("result");
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}
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
			Intent i = new Intent(_activity, Beam.class);
			i.putExtra("payload",Crypt._keyPair.getPublic().getEncoded());
			startActivityForResult(i, 1);
		}
		
	};
}
