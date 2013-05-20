package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditActivity extends BaseActivity 
{

	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initEditText(R.id.edit_titleText, SETTINGS_PREFS_CURRENT_NOTE);
		
		ImageButton doneButton = (ImageButton) findViewById(R.id.edit_doneButton);
		doneButton.setOnClickListener(new DoneButtonListener());
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
	
	private class DoneButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			save();
			startActivity(new Intent(EditActivity.this, MainActivity.class));
		}
	}
	
	//save the note
	public void save()
	{
		//update the database
	}
	
	//runs when the back button is pressed
	public void onBackPressed()
	{
		save();
		super.onBackPressed();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	

}
