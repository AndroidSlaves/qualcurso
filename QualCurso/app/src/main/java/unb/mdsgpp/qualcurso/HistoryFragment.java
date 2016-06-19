/*****************************
 * Class name: HistoryFragment (.java)
 *
 * Purpose: Class that shows to the user the history of views viewed in the program recently.
 *****************************/

package unb.mdsgpp.qualcurso;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import models.Course;
import models.Institution;
import models.Search;

public class HistoryFragment extends Fragment {

	// Entity that connects to the database.
	BeanListCallbacks beanCallbacks;

	// Method that instantiate the historyFragment object, the constructor.
	public HistoryFragment() {
		super();
	}

	// Method that attach an activity and treat the possible exceptions.
	@Override
	public void onAttach(Activity activity) {
		assert (activity !=null) : "activity must never be null";

		super.onAttach(activity);
		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch(ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement BeanListCallbacks.");
		}
	}

	// Method that detach an activity and treat the possible exceptions.
	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {

		// View that works as a container for the data.
		View rootView = inflater.inflate(R.layout.fragment_history, container, false);
		assert (rootView != null) : "rootView can never be null";

		// List of objects to be translated in itens for the user.
		final ListView history = (ListView) rootView.findViewById(R.id.listHistory) ;

		// List of object searches that are filters for the list
		ArrayList<Search> searches = Search.getAll();
		assert (searches != null) : "searches can never be null";

		Collections.reverse(searches);

		// Adapter that creates a list of items in the history (listView).
		ListHistoryAdapter historyAdapter = new ListHistoryAdapter(this.getActivity()
                                                            .getApplicationContext(),
                                                             R.id.listHistory, searches);

		assert (historyAdapter != null) : "Adapter view must never be null.";

		history.setAdapter(historyAdapter);
		
		history.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Search search = (Search) parent.getItemAtPosition(position);

                if (search.getOption() == Search.INSTITUTION) {
                    displayInstitutionList(search);
                } else if (search.getOption() == Search.COURSE) {
                    displayCourseList(search);
                } else {
                    /* Nothing to do! */
                }
            }
        });

		return rootView;
	}

	// Show list of institutions to the user filtered by the search attr.
	private void displayInstitutionList(Search search) {
		assert (search != null) : "search must never be null";

		//  List of institutions to be displayed to the user.
		ArrayList<Institution> institutions = Institution.getInstitutionsByEvaluationFilter(search);
		assert (institutions != null) : "institutions arraylist must never be null.";

		if(institutions.size() == 0) {
            displayToastMessage(getResources().getString(R.string.empty_histoty_search_result));
        } else {
            beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(institutions,search));
        }
	}

	// Show list of institutions to the user filtered by the search attr.
	private void displayCourseList(Search search) {
		assert (search != null) : "search must never be null";

		// List of courses to be displayed to the user.
		ArrayList<Course> courses = Course.getCoursesByEvaluationFilter(search);

		if( courses.size() == 0 )
			displayToastMessage(getResources().getString(R.string.empty_histoty_search_result));
		else
			beanCallbacks.onBeanListItemSelected(SearchListFragment.newInstance(courses, search));
	}

	// Show custom message to the user on screen.
	private void displayToastMessage(String textMessage) {
		assert (textMessage != null) : "textMessage must never be null";

		// Object that interacts to the screen and shows message.
		Toast toast = Toast.makeText(this.getActivity().getApplicationContext(), textMessage,
                Toast.LENGTH_LONG);
		toast.show();
	}
}
