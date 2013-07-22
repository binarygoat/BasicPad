package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditActivity extends BaseActivity 
{
	private Note currentNote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		getNote();
		
		initEditText(R.id.edit_titleText, currentNote.getTitle());
		initEditText(R.id.edit_bodyText, currentNote.getBody());
		
		ImageButton doneButton = (ImageButton) findViewById(R.id.edit_doneButton);
		doneButton.setOnClickListener(new DoneButtonListener());
	}

	private void getNote()
	{
		int currentNoteId = settings.getInt(SETTINGS_PREFS_CURRENT_NOTE, 0);
		
		//if the id is not set to 0 then load the note from the database
		//else it is a new note
		if(currentNoteId != 0)
		{
			DatabaseHandler dh = new DatabaseHandler(this);
			currentNote = dh.getTextNote(currentNoteId);
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
	}
	
	private class DoneButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			//animate the done button then return to MainActivity
			Animator.make(EditActivity.this, R.id.edit_doneButton, Animator.BUTTON_ANIM, new AnimationListener(){
				
				public void onAnimationEnd(Animation animation) 
				{
					save();
					startActivity(new Intent(EditActivity.this, MainActivity.class));
				}

				public void onAnimationRepeat(Animation animation) {}

				public void onAnimationStart(Animation animation) {}
				
			});
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
			//create and set value of id
			currentNote.setId(dh.createNote(currentNote));
		}
		
		/*
		//save to dropbox
		try
		{
			saveToDbx(currentNote.getTitle(), currentNote.getBody());
		}
		catch(Exception e)
		{
			
		}
		*/
		
		Toast t = Toast.makeText(this, "Note Saved", Toast.LENGTH_LONG);
		t.show();
	}
	
	//delete the current note
	//and return to the home screen
	public void delete()
	{
		//delete the current note
		DatabaseHandler dh = new DatabaseHandler(this);
		dh.deleteNote(currentNote.getId());
		
		Toast t = Toast.makeText(this, "Note Deleted", Toast.LENGTH_LONG);
		t.show();
		
		//and return to the home screen
		startActivity(new Intent(this, MainActivity.class));
	}
	
	//runs when the back button is pressed
	public void onBackPressed()
	{
		save();
		super.onBackPressed();
	}

	public void onPause()
	{
		super.onPause();
		
		findViewById(R.id.edit_doneButton).clearAnimation();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		//switch based on the selected menu item
		switch (item.getItemId()) 
		{
		    case R.id.edit_menuitem_home:
		    	startActivity(new Intent(this, MainActivity.class));
		    	break;
		    case R.id.edit_menuitem_new:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, EditActivity.class));
		    	break;
		    case R.id.edit_menuitem_newPhoto:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, PhotoActivity.class));
		    	break;
		    case R.id.edit_menuitem_settings:
		    	startActivity(new Intent(this, SettingsActivity.class));
		    	break;
		    case R.id.edit_menuitem_save:
		    	save();
		    	break;
		    case R.id.edit_menuitem_delete:
		    	delete();
		    	break;
		}
		
		return true;
	}
}
