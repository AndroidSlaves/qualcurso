/*****************************
 * Class name: RankingFragment (.java)
 *
 * Purpose: Lets the user select a course, a year and a indicator to show a ranking with the better
 * institution within the best results in the chosen params.
 *****************************/

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
	private static final String COURSE = "course";
	private static final String FILTER_FIELD = "filterField";
	private final String CLASS_CAST_EXCEPTION_COMPLEMENT = "must implement BeanListCallbacks.";

    public BeanListCallbacks beanCallbacks = null;
    public Spinner indicatorListFilterFieldSpinner = null;
    public Spinner yearSpinner = null;
    public ListView evaluationList = null;
    public AutoCompleteTextView autoCompleteField = null;
    public Course currentSelection = null;
    public String filterField = Indicator.DEFAULT_INDICATOR;

	public RankingFragment() {
		super();
	}

    /**
     * Preparation callbacks bean when the user access the fragment ranking used for the system to
     * know where to return.
     *
     * @param ACTIVITY
     *              Previous call activity
     */
	@Override
	public void onAttach(final Activity ACTIVITY) {
		super.onAttach(ACTIVITY);

		try {
			beanCallbacks = (BeanListCallbacks) ACTIVITY;
		} catch(ClassCastException excClassCast) {
			String classCastException = ACTIVITY.toString() + CLASS_CAST_EXCEPTION_COMPLEMENT;
			throw new ClassCastException(classCastException);
		}

		assert (ACTIVITY != null) : "Receive a null treatment";
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
     * Controller attributes of the link and your field vision and setting their values.
     *
     * @param inflater
     *              Component Layout inflates the screen to the user
     * @param container
     *              Storage box components Layout
     * @param savedInstanceState
     *              Saves the state of the instance at this point in the application
     *
     * @return rootView
     *          Component view inflated with screen attributes.
     */
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final int RANKING_FRAGMENT_ID = R.layout.ranking_fragment;
        final View rootView = inflater.inflate(RANKING_FRAGMENT_ID, container, false);

        if(savedInstanceState != null) {
			if(savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState.getParcelable(COURSE));
			} else {
			    /*Nothing to do.*/
            }

			if(savedInstanceState.getString(FILTER_FIELD) != null) {
				setFilterField(savedInstanceState.getString(FILTER_FIELD));
			} else {
			    /*Nothing to do.*/
            }
		} else {
		    /*Nothing to do.*/
        }

        // Setting spinners listener.
        this.indicatorListFilterFieldSpinner = (Spinner) rootView.findViewById(R.id.field);
        this.yearSpinner = (Spinner) rootView.findViewById(R.id.year);
        this.indicatorListFilterFieldSpinner.setOnItemSelectedListener(getFilterFieldSpinnerListener());
        this.yearSpinner.setOnItemSelectedListener(getYearSpinnerListener());

        // Setting evaluation list listener
        this.evaluationList = (ListView) rootView.findViewById(R.id.evaluationList);
        evaluationList.setOnItemClickListener(getEvaluationListListener());

        // Setting autocomplete field listener.
        autoCompleteField = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        autoCompleteField.setOnItemClickListener(getAutoCompleteListener(rootView));

        //Setting autocomplete adapters
        setAutoCompleteAdapters();

        if(currentSelection != null && filterField != Indicator.DEFAULT_INDICATOR) {
			updateList();
		} else {
		    /*Nothing to do.*/
        }

		assert (rootView != null) : "Receive a null treatment";
		return rootView;
	}

	private void setAutoCompleteAdapters() {
        // Getting the variables for the autoCompleteAdapter
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
        this.indicatorListFilterFieldSpinner.setAdapter(arrayAdapter);
	}

    /**
     * Saves application data when the user is not using it.
     *
     * @param outState
     *          Persistent object data
     */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(COURSE, this.currentSelection);
		outState.putString(FILTER_FIELD, this.filterField);
		super.onSaveInstanceState(outState);
		assert (outState != null) : "Receive a null treatment";
	}

    /**
     * When a course is selected in the courses list, set this course as the current course for
     * ranking.
     *
     * @param rootView
     *              View screen component system user
     * @return
     *              Screen component
     */
	public OnItemClickListener getAutoCompleteListener(final View rootView){
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();

				assert (rootView != null) : "Receive a null treatment";
				hideKeyboard(rootView);
			}
		};
	}

    /**
     * Get the clicked item on the evaluation list and prepare a fragment to view all evaluation
     * data.
     *
     * @return
     *          Returns actions that are performed by clicking on a Evaluation.
     */
	public OnItemClickListener getEvaluationListListener(){
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int EVALUATION_YEAR = Integer.parseInt(((HashMap<String, String>)
                        					parent.getItemAtPosition(position)).get("year"));

                final int EVALUATION_COURSE_ID = Integer.parseInt(((HashMap<String, String>)
                        						 parent.getItemAtPosition(position))
												 .get("id_course"));

                final int EVALUATION_INSTITUTION_ID = Integer.parseInt(((HashMap<String, String>)
                        							  parent.getItemAtPosition(position))
													  .get("id_institution"));

                EvaluationDetailFragment fragment = EvaluationDetailFragment.newInstance(
                        							EVALUATION_INSTITUTION_ID,EVALUATION_COURSE_ID,
													EVALUATION_YEAR);

                beanCallbacks.onBeanListItemSelected(fragment);
			}
		};
	}

    /**
     * On select event of ranking indicator list.
     *
     * @return
     *              It returns a listener for a item selected on the indicator spinner list.
     */
	public OnItemSelectedListener getFilterFieldSpinnerListener() {
		return new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView arg0, View arg1, int arg2, long arg3) {
                setFilterField(((Indicator) arg0.getItemAtPosition(arg2)).getValue());

                if(currentSelection != null && filterField != Indicator.DEFAULT_INDICATOR) {
					updateList();
				} else {
				    /*Nothing to do.*/
                }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		};
		
	}

    /**
     * On select event of ranking year list.
     *
     * @return
     *              It returns a listener for a item selected on the year spinner list.
     */
	public OnItemSelectedListener getYearSpinnerListener() {
		return new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView arg0, View arg1, int arg2, long arg3) {
				if(currentSelection != null && filterField != Indicator.DEFAULT_INDICATOR) {
					updateList();
				} else {
				    /*Nothing to do.*/
                }
			}

			@Override
			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub
			}
		};
	}

    /**
     * Get a arrayList with all Ranking fields and the current selected indicator to be used on the
     * search for the evaluations.
     *
     * @return ArrayList<String> fields
     *              ArrayList to catch a fields list containing all the attributes.
     */
	public ArrayList<String> getListFields(){
        final String FIELDS_NAMES[] = {this.filterField,"id_institution","id_course","acronym",
                					  "year"};
        ArrayList<String> fields = new ArrayList<String>();

		// Max number that will be added in fields names.
		final int FIELDS_NAMES_MAX = 5;

		for(int i = 0; i < FIELDS_NAMES_MAX; i++) {
			fields.add(FIELDS_NAMES[i]);
		}

		return fields;
	}

    /**
     * Get the selected year, by default set the Ranking year to the last year available.
     *
     * @return year
     *              Int - Evaluation of the year to be searched
     */
	public int getYear(){
		int year = 0;
        final int SPINNER_POSITION = yearSpinner.getSelectedItemPosition();

        // Get year spinner and return the selected year.
		if(SPINNER_POSITION != 0) {
            final String YEAR_SPINNER_OBJECT = yearSpinner.getSelectedItem().toString();
			year = Integer.parseInt(YEAR_SPINNER_OBJECT);

		} else {
			yearSpinner.setSelection(yearSpinner.getAdapter().getCount() - 1);
            final int YEAR_SPINNER_COUNT = yearSpinner.getAdapter().getCount() - 1;
            final Object YEAR_SPINNER_OBJECT = yearSpinner.getAdapter().getItem(YEAR_SPINNER_COUNT);
			year = Integer.parseInt(YEAR_SPINNER_OBJECT.toString());
		}

		return year;
	}

    /**
     * Insert the filter field in the user's screen.
     *
     * @param FILTER_FIELD
     *              Filter field to search.
     */
	public void setFilterField(final String FILTER_FIELD) {
		assert (FILTER_FIELD != null) : "Receive a null treatment";
		this.filterField = FILTER_FIELD;
	}

    /**
     * Set the selection set a course.
     *
     * @param CURRENT_SELECTION
     *              The current selection of a course
     */
	public void setCurrentSelection(final Course CURRENT_SELECTION) {
		assert (CURRENT_SELECTION != null) : "Receive a null treatment";
		this.currentSelection = CURRENT_SELECTION;
	}

    /**
     * The user from presentation.
     *
     * @param TEXT_MESSAGE
     *          Message of system.
     */
	private void displayToastMessage(final String TEXT_MESSAGE) {
		final Context context = this.getActivity().getApplicationContext();
		assert (context != null) : "Receive a null treatment";
		assert (TEXT_MESSAGE != null) : "Receive a null treatment";
        Toast toast = Toast.makeText(context, TEXT_MESSAGE, Toast.LENGTH_SHORT);
		toast.show();
	}

    /**
     * Updates the search result list.
     */
	public void updateList() {
		if(this.filterField != Indicator.DEFAULT_INDICATOR) {
            // Getting values to set adapter.
			final ArrayList<String> fields = getListFields();
            final String ID = "id_institution";
			final GenericBeanDAO gDB = new GenericBeanDAO();
            int year = getYear();
            int id = this.currentSelection.getId();
            final String SQL_QUERY = "id_course =" + id + " AND year =" + year;

            // Get hash of institutions from database.
            ArrayList<HashMap<String, String>> selectedListFromDB = gDB.selectOrdered(fields,
                    fields.get(0), SQL_QUERY, ID, true);

            // Creating list adapter
            Context context = getActivity().getApplicationContext();
            final int ID_LIST_ITEM = R.layout.list_item;
			ListAdapter adapter = new ListAdapter(context, ID_LIST_ITEM, selectedListFromDB);

            // Setting adapter.
			evaluationList.setAdapter(adapter);

        } else {
            String emptySearchFilter = getResources().getString(R.string.empty_search_filter);
			displayToastMessage(emptySearchFilter);
		}
	}

    /**
     * This method forces the digital Keyboard to be hidden.
     *
     * @param VIEW
     *              The digital keyboard view component.
     */
	private void hideKeyboard(final View VIEW) {
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                				 .getSystemService(Context.INPUT_METHOD_SERVICE);
								 inputMethodManager.hideSoftInputFromWindow(VIEW.getWindowToken(),
										 InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
}
