/*****************************
 * Class name: SearchByIndicatorFragment (.java)
 *
 * Purpose:
 *****************************/

package unb.mdsgpp.qualcurso;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import helpers.Indicator;
import models.Course;
import models.Institution;
import models.Search;

public class SearchByIndicatorFragment extends Fragment {
	
	BeanListCallbacks beanCallbacks;
	Spinner listSelectionSpinner = null;
	Spinner filterFieldSpinner = null;
	Spinner yearSpinner = null;
	CheckBox maximum = null;
	EditText firstNumber = null;
	EditText secondNumber = null;
	Button searchButton = null;

	private final String TAG = SearchByIndicatorFragment.class.getSimpleName();

    /**
     * Declaration of an empty constructor.
     */
	public SearchByIndicatorFragment() {
        super();
	}

    /**
     * Called when a fragment is first attached to its activity.
     *
     * @param activity
     */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		assert (activity != null) : "Activity context must never be null.";

		try {
			Log.d(TAG, "Trying to attach activity to bean list...");
			beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
			Log.e(TAG, "ERROR: Can't attach activity to bean list!");
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}

    /**
     * Called once the fragment is associated with its activity.
     */
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }


    /**
     *
     * Container is responsible to generate the LayoutParams of the view savedInstanceState if the
     * fragment is being recreated from a previous saved state, this is the state.Inflater is
     * responsible to inflate a view.
     *
     * @param inflater
     *              Component Layout inflates the screen to the user
     * @param container
     *              Storage box components Layout
     * @param savedInstanceState
     *              Saves the state of the instance at this point in the application
     * @return rootView
     *          Component view inflated with screen attributes.
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		assert (inflater != null) : "LayoutInflater must never be null.";
		assert (container != null) : "Container layout must never be null.";
		assert (savedInstanceState != null) : "Bundle instance must never be null.";

		View rootView = inflater.inflate(R.layout.search_fragment, container,false);
		
		listSelectionSpinner = (Spinner) rootView.findViewById(R.id.course_institution);
		filterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);
		filterFieldSpinner.setAdapter(new ArrayAdapter<Indicator>(getActionBar().getThemedContext(),
                R.layout.simple_spinner_item, R.id.spinner_item_text,Indicator.getIndicators()));
		yearSpinner = (Spinner) rootView.findViewById(R.id.year);
		maximum = (CheckBox) rootView.findViewById(R.id.maximum);
		firstNumber = (EditText) rootView.findViewById(R.id.firstNumber);
		secondNumber = (EditText) rootView.findViewById(R.id.secondNumber);
		searchButton = (Button) rootView.findViewById(R.id.buttonSearch);
		
		searchButton.setOnClickListener(getClickListener());
		
		// Event to disable second number when MAX is checked
		maximum.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if( maximum.isChecked() ) {
					secondNumber.setEnabled(false);
				} else {
					secondNumber.setEnabled(true);
				}
			}
		});

		assert (rootView != null) : "Receive a null treatment";
		return rootView;
	}

    /**
     * Related activities when an item was clicked.
     * @return OnClickListener()
     */
	public OnClickListener getClickListener(){
		return new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int firstNumber, secondNumber, year, max, listSelectionPosition;

				if(SearchByIndicatorFragment.this.firstNumber.getText().length() == 0) {
					SearchByIndicatorFragment.this.firstNumber.setText("0");
				} else {
                    /* Nothing to do! */
                }

				if(SearchByIndicatorFragment.this.secondNumber.getText().length() == 0) {
					maximum.setChecked(true);
                } else {
                    /* Nothing to do! */
                }

				String firstNumberValue = SearchByIndicatorFragment.this.firstNumber.getText().toString();
				String secondNumberValue = SearchByIndicatorFragment.this.secondNumber.getText().toString();

				firstNumber = Integer.parseInt(firstNumberValue);
				secondNumber = maximum.isChecked() ? -1 : Integer.parseInt(secondNumberValue);
				listSelectionPosition = listSelectionSpinner.getSelectedItemPosition();

				if(yearSpinner.getSelectedItemPosition() != 0) {
					year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
				} else {
					year = Integer.parseInt(yearSpinner.getAdapter().getItem(yearSpinner
										   .getAdapter().getCount()-1).toString());
				}

				if(maximum.isChecked()) {
					max = -1;
				} else {
					max = secondNumber;
				}

				SearchByIndicatorFragment.this.firstNumber.clearFocus();
				SearchByIndicatorFragment.this.secondNumber.clearFocus();
				hideKeyboard(arg0);

				updateSearchList(firstNumber, max, year, listSelectionPosition,
						        ((Indicator)filterFieldSpinner.getItemAtPosition(filterFieldSpinner
								.getSelectedItemPosition())));
			}
		};
	}

	private void hideKeyboard(View view) {
		assert (view != null) : "View context must never be null.";

		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context
								  .INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager
								    .RESULT_UNCHANGED_SHOWN);
	}

	private void callInstitutionList(int min, int max, int year, Indicator filterField) {
		assert (min >= 0) : "Minimum value can't be negative.";
		assert (max >= 0) : "Maximum value can't be negative";
		assert (year >= 0) : "Year value can't be negative";
		assert (filterField != null) : "Field can't be null.";

		Calendar calendar = Calendar.getInstance();
		Search search = new Search();
		search.setDate(new Date(calendar.getTime().getTime()));
		search.setYear(year);
		search.setOption(1);
		search.setIndicator(filterField);
		search.setMinValue(min);
		search.setMaxValue(max);
		search.save();
		ArrayList<Institution> beanList = Institution.getInstitutionsByEvaluationFilter(search);
		beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(beanList,search),
											 R.id.search_list);
	}

	private void callCourseList(int min, int max, int year, Indicator filterField){
		assert (min >= 0) : "Minimum value can't be negative.";
		assert (max >= 0) : "Maximum value can't be negative";
		assert (year >= 0) : "Year value can't be negative";
		assert (filterField != null) : "Field can't be null.";

		Calendar calendar = Calendar.getInstance();
		Search search = new Search();
		search.setDate(new Date(calendar.getTime().getTime()));
		search.setYear(year);
		search.setOption(0);
		search.setIndicator(filterField);
		search.setMinValue(min);
		search.setMaxValue(max);
		search.save();
		ArrayList<Course> beanList = Course.getCoursesByEvaluationFilter(search);
		beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(beanList,search),
				                             R.id.search_list);
	}
	
	private void updateSearchList(int min, int max, int year, int listSelectionPosition,
								  Indicator filterField) {
		assert (min >= 0) : "Minimum value can't be negative.";
		assert (max >= 0) : "Maximum value can't be negative";
		assert (year >= 0) : "Year value can't be negative";
		assert (filterField != null) : "Field can't be null.";

		if(filterField.getValue() == Indicator.DEFAULT_INDICATOR) {
			Context context = QualCurso.getInstance();
			String emptySearchFilter = getResources().getString(R.string.empty_search_filter);

			Toast toast = Toast.makeText(context, emptySearchFilter, Toast.LENGTH_SHORT);
			toast.show();
		} else {
				switch (listSelectionPosition) {
				case 0:
					listSelectionSpinner.setSelection(listSelectionSpinner.getAdapter().getCount()-1);
					yearSpinner.setSelection(yearSpinner.getAdapter().getCount()-1);

					callInstitutionList(min, max, year, filterField);
					break;

				case 1:
					callCourseList(min, max, year, filterField);
					break;

				case 2:
					callInstitutionList(min, max, year, filterField);
					break;

				default: /* Nothing to do! */
					break;
				}
		}
		
	}

    /**
     * Retrieve a reference to this activity's ActionBar.
     *
     * @return myActionBar
     */
	private ActionBar getActionBar() {
		ActionBar myActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

		assert (myActionBar != null) : "Receive a null treatment";
        return myActionBar;
    }
}