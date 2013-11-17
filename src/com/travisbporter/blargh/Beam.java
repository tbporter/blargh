package com.travisbporter.blargh;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


public class Beam extends Activity implements CreateNdefMessageCallback {
    NfcAdapter _nfc;
    TextView _textView;
    Button _send;
    Button _recv;
    Activity _activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beam);
        _textView = (TextView) findViewById(R.id.tv_beam_rcv);
        // Check for available NFC Adapter
        _nfc = NfcAdapter.getDefaultAdapter(this);
        if (_nfc == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        _activity = this;
        
        _send = (Button) findViewById(R.id.button_beam_send);
        _send.setOnClickListener(_sendListener);
        
        _recv = (Button) findViewById(R.id.button_beam_recv);
        _recv.setOnClickListener(_recvListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        //textView = (TextView) findViewById(R.id.textView);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        try {
			_textView.setText(Crypt.byteToString(msg.getRecords()[0].getPayload()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /*Intent returnIntent = new Intent();
        returnIntent.putExtra("result",msg.getRecords()[0].getPayload().toString());
        setResult(1337,intent);     
        finish();*/
    }
    
    private OnClickListener _sendListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			_nfc.setNdefPushMessage(createNdefMessage(null),_activity);
			Toast.makeText(_activity, "PushMessage", Toast.LENGTH_LONG).show();

		}
		
	};
	
    private OnClickListener _recvListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			_nfc.setNdefPushMessage(null, _activity);
		}
		
	};
	
	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
        NdefMessage msg = new NdefMessage(NdefRecord.createMime( "application/com.travisbporter.blargh",getIntent().getByteArrayExtra("payload")));
        return msg;
	}
}