package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.ArrayList;
import java.util.HashMap;

import models.Course;
import models.GenericBeanDAO;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class RankingFragment extends Fragment {
	BeanListCallbacks beanCallbacks;
	Spinner filterFieldSpinner = null;
	Spinner yearSpinner = null;
	ListView evaluationList = null;
	AutoCompleteTextView autoCompleteField = null;
	Course currentSelection = null;
	String filterField = Indicator.DEFAULT_INDICATOR;

	private static final String COURSE = "course";
	private static final String FILTER_FIELD = "filterField";
	private final String CLASS_CAST_EXCEPTION_COMPLEMENT = "must implement BeanListCallbacks.";

	public RankingFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;

		} catch (ClassCastException e) {
			String classCastException = activity.toString() + CLASS_CAST_EXCEPTION_COMPLEMENT;
			throw new ClassCastException(classCastException);
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
        final int RANKING_FRAGMENT_ID = R.layout.ranking_fragment;
        View rootView = inflater.inflate(RANKING_FRAGMENT_ID, container, false);
        if (savedInstanceState != null) {
			if (savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState.getParcelable(COURSE));
			}
			if (savedInstanceState.getString(FILTER_FIELD) != null) {
				setFilterField(savedInstanceState.getString(FILTER_FIELD));

			}
		}

        // Setting spinners listener.
        this.filterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);
        this.yearSpinner = (Spinner) rootView.findViewById(R.id.year);
        this.filterFieldSpinner.setOnItemSelectedListener(getFilterFieldSpinnerListener());
        this.yearSpinner.setOnItemSelectedListener(getYearSpinnerListener());

        // Setting evaluation list listener
        this.evaluationList = (ListView) rootView.findViewById(R.id.evaluationList);
        evaluationList.setOnItemClickListener(getEvaluationListListener());

        // Setting autocomplete field listener.
        autoCompleteField = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        autoCompleteField.setOnItemClickListener(getAutoCompleteListener(rootView));

        //Setting autocomplete adapters
        setAutoCompleteAdapters();

        if (currentSelection != null && filterField != Indicator.DEFAULT_INDICATOR) {
			updateList();
		}

        return rootView;
	}
	private void setAutoCompleteAdapters(){
        // Getting the variables for the autocompleteAdapter
        Context context = getActivity().getApplicationContext();
        final int CUSTOM_TEXT_VIEW = R.layout.custom_textview;
        ArrayList<Course> courses = Course.getAll();

        // Setting auto complete adapters.
        autoCompleteField.setAdapter(new ArrayAdapter<Course>(context,CUSTOM_TEXT_VIEW, courses));

        // Getting parameters for ArrayAdapter
        Context themedContext = getActivity().getApplicationContext();
        final int ID_SPINNER = R.layout.simple_spinner_item;
        final int ID_ITEM_SPINNER = R.id.spinner_item_text;
        ArrayList<Indicator> indicator = Indicator.getIndicators();

        //Creating and setting ArrayAdapter
        ArrayAdapter arrayAdapter = new ArrayAdapter<Indicator>(themedContext, ID_SPINNER,
                ID_ITEM_SPINNER, indicator);
        this.filterFieldSpinner.setAdapter(arrayAdapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(COURSE, this.currentSelection);
		outState.putString(FILTER_FIELD, this.filterField);
		super.onSaveInstanceState(outState);
	}
	
	public OnItemClickListener getAutoCompleteListener(final View rootView){
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();

				hideKeyboard(rootView);
			}
		};
	}

	public OnItemClickListener getEvaluationListListener(){
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int EVALUATION_YEAR = Integer.parseInt(((HashMap<String, String>)
                        parent.getItemAtPosition(position)).get("year"));
                final int EVALUATION_COURSE_ID = Integer.parseInt(((HashMap<String, String>)
                        parent.getItemAtPosition(position)).get("id_course"));
                final int EVALUATION_INSTITUTION_ID = Integer.parseInt(((HashMap<String, String>)
                        parent.getItemAtPosition(position)).get("id_institution"));

                EvaluationDetailFragment fragment = EvaluationDetailFragment.newInstance(
                        EVALUATION_INSTITUTION_ID,EVALUATION_COURSE_ID,EVALUATION_YEAR);

                beanCallbacks.onBeanListItemSelected(fragment);
			}
		};
	}
	
	public OnItemSelectedListener getFilterFieldSpinnerListener(){
		return new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                setFilterField(((Indicator) arg0.getItemAtPosition(arg2)).getValue());

                if (currentSelection != null && filterField != Indicator.DEFAULT_INDICATOR) {
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		};
		
	}
	
	public OnItemSelectedListener getYearSpinnerListener(){
		return new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (currentSelection != null && filterField != Indicator.DEFAULT_INDICATOR) {
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		};
	}
	
	public ArrayList<String> getListFields(){
		ArrayList<String> fields = new ArrayList<String>();
		fields.add(this.filterField);
		fields.add("id_institution");
		fields.add("id_course");
		fields.add("acronym");
		fields.add("year");
		return fields;
	}
	
	public int getYear(){
		int year = 0;
		if (yearSpinner.getSelectedItemPosition() != 0) {
			year = Integer.parseInt(yearSpinner.getSelectedItem()
					.toString());
		} else {
			yearSpinner
					.setSelection(yearSpinner.getAdapter().getCount() - 1);
			year = Integer.parseInt(yearSpinner.getAdapter()
					.getItem(yearSpinner.getAdapter().getCount() - 1)
					.toString());
		}
		return year;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public void setCurrentSelection(Course currentSelection) {
		this.currentSelection = currentSelection;
	}
	
	private void displayToastMessage(String textMenssage) {
		Toast toast = Toast.makeText(
				this.getActivity().getApplicationContext(), textMenssage,
				Toast.LENGTH_SHORT);
		toast.show();
	}

	public void updateList() {
		if (this.filterField != Indicator.DEFAULT_INDICATOR) {
			final ArrayList<String> fields = getListFields();
			int year = getYear();
			GenericBeanDAO gDB = new GenericBeanDAO();
			ListAdapter adapter = new ListAdapter(getActivity()
					.getApplicationContext(), R.layout.list_item,
					gDB.selectOrdered(fields, fields.get(0), "id_course ="
							+ this.currentSelection.getId() + " AND year ="
							+ year, "id_institution", true));
			evaluationList.setAdapter(adapter);
		} else {
			String emptySearchFilter = getResources().getString(
					R.string.empty_search_filter);
			displayToastMessage(emptySearchFilter);
		}
	}

	private void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
}
