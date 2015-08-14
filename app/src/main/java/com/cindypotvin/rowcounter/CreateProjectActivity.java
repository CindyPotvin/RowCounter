package com.cindypotvin.rowcounter;

import com.cindypotvin.rowcounter.model.ProjectsDatabaseHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Represents an activity used to create a new knitting project. The project is only created if the
 * user presses the create button.
 */
public class CreateProjectActivity extends Activity {
	/**
	 * Name of the key used to save the state of the row counters amount in the bundle.
	 */
	private static final String ROW_COUNTERS_AMOUNT_STATE = "rowCountersAmount";
	
	/**
	 * Name of the key used to save the state of the rows amount counters in the bundle.
	 */
	private static final String ROWS_AMOUNT_STATE = "rowsAmount";
	
	/**
	 * The counter view for the amount of row counters to create.
	 */
	private CounterView mRowCountersAmountView;
	
	/**
	 * The counter view for the amount of rows to create by row counter.
	 */	
	private CounterView mRowsAmountView;
	
	 /**
	 * Creates a new project when the create project button is pressed from the values selected by 
	 * the user.
	 */
	OnClickListener mCreateProjectOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			// Get the values to save the project
			TextView projectNameView = (TextView)CreateProjectActivity.this.findViewById(R.id.create_project_name);
			String projectName = projectNameView.getText().toString();
			
			int rowCounterAmount = mRowCountersAmountView.getValue();
			int rowsAmount = mRowsAmountView.getValue();
			
			// Gets the database helper to access the database for the application
			ProjectsDatabaseHelper database = new ProjectsDatabaseHelper(CreateProjectActivity.this);
			
			// Creates the project in the database 
			database.createProject(projectName, rowCounterAmount, rowsAmount);
			
			finish();
			}
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Restore the previous state of the activity, if any
		super.onCreate(savedInstanceState);
		
		// Sets the layout to use for the activity
		setContentView(R.layout.create_project_activity);
		
		mRowsAmountView = (CounterView)CreateProjectActivity.this.findViewById(R.id.rows_amount);
		mRowCountersAmountView = (CounterView)CreateProjectActivity.this.findViewById(R.id.row_counters_amount);
		// Sets the onClick method for the button that creates the project
		Button createButton = (Button) this.findViewById(R.id.create_project_create_button);
		createButton.setOnClickListener(mCreateProjectOnClickListener);

		// Check if a previously destroyed activity is being recreated.
		if (savedInstanceState != null) {
			// Restore value of counters from saved state
			mRowCountersAmountView.setValue(savedInstanceState.getInt(ROW_COUNTERS_AMOUNT_STATE));
			mRowsAmountView.setValue(savedInstanceState.getInt(ROWS_AMOUNT_STATE));
		} 
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    // Restore value of counters from saved state. This is the same as restoring from the 
	    // onCreate and is executed after onCreate
	    
	    mRowCountersAmountView.setValue(savedInstanceState.getInt(ROW_COUNTERS_AMOUNT_STATE));
	    mRowsAmountView.setValue(savedInstanceState.getInt(ROWS_AMOUNT_STATE));
	}

	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    savedInstanceState.putInt(ROW_COUNTERS_AMOUNT_STATE, mRowCountersAmountView.getValue());
	    savedInstanceState.putInt(ROWS_AMOUNT_STATE, mRowsAmountView.getValue());

	    super.onSaveInstanceState(savedInstanceState);
	}
}
