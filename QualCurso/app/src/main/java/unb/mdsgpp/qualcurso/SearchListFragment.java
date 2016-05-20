/*****************************
 * Class name: SearchListFragment (.java)
 *
 * Purpose: Creates and inflates the fragment used to list the result of the user's search.
 *****************************/

package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;

import models.Course;
import models.Institution;
import models.Search;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchListFragment extends ListFragment {

    // Global variable to show on screen the string year.
	private static final String YEAR = "year";

    // Global variable to show on screen the string field.
	private static final String FIELD = "field";

    // Global variable to show on screen the string rangeA.
	private static final String RANGE_A = "rangeA";

    // Global variable to show on screen the string rangeB.
	private static final String RANGE_B = "rangeB";

    // Global variable to show on screen the string beanList.
	private static final String BEAN_LIST = "beanList";

    // Declaration of the BeanListCallbacks as beanCallbacks.
	public BeanListCallbacks beanCallbacks;

    private final String CLASS_CAST_EXCEPTION_COMPLEMENT = "must implement BeanListCallbacks.";

    /**
     * Declaration of an empty constructor.
     */
	public SearchListFragment() {
	}

    /**
     * Create a fragment container and show a table with year, field, value, list.
     * Create a list of presentation to the user through input search.
     *
     * @param LIST_OF_PARCELABLES
     *              Interface list used to create a search ListFragment.
     * @param SEARCH
     *              The search object to create a search ListFragment.
     * @return fragment
     *              The return a new instance presenting a list from a search.
     */
	public static SearchListFragment newInstance(ArrayList<?extends Parcelable> LIST_OF_PARCELABLES,
                                                 Search SEARCH) {

		SearchListFragment fragment = new SearchListFragment();

		Bundle args = new Bundle(); // Instance of a new Bundle object named args.

		args.putInt(YEAR, SEARCH.getYear());
		args.putString(FIELD, SEARCH.getIndicator().getValue());
		args.putInt(RANGE_A, SEARCH.getMinValue());
		args.putInt(RANGE_B, SEARCH.getMaxValue());
		args.putParcelableArrayList(BEAN_LIST, LIST_OF_PARCELABLES);

		fragment.setArguments(args);

		assert (fragment != null) : "Receive a null treatment";
		return fragment;
	}

    /**
     * Fragment associated with an activity and on the traceability of activitys.
     *
     * @param activity
     *              Prior to the current fragment activity.
     */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + CLASS_CAST_EXCEPTION_COMPLEMENT);
        }
	}

    /**
     * Disassociate callbacks bean when the fragment is no longer needed.
     */
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }

    /**
     * Performs the initial creation of the fragment, creating a view, inflating it.
     *
     * @param inflater
     *              Responsible to inflate a view
     * @param container
     *              Responsible to generate the LayoutParams of the view
     * @param savedInstanceState
     *              If the fragment is being recreated from a previous saved state, this is the
     *              state.
     *
     * @return rootView
     *              View inflated with the necessary components for the fragment.
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Get arguments for the generic element bean.
		ArrayList<Parcelable> list;
		if(getArguments().getParcelableArrayList(BEAN_LIST) != null) {
			list = getArguments().getParcelableArrayList(BEAN_LIST);
		} else {
			list = savedInstanceState.getParcelableArrayList(BEAN_LIST);
		}

		//try to set the listAdapter with the list of search list.
		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list, container, false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		try {
			Context context = getActionBar().getThemedContext();
			int customViewId = R.layout.custom_textview;
			ArrayAdapter arrayAdapter = new ArrayAdapter<Parcelable>(context,customViewId, list);
			rootView.setAdapter(arrayAdapter);

		} catch(SQLException e) {
			e.printStackTrace();
		}

		assert (rootView != null) : "Receive a null treatment";
		return rootView;
	}

    /**
     * Recover the state for instance an activity before being aborted so that the state can be
     * restored in onCreate.
     *
     * @param outState
     *              Previous state as parameter
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(BEAN_LIST, getArguments().getParcelableArrayList(BEAN_LIST));
		super.onSaveInstanceState(outState);
	}

    /**
     * Triggering when an item in the list is clicked / selected.
     *
     * @param listView
     *              List being viewed by the user on the screen.
     * @param view
     *              View used as a graphical user interface.
     * @param LIST_ITEM_POSITION
     *              Item position clicked in the list.
     * @param ID
     *              Id of the selected item.
     */
	@Override
	public void onListItemClick(ListView listView, View view, final int LIST_ITEM_POSITION,
                                final long ID) {
		// Get indicator based on the clicked item.
		Parcelable bean = (Parcelable)listView.getItemAtPosition(LIST_ITEM_POSITION);
		String arguments = getArguments().getString(FIELD);
		Indicator indicator = Indicator.getIndicatorByValue(arguments);

		// Get constants for year and ranges.
		final int YEAR = getArguments().getInt(SearchListFragment.YEAR);
		final int RANGEA = getArguments().getInt(RANGE_A);
		final int RANGEB = getArguments().getInt(RANGE_B);

		// Create a search with this indicator and year.
		Search search = new Search();
		search.setIndicator(indicator);
		search.setYear(YEAR);

		// Select of option for search based on click
		if(bean instanceof Institution) {
			search.setOption(Search.INSTITUTION);
		}else if(bean instanceof Course) {
			search.setOption(Search.COURSE);
		}

		// Make search based on the ranges and set the selected institutions or courses.
		search.setMinValue(RANGEA);
        search.setMaxValue(RANGEB);
		beanCallbacks.onSearchBeanSelected(search, bean);

		super.onListItemClick(listView, view, LIST_ITEM_POSITION, ID);
	}

    /**
     * Receive action occurred in Action Bar menu
     *
     * @return actionBar
     *              Menu component (application bar) according to the activity.
     */
	private ActionBar getActionBar() {
		ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

		assert (actionBar != null) : "Receive a null treatment";
        return actionBar;
    }

}
