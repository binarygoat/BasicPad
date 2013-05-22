package com.notepad;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class BaseActivity extends Activity
{
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
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.onCreateOptionsMenu(menu);
		
		getMenuInflater().inflate(R.menu.base_menu, menu);
		
		menu.findItem(R.id.menuitem_new).setIntent(new Intent(this, EditActivity.class));
		menu.findItem(R.id.menuitem_newPhoto).setIntent(new Intent(this, PhotoActivity.class));
		menu.findItem(R.id.menuitem_home).setIntent(new Intent(this, MainActivity.class));
		
		menu.findItem(R.id.menuitem_settings).setIntent(new Intent(this, SettingsActivity.class));
		
		return true;
	}
	
}
