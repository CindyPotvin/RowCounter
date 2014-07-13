package com.cindypotvin.rowcounter.model;

import java.util.ArrayList;

/**
 * Represents a knitting project with a name and row counters to count the rows
 * to knit.
 */
public class Project {
private long mId;
private String mName;
private ArrayList<RowCounter> mRowCounters = new ArrayList<RowCounter>();

/**
 * Gets the identifier of the project.
 * 
 * @return the identifier of the project.
 */
public long getId() {
	return mId;
}

/**
 * Sets the identifier of the project.
 * 
 * @param id the identifier of the project.
 */
public void setId(long id) {
	mId = id;
}

/**
 * Gets the name of the project.
 * 
 * @return the name of the project.
 */
public String getName() {
	return (mName);
}

/**
 * Sets the name of the project.
 * 
 * @param name the name of the project to set.
 */
public void setName(String name) {
	mName = name;
}

/**
 * Gets the list of row counter belonging to the current project.
 * 
 * @return the list of row counter.
 */
public ArrayList<RowCounter> getRowCounters() {
	return (mRowCounters);
}

/**
 * Sets the list of row counter for the current project. Existing counters in 
 * the list will no be kept.
 * 
 * @param rowCounters the row counters to set.
 */
public void setRowCounters(ArrayList<RowCounter> rowCounters) {
	mRowCounters = rowCounters;
}
}
