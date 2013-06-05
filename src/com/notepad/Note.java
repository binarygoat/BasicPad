package com.notepad;

// ListItem holds the data necessary for a view in a NoteList
public class Note {
	private int noteId;//the id in the database for the note
	private String title;//the title text to be displayed
	private String noteType;
	private int iconId;
	private String noteBody;
	
	//initialize all the data when created
	public Note(String title, int noteId, int icon)
	{
		this.title = title;
		this.noteId = noteId;
		this.noteType = "other";
		this.noteBody = "";
		this.iconId = icon;
	}
		
	//initialize all the data when created
	public Note(String title, int noteId, String type, int icon)
	{
		this.title = title;
		this.noteId = noteId;
		this.noteType = type;
		this.noteBody = "";
		this.iconId = icon;
	}
	
	//initialize all the data when created
	public Note(String title, int noteId, String type, String body, int icon)
	{
		this.title = title;
		this.noteId = noteId;
		this.noteType = type;
		this.noteBody = body;
		this.iconId = icon;
	}
	
	public String toString()
	{
		return this.title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getType()
	{
		return noteType;
	}
	
	public String getBody()
	{
		return noteBody;
	}
	
	public int getId()
	{
		return noteId;
	}
	
	public int getIconId()
	{
		return iconId;
	}
	
	//sets
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setType(String type)
	{
		this.noteType = type;
	}
	
	public void setBody(String body)
	{
		this.noteBody = body;
	}
	
	public void setId(int id)
	{
		this.noteId= id;
	}
	
	public void setIconId(int id)
	{
		this.iconId = id;
	}
}
