package com.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteListArrayAdapter extends ArrayAdapter<ListItem>
{
	private Context context;
	private ListItem[] values;
	private int layout;
	
	static class ViewHolder 
	{
	    public TextView textView;
	    public ImageView imageView;
	}
		
	public NoteListArrayAdapter(Context context, ListItem[] values) 
	{
		super(context, R.layout.list_rowlayout, values);
		
		this.context = context;
	    this.values = values;
	    this.layout = R.layout.list_rowlayout;
	}
	
	public NoteListArrayAdapter(Context context, ListItem[] values, int customLayout) 
	{
		super(context, R.layout.list_rowlayout, values);
		
		this.context = context;
	    this.values = values;
	    this.layout = customLayout;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		
		//if there is no view to reuse
		//then inflate the xml Layout and create a new object
		if(rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(layout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) rowView.findViewById(R.id.list_itemText);
			viewHolder.imageView = (ImageView) rowView.findViewById(R.id.list_icon);
			rowView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();

		ListItem s = values[position];

		if(s != null)
		{
			holder.textView.setText(s.getTitle());
			holder.imageView.setImageResource(s.getIconId());
		}
		
		return rowView;
	}
	
}
