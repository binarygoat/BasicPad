package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SyncActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		
		Button submitButton = (Button) findViewById(R.id.sync_submitButton);
		submitButton.setOnClickListener(new submitButtonListener());
	}

	
	private class submitButtonListener implements View.OnClickListener 
	{
		public void onClick(View v) 
		{
			boolean validEmail = validateEmail(R.id.sync_email);
			boolean validPassword = validateEditText(R.id.sync_password);
			boolean validDeviceName = validateEditText(R.id.sync_deviceName);
			
			boolean validOptions = validateRadio(R.id.sync_options, R.id.sync_errorOptions);
			boolean validCheckPlainText = validateCheck(R.id.sync_checkPlainText, R.id.sync_errorCheckPlainText);
			
			//if everything is valid then toast
			if(validEmail && validPassword && validDeviceName && validOptions && validCheckPlainText)
			{
				//Toast myToast = Toast.makeText(SyncActivity.this, "All feilds are valid", Toast.LENGTH_LONG);
				//myToast.show();
				
				startActivity(new Intent(SyncActivity.this, SettingsActivity.class));
			}
			
		}
	}
	

	public boolean validateEmail(int editTextId)
	{
		boolean retVal = false;
	
		//get text from text box
		EditText editText = (EditText)findViewById(editTextId);
		String text = editText.getText().toString().trim();
		
		//correct email evaluation will be added later
		if(text.length() < 5)
		{
			editText.setText("");
			EditTextError(editTextId, "*Please enter a valid address");
			retVal = false;
		}
		else
		{
			
			retVal = true;
		}
		
		return retVal;
	}
	
	
	public boolean validateEditText(int editTextId)
	{
		boolean retVal = false;
		
		//get text from text box
		EditText editText = (EditText)findViewById(editTextId);
		String text = editText.getText().toString().trim();
		
		if(text.length() < 2)
		{
			editText.setText("");
			EditTextError(editTextId, "*Required");
			retVal = false;
		}
		else
		{
			
			retVal = true;
		}
		
		return retVal;
	}
	
	public void EditTextError(int editTextId, String message)
	{
		EditText editText = (EditText) findViewById(editTextId);
		editText.setHint(message);
		editText.setHintTextColor(getResources().getColor(R.color.error));
	}
	
	public boolean validateRadio(int radioId, int errorTextViewId)
	{
		boolean retVal = false;
		
		//get text from text box
		RadioGroup radioGroup = (RadioGroup)findViewById(radioId);
		//String text = editText.getText().toString().trim();
		TextView errorTV = (TextView)findViewById(errorTextViewId);
		
		if(radioGroup.getCheckedRadioButtonId() == -1)
		{
			errorTV.setVisibility(View.VISIBLE);
			retVal = false;
		}
		else
		{
			errorTV.setVisibility(View.INVISIBLE);
			retVal = true;
		}
		
		return retVal;
	}
	

	public boolean validateCheck(int checkId, int errorTextViewId)
	{
		boolean retVal = false;
		
		//get text from text box
		CheckBox checkBox = (CheckBox)findViewById(checkId);
		//String text = editText.getText().toString().trim();
		TextView errorTV = (TextView)findViewById(errorTextViewId);
		
		if(!checkBox.isChecked())
		{
			errorTV.setVisibility(View.VISIBLE);
			retVal = false;
		}
		else
		{
			errorTV.setVisibility(View.INVISIBLE);
			retVal = true;
		}
		
		return retVal;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sync, menu);
		return true;
	}

}
