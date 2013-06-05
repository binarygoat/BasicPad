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
	
	private static final String MENUITEM_HOME = "Return to Home screen";
	private static final String MENUITEM_SYNC = "Sync Settings";
	private static final String MENUITEM_FONT = "Font";
	private static final String MENUITEM_HELP = "Help";
	
	private static final int SETTINGS_ICON_ID = R.drawable.settings_icon;
	private static final int CHECK_ICON_ID = R.drawable.check_icon;
	private static final int SYNC_ICON_ID = R.drawable.sync_icon;
	private static final int HELP_ICON_ID = R.drawable.help_icon;
	private static final int FONT_ICON_ID = R.drawable.font_icon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initSettingsList();
	}

	//populates the ListView with Settings options
	public void initSettingsList()
	{
		ListView settingsListView = (ListView) findViewById(R.id.settings_optionsList);
		
		Note[] settingsListValues = new Note[4];
		
		settingsListValues[0] = new Note(MENUITEM_HOME, 0, CHECK_ICON_ID);
		settingsListValues[1] = new Note(MENUITEM_SYNC, 0, SYNC_ICON_ID);
		settingsListValues[2] = new Note(MENUITEM_FONT, 0, FONT_ICON_ID);
		settingsListValues[3] = new Note(MENUITEM_HELP, 0, HELP_ICON_ID);
		
		NoteListArrayAdapter adapter = new NoteListArrayAdapter(this, settingsListValues, R.layout.list_rowlayout_dark);
		
		settingsListView.setAdapter(adapter);
		
	    settingsListView.setOnItemClickListener(new ListClickListener());
	    
	}
	
	private class ListClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			//set the current note title to settings
			Note selected = (Note) parent.getItemAtPosition(position);
			
			
			//switch to the appropriate activity
			if(selected.toString().equals(MENUITEM_HOME))
			{
				startActivity(new Intent(SettingsActivity.this, MainActivity.class));
			}
			else if(selected.toString().equals(MENUITEM_SYNC))
			{
				startActivity(new Intent(SettingsActivity.this, SyncActivity.class));
			}
			else if(selected.toString().equals(MENUITEM_FONT))
			{
				startActivity(new Intent(SettingsActivity.this, FontActivity.class));
			}
			else if(selected.toString().equals(MENUITEM_HELP))
			{
				//switch to help activity
				//and send extra data so that the user can return to the settings activity
				startActivity(new Intent(SettingsActivity.this, HelpActivity.class).putExtra("sender", "SettingsActivity"));
			}
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
