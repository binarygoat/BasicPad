package com.notepad;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;

public class MainActivity extends BaseActivity 
{
	private SharedPreferences settings;
	
	private static final int NOTE_ICON_ID = R.drawable.note_icon;
	private static final int PHOTO_ICON_ID = R.drawable.photo_icon;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		showHelp();
		
		initNoteList();
		
		initNavButtons();
		
	}

	//if the help screen has not been shown to the user
	//redirect to the the HelpActivity
	public void showHelp()
	{
		if(!settings.contains(SETTINGS_PREFS_HELP) || settings.getBoolean(SETTINGS_PREFS_HELP, true))
		{
			startActivity(new Intent(MainActivity.this, HelpActivity.class).putExtra("sender", "MainActivity"));
		}	
	}
	
	//populates the ListView with recent notes
	public void initNoteList()
	{
		ListView noteListView = (ListView) findViewById(R.id.main_noteList);
		/*
		//create the array that will contain list items
		String[] noteListValues = new String[17];
		
	    //temp data
	    //the list will be populated from the database
	    noteListValues[0] ="To Do";
	    noteListValues[1] ="PCC";
	    noteListValues[2] ="QFC";
	    noteListValues[3] ="Whole Foods";
	    noteListValues[4] ="Trader Joe's";
	    noteListValues[5] ="Safeway";
	    noteListValues[6] ="Target";
	    noteListValues[7] ="Sample Note 0";
	    noteListValues[8] ="Sample Note 1";
	    noteListValues[9] ="Sample Note 2";
	    noteListValues[10] ="Sample Note 3";
	    noteListValues[11] ="Sample Note 4";
	    noteListValues[12] ="Sample Note 5";
	    noteListValues[13] ="Sample Note 6";
	    noteListValues[14] ="Sample Note 7";
	    noteListValues[15] ="Sample Note 8";
	    noteListValues[16] ="Sample Note 9";
	    
	    
	    NoteListArrayAdapter adapter = new NoteListArrayAdapter(this, noteListValues);
	    noteListView.setAdapter(adapter);
	    */
		//create the array that will contain list items
				ListItem[] noteListValues = new ListItem[11];
				
			    //temp data
			    //the list will be populated from the database
			    noteListValues[0] = new ListItem("To Do", 0, NOTE_ICON_ID);
			    noteListValues[1] = new ListItem("Sample Note 0", 0, NOTE_ICON_ID);
			    noteListValues[2] = new ListItem("Sample Photo Note 0", 0, PHOTO_ICON_ID);
			    noteListValues[3] = new ListItem("Sample Note 1", 0, NOTE_ICON_ID);
			    noteListValues[4] = new ListItem("Sample Note 2", 0, NOTE_ICON_ID);
			    noteListValues[5] = new ListItem("Sample Photo Note 1", 0, PHOTO_ICON_ID);
			    noteListValues[6] = new ListItem("Sample Photo Note 2", 0, PHOTO_ICON_ID);
			    noteListValues[7] = new ListItem("Sample Note 3", 0, NOTE_ICON_ID);
			    noteListValues[8] = new ListItem("Sample Note 4", 0, NOTE_ICON_ID);
			    noteListValues[9] = new ListItem("Sample Photo Note 3", 0, PHOTO_ICON_ID);
			    noteListValues[10] = new ListItem("Sample Note 5", 0, NOTE_ICON_ID);
			    
			    
			    NoteListArrayAdapter adapter = new NoteListArrayAdapter(this, noteListValues);
			    noteListView.setAdapter(adapter);

		
	    noteListView.setOnItemClickListener(new NoteListClickListener());
	    
	}
	
	//add clickListeners to the nav buttons
	public void initNavButtons()
	{
		ImageButton newButton = (ImageButton) findViewById(R.id.main_newNoteButton);
		newButton.setOnClickListener(new NavButtonListener());
		
		ImageButton newPhotoButton = (ImageButton) findViewById(R.id.main_newPhotoButton);
		newPhotoButton.setOnClickListener(new NavButtonListener());
		
		ImageButton searchButton = (ImageButton) findViewById(R.id.main_searchButton);
		searchButton.setOnClickListener(new NavButtonListener());
	}
	
	//Click listener for the ListView Items
	private class NoteListClickListener implements OnItemClickListener
	{
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			//set the current note title to settings
			ListItem selected = (ListItem) parent.getItemAtPosition(position);
			setCurrentNoteToPrefs(selected.toString());
			
			if(selected.getIconId() == NOTE_ICON_ID)
			{
				//switch to the note editor activity
				startActivity(new Intent(MainActivity.this, EditActivity.class));
			}
			else if(selected.getIconId() == PHOTO_ICON_ID)
			{
				//switch to the Photo activity
				startActivity(new Intent(MainActivity.this, PhotoActivity.class));
			}
				
		}
		
	}
	
	//saves the title of the current note to settings
	public void setCurrentNoteToPrefs(String noteTitle)
	{
		Editor editor = settings.edit();
		editor.putString(SETTINGS_PREFS_CURRENT_NOTE, noteTitle);
		editor.commit();
	}
	
	//Click listener for the navigation buttons
	private class NavButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			int id = sender.getId();
			
			//determine which button was clicked
			//then switch to the appropriate activity
			switch (id)
			{
				case R.id.main_newNoteButton:
					setCurrentNoteToPrefs("New Note");
					startActivity(new Intent(MainActivity.this, EditActivity.class));
					break;
					
				case R.id.main_newPhotoButton:
					startActivity(new Intent(MainActivity.this, PhotoActivity.class));
					break;
					
				case R.id.main_searchButton:
					startActivity(new Intent(MainActivity.this, SearchActivity.class));
					break;
			}
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
