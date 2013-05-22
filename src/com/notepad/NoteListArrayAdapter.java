package com.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteListArrayAdapter extends ArrayAdapter<String>
{
	private Context context;
	private String[] values;
	
	static class ViewHolder 
	{
	    public TextView textView;
	    public ImageView imageView;
	}
		
	public NoteListArrayAdapter(Context context, String[] values) 
	{
		super(context, R.layout.list_rowlayout, values);
		
		this.context = context;
	    this.values = values;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		
		if(rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.list_rowlayout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) rowView.findViewById(R.id.list_itemText);
			viewHolder.imageView = (ImageView) rowView.findViewById(R.id.list_icon);
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
	    String s = values[position];
	    
	    holder.textView.setText(s);
	    holder.imageView.setImageResource(R.drawable.note_icon);
		
		//LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//rowView = inflator.inflate(R.layout.list_rowlayout, parent, false);
		//TextView textView = (TextView) rowView.findViewById(R.id.list_itemText);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.list_icon);
		//textView.setText(values[position]);
		
		//imageView.setImageResource(R.drawable.note_icon);
		
		return rowView;
	}
	
}
