package com.cindypotvin.rowcounter.model;

/**
 * Represents a single row counter in a project with its current amount and the
 * amount to reach.
 */
public class RowCounter {
	private long mId;
	private long mFinalAmount;
	private long mCurrentAmount;
	
	/**
	 * Adds one row to the row counter. If the value is already at the final 
	 * value for the counter, nothing is done.
	 */
	public void addOneRow() {
		if (mCurrentAmount < mFinalAmount)
			mCurrentAmount++;
	}
	
	/**
	 * Gets the current amount of the row counter.
	 * 
	 * @return the current amount
	 */
	public long getCurrentAmount() {
		return (mCurrentAmount);
	}
	
	/**
	 * Gets the final amount the counter will reach.
	 * 
	 * @return the final amount for the counter.
	 */
	public long getFinalAmount() {
		return (mFinalAmount);
	}

	/**
	 * Gets the identifier of the row counter.
	 * 
	 * @return the identifier of the row counter.
	 */
	public long getId() {
		return (mId);
	}

	/**
	 * Removes one row from the row counter. If the value is already 0, nothing
	 * is done.
	 */
	public void removeOneRow() {
		if (mCurrentAmount > 0)
			mCurrentAmount--;
	}
	
	/**
	 * Sets the current amount of the row counter.
	 * 
	 * @param currentAmount
	 *            the amount to set.
	 */
	public void setCurrentAmount(long currentAmount) {
		mCurrentAmount = currentAmount;
	}
	
	/**
	 * Sets the final amount that the counter can reach.
	 * 
	 * @param finalAmount
	 *            the final amount that can be reached.
	 */
	public void setFinalAmount(long finalAmount) {
		mFinalAmount = finalAmount;
	}

	/**
	 * Sets the identifier of the row counter.
	 * 
	 * @param id
	 *            the identifier to set.
	 */
	public void setId(long id) {
		mId = id;
	}

	@Override
	public String toString() {
		//Returns the value to display for the row counter
		return (mCurrentAmount + "/" + mFinalAmount);
	}
}
