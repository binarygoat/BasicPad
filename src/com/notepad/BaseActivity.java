package com.notepad;

import java.io.IOException;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;

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
	//protected DbxAccountManager mDbxAcctMgr;
	//static final int REQUEST_LINK_TO_DBX = 0;
	
	public static final String SETTINGS_PREFS = "Setting Prefs";
	public static final String SETTINGS_PREFS_CURRENT_NOTE = "Current Note";
	public static final String SETTINGS_PREFS_HELP = "Show Help";
	
	//Sync settings
	public static final String SETTINGS_PREFS_SYNC_EMAIL = "sync email";
	public static final String SETTINGS_PREFS_SYNC_PASSWORD = "sync password";
	public static final String SETTINGS_PREFS_SYNC_DEVICE = "sync device name";
	
	public static final String SETTINGS_PREFS_SYNC_TEXTANDPHOTOS = "sync text and photos";
	public static final String SETTINGS_PREFS_SYNC_AGREE = "sync agree";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		//mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), DbxKeys.APP_KEY, DbxKeys.APP_SECRET);
		
		//initDropbox();
	}
/*
	public void initDropbox()
	{
		if(!mDbxAcctMgr.hasLinkedAccount())
		{
			mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);//connect to dropbox
		}
	}
*/	
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		/*
		//if dropbox is connected
	    if (requestCode == REQUEST_LINK_TO_DBX) 
	    {
	        if (resultCode == Activity.RESULT_OK) 
	        {
	            // ... Start using Dropbox files.
	        } 
	        else 
	        {
	            // ... Link failed or was cancelled by the user.
	        }            
	    }
	    else 
	    {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	    */
	}
/*
	public void saveToDbx(String title, String body) throws InvalidPathException, DbxException
	{
		DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
		
		DbxFile testFile = dbxFs.create(new DbxPath(title + ".txt"));
		try {
		    testFile.writeString(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    testFile.close();
		}
	}
*/
}
