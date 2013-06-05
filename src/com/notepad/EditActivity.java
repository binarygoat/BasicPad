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
	private Note currentNote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		getNote();
		
		initEditText(R.id.edit_titleText, currentNote.getTitle());
		initEditText(R.id.edit_bodyText, currentNote.getBody());
		
		ImageButton doneButton = (ImageButton) findViewById(R.id.edit_doneButton);
		doneButton.setOnClickListener(new DoneButtonListener());
	}

	private void getNote()
	{
		//if the id is not set to 0 then load the note from the database
		//else it is a new note
		if(settings.getInt(SETTINGS_PREFS_CURRENT_NOTE, 0) != 0)
		{
			DatabaseHandler dh = new DatabaseHandler(this);
			currentNote = dh.getTextNote(settings.getInt(SETTINGS_PREFS_CURRENT_NOTE, 0));
		}
		else
		{
			currentNote = new Note("", 0, DatabaseHandler.TEXT_TYPE, 0);
		}
	}
	
	private void initEditText(int id, String text)
	{
		EditText et = (EditText) findViewById(id);
		
		et.setText(text);
		
		//if the note title is set and it is not a new note
		//if(settings.contains(prefKey) && !settings.getString(prefKey, "").equals("New Note"))
		//{
			//et.setText(settings.getString(prefKey, ""));
		//}
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
		EditText titleET = (EditText) findViewById(R.id.edit_titleText);
		EditText bodyET = (EditText) findViewById(R.id.edit_bodyText);
		
		currentNote.setTitle(titleET.getText().toString());
		currentNote.setBody(bodyET.getText().toString());
		
		//update the database
		DatabaseHandler dh = new DatabaseHandler(this);
		
		//if it is not a new note then update; else create
		if(currentNote.getId() > 0)
		{
			dh.updateNote(currentNote);
		}
		else
		{
			dh.createNote(currentNote);
		}
	}
	
	//runs when the back button is pressed
	public void onBackPressed()
	{
		save();
		super.onBackPressed();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	

}
