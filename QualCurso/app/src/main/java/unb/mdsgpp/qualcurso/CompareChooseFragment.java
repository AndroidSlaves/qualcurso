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

public class CompareChooseFragment extends Fragment implements CheckBoxListCallbacks{

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

	public CompareChooseFragment() {
		super();
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
			Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.compare_choose_fragment, container, false);

		if (savedInstanceState != null) {
			if (savedInstanceState.getParcelable(COURSE) != null) {
				setCurrentSelection((Course) savedInstanceState.getParcelable(COURSE));
			} else {/*Nothing to do*/}
		} else {/*Nothing to do*/}

        boundLayoutObjects(rootView);

        setAutoCompleteArrayAdapter();

		// Set objects events
		this.yearSpinner.setOnItemSelectedListener(getYearSpinnerListener());
		this.autoCompleteField.setOnItemClickListener(getAutoCompleteListener(rootView));

		return rootView;
	}
	
	public OnItemClickListener getAutoCompleteListener(final View rootView){
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
				setCurrentSelection((Course) parent.getItemAtPosition(position));
				updateList();

				hideKeyboard(rootView);
			}
		};
	}

	public OnItemSelectedListener getYearSpinnerListener(){
		return new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(selectedCourse!=null){
					updateList();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		};
	}

	public void updateList() {
		selectedInstitutions = new ArrayList<Institution>();
        final int selectedItemPosition = yearSpinner.getSelectedItemPosition();

		if (selectedItemPosition != 0) {
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
		if (this.selectedCourse != null) {
			ArrayList<Institution> courseInstitutions = this.selectedCourse.getInstitutions(selectedYear);
            Context themedView = getActionBar().getThemedContext();
            int compareChooseListItemId = R.layout.compare_choose_list_item;

			compareAdapterList = new ListCompareAdapter(themedView,compareChooseListItemId,courseInstitutions, this);

			this.institutionList.setAdapter(compareAdapterList);
		} else {/*Nothing to do*/}
	}

	@Override
	public void onCheckedItem(CheckBox checkBox) {

        int institutionId = ListCompareAdapter.INSTITUTION;
		Institution institution = ((Institution) checkBox.getTag(institutionId));
        Boolean institutionExists = selectedInstitutions.contains(institution);

		if(!institutionExists){
			selectedInstitutions.add(institution);

			if(selectedInstitutions.size() == 2){
                Log.d("Selected year: ", Integer.toString(selectedYear));
                final int firstSelectedInstitutionId = selectedInstitutions.get(0).getId();
                final int secondSelectedInstitutionId = selectedInstitutions.get(1).getId();
                final int courseId = selectedCourse.getId();

				Evaluation evaluationA = Evaluation.getFromRelation(firstSelectedInstitutionId, courseId, selectedYear);
                Evaluation evaluationB = Evaluation.getFromRelation(secondSelectedInstitutionId, courseId, selectedYear);

                CompareShowFragment compareShowFragment =CompareShowFragment.newInstance(evaluationA.getId(), evaluationB.getId());
                beanCallbacks.onBeanListItemSelected(compareShowFragment);
			} else {/*Nothing to do*/}
		} else {/*Nothing to do*/}
	}
	@Override
	public void onPause() {
		selectedInstitutions = new ArrayList<Institution>();
		super.onPause();
	}

	@Override
	public void onUnchekedItem(CheckBox checkBox) {
		// TODO Auto-generated method stub
		Institution institution = ((Institution)checkBox.getTag(ListCompareAdapter.INSTITUTION));
		if(selectedInstitutions.contains(institution)){
			selectedInstitutions.remove(institution);
		} else {/*Nothing to do*/}
	}

    public void setCurrentSelection(Course currentSelection) {
        this.selectedCourse = currentSelection;
    }

    private void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}
	
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private void setAutoCompleteArrayAdapter(){
        int customTextViewId = R.layout.custom_textview;
        Context themedContext = getActionBar().getThemedContext();
        ArrayList<Course> listOfCourses = Course.getAll();

        ArrayAdapter autoCompleteAdapter = new ArrayAdapter<Course>(themedContext,customTextViewId,listOfCourses);

        this.autoCompleteField.setAdapter(autoCompleteAdapter);
    }

    private void boundLayoutObjects(final View rootView){
        assert(rootView != null): "rootView must never be null";

        // bound variables with layout objects
        int yearSpinnerId = R.id.compare_year;
        this.yearSpinner = (Spinner) rootView.findViewById(yearSpinnerId);

        int autoCompleteId = R.id.autoCompleteTextView;
        this.autoCompleteField = (AutoCompleteTextView) rootView.findViewById(autoCompleteId);

        int institutionListId = R.id.institutionList;
        this.institutionList = (ListView) rootView.findViewById(institutionListId);
    }

}
