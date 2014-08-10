package com.cindypotvin.rowcounter.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * This class helps open, create, and upgrade the database file containing the
 * projects and their row counters.
 */
public class ProjectsDatabaseHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	// The name of the database file on the file system
	public static final String DATABASE_NAME = "Projects.db";

	public ProjectsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Deletes the specified row counter from the database.
	 * 
	 * @param rowCounter the row counter to remove.
	 */
	public void deleteRowCounter(RowCounter rowCounter) {
		SQLiteDatabase db = getWritableDatabase();
		
		db.delete(RowCounterContract.TABLE_NAME, 			  
				  RowCounterContract.RowCounterEntry._ID +"=?",
				  new String[] { String.valueOf(rowCounter.getId()) });
	}

	/**
	 * Gets the specified project from the database.
	 * 
	 * @param projectId the identifier of the project to get.
	 * 
	 * @return the specified project.
	 */
	public Project getProject(long projectId) {
		// Gets the database in the current database helper in read-only mode
		SQLiteDatabase db = getReadableDatabase();
		
		// After the query, the cursor points to the first database row 
		// returned by the request
		Cursor projCursor = db.query(ProjectContract.TABLE_NAME, 
									 null, 
									 ProjectContract.ProjectEntry._ID + "=?", 
									 new String[] { String.valueOf(projectId) }, 
									 null, 
									 null,
									 null);
		projCursor.moveToNext();
	   // Get the value for each column for the database row pointed by 
	   // the cursor using the getColumnIndex method of the cursor and 
	   // use it to initialize a Project object by database row
		Project project = new Project();
		project.setId(projCursor.getLong(projCursor.getColumnIndex(ProjectContract.ProjectEntry._ID)));
	    project.setName(projCursor.getString(projCursor.getColumnIndex(ProjectContract.ProjectEntry.COLUMN_NAME_TITLE)));
	    // Get all the row counters for the current project from the 
	    // database and add them all to the Project object
		project.setRowCounters(getRowCounters(projectId));
			
	    return (project);
		}
	
	/**
	 * Gets the list of projects from the database.
	 * 
	 * @return the current projects from the database.
	 */
	public ArrayList<Project> getProjects()
		{
		ArrayList<Project> projects = new ArrayList<Project>();
		// Gets the database in the current database helper in read-only mode
		SQLiteDatabase db = getReadableDatabase();
		
		// After the query, the cursor points to the first database row 
		// returned by the request.
		Cursor projCursor = db.query(ProjectContract.TABLE_NAME, null, null, null, null, null, null);
		while (projCursor.moveToNext()) {
		    // Get the value for each column for the database row pointed by 
			// the cursor using the getColumnIndex method of the cursor and 
			// use it to initialize a Project object by database row
			Project project = new Project();
			long projectId = projCursor.getLong(projCursor.getColumnIndex(ProjectContract.ProjectEntry._ID));
			project.setId(projCursor.getLong(projCursor.getColumnIndex(ProjectContract.ProjectEntry._ID)));
			project.setName(projCursor.getString(projCursor.getColumnIndex(ProjectContract.ProjectEntry.COLUMN_NAME_TITLE)));
			// Get all the row counters for the current project from the 
			// database and add them all to the Project object
			project.setRowCounters(getRowCounters(projectId));
			
			projects.add(project);
			}
		
		return (projects);
		}
	
	/**
	 * Gets the list of row counters for a project from the database.
	 * 
	 * @param projectId the unique identifier of the project for which to get 
	 * the row counters.
	 * 
	 * @return the row counters for the specified project, or an empty arraylist 
	 * if no counter was found.
	 */
	private ArrayList<RowCounter> getRowCounters(long projectId)
	    {
		ArrayList<RowCounter> rowCounters = new ArrayList<RowCounter>();
		// Gets the database in the current database helper in read-only mode
		SQLiteDatabase db = getReadableDatabase();
		
		Cursor countCursor = db.query(RowCounterContract.TABLE_NAME, 
									  null, 
									  RowCounterContract.RowCounterEntry.COLUMN_NAME_PROJECT_ID + "=?", 
									  new String[]{ String.valueOf(projectId) }, 
									  null, 
									  null, 
									  RowCounterContract.RowCounterEntry._ID);
		// After the query, the cursor points to the first database row 
		//returned by the request.
		while (countCursor.moveToNext()) {
		    // Get the value for each column for the database row pointed by 
			// the cursor using the getColumnIndex method of the cursor and 
			// use it to initialize a RowCounter object by database row
			RowCounter rowCounter = new RowCounter();
			rowCounter.setId(countCursor.getLong(countCursor.getColumnIndex(RowCounterContract.RowCounterEntry._ID)));
			
			long currentAmount = countCursor.getLong(countCursor.getColumnIndex(RowCounterContract.RowCounterEntry.COLUMN_NAME_CURRENT_AMOUNT));
			rowCounter.setCurrentAmount(currentAmount);
			
			long finalAmount = countCursor.getLong(countCursor.getColumnIndex(RowCounterContract.RowCounterEntry.COLUMN_NAME_FINAL_AMOUNT));
			rowCounter.setFinalAmount(finalAmount);
			
			rowCounters.add(rowCounter);
			}
		
		return (rowCounters);
	    }
	
	/**
	 * Initialize example data to show when the application is first installed. 
	 * 
	 * @param db the database being initialized.
	 */
	private void initializeExampleData(SQLiteDatabase db)
		{
		// A lot of code is repeated here that could be factorised in methods, 
		// but this is clearer for the example
		
		// Insert the database row for an example project in the project table in the
		// database
		long projectId;
		ContentValues firstProjectValues = new ContentValues();
        firstProjectValues.put(ProjectContract.ProjectEntry.COLUMN_NAME_TITLE, "Flashy Scarf");
        projectId = db.insert(ProjectContract.TABLE_NAME, null, firstProjectValues);
		// Insert the database rows for a row counter linked to the project row 
        // just created in the database (the insert method returns the 
        // identifier of the row)
		ContentValues firstProjectCounterValues = new ContentValues();
		firstProjectCounterValues.put(RowCounterContract.RowCounterEntry.COLUMN_NAME_PROJECT_ID, projectId);
		firstProjectCounterValues.put(RowCounterContract.RowCounterEntry.COLUMN_NAME_FINAL_AMOUNT, 120);
		db.insert(RowCounterContract.TABLE_NAME, null, firstProjectCounterValues);
		
		// Insert the database row for a second example project in the project 
		// table in the database.
		ContentValues secondProjectValues = new ContentValues();
		secondProjectValues.put(ProjectContract.ProjectEntry.COLUMN_NAME_TITLE, "Simple Socks");
		projectId = db.insert(ProjectContract.TABLE_NAME, null, secondProjectValues);
		// Insert the database rows for two identical row counters for the 
		// project in the database
		ContentValues secondProjectCounterValues = new ContentValues();
		secondProjectCounterValues.put(RowCounterContract.RowCounterEntry.COLUMN_NAME_PROJECT_ID, projectId);
		secondProjectCounterValues.put(RowCounterContract.RowCounterEntry.COLUMN_NAME_FINAL_AMOUNT, 80);
		db.insert(RowCounterContract.TABLE_NAME, null, secondProjectCounterValues);
		db.insert(RowCounterContract.TABLE_NAME, null, secondProjectCounterValues);	
		}
	
	/**
	 * Creates the underlying database with the SQL_CREATE_TABLE queries from
	 * the contract classes to create the tables and initialize the data. 
	 * The onCreate is triggered the first time someone tries to access 
	 * the database with the getReadableDatabase or 
	 * getWritableDatabase methods.
	 * 
	 * @param db the database being accessed and that should be created.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create the database to contain the data for the projects
		db.execSQL(ProjectContract.SQL_CREATE_TABLE);
		db.execSQL(RowCounterContract.SQL_CREATE_TABLE);
		
		initializeExampleData(db);
	}

	/**
	 * 
	 * This method must be implemented if your application is upgraded and must
	 * include the SQL query to upgrade the database from your old to your new
	 * schema.
	 * 
	 * @param db the database being upgraded.
	 * @param oldVersion the current version of the database before the upgrade.
	 * @param newVersion the version of the database after the upgrade.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Logs that the database is being upgraded
		Log.i(ProjectsDatabaseHelper.class.getSimpleName(),
				"Upgrading database from version " + oldVersion + " to " + newVersion);
	}
	
	/**
	 * Updates the current amount of the row counter in the database to the value 
	 * in the object passed as a parameter.
	 * 
	 * @param rowCounter the object containing the current amount to set.
	 */
	public void updateRowCounterCurrentAmount(RowCounter rowCounter) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues currentAmountValue = new ContentValues();
		currentAmountValue.put(RowCounterContract.RowCounterEntry.COLUMN_NAME_CURRENT_AMOUNT, rowCounter.getCurrentAmount());
		
		db.update(RowCounterContract.TABLE_NAME, 
				  currentAmountValue, 
				  RowCounterContract.RowCounterEntry._ID +"=?",
				  new String[] { String.valueOf(rowCounter.getId()) });
	}
}

