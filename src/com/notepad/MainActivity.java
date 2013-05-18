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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initNoteList();
		
		initNavButtons();
		
		showHelp();
	}

	//if the help screen has not been shown to the user
	//redirect to the the HelpActivity
	public void showHelp()
	{
		//startActivity(new Intent(MainActivity.this, HelpActivity.class));
		
		if(!settings.contains(SETTINGS_PREFS_HELP) || settings.getBoolean(SETTINGS_PREFS_HELP, true))
		{
			startActivity(new Intent(MainActivity.this, HelpActivity.class));
		}
		
	}
	
	//populates the ListView with recent notes
	public void initNoteList()
	{
		ListView noteListView = (ListView) findViewById(R.id.main_noteList);
		
	    ArrayList<String> noteListValues = new ArrayList<String>();
	    
	    //temp data
	    //the list will be populated from the database
	    noteListValues .add("To Do");
	    noteListValues .add("PCC");
	    noteListValues .add("QFC");
	    noteListValues .add("Whole Foods");
	    noteListValues .add("Trader Joe's");
	    noteListValues .add("Safeway");
	    noteListValues .add("Target");
	    noteListValues .add("Sample Note 0");
	    noteListValues .add("Sample Note 1");
	    noteListValues .add("Sample Note 2");
	    noteListValues .add("Sample Note 3");
	    noteListValues .add("Sample Note 4");
	    noteListValues .add("Sample Note 5");
	    noteListValues .add("Sample Note 6");
	    noteListValues .add("Sample Note 7");
	    noteListValues .add("Sample Note 8");
	    noteListValues .add("Sample Note 9");
	    
	    
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteListValues);
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
		
		ImageButton settingsButton = (ImageButton) findViewById(R.id.main_settingsButton);
		settingsButton.setOnClickListener(new NavButtonListener());
	}
	
	private class NoteListClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			//set the current note title to settings
			String selected = (String) parent.getItemAtPosition(position);
			setCurrentNoteToPrefs(selected);
			
			//switch to the note editor activity
			startActivity(new Intent(MainActivity.this, EditActivity.class));	
		}
		
	}
	
	//saves the title of the current note to settings
	public void setCurrentNoteToPrefs(String noteTitle)
	{
		Editor editor = settings.edit();
		editor.putString(SETTINGS_PREFS_CURRENT_NOTE, noteTitle);
		editor.commit();
	}
	
	private class NavButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			
			int id = sender.getId();
			
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
					
					
				case R.id.main_settingsButton:
					startActivity(new Intent(MainActivity.this, SettingsActivity.class));
					break;
			}
			
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
