package com.travisbporter.blargh;

import java.security.PublicKey;
import java.util.List;

import android.app.Activity;
import android.database.DataSetObserver;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class Contact {


	public String _nick;
	public String _userName;
	public String _url;
	public PublicKey _pubKey;
	
	public Contact(String nick, String username, String url, PublicKey pubKey){
		_nick = nick;
		_userName = username;
		_url = url;
		_pubKey = pubKey;
	}
	
	static public class ContactAdapter implements SpinnerAdapter{
	    private Activity _activity;
	    private List<Contact> _contacts; 
	    
	    public ContactAdapter(Activity activity, List<Contact> contacts){
	    	_activity = activity;
	    	_contacts = contacts;
	    }
	    @Override
	    public int getCount() {
	        return _contacts.size();
	    }
	    @Override
	    public Object getItem(int position) {
	        return _contacts.get(position);
	    }

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEmpty() {
			return _contacts.isEmpty();
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public View getDropDownView(int position, View arg1, ViewGroup arg2) {
			TextView text = new TextView(_activity);
			text.setText(_contacts.get(position)._nick);
			return text;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			TextView text = new TextView(_activity);
			text.setText(_contacts.get(position)._nick);
			return text;
		}
	}
}
