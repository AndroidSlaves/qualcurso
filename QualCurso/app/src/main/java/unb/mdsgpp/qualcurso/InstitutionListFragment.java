/*****************************
 * Class name: InstitutionListFragment (.java)
 *
 * Purpose: Class that represents the list of intitutions screen.
 *****************************/

package unb.mdsgpp.qualcurso;

import android.database.SQLException;

import java.util.ArrayList;

import models.Course;
import models.Institution;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class InstitutionListFragment extends ListFragment{

	// identification number of the chosen course.
	private static final String ID_COURSE = "idCourse";

	// identification number of the listed institutions.
	private static final String IDS_INSTITUTIONS = "idsInstitutions";

	// Year to filter the institutions for the user.
	private static final String YEAR = "year";

	// Generic object to connects to the database.
	BeanListCallbacks beanCallbacks;

	public InstitutionListFragment() {
		super();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, 0);
		args.putInt(YEAR, 0);
		args.putParcelableArrayList(IDS_INSTITUTIONS, getInstitutionsList(0));
		this.setArguments(args);
	}

	/**
	 * Creates a new instance of the InstitutionListFragment with a different year.
	 *
	 * @param id
	 * 				Object id.
	 * @param year
	 * 				Object year.
	 *
	 * @return
	 * 				The fragment with list os intitutions
	 */
	public static InstitutionListFragment newInstance(int id, int year){
		assert (id >= 0) : "id must never be negative";
		assert (year > 1990) : "year must never be smaller";

		InstitutionListFragment fragment = new InstitutionListFragment();
		Bundle args = new Bundle();
		args.putInt(ID_COURSE, id);
		args.putInt(YEAR, year);
		args.putParcelableArrayList(IDS_INSTITUTIONS, getInstitutionsList(id));
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Creates a new instance of the InstitutionListFragment with a diferent year and with a
	 * given list of institutions.
	 *
	 * @param id
	 * 				Object id.
	 * @param year
	 * 				Object year.
	 * @param institutions
	 * 				Object institutions.
	 * @return
	 * 				The fragment with list os intitutions
	 */
	public static InstitutionListFragment newInstance(int id, int year,
													  ArrayList<Institution> institutions){
		assert (id >= 0) : "id must never be negative";
		assert (year > 1990) : "search must never be smaller than 1990";
		assert (institutions != null) : "institutions must never be null";

		// Fragment that the view will fill.
		InstitutionListFragment fragment = new InstitutionListFragment();

		//List of string necessary to instatiate the object.
		Bundle arguments = new Bundle();
		arguments.putInt(ID_COURSE, id);
		arguments.putInt(YEAR, year);
		arguments.putParcelableArrayList(IDS_INSTITUTIONS, institutions);
		fragment.setArguments(arguments);
		return fragment;
	}

	// Method to create parcel with the saved data.
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(IDS_INSTITUTIONS, getArguments()
				.getParcelableArrayList(IDS_INSTITUTIONS));
	}

	// When creating view, start the necessary arguments to list to the user the institutions.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// List that will populate the view to te user.
		ArrayList<Institution> list; 
		if(getArguments().getParcelableArrayList(IDS_INSTITUTIONS) != null){
			list = getArguments().getParcelableArrayList(IDS_INSTITUTIONS);
		}else{
			list = savedInstanceState.getParcelableArrayList(IDS_INSTITUTIONS);
		}

		// ListView of institutions objects.
		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list, container,
				false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		try {
			if(list != null){
				rootView.setAdapter(new ArrayAdapter<Institution>(
						getActionBar().getThemedContext(),
						R.layout.custom_textview, list));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rootView;
	}

	// Method that attach an activity and treat the possible exceptions.
	@Override
	public void onAttach(Activity activity) {
		assert (activity != null) : "activity must never be null";
		super.onAttach(activity);
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}

	// Method that detach an activity and treat the possible exceptions.
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }

	/*
	 * Method that selects the item clicked on the list and directs the user to the appropriate
   	 * view.
   	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		assert (l != null) : "l must never be null";
		assert (v != null) : "v must never be null";
		assert (position >= 0) : "position must never be null";
		assert (id >= 0) : "id must never be negative";

		if(getArguments().getInt(ID_COURSE)==0){
			beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(((Institution)l
					.getItemAtPosition(position)).getId(),getArguments().getInt(YEAR)));
		}else {
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment
					.newInstance(((Institution)l.getItemAtPosition(position)).getId()
							,getArguments().getInt(ID_COURSE),getArguments().getInt(YEAR)));
		}
			super.onListItemClick(l, v, position, id);
	}

	// Get the institution list from the database to populate the listview for the user.
	private static ArrayList<Institution> getInstitutionsList(int idCourse) throws SQLException{

		assert (idCourse >= 0) : "id must never be negative";

		if(idCourse == 0){
			return Institution.getAll();
		}else{
			return Course.get(idCourse).getInstitutions();
		}
	}

	// Get the action bar with the option for the user.
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
}
