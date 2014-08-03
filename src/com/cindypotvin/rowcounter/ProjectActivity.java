package com.cindypotvin.rowcounter;

import com.cindypotvin.rowcounter.model.Project;
import com.cindypotvin.rowcounter.model.ProjectsDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Shows a single project with its row counters.
 */
public class ProjectActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_activity);
		
		Intent intent = getIntent();
		long projectId = intent.getLongExtra("project_id", -1);
		
		
		// Gets the database helper to access the database for the application
		ProjectsDatabaseHelper database = new ProjectsDatabaseHelper(this);
		// Use the helper to get the current project
		Project currentProject = database.getProject(projectId);

	    TextView projectNameView = (TextView)findViewById(R.id.project_name); 
	    projectNameView.setText(currentProject.getName());
	      
		// Initialize the listview to show the row counters for the project from 
		// the database
        ListView rowCounterList = (ListView)findViewById(R.id.row_counter_list);
        ListAdapter rowCounterListAdapter = new RowCounterAdapter(this,
        													      R.layout.rowcounter_row,
        													      currentProject.getRowCounters());
        rowCounterList.setAdapter(rowCounterListAdapter);
	}
}
