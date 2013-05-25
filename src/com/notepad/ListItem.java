package com.notepad;

// ListItem holds the data necessary for a view in a NoteList
public class ListItem {
	private String title;//the title text to be displayed
	private int iconId;//the resource id of the icon to display
	private int noteId;//the id in the database for the note
	
	//initialize all the data when created
	public ListItem(String title, int noteId, int iconId)
	{
		this.title = title;
		this.noteId = noteId;
		this.iconId = iconId;
	}
	
	public String toString()
	{
		return this.title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getIconId()
	{
		return iconId;
	}
	
	public int getId()
	{
		return noteId;
	}
	
	//sets
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setIcon(int iconId)
	{
		this.iconId = iconId;
	}
	
	public void setId(int id)
	{
		this.noteId= id;
	}
}
