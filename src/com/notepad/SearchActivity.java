package com.notepad;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends BaseActivity {

	private static final int NOTE_ICON_ID = R.drawable.note_icon;
	private static final int PHOTO_ICON_ID = R.drawable.photo_icon;
	
	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initNoteList();
	}

	//populates the ListView with recent notes
	public void initNoteList()
	{
		ListView noteListView = (ListView) findViewById(R.id.search_resultList);
		
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
				startActivity(new Intent(SearchActivity.this, EditActivity.class));
			}
			else if(selected.getIconId() == PHOTO_ICON_ID)
			{
				//switch to the Photo activity
				startActivity(new Intent(SearchActivity.this, PhotoActivity.class));
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
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
