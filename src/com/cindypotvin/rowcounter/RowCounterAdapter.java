package com.cindypotvin.rowcounter;

import java.util.ArrayList;

import com.cindypotvin.rowcounter.model.ProjectsDatabaseHelper;
import com.cindypotvin.rowcounter.model.RowCounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class RowCounterAdapter extends ArrayAdapter<RowCounter> {

	private int mLayoutResourceId;
	private ArrayList<RowCounter> mData;
	    
	public RowCounterAdapter(Context context, int layoutResourceId, ArrayList<RowCounter> data) {
		super(context, layoutResourceId, data);
		
		mLayoutResourceId = layoutResourceId;
		mData = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Creates the view to show in the listview for a single row counter
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mLayoutResourceId, parent, false);
			}
		     
		// Set a value for the name of the counter (its position in the list).
		RowCounter currentRowCounter = mData.get(position);
        TextView rowCounterNameView = (TextView)convertView.findViewById(R.id.row_counter_name);
        rowCounterNameView.setText("#" + String.valueOf(position + 1));
        
        // Display the current values of the counter.
        TextView rowCounterValueView = (TextView)convertView.findViewById(R.id.row_counter_value);
        rowCounterValueView.setText(currentRowCounter.toString());
        
        // When the minus button is pressed, remove one from the counter
        Button removeButton = (Button)convertView.findViewById(R.id.row_counter_remove);
        removeButton.setTag(currentRowCounter);
        removeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            	RowCounter clickedRowCounter = (RowCounter)view.getTag();
            	
            	// Remove one from the row counter object
            	clickedRowCounter.removeOneRow();
            	
            	// Update the database with the new value
            	ProjectsDatabaseHelper database = new ProjectsDatabaseHelper(getContext());
            	database.updateRowCounterCurrentAmount(clickedRowCounter);
            	
            	// Refresh the value displayed for the counter
            	TextView parentView = (TextView)((View)view.getParent()).findViewById(R.id.row_counter_value);
            	parentView.setText(clickedRowCounter.toString());
            	}
        	});
        
        // When the plus button is pressed, add one to the counter
        Button addButton = (Button)convertView.findViewById(R.id.row_counter_add);
        addButton.setTag(currentRowCounter);
        addButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            	RowCounter clickedRowCounter = (RowCounter)view.getTag();
            	
            	// Add one to the row counter object
            	clickedRowCounter.addOneRow();
            	
            	// Update the database with the new value
            	ProjectsDatabaseHelper database = new ProjectsDatabaseHelper(getContext());
            	database.updateRowCounterCurrentAmount(clickedRowCounter);
            	
            	// Refresh the value displayed for the counter
            	TextView parentView = (TextView)((View)view.getParent()).findViewById(R.id.row_counter_value);
            	parentView.setText(clickedRowCounter.toString());
            	}
        	});
        
        // When the X button is pressed, delete the row counter. Ideally, a 
        // warning should be displayed here.
        Button deleteButton = (Button)convertView.findViewById(R.id.row_counter_delete);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            	int clickedPosition = (Integer)view.getTag();
            	
            	RowCounter clickedRowCounter = mData.get(clickedPosition);
            	
            	// Removes the row counter from the database
            	ProjectsDatabaseHelper database = new ProjectsDatabaseHelper(getContext());
            	database.deleteRowCounter(clickedRowCounter);
            	
            	// Removes the row counter from the list displayed
            	mData.remove(clickedPosition);
                notifyDataSetChanged();
            	}
        	});
        
		return (convertView);
	}
}
