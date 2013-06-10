package com.notepad;


import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PhotoActivity extends BaseActivity {
	private static final int CAMERA_REQUEST = 1;
	//private SharedPreferences settings;
	private Note currentNote;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		//settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		getNote();
		
		ImageButton doneButton = (ImageButton) findViewById(R.id.photo_doneButton);
		doneButton.setOnClickListener(new DoneButtonListener());
		
		Button captureButton = (Button) findViewById(R.id.photo_captureButton);
		captureButton.setOnClickListener(new CaptureButtonListener());
		
		//set the title text
		initEditText(R.id.photo_titleText, currentNote.getTitle());
		
		//if it's not a new note then load
		if(currentNote.getId() != 0)
		{
			loadPhoto("" + currentNote.getId());
		}
		
	}
	
	private void getNote()
	{
		int currentNoteId = settings.getInt(SETTINGS_PREFS_CURRENT_NOTE, 0);
		
		//if the id is not set to 0 then load the note from the database
		//else it is a new note
		if(currentNoteId != 0)
		{
			DatabaseHandler dh = new DatabaseHandler(this);
			currentNote = dh.getPhotoNote(currentNoteId);
		}
		else
		{
			currentNote = new Note("", 0, DatabaseHandler.PHOTO_TYPE, 0);
		}
		
	}

	private void loadPhoto(String fileName)
	{
		//makes a new file and gives you the path
		Uri photoUri = Uri.fromFile(new File(this.getFilesDir(), fileName));
		
		//get image view
		ImageView image = (ImageView) findViewById(R.id.photo_image);
		
		//set photo to ImageView
		image.setImageURI(null);//clear the current image
		image.setImageURI(photoUri);//set to the new image
		
	}
	
	private void initEditText(int id, String text)
	{
		EditText et = (EditText) findViewById(id);
		
		et.setText(text);
	}
	
	//starts the camera
	public void takePhoto()
	{
		// make the intent
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		
		startActivityForResult(Intent.createChooser(cameraIntent, "Take a photo"),CAMERA_REQUEST);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//as long as you are returning from the camera // you could be returning from another request
		if(requestCode == CAMERA_REQUEST)
		{
			//if they canceled then don't try to save
			if(resultCode == Activity.RESULT_CANCELED) return;
			
			//if the camera returns a result
			if(resultCode == Activity.RESULT_OK)
			{
				//get data from camera and turn it into a Bitmap
				Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
				
				if(cameraPic == null) return;
				
				savePhoto(cameraPic);
				
			}
					
		}
	}
	//save the photo to the file system
	private void savePhoto(Bitmap pic)
	{
		save();//save the note to the database to ensure the current note has an id
		
		String fileName  = "" + currentNote.getId();
		
		try
		{
			pic.compress(CompressFormat.JPEG, 90, openFileOutput(fileName, MODE_PRIVATE));
		}
		catch (Exception e)
		{
			return;
		}
		
		loadPhoto(fileName);
	}
	
	private class DoneButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			//animate the done button then return to MainActivity
			Animator.make(PhotoActivity.this, R.id.photo_doneButton, Animator.BUTTON_ANIM, new AnimationListener(){
				
				public void onAnimationEnd(Animation animation) 
				{
					save();
					startActivity(new Intent(PhotoActivity.this, MainActivity.class));
				}

				public void onAnimationRepeat(Animation animation) {}

				public void onAnimationStart(Animation animation) {}
				
			});
		}
	}
	
	private class CaptureButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			takePhoto();
		}
	}
	
	//save the note
	public void save()
	{
		EditText titleET = (EditText) findViewById(R.id.photo_titleText);
		//EditText bodyET = (EditText) findViewById(R.id.edit_bodyText);
		
		currentNote.setTitle(titleET.getText().toString());
		//currentNote.setBody(bodyET.getText().toString());
		
		//update the database
		DatabaseHandler dh = new DatabaseHandler(this);
		
		//if it is not a new note then update; else create
		if(currentNote.getId() > 0)
		{
			dh.updateNote(currentNote);
		}
		else
		{
			currentNote.setId(dh.createNote(currentNote));
		}
	}	
	
	//delete the current note
	//and return to the home screen
	public void delete()
	{
		//delete the current note
		//and return to the home screen
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		//switch based on the selected menu item
		switch (item.getItemId()) 
		{
		    case R.id.edit_menuitem_home:
		    	startActivity(new Intent(this, MainActivity.class));
		    	break;
		    case R.id.edit_menuitem_new:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, EditActivity.class));
		    	break;
		    case R.id.edit_menuitem_newPhoto:
		    	setCurrentNoteToPrefs(0);
		    	startActivity(new Intent(this, PhotoActivity.class));
		    	break;
		    case R.id.edit_menuitem_settings:
		    	startActivity(new Intent(this, SettingsActivity.class));
		    	break;
		    case R.id.edit_menuitem_save:
		    	save();
		    	break;
		    case R.id.edit_menuitem_delete:
		    	delete();
		    	break;
		    
		}
		
		return true;
	}

}
