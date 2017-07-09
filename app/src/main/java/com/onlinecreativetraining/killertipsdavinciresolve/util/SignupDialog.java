package com.onlinecreativetraining.killertipsdavinciresolve.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onlinecreativetraining.killertipsdavinciresolve.DataModel.Constants;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.common.Functions;

import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.lists.ListMethods;

public class SignupDialog extends Dialog implements OnClickListener {

	EditText nEmail;
	EditText nFirst;
	EditText nLast;
	EditText nCompany;

	public SignupDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newsletter_layout);
		this.setTitle("Newsletter Signup");

		nEmail = (EditText)findViewById(R.id.newsletter_email);
		nFirst = (EditText)findViewById(R.id.newsletter_firstname);
		nLast = (EditText)findViewById(R.id.newsletter_lastename);
		nCompany = (EditText)findViewById(R.id.newsletter_company);
		TextView nSignup = (TextView)findViewById(R.id.newsletter_signup);
		TextView nCancel = (TextView)findViewById(R.id.newsletter_cancel);

		nSignup.setOnClickListener(this);
		nCancel.setOnClickListener(this);

	}

	public void onClick(View clicked) {
    	Log.d(this.getClass().getName(), "Clicked: " + clicked.toString());
    	
    	switch (clicked.getId()) {
    	case R.id.newsletter_cancel:
    		this.cancel();
    		break;
    	case R.id.newsletter_signup:
			final String email = nEmail.getText().toString();
			String firstName = nFirst.getText().toString();
			String lastName = nLast.getText().toString();
			String company = nCompany.getText().toString();

			if(!Functions.isValidEmail(email)){
				Toast.makeText(getContext(), "Please input email correctly!", Toast.LENGTH_SHORT).show();
				return;
			}
			if(firstName.equals("")){
				Toast.makeText(getContext(), "Please input first name correctly!", Toast.LENGTH_SHORT).show();
				return;
			}
			if(lastName.equals("")){
				Toast.makeText(getContext(), "Please input last name correctly!", Toast.LENGTH_SHORT).show();
				return;
			}
        	// show progress dialog
    		final ProgressDialog progressDialog = ProgressDialog.show(getContext(),
    				"Uploading to MailChimp", "Adding e-mail to MailChimp", true, false);// not cancelable, TODO: this dialog needs to be accessible elsewhere
    		
    		Runnable run = new Runnable() {
    			public void run() {
    	    		if (email != null && email.trim().length() > 0) {
	    				addToList(email, progressDialog);
    	    		}
    			}
    		};
    		(new Thread(run)).start();
    		
    		break;
    		default:
    			Log.e("MailChimp", "Unable to handle onClick for view " + clicked.toString());
    	}
	}
    
private void addToList(String emailAddy, final ProgressDialog progressDialog) {
    	
		ListMethods listMethods = new ListMethods(Constants.apiKey);
		String message = "Signup successful!";
		try {
			listMethods.listSubscribe(Constants.chimpID, emailAddy);
		} catch (MailChimpApiException e) {
			Log.e("MailChimp", "Exception subscribing person: " + e.getMessage());
			message = "Signup failed: " + e.getMessage();
		} finally {
			progressDialog.dismiss();
			this.dismiss();			
			showResult(message);
		}
		
    }

	private void showResult(final String message) {
		Runnable run = new Runnable() {
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
				builder.setMessage(message).setPositiveButton("OK", new Dialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		};
		getOwnerActivity().runOnUiThread(run);
	}
}
