package com.cindypotvin.rowcounter;

import java.util.ArrayList;
import java.util.List;

import com.cindypotvin.rowcounter.model.Project;
import com.cindypotvin.rowcounter.model.ProjectsDatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
/**
* The main activity of the application showing a list of projects. 
**/
public class MainActivity extends Activity {

	private ListView mListView;
	private ArrayList<Project> mProjects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_activity);

		// Sets the button that starts the activity to create a new project
		Button createProjectActivityButton = (Button) findViewById(R.id.create_project_activity_button);
		createProjectActivityButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Open the activity to create a new project
				Intent createProjectIntent = new Intent(MainActivity.this, CreateProjectActivity.class);
				MainActivity.this.startActivity(createProjectIntent);	
			}
		});
		
	}
	
	@Override
	protected void onStart()
		{
		super.onStart();
				
		// Loads the projects during the onStart so they are reloaded when the activity is resumed 
		// after creating a new project
		
		// Gets the database helper to access the database for the application
		ProjectsDatabaseHelper database = new ProjectsDatabaseHelper(this);
		// Use the helper to get the list of projects from the database
		mProjects = database.getProjects();

		// Initialize the listview to show the projects from the database

		// Gets the titles to display from the list of projects in a list for
		// the listview adapter
		List<String> listViewValues = new ArrayList<String>();
		for (Project currentProject : mProjects) {
			listViewValues.add(currentProject.getName());
		}

		// Initialise a listview adapter for the listview and set it to the
		// listview to show the data
		mListView = (ListView) findViewById(R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				listViewValues.toArray(new String[listViewValues.size()]));
		mListView.setAdapter(adapter);

		// Set an click listener to the elements of the listview so a
		// message can be shown for each project
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Get clicked project
				Project project = mProjects.get(position);
				// Open the activity for the selected project
				Intent projectIntent = new Intent(MainActivity.this, ProjectActivity.class);
				projectIntent.putExtra("project_id", project.getId());
				MainActivity.this.startActivity(projectIntent);
			}
		});
	}

}
