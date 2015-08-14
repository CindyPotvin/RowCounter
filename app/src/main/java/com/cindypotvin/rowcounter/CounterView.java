package com.cindypotvin.rowcounter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Represents a control that display a counter with add and remove buttons.
 */
public class CounterView extends LinearLayout {

	/**
	 * The current value of the counter, starts at one.
	 */
	private int mCounterValue = 1;

	public CounterView(Context context) {
		super(context);

		initializeViews(context);
	}

	public CounterView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initializeViews(context);
	}

	public CounterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initializeViews(context);
	}
	
	/**
	 * Gets the current value of the counter.
	 * 
	 * @return the value saved in the counter.
	 */
	public int getValue() {
		return mCounterValue;
	}

	/**
	 * Inflates the views in the layout and refreshes the counter to show the current value.
	 * 
	 * @param context the current context for the control.
	 */
	private void initializeViews(Context context) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.counter_view, this);
		
		refreshCounterValueView();
	}

	@Override
	protected void onFinishInflate() {
		
		// When the controls in the layout are doing being inflated, set the callback for the 
		// add and remove buttons.
		super.onFinishInflate();

		// When the minus button is pressed, remove one from the counter
		Button removeButton = (Button) this.findViewById(R.id.counter_view_remove);
		removeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				
				// Remove one from for value of the counter
				if (mCounterValue > 0)
					mCounterValue--;

				// Refresh the value displayed for the counter
				refreshCounterValueView();
			}
		});

		// When the plus button is pressed, add one to the counter
		Button addButton = (Button) this.findViewById(R.id.counter_view_add);
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				// Add one to the value of the counter
				mCounterValue++;

				// Refresh the value displayed for the counter
				refreshCounterValueView();
			}
		});
	}

	/**
	 * Refreshes the value displayed by the counter with the value stored in the counter.
	 */
	private void refreshCounterValueView() {
		TextView valueView = (TextView)this.findViewById(R.id.counter_view_value);
		valueView.setText(String.valueOf(mCounterValue));
	}
	
	/**
	 * Sets the current value of the counter and displays it.
	 * 
	 * @param counterValue the value to set in the counter.
	 */
	public void setValue(int counterValue) {
		mCounterValue = counterValue;
		refreshCounterValueView();
	}
}