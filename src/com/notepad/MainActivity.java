package com.notepad;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class MainActivity extends BaseActivity 
{
	private SharedPreferences settings;
	
	
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
		Animator.make(this, R.id.main_noteList, Animator.LIST_OPEN_ANIM);
		
		ListView noteListView = (ListView) findViewById(R.id.main_noteList);
		
		DatabaseHandler dh = new DatabaseHandler(this);
		Note[] noteListValues = dh.getNoteList();
		
		//if the database is empty then add sample data
		if(noteListValues.length < 1)
		{
			dh.createNote("Sample Note", "This is a sample note", DatabaseHandler.TEXT_TYPE);
			dh.createNote("To Do", "This is an example To Do list", DatabaseHandler.TEXT_TYPE);
			dh.createNote("Groceries", "This is an example grocery list", DatabaseHandler.TEXT_TYPE);
			dh.createNote("ITC 162 To Do", "This is an example To Do list", DatabaseHandler.TEXT_TYPE);
			dh.createNote("Sample Photo Note", DatabaseHandler.PHOTO_TYPE);
			dh.createNote("Sample Photo Note 2", DatabaseHandler.PHOTO_TYPE);
			
			noteListValues = dh.getNoteList();
		}
	    
		
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
		private AdapterView<?> par;
		private int pos;
		
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			par = parent;
			pos = position;
			
			Animator.make(MainActivity.this, R.id.main_noteList, Animator.LIST_CLOSE_ANIM, new AnimationListener(){

				public void onAnimationEnd(Animation animation) 
				{
					ListView noteList = (ListView) findViewById(R.id.main_noteList);
					noteList.setVisibility(4);//global constant INVISIBLE was giving an error
					
					//set the current note id to settings
					Note selected = (Note) par.getItemAtPosition(pos);
					setCurrentNoteToPrefs(selected.getId());
					
					
					if(selected.getType().equals(DatabaseHandler.TEXT_TYPE))
					{
						//switch to the note editor activity
						startActivity(new Intent(MainActivity.this, EditActivity.class));
					}
					else if(selected.getType().equals(DatabaseHandler.PHOTO_TYPE))
					{
						//switch to the Photo activity
						startActivity(new Intent(MainActivity.this, PhotoActivity.class));
					}	
					
					noteList.setVisibility(0);//global constant VISIBLE was giving an error
					
				}

				public void onAnimationRepeat(Animation animation) {}

				public void onAnimationStart(Animation animation) {}
			
			});	
		}
		
	}
	
	//saves the ID of the current note to settings
	public void setCurrentNoteToPrefs(int noteId)
	{
		Editor editor = settings.edit();
		editor.putInt(SETTINGS_PREFS_CURRENT_NOTE, noteId);
		editor.commit();
	}
	
	//Click listener for the navigation buttons
	private class NavButtonListener implements View.OnClickListener 
	{
		private int id;
		
		public void onClick(View sender) 
		{
			id = sender.getId();
			
			Animator.make(MainActivity.this, id, Animator.BUTTON_ANIM, new AnimationListener(){
			
				public void onAnimationEnd(Animation animation) 
				{
					
					//determine which button was clicked
					//then switch to the appropriate activity
					switch (id)
					{
						case R.id.main_newNoteButton:
							setCurrentNoteToPrefs(0);//id of a new note is generated by the database
							startActivity(new Intent(MainActivity.this, EditActivity.class));
							break;
							
						case R.id.main_newPhotoButton:
							setCurrentNoteToPrefs(0);//id of a new note is generated by the database
							startActivity(new Intent(MainActivity.this, PhotoActivity.class));
							break;
							
						case R.id.main_searchButton:
							startActivity(new Intent(MainActivity.this, SearchActivity.class));
							break;
					}
				}

				public void onAnimationRepeat(Animation animation) {}

				public void onAnimationStart(Animation animation) {}
				
			});
			
		}
	}
	
	public void onPause()
	{
		super.onPause();
		
		findViewById(R.id.main_noteList).clearAnimation();
		findViewById(R.id.main_newNoteButton).clearAnimation();
		findViewById(R.id.main_newPhotoButton).clearAnimation();
		findViewById(R.id.main_searchButton).clearAnimation();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
