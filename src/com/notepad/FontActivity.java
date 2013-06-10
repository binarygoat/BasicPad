package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FontActivity extends BaseActivity {
	//private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_font);
		
		//settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		initSpinner(R.id.font_face, R.array.font_faceArray, SETTINGS_PREFS_FONT_FACE);
		initSpinner(R.id.font_color, R.array.font_colorArray, SETTINGS_PREFS_FONT_COLOR);
		initSpinner(R.id.font_size, R.array.font_sizeArray, SETTINGS_PREFS_FONT_SIZE);
		
		Button contButton = (Button) findViewById(R.id.font_contButton);
		contButton.setOnClickListener(new ContButtonListener());
	}

	
	private void initSpinner(int spinnerId, int arrayId, String prefKey)
	{
		Spinner s = (Spinner) findViewById(spinnerId);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, 
				android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		
		if(settings.contains(prefKey))
		{
			int selectedId = settings.getInt(prefKey, 0);
			s.setSelection(selectedId);
		}
	}
	
	private class ContButtonListener implements View.OnClickListener
	{
		public void onClick(View v) 
		{
			saveSpinnerToPrefs(R.id.font_face, SETTINGS_PREFS_FONT_FACE);
			saveSpinnerToPrefs(R.id.font_color, SETTINGS_PREFS_FONT_COLOR);
			saveSpinnerToPrefs(R.id.font_size, SETTINGS_PREFS_FONT_SIZE);
			
			//go to settings activity
			startActivity(new Intent(FontActivity.this, SettingsActivity.class));
		}
		
	}
	
	
	public void saveSpinnerToPrefs(int spinnerId, String prefKeyId)
	{
		Spinner spinner = (Spinner)findViewById(spinnerId);
		int selectedId = spinner.getSelectedItemPosition();
		//String text = spinner.getSelectedItem().toString();
		
		Editor editor = settings.edit();
		//editor.putString(prefKeyString, text);
		editor.putInt(prefKeyId, selectedId);
		editor.commit();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.sub_act, menu);
		return true;
	}

}
