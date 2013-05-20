package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class HelpActivity extends BaseActivity {

	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initStartUpCheck();
		
		Button contButton = (Button) findViewById(R.id.help_continueButton);
		contButton.setOnClickListener(new ContButtonListener());
		
	}
	
	//set the check box state
	private void initStartUpCheck()
	{
		CheckBox cb = (CheckBox) findViewById(R.id.help_showAtStartCheck);
		
		//if settings contains help then set the check box to match 
		//the saved value;
		//else set to true
		if(settings.contains(SETTINGS_PREFS_HELP))
		{
			cb.setChecked(settings.getBoolean(SETTINGS_PREFS_HELP, true));
		}
		else
		{
			cb.setChecked(true);
		}
		
	}

	private class ContButtonListener implements View.OnClickListener
	{
		public void onClick(View v) 
		{
			//save the status of the check box to settings
			saveCheckStatus();
			
			
			//ComponentName sender = getCallingActivity();
			//String s = sender.getShortClassName().toString();
			//getIntent().getStringExtra("from").equals("activity1")
			//Toast myToast = Toast.makeText(HelpActivity.this, getIntent().getStringExtra("sender"), Toast.LENGTH_LONG);
			//myToast.show();
			
			
			//if the sender is the SettingsActivity then return to settings, else go to the home screen
			if(getIntent().getStringExtra("sender").equals("SettingsActivity"))
			{
				//go to settings activity
				startActivity(new Intent(HelpActivity.this, SettingsActivity.class));
			}
			else
			{
				//go to main activity
				startActivity(new Intent(HelpActivity.this, MainActivity.class));
			}
			
		}
		
	}
	
	//save the state of the check box to settings
	private void saveCheckStatus()
	{
		CheckBox cb = (CheckBox) findViewById(R.id.help_showAtStartCheck);
		
		Editor editor = settings.edit();
		editor.putBoolean(SETTINGS_PREFS_HELP, cb.isChecked());
		editor.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

}
