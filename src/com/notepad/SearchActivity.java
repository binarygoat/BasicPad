package com.notepad;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends BaseActivity {

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
		
	    ArrayList<String> noteListValues = new ArrayList<String>();
	    
	    //temp data
	    //the list will be populated from the database
	    noteListValues .add("To Do");
	    noteListValues .add("PCC");
	    noteListValues .add("QFC");
	    noteListValues .add("Whole Foods");
	    noteListValues .add("Super Suplements");
	    noteListValues .add("Trader Joe's");
	    noteListValues .add("Safeway");
	    noteListValues .add("Target");
	    
	    
	    
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noteListValues);
	    noteListView.setAdapter(adapter);

	    noteListView.setOnItemClickListener(new NoteListClickListener());
	    
	}
	
	private class NoteListClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			//set the current note title to settings
			String selected = (String) parent.getItemAtPosition(position);
			setCurrentNoteToPrefs(selected);
			
			//switch to the note editor activity
			startActivity(new Intent(SearchActivity.this, EditActivity.class));	
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
