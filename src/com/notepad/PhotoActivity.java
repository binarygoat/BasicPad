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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PhotoActivity extends BaseActivity {
	private static final int CAMERA_REQUEST = 1;
	
	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		settings = getSharedPreferences(SETTINGS_PREFS, Context.MODE_PRIVATE);
		
		ImageButton doneButton = (ImageButton) findViewById(R.id.photo_doneButton);
		doneButton.setOnClickListener(new DoneButtonListener());
		
		Button captureButton = (Button) findViewById(R.id.photo_captureButton);
		captureButton.setOnClickListener(new CaptureButtonListener());
		
		//getPhoto();
		
	}

	public void getPhoto()
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
	
	private void savePhoto(Bitmap pic)
	{
		String fileName  = "temp_photo.jpg";
		
		try
		{
			pic.compress(CompressFormat.JPEG, 75, openFileOutput(fileName, MODE_PRIVATE));
		}
		catch (Exception e)
		{
			return;
		}
		
		//makes a new file and gives you the path
		Uri photoUri = Uri.fromFile(new File(this.getFilesDir(), fileName));
		
		//get image view
		ImageView image = (ImageView) findViewById(R.id.photo_image);
		
		//set photo to ImageView
		image.setImageURI(null);//clear the current image
		image.setImageURI(photoUri);//set to the new image
		
	}
	
	private class DoneButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			save();
			startActivity(new Intent(PhotoActivity.this, MainActivity.class));
		}
	}
	
	private class CaptureButtonListener implements View.OnClickListener 
	{
		public void onClick(View sender) 
		{
			getPhoto();
		}
	}
	
	//save the note
	public void save()
	{
		//update the database
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}

}
