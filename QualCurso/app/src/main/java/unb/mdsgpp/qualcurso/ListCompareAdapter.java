/************************
 * Class name: ListCompareAdapter (.java)
 *
 * Purpose: Inflates the structure of the list screen of the institutions.
 ************************/

package unb.mdsgpp.qualcurso;

import java.util.ArrayList;
import java.util.List;

import models.Institution;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ListCompareAdapter extends ArrayAdapter<Institution> implements
		OnCheckedChangeListener{

	// Defines the structure of the term presented institutions, word formatting.
	public static int INSTITUTION = R.string.institution;

	// Indicates the selected position of the item in the CheckBox component list.
	public static int POSITION = R.id.checkbox;

	// Calls to call another Fragment identifying call.
	private Fragment callingFragment = null;

	// Used to identify which item from the list of institutions was selected by the user.
	private CheckBoxListCallbacks checkBoxCallBacks;

	// Component used to list the institutions.
	private CheckBox checkBox = null;

	// List of items within the CheckBox component to the user's selection.
	private ArrayList<Boolean> checkedItems = new ArrayList<Boolean>();

    // Used to Log system.
    final String TAG = ListCompareAdapter.class.getSimpleName();

	/**
	 * Rescuing Father class attribute values, sets the Fragment type attribute to call fragment
	 * creates the list and add items to a list to be used in the application.
	 *
	 * @param context
	 * 				Refers to the context that class where instantiated.
	 * @param resource
	 *				Value that will be placed on Class Father.
	 * @param item
	 * 				Which item of the list will be shown.
	 * @param callingFragment
	 * 				Fragment that will be shown.
     */
	@SuppressLint("Assert")
	public ListCompareAdapter(Context context, int resource, List<Institution> item,
							  Fragment callingFragment) {

		super(context, resource, item);
		assert (context != null) : "Receive the null context of treatment";
		assert (resource > 0) : "Treatment to lower value of resource";
		assert (callingFragment != null) : "Receive the null treatment";

		this.callingFragment = callingFragment;
		checkedItems = new ArrayList<Boolean>();
		for(int aux = 0; aux < this.getCount(); aux++) {
			checkedItems.add(false);
		}
	}

	/**
	 * Works to inflate the user view screen showing the list CheckBox, Selects and transmits the
	 * data of the selected institution for users.
	 *
	 * @param position
	 * 				Id of item clicked by user.
	 * @param contextView
	 * 				The actual view that user sees.
	 * @param parent
	 * 				The view pattern.
     * @return
	 * 				View selected by user.
     */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View contextView, ViewGroup parent) {

		View currentView = contextView;
		checkBoxCallBacks = (CheckBoxListCallbacks)this.callingFragment;

		if(currentView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(getContext());
			currentView = layoutInflater.inflate(R.layout.compare_choose_list_item, null);
			assert (currentView != null) : "this view should not be null";

		} else {
		    /*Nothing to do*/
        }

		Institution institution = getItem(position);

		if(institution != null) {
			checkBox = (CheckBox) currentView.findViewById(R.id.compare_institution_checkbox);
			assert (checkBox != null) : "checkbox should not be null";

			checkBox.setText(institution.getAcronym());
			checkBox.setTag(INSTITUTION, institution);
			checkBox.setTag(POSITION, position);
			checkBox.setChecked(checkedItems.get(position));
			checkBox.setOnCheckedChangeListener(this);
			currentView.setTag(checkBox);

		} else {
            Log.w(TAG, "Null institution to take position");
        }

		return currentView;
	}

	/**
	 * Select the item to be clicked, used to return the position of the list button.
	 *
	 * @param buttonView
	 * 				Which button is clicked.
	 * @param isChecked
	 * 				Store information about user clicking this checkBox.
     */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int position = (Integer)buttonView.getTag(ListCompareAdapter.POSITION);

        if (position != ListView.INVALID_POSITION) {
        	if(isChecked == true) {
				checkBoxCallBacks.onCheckedItem((CheckBox)buttonView);
			} else {
				checkBoxCallBacks.onUnchekedItem((CheckBox)buttonView);
			}
        	checkedItems.set(position, isChecked);
        } else {
            Log.d(TAG, "Invalid position - buttonView");
        }
	}
}
