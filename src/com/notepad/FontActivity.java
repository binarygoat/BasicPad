package com.notepad;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FontActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_font);
		
		initSpinner(R.id.font_face, R.array.font_faceArray);
		initSpinner(R.id.font_color, R.array.font_colorArray);
		initSpinner(R.id.font_size, R.array.font_sizeArray);
		
		Button contButton = (Button) findViewById(R.id.font_contButton);
		contButton.setOnClickListener(new ContButtonListener());
	}

	
	private void initSpinner(int spinnerId, int arrayId)
	{
		Spinner s = (Spinner) findViewById(spinnerId);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, 
				android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
	}
	
	private class ContButtonListener implements View.OnClickListener
	{
		public void onClick(View v) 
		{
			
			//go to settings activity
			startActivity(new Intent(FontActivity.this, SettingsActivity.class));
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.font, menu);
		return true;
	}

}
