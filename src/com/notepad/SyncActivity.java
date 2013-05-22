package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SyncActivity extends BaseActivity {

	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initEditText(R.id.sync_email, SETTINGS_PREFS_SYNC_EMAIL);
		initEditText(R.id.sync_password, SETTINGS_PREFS_SYNC_PASSWORD);
		initEditText(R.id.sync_deviceName, SETTINGS_PREFS_SYNC_DEVICE);
		
		initRadioGroup(SETTINGS_PREFS_SYNC_TEXTANDPHOTOS);
		initCheckBox(R.id.sync_checkPlainText, SETTINGS_PREFS_SYNC_AGREE);
		
		
		Button submitButton = (Button) findViewById(R.id.sync_submitButton);
		submitButton.setOnClickListener(new submitButtonListener());
		
		
	}

	public void initEditText(int id, String prefKey)
	{
		EditText et = (EditText) findViewById(id);
		
		//if the note title is set and it is not a new note
		if(settings.contains(prefKey) && !settings.getString(prefKey, "").equals("New Note"))
		{
			et.setText(settings.getString(prefKey, ""));
		}
	}
	
	//set the check box state
	private void initCheckBox(int id, String prefKey)
	{
		CheckBox cb = (CheckBox) findViewById(id);
		
		if(settings.contains(prefKey))
		{
			cb.setChecked(settings.getBoolean(prefKey, false));
		}
		else
		{
			cb.setChecked(false);
		}
		
	}
	
	//Select the correct radio button
	public void initRadioGroup(String prefKey)
	{
		if(settings.contains(prefKey))
		{
			int id = settings.getInt(prefKey, 0);
			RadioButton radioButton = (RadioButton) findViewById(id);
			radioButton.setChecked(true);
		}
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
				savePref(SETTINGS_PREFS_SYNC_EMAIL, R.id.sync_email);
				savePref(SETTINGS_PREFS_SYNC_PASSWORD, R.id.sync_password);
				savePref(SETTINGS_PREFS_SYNC_DEVICE, R.id.sync_deviceName);
				
				//saveRadioStatus(SETTINGS_PREFS_SYNC_TEXTANDPHOTOS, R.id.sync_options);
				saveRadioToPrefs(R.id.sync_options, SETTINGS_PREFS_SYNC_TEXTANDPHOTOS);
				
				saveCheckStatus(SETTINGS_PREFS_SYNC_AGREE, R.id.sync_checkPlainText);
				
				startActivity(new Intent(SyncActivity.this, SettingsActivity.class));
			}
			
		}
	}
	
	//saves the text from an EditText to settings
	public void savePref(String prefKey, int id)
	{
		EditText editText = (EditText)findViewById(id);
		String text = editText.getText().toString().trim();
		
		Editor editor = settings.edit();
		editor.putString(prefKey, text);
		editor.commit();
	}
	
	//save the state of the check box to settings
	private void saveCheckStatus(String prefKey, int Id)
	{
		CheckBox cb = (CheckBox) findViewById(Id);
		
		Editor editor = settings.edit();
		editor.putBoolean(prefKey, cb.isChecked());
		editor.commit();
	}

	//save the state of the check box to settings
	private void saveRadioStatus(String prefKey, int Id)
	{
		RadioGroup rg = (RadioGroup) findViewById(Id);
		
		Editor editor = settings.edit();
		editor.putInt(prefKey, rg.getCheckedRadioButtonId());
		editor.commit();
	}
		
	public void saveRadioToPrefs(int radioGroupId, String prefKeyId)
	{
		RadioGroup radioGroup = (RadioGroup)findViewById(radioGroupId);
		int selectedId = radioGroup.getCheckedRadioButtonId();
		
		Editor editor = settings.edit();
		editor.putInt(prefKeyId, selectedId);
		editor.commit();
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
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.sync, menu);
		return true;
	}

}
