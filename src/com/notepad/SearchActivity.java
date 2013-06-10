package com.notepad;


import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends BaseActivity {

	private static final int NOTE_ICON_ID = R.drawable.note_icon;
	private static final int PHOTO_ICON_ID = R.drawable.photo_icon;
	
	//private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		//settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		loadNoteList("");
		
		EditText searchText = (EditText) findViewById(R.id.search_searchText);
		searchText.addTextChangedListener(new SearchTextChangedListener());
	}

	//populates the ListView with recent notes
	public void loadNoteList(String s)
	{
		ListView noteListView = (ListView) findViewById(R.id.search_resultList);
		DatabaseHandler dh = new DatabaseHandler(this);
		Note[] noteListValues = dh.search(s);
		
	    NoteListArrayAdapter adapter = new NoteListArrayAdapter(this, noteListValues);
	    noteListView.setAdapter(adapter);

		
	    noteListView.setOnItemClickListener(new NoteListClickListener());
	    
	}
	
	private class NoteListClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
		{
			//set the current note id to settings
			Note selected = (Note) parent.getItemAtPosition(position);
			setCurrentNoteToPrefs(selected.getId());
			
			
			if(selected.getType().equals(DatabaseHandler.TEXT_TYPE))
			{
				//switch to the note editor activity
				startActivity(new Intent(SearchActivity.this, EditActivity.class));
			}
			else if(selected.getType().equals(DatabaseHandler.PHOTO_TYPE))
			{
				//switch to the Photo activity
				startActivity(new Intent(SearchActivity.this, PhotoActivity.class));
			}
			
		}
		
	}
	
	private class SearchTextChangedListener implements TextWatcher
	{
		public void afterTextChanged(Editable s) 
		{
			loadNoteList(s.toString());
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.sub_act, menu);
		return true;
	}
	
	

}
