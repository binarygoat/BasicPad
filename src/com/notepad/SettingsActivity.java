package com.notepad;

import java.util.ArrayList;

import android.os.Bundle;
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
import android.widget.TextView;

public class SettingsActivity extends BaseActivity {

	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initNoteList();
	}

	//populates the ListView with recent notes
	public void initNoteList()
	{
		ListView settingsListView = (ListView) findViewById(R.id.settings_optionsList);
		
	    ArrayList<String> settingsListValues = new ArrayList<String>();
	    
	    settingsListValues.add("Setup Sync");
	    settingsListValues.add("Font");
	    
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsListValues);
	    settingsListView.setAdapter(adapter);

	    settingsListView.setOnItemClickListener(new NoteListClickListener());
	    
	}
	
	private class NoteListClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			//set the current note title to settings
			String selected = (String) parent.getItemAtPosition(position);
			
			if(selected.equals(""))
			
			//switch to the note editor activity
			startActivity(new Intent(SettingsActivity.this, SyncActivity.class));
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
