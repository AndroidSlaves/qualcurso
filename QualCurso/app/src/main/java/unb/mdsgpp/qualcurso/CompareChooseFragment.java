/*****************************
 * Class name: CompareChooseFragment (.java)
 *
 * Purpose: User interface behavior definition related to comparisons between institutions from a
 *              specific course.
 *****************************/

package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Course;
import models.Evaluation;
import models.Institution;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CompareChooseFragment extends Fragment implements CheckBoxListCallbacks {
    private BeanListCallbacks beanCallbacks;

	private static final String COURSE = "course";
    private final String CLASS_CAST_EXCEPTION_COMPLEMENT = "must implement BeanListCallbacks.";

	private Spinner yearSpinner = null;
	private AutoCompleteTextView autoCompleteField = null;
	private ListView institutionList = null;
	private ListCompareAdapter compareAdapterList = null;
	private int selectedYear = 2016;
	private Course selectedCourse = null;
	private ArrayList<Institution> selectedInstitutions = new ArrayList<Institution>();

    /**
     * Declaration of an empty constructor.
     */
	public CompareChooseFragment() {
        super();
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

        } catch (ClassCastException e) {
            String classCastException = activity.toString() + CLASS_CAST_EXCEPTION_COMPLEMENT;
			throw new ClassCastException(classCastException);
        }

		assert (activity != null) : "Receive a null treatment";
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
		View rootView = inflater.inflate(R.layout.compare_choose_fragment, container, false);

		if (savedInstanceState != null && savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState.getParcelable(COURSE));
		} else {
		    /*Nothing to do*/
        }

        boundLayoutObjects(rootView);

        setAutoCompleteArrayAdapter();

		// Set objects events
		this.yearSpinner.setOnItemSelectedListener(getYearSpinnerListener());
		this.autoCompleteField.setOnItemClickListener(getAutoCompleteListener(rootView));

		assert (rootView != null) : "Receive a null treatment";
		return rootView;
	}

    /**
     * When an institution is selected in the list of institutions from the course, set these
     * institutions chosen for comparison.
     *
     * @param rootView
     *              View screen component system user
     * @return
     *              Screen component
     */
	public OnItemClickListener getAutoCompleteListener(final View rootView) {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();

				hideKeyboard(rootView);
			}
		};
	}

    /**
     * By selecting the year related to research institutions for comparison.
     *
     * @return
     *              It returns a listener for a item selected on the year spinner list.
     */
	public OnItemSelectedListener getYearSpinnerListener() {
		return new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(selectedCourse!=null){
					updateList();
				}else {
					/*Nothing to do.*/
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
	}

    /**
     * Updates the search result list.
     */
	public void updateList() {
		selectedInstitutions = new ArrayList<Institution>();
        final int selectedItemPosition = yearSpinner.getSelectedItemPosition();

		if(selectedItemPosition != 0) {
			selectedYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());

		} else {
            final int lastArrayPosition = yearSpinner.getAdapter().getCount() - 1;
			yearSpinner.setSelection(lastArrayPosition);

            /* If there is future problems, there was a command here:
             * lastArrayPosition = yearSpinner.getAdapter().getCount() - 1;*/
            Adapter listAdapter = yearSpinner.getAdapter();
            String lastItem = listAdapter.getItem(lastArrayPosition).toString();
			selectedYear = Integer.parseInt(lastItem);
		}

		if(this.selectedCourse != null) {
			ArrayList<Institution> courseInstitutions = this.selectedCourse
                    .getInstitutions(selectedYear);
            Context themedView = getActionBar().getThemedContext();
            int compareChooseListItemId = R.layout.compare_choose_list_item;

			compareAdapterList = new ListCompareAdapter(themedView,compareChooseListItemId,
                    courseInstitutions, this);

			this.institutionList.setAdapter(compareAdapterList);
		} else {
		    /*Nothing to do*/
        }
	}

    /**
     * Obtaining parameters of the institutions to be compared.
     *
     * @param checkBox
     *              Selection of user institutions.
     */
	@Override
	public void onCheckedItem(CheckBox checkBox) {

        int institutionId = ListCompareAdapter.INSTITUTION;
		Institution institution = ((Institution) checkBox.getTag(institutionId));
        Boolean institutionExists = selectedInstitutions.contains(institution);
        final int INSTITUTIONS_OF_AMOUNT_COMPARED = 2;

		if(!institutionExists){
			selectedInstitutions.add(institution);

			if(selectedInstitutions.size() == INSTITUTIONS_OF_AMOUNT_COMPARED){
                Log.d("Selected year: ", Integer.toString(selectedYear));
                final int firstSelectedInstitutionId = selectedInstitutions.get(0).getId();
                final int secondSelectedInstitutionId = selectedInstitutions.get(1).getId();
                final int courseId = selectedCourse.getId();

				Evaluation evaluationA = Evaluation.getFromRelation(firstSelectedInstitutionId,
										 courseId, selectedYear);
                Evaluation evaluationB = Evaluation.getFromRelation(secondSelectedInstitutionId,
										 courseId, selectedYear);
                CompareShowFragment compareShowFragment = CompareShowFragment
														  .newInstance(evaluationA.getId(),
																  evaluationB.getId());
                beanCallbacks.onBeanListItemSelected(compareShowFragment);

			} else {
			    /*Nothing to do*/
            }
		} else {
		    /*Nothing to do*/
        }
	}

    /**
     * Insertion of the institutions selected in the comparison list.
     */
	@Override
	public void onPause() {
		selectedInstitutions = new ArrayList<Institution>();
		super.onPause();
	}

    /**
     * Institution removal previously selected for comparison.
     *
     * @param checkBox
     *              Selection of user institutions.
     */
	@Override
	public void onUnchekedItem(CheckBox checkBox) {
		Institution institution = ((Institution)checkBox.getTag(ListCompareAdapter.INSTITUTION));

		if(selectedInstitutions.contains(institution)) {
			selectedInstitutions.remove(institution);
		} else {
		    /*Nothing to do*/
        }
	}

    /**
     * Set the selection set a institution.
     *
     * @param CURRENT_SELECTION
     *              The current selection of a institution
     */
    public void setCurrentSelection(final Course CURRENT_SELECTION) {
		assert (CURRENT_SELECTION != null) : "Receive a null treatment";
        this.selectedCourse = CURRENT_SELECTION;
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

    /**
     * Retrieve a reference to this activity's ActionBar.
     * @return action
     *              ActionBar obtained by Activity and its support
     */
	private ActionBar getActionBar() {
        ActionBar action = ((ActionBarActivity) getActivity()).getSupportActionBar();
		assert (action != null) : "Receive a null treatment";
        return action;
    }

    /**
     * Assisted in the course of research with auto search add-on.
     */
    private void setAutoCompleteArrayAdapter() {
        int customTextViewId = R.layout.custom_textview;
        Context themedContext = getActionBar().getThemedContext();
        ArrayList<Course> listOfCourses = Course.getAll();

        ArrayAdapter autoCompleteAdapter = new ArrayAdapter<Course>(themedContext,customTextViewId,
                listOfCourses);

        this.autoCompleteField.setAdapter(autoCompleteAdapter);
    }

    /**
     * Bond of layout objects from the id.
     *
     * @param ROOTVIEW
     *              View screen component system user.
     */
    private void boundLayoutObjects(final View ROOTVIEW) {
        // bound variables with layout objects
        int yearSpinnerId = R.id.compare_year;
        this.yearSpinner = (Spinner) ROOTVIEW.findViewById(yearSpinnerId);

        int autoCompleteId = R.id.autoCompleteTextView;
        this.autoCompleteField = (AutoCompleteTextView) ROOTVIEW.findViewById(autoCompleteId);

        int institutionListId = R.id.institutionList;
        this.institutionList = (ListView) ROOTVIEW.findViewById(institutionListId);

		assert(ROOTVIEW != null): "rootView must never be null";
    }
}
