package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import models.Course;
import models.Institution;
import models.Search;
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

public class SearchByIndicatorFragment extends Fragment {
	
	BeanListCallbacks beanCallbacks;
	Spinner listSelectionSpinner = null;
	Spinner filterFieldSpinner = null;
	Spinner yearSpinner = null;
	CheckBox maximum = null;
	EditText firstNumber = null;
	EditText secondNumber = null;
	Button searchButton = null;
	// Used to Log system.
	private final String TAG = SearchByIndicatorFragment.class.getSimpleName();
	public SearchByIndicatorFragment() {
		super();
	}

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

	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		assert (inflater != null) : "LayoutInflater must never be null.";
		assert (container != null) : "Container layout must never be null.";
		assert (savedInstanceState != null) : "Bundle instance must never be null.";

		View rootView = inflater.inflate(R.layout.search_fragment, container,
				false);
		
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
	
	public OnClickListener getClickListener(){
		return new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int number1, number2, year, max, listSelectionPosition;

				if( firstNumber.getText().length() == 0) {
					firstNumber.setText("0");
				}

				if( secondNumber.getText().length() == 0 ) {
					maximum.setChecked(true);
				}

				String firstNumberValue = firstNumber.getText().toString();
				String secondNumberValue = secondNumber.getText().toString();

				number1 = Integer.parseInt(firstNumberValue);
				number2 = maximum.isChecked() ? -1 : Integer.parseInt(secondNumberValue);
				listSelectionPosition = listSelectionSpinner.getSelectedItemPosition();

				if( yearSpinner.getSelectedItemPosition() != 0 ) {
					year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
				} else {
					year = Integer.parseInt(yearSpinner.getAdapter().getItem(yearSpinner
										   .getAdapter().getCount()-1).toString());
				}

				if(maximum.isChecked()){
					max = -1;
				}else{
					max = number2;
				}

				firstNumber.clearFocus();
				secondNumber.clearFocus();
				hideKeyboard(arg0);

				updateSearchList(number1, max, year, listSelectionPosition,
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

	private void callInstitutionList(int min, int max, int year, Indicator filterField){
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

				default:
					break;
				}
		}
		
	}
	
	private ActionBar getActionBar() {
		ActionBar myActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

		assert (myActionBar != null) : "Receive a null treatment";
        return myActionBar;
    }
}