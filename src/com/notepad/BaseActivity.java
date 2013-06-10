package com.notepad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity
{
	protected SharedPreferences settings;
	
	public static final String SETTINGS_PREFS = "Setting Prefs";
	public static final String SETTINGS_PREFS_CURRENT_NOTE = "Current Note";
	public static final String SETTINGS_PREFS_HELP = "Show Help";
	
	//Sync settings
	public static final String SETTINGS_PREFS_SYNC_EMAIL = "sync email";
	public static final String SETTINGS_PREFS_SYNC_PASSWORD = "sync password";
	public static final String SETTINGS_PREFS_SYNC_DEVICE = "sync device name";
	
	public static final String SETTINGS_PREFS_SYNC_TEXTANDPHOTOS = "sync text and photos";
	public static final String SETTINGS_PREFS_SYNC_AGREE = "sync agree";
	
	//Font settings
	public static final String SETTINGS_PREFS_FONT_FACE = "font face";
	public static final String SETTINGS_PREFS_FONT_COLOR = "font color";
	public static final String SETTINGS_PREFS_FONT_SIZE = "font size";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.onCreateOptionsMenu(menu);
		
		getMenuInflater().inflate(R.menu.sub_act, menu);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		//switch based on the selected menu item
		switch (item.getItemId()) 
		{
		    case R.id.menuitem_home:
		    	startActivity(new Intent(this, MainActivity.class));
		    	break;
		    case R.id.menuitem_new:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, EditActivity.class));
		    	break;
		    case R.id.menuitem_newPhoto:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, PhotoActivity.class));
		    	break;
		    case R.id.menuitem_settings:
		    	startActivity(new Intent(this, SettingsActivity.class));
		    	break;
		}
		
		return true;
	}
	
	//saves the ID of the current note to settings
	public void setCurrentNoteToPrefs(int noteId)
	{
		Editor editor = settings.edit();
		editor.putInt(SETTINGS_PREFS_CURRENT_NOTE, noteId);
		editor.commit();
	}

}
