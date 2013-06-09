package com.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper
{
	private Context c;
	
	private static final String DATABASE_NAME = "notePad.db";
	private static final int DATABASE_VERSION = 1;
	
	//Table names
	public static final String NOTES_TABLE = "Notes";
	public static final String NOTE_BODIES_TABLE = "NoteBodies";
	
	//Notes columns
	public static final String NOTE_ID = "noteId";
	public static final String NOTE_TITLE = "noteTitle";
	public static final String NOTE_TYPE = "noteType";
	public static final String NOTE_CREATED = "dateCreated";
	public static final String NOTE_MODIFIED = "dateModified";
	
	//NoteBodies columns
	//noteId; foreign key from Notes table
	public static final String NOTE_BODY = "noteBody";
	
	public static final String TEXT_TYPE = "text";
	public static final String PHOTO_TYPE = "photo";
	
	private static final int NOTE_ICON_ID = R.drawable.note_icon;
	private static final int PHOTO_ICON_ID = R.drawable.photo_icon;
 
	public DatabaseHandler(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		c = context;
	}

	public void onCreate(SQLiteDatabase db) 
	{
		String notesTableSQL = "CREATE TABLE " + NOTES_TABLE + 
					"(" +
						NOTE_ID + " integer primary key autoincrement," +
						NOTE_TITLE + " text not null," +
						NOTE_TYPE + " text not null," +
						NOTE_CREATED + " integer not null," +
						NOTE_MODIFIED + " integer not null" +
					"); ";
		
		db.execSQL(notesTableSQL);
		
		String noteBodiesTableSQL = "CREATE TABLE " + NOTE_BODIES_TABLE +
				"(" +
					NOTE_ID + " integer," +
					NOTE_BODY + " text," +
					"foreign key (" + NOTE_ID + ") references " + NOTES_TABLE + " (" + NOTE_ID+ ")" +
				");";
		
		db.execSQL(noteBodiesTableSQL);
		
	}

	//create a new note in the database
	public int createNote(String title, String type)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(NOTE_TITLE, title);
		values.put(NOTE_TYPE, type);

		//get the current time and set the modified time
		Time now = new Time();
		now.setToNow();
		values.put(NOTE_CREATED, now.toMillis(false));
		values.put(NOTE_MODIFIED, now.toMillis(false));
		
		//insert into the database and return the id of the new row
		int retVal = (int) db.insert(NOTES_TABLE, null, values);
		db.close();
		
		return retVal;
	}
	
	//create a new note in the database
	public int createNote(String title, String body, String type)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(NOTE_TITLE, title);
		values.put(NOTE_TYPE, type);
		
		//get the current time and set the modified time
		Time now = new Time();
		now.setToNow();
		values.put(NOTE_CREATED, now.toMillis(false));
		values.put(NOTE_MODIFIED, now.toMillis(false));
		
		//insert into the database and return the id of the new row
		int noteId = (int) db.insert(NOTES_TABLE, null, values);
		
		ContentValues valuesBody = new ContentValues();
		valuesBody.put(NOTE_ID, noteId);
		valuesBody.put(NOTE_BODY, body);
		
		int bodyId = (int) db.insert(NOTE_BODIES_TABLE, null, valuesBody);
		db.close();
		
		return noteId;
	}
	
	public int createNote(Note note)
	{
		return createNote(note.getTitle(), note.getBody(), note.getType());
	}
	
	//returns the note from the database with the specified id
	public Note getTextNote(int id)
	{
		//create an error note; in case this does't work
		Note retVal = new Note("Error retreving note", 0, DatabaseHandler.TEXT_TYPE, NOTE_ICON_ID);
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		//select id, title, type, and body from the notes and body tables
		String sqlQuery = "SELECT " + 
				"n." + NOTE_ID + ", " + 
				NOTE_TITLE + ", " +
				NOTE_TYPE + ", " +
				NOTE_BODY +
				" FROM " + NOTES_TABLE + " as n " +
				"INNER JOIN " + NOTE_BODIES_TABLE + " as b " +
				"ON n." + NOTE_ID + " = b." + NOTE_ID + 
				" WHERE n." + NOTE_ID + " = " + id;
		
		Cursor cursor = db.rawQuery(sqlQuery, null);
		
		if(cursor.moveToFirst())
		{
			retVal = new Note(cursor.getString(1), cursor.getInt(0), cursor.getString(2), cursor.getString(3), NOTE_ICON_ID);
		}
		
		db.close();
		return retVal;
		
	}
	
	//returns the note from the database with the specified id
	public Note getPhotoNote(int id)
	{
		//create an error note; in case this does't work
		Note retVal = new Note("Error retreving note", 0, DatabaseHandler.TEXT_TYPE, NOTE_ICON_ID);
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		//select id, title, type, and body from the notes and body tables
		String sqlQuery = "SELECT " + 
				NOTE_ID + ", " + 
				NOTE_TITLE + ", " +
				NOTE_TYPE +
				" FROM " + NOTES_TABLE +
				" WHERE " + NOTE_ID + " = " + id;
		
		Cursor cursor = db.rawQuery(sqlQuery, null);
		
		if(cursor.moveToFirst())
		{
			retVal = new Note(cursor.getString(1), cursor.getInt(0), cursor.getString(2), PHOTO_ICON_ID);
		}
		
		db.close();
		return retVal;
		
	}

	//returns the all notes in an array with the most recently
	//modified first
	public Note[] getNoteList()
	{
		//select; newest date first
		String sql = "SELECT " + 
				NOTE_ID + ", " + 
				NOTE_TITLE + ", "+
				NOTE_TYPE + ", " + 
				NOTE_MODIFIED +
				" FROM " + NOTES_TABLE + 
				" ORDER BY " + NOTE_MODIFIED + " DESC";
		
		return getNoteArrayFromSQL(sql);
	}
	
	//return an array with all notes containing the supplied string
	public Note[] search(String s)
	{
		//select; newest date first
		String sql = "SELECT " + 
				NOTE_ID + ", " + 
				NOTE_TITLE + ", "+
				NOTE_TYPE + ", " + 
				NOTE_MODIFIED +
				" FROM " + NOTES_TABLE + 
				" WHERE " + NOTE_TITLE + " LIKE '%" + s + "%' " + 
				" ORDER BY " + NOTE_MODIFIED + " DESC";
		
		return getNoteArrayFromSQL(sql);
	}
	
	//returns an array with the notes selected by the provided sql statement
	public Note[] getNoteArrayFromSQL(String sql)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		//create the cursor with the input sql statement
		Cursor cursor = db.rawQuery(sql, null);
		
		int count = cursor.getCount();
		
		Note[] array = new Note[count];
		
		if(cursor.moveToFirst())
		{
			for(int i = 0; i < count; i++)
			{
				int icon = NOTE_ICON_ID;
				
				//if this is a photo note then set the icon
				if(cursor.getString(2).equals(PHOTO_TYPE))
					icon = PHOTO_ICON_ID;
				
				array[i] = new Note(cursor.getString(1), cursor.getInt(0), cursor.getString(2), icon);
				cursor.moveToNext();
			}
		}
		
		db.close();
		
		return array;
	}
	
	//remove the note from the database
	public boolean deleteNote(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		String sql = "DELETE FROM " + NOTES_TABLE + " WHERE " + NOTE_ID + " = " + id;
		String sqlBody = "DELETE FROM " + NOTE_BODIES_TABLE + " WHERE " + NOTE_ID + " = " + id;
		
		db.rawQuery(sql, null);
		db.rawQuery(sqlBody, null);
		
		db.close();
		
		return true;
	}
	
	public int updateNote(Note note)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(NOTE_TITLE, note.getTitle());
		
		//get the current time and set the modified time
		Time now = new Time();
		now.setToNow();
		values.put(NOTE_MODIFIED, now.toMillis(false));
		
		int noteUpdate = db.update(NOTES_TABLE, values, NOTE_ID + " = ?", new String[] { String.valueOf(note.getId())});
		
		if(!note.getBody().equals(""))
		{
			ContentValues valuesBody = new ContentValues();
			valuesBody.put(NOTE_BODY, note.getBody());
			
			int bodyUpdate = db.update(NOTE_BODIES_TABLE, valuesBody, NOTE_ID + " = ?", new String[] { String.valueOf(note.getId())});
		}
		
		db.close();
		return noteUpdate;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
