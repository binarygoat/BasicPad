package com.notepad;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class PhotoActivity extends BaseActivity {

	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		ImageButton newButton = (ImageButton) findViewById(R.id.photo_capture);
		newButton.setOnClickListener(new NavButtonListener());
		
	}

	private class NavButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			setCurrentNoteToPrefs("New Note");
			startActivity(new Intent(PhotoActivity.this, PhotoViewerActivity.class));
			
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
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}

}
