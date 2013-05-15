package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.EditText;

public class PhotoViewerActivity extends BaseActivity {

	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_viewer);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initEditText(R.id.photoViewer_titleText, SETTINGS_PREFS_CURRENT_NOTE);
	}

	public void initEditText(int id, String prefKey)
	{
		EditText et = (EditText) findViewById(id);
		
		if(settings.contains(prefKey))
		{
			et.setText(settings.getString(prefKey, ""));
		}
	}
	
	//save the note
	public void save()
	{
		//update the database
	}
	
	//runs when the back button is pressed
	public void onBackPressed()
	{
		save();
		startActivity(new Intent(PhotoViewerActivity.this, MainActivity.class));
		//super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_viewer, menu);
		return true;
	}

}
