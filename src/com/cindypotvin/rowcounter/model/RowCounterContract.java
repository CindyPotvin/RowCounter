package com.cindypotvin.rowcounter.model;

import android.provider.BaseColumns;

/**
 * This class represents a contract for a row_counter table containing row
 * counters for projects. The project must exist before creating row counters 
 * since the counter have a foreign key to the project.
 */
public final class RowCounterContract {

	/**
	 * Contains the name of the table to create that contains the row counters.
	 */
	public static final String TABLE_NAME = "row_counter";

	/**
	 * Contains the SQL query to use to create the table containing the row counters.
	 */
	public static final String SQL_CREATE_TABLE = "CREATE TABLE "
			+ RowCounterContract.TABLE_NAME + " ("
			+ RowCounterContract.RowCounterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ RowCounterContract.RowCounterEntry.COLUMN_NAME_PROJECT_ID + " INTEGER,"
			+ RowCounterContract.RowCounterEntry.COLUMN_NAME_CURRENT_AMOUNT + " INTEGER DEFAULT 0,"
			+ RowCounterContract.RowCounterEntry.COLUMN_NAME_FINAL_AMOUNT + " INTEGER," 
			+ "FOREIGN KEY (" + RowCounterContract.RowCounterEntry.COLUMN_NAME_PROJECT_ID + ") REFERENCES projects(" + ProjectContract.ProjectEntry._ID + "));";

	/**
	 * This class represents the rows for an entry in the row_counter table. The
	 * primary key is the _id column from the BaseColumn class.
	 */
	public static abstract class RowCounterEntry implements BaseColumns {

		// Identifier of the project to which the row counter belongs
		public static final String COLUMN_NAME_PROJECT_ID = "project_id";

		// Final amount of rows to reach
		public static final String COLUMN_NAME_FINAL_AMOUNT = "final_amount";

		// Current amount of rows done
		public static final String COLUMN_NAME_CURRENT_AMOUNT = "current_amount";
	}
}