/*****************************
 * Class name: CourseListFragment (.java)
 *
 * Purpose: Fragment that shows the list of courses and with its functionalities.
 ****************************/

package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Course;
import models.Institution;
import android.database.SQLException;
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

public class CourseListFragment extends ListFragment{

	private static final String ID_INSTITUTION = "idInstitution";
	private static final String IDS_COURSES = "idsCourses";
	private static final String YEAR = "year";
	
	BeanListCallbacks beanCallbacks;

    /**
     * Default constructor that sets the class variables.
     */
	public CourseListFragment() {
		super();
		Bundle args = new Bundle();
		args.putInt(ID_INSTITUTION, 0);
		args.putParcelableArrayList(IDS_COURSES, getCoursesList(0));
		this.setArguments(args);
	}

    /**
     * Creates a new instance of the list of courses based on the id and the year.
     *
     * @param id
     *              identification number of the list of courses.
     *
     * @param year
     *              selected year of the evaluation by the user.
     *
     * @return
     *              return the fragment in which the list will be.
     */
	public static CourseListFragment newInstance(int id, int year){
		CourseListFragment fragment = new CourseListFragment();
		Bundle args = new Bundle();
		args.putInt(ID_INSTITUTION, id);
		args.putInt(YEAR, year);
		args.putParcelableArrayList(IDS_COURSES, getCoursesList(id));
		fragment.setArguments(args);
		return fragment;
	}

    /**
     * Creates a new instance of the list of courses based on the id and the year.
     *
     * @param id
     *              identification number of the list of courses.
     *
     * @param year
     *              selected year of the evaluation by the user.
     *
     * @param list
     *              list of courses that should be listed.
     * @return
     *              return the fragment in which the list will be.
     */
	public static CourseListFragment newInstance(int id, int year, ArrayList<Course> list){
		CourseListFragment fragment = new CourseListFragment();
		Bundle args = new Bundle();
		args.putInt(ID_INSTITUTION, id);
		args.putInt(YEAR, year);
		args.putParcelableArrayList(IDS_COURSES, list);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ArrayList<Course> list;
		if(getArguments().getParcelableArrayList(IDS_COURSES) != null){
			list = getArguments().getParcelableArrayList(IDS_COURSES);
		}else{
			list = savedInstanceState.getParcelableArrayList(IDS_COURSES);
		}
		ListView rootView = (ListView) inflater.inflate(R.layout.fragment_list, container,
				false);
		rootView = (ListView) rootView.findViewById(android.R.id.list);
		try {
			if(list != null){
			rootView.setAdapter(new ArrayAdapter<Course>(
			        getActionBar().getThemedContext(),
			        R.layout.custom_textview,
			        list));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rootView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(IDS_COURSES, getArguments().getParcelableArrayList(IDS_COURSES));	
	}

    /**
     * Creates instance of a InstitutionList or a EvaluationDetailFragment based on what was selected by
     * the user.
     *
     * @param l
     *              listView which the user clicked.
     *
     * @param v
     *              obligatory view parameter for original super class.
     *
     * @param position
     *              position of the item clicked by the user.
     *
     * @param id
     *              obligatory id parameter for original super class.
     */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if(getArguments().getInt(ID_INSTITUTION) == 0){
			
			beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance((((Course)l.getAdapter().getItem(position)).getId()), getArguments().getInt(YEAR)));
		}else{
			beanCallbacks.onBeanListItemSelected(EvaluationDetailFragment.newInstance(getArguments().getInt(ID_INSTITUTION), ((Course)l.getAdapter().getItem(position)).getId(),getArguments().getInt(YEAR)));
		}
		super.onListItemClick(l, v, position, id);
	}
	
	@Override
	public void onAttach(Activity activity) {
		assert (activity != null) : "Receive a null treatment";
		super.onAttach(activity);
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}
	
	@Override
    public void onDetach() {
        super.onDetach();
        beanCallbacks = null;
    }

    /**
     * Get course list from course bean database.
     *
     * @param idInstitution
     *              Institution from where this course is from.
     *
     * @return
     *              the list with all the institutions.
     *
     * @throws SQLException
     *              there maybe a problem accessing the database.
     */
	private static ArrayList<Course> getCoursesList(int idInstitution) throws SQLException{
		if(idInstitution == 0){
			return Course.getAll();
		}else{
			return Institution.get(idInstitution).getCourses();
		}
	}

    /**
     *
     * @return the action bar with the menus for the user.
     */
	private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
	
}
