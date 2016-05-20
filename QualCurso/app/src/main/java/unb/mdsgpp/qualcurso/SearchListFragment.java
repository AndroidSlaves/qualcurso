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

public class SearchListFragment extends ListFragment{
	
	private static final String YEAR = "year";
	private static final String FIELD = "field";
	private static final String RANGE_A = "rangeA";
	private static final String RANGE_B = "rangeB";
	private static final String BEAN_LIST = "beanList";
	private final String CLASS_CAST_EXCEPTION_COMPLEMENT = "must implement BeanListCallbacks.";

	BeanListCallbacks beanCallbacks;
	
	public SearchListFragment() {
	}
	
	public static SearchListFragment newInstance(final ArrayList<? extends Parcelable> LIST_OF_PARCELABLES, Search search){
		SearchListFragment fragment = new SearchListFragment();
		Bundle args = new Bundle();

		//set arguments.
		args.putInt(YEAR, search.getYear());
		args.putString(FIELD, search.getIndicator().getValue());
		args.putInt(RANGE_A, search.getMinValue());
		args.putInt(RANGE_B, search.getMaxValue());
		args.putParcelableArrayList(BEAN_LIST,LIST_OF_PARCELABLES);
		fragment.setArguments(args);

		assert (fragment != null) : "Receive a null treatment";
		return fragment;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+ CLASS_CAST_EXCEPTION_COMPLEMENT);
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Get arguments for the generic element bean.
		ArrayList<Parcelable> list;
		if(getArguments().getParcelableArrayList(BEAN_LIST) != null){
			list = getArguments().getParcelableArrayList(BEAN_LIST);
		}else{
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		assert (rootView != null) : "Receive a null treatment";
		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList(BEAN_LIST, getArguments().getParcelableArrayList(BEAN_LIST));
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onListItemClick(ListView listView, View view,final int LIST_ITEM_POSITION,final long ID) {
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
		if(bean instanceof Institution){
			search.setOption(Search.INSTITUTION);
		}else if(bean instanceof Course){
			search.setOption(Search.COURSE);
		}

		// Make search based on the ranges and set the selected institutions or courses.
		search.setMinValue(RANGEA);
		search.setMaxValue(RANGEB);
		beanCallbacks.onSearchBeanSelected(search, bean);

		super.onListItemClick(listView, view, LIST_ITEM_POSITION, ID);
	}
	
	private ActionBar getActionBar() {
		ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

		assert (actionBar != null) : "Receive a null treatment";
        return actionBar;
    }

}
