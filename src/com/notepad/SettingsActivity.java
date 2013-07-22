package com.notepad;


import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsActivity extends BaseActivity {

	//private SharedPreferences settings;
	
	private static final String MENUITEM_HOME = "Return to Home screen";
	private static final String MENUITEM_SYNC = "Sync Settings";
	private static final String MENUITEM_HELP = "Help";
	
	private static final int CHECK_ICON_ID = R.drawable.check_icon;
	private static final int SYNC_ICON_ID = R.drawable.sync_icon;
	private static final int HELP_ICON_ID = R.drawable.help_icon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		initSettingsList();
	}

	//populates the ListView with Settings options
	public void initSettingsList()
	{
		ListView settingsListView = (ListView) findViewById(R.id.settings_optionsList);
		
		Note[] settingsListValues = new Note[2];
		
		settingsListValues[0] = new Note(MENUITEM_HOME, 0, CHECK_ICON_ID);
		settingsListValues[1] = new Note(MENUITEM_HELP, 0, HELP_ICON_ID);
		//settingsListValues[2] = new Note(MENUITEM_SYNC, 0, SYNC_ICON_ID);
		
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
		//super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) 
	{
		//switch based on the selected menu item
		switch (item.getItemId()) 
		{
		    case R.id.settings_menuitem_home:
		    	startActivity(new Intent(this, MainActivity.class));
		    	break;
		    case R.id.settings_menuitem_new:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, EditActivity.class));
		    	break;
		    case R.id.settings_menuitem_newPhoto:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, PhotoActivity.class));
		    	break;
		}
		
		return true;
	}
	
}
