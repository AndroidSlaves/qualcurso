/*****************************
 * Class name: TabsFragment (.java)
 *
 * Purpose: Fragment screen with one course tab and one institution tab.
 *****************************/

package unb.mdsgpp.qualcurso;

import java.util.ArrayList;

import models.Bean;
import models.Course;
import models.Institution;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class TabsFragment extends Fragment implements OnTabChangeListener, OnQueryTextListener {

	// Generic entity that will connect with the database.
	BeanListCallbacks beanCallbacks;

	// Represent the name of the tab object.
	private static final String TAG = "FragmentTabs";

	// Represent the name "institutions" shown in the tab to the user.
	public static final String TAB_INSTITUTIONS = "tabInstitutions";

	/*
	 * Represent the name "courses" shown in the tab to the user. View mRoot: The container view
	 * that holds the tabs.
	 */
	public static final String TAB_COURSES = "tabCourses";

	// The container view that holds the tabs.
	private View mRoot;
	private TabHost mTabHost;

	// Represents the tab that the user is seeing at that momment.
	private int mCurrentTab;

	// View on the screen to search for institution or courses.
	private SearchView mSearchView;

	// List of all courses to be listed when clicking the tabs.
	private ArrayList<Course> allCourses = Course.getAll();

	// List of all courses to be listed when clicking the tabs.
	private ArrayList<Institution> allInstitutions = Institution.getAll();

	// Catches the exceptions possible from the BenListCallBacks.
	@Override
	public void onAttach(Activity activity) {
		assert (activity != null) : "activity must never be null";

		super.onAttach(activity);
		try {
            beanCallbacks = (BeanListCallbacks) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()+" must implement BeanListCallbacks.");
        }
	}

	// Inflates the fragments with the tabs.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.tabs_fragment, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		setupTabs();

		return mRoot;
	}

	// Saves instance of the activity in order to not lose information.
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		assert (savedInstanceState != null) : "savedInstanceState must never be null";

		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);

		// Manually start loading stuff in the first tab
		updateTab(TAB_INSTITUTIONS, R.id.tab_1);
	}

	// Create the initial values for the tabs with its views.
	private void setupTabs() {
		mTabHost.setup(); // you must call this before adding your tabs!
		mTabHost.addTab(mTabHost.newTabSpec(TAB_INSTITUTIONS).setIndicator(getString(R.string
				.institutions)).setContent(R.id.tab_1));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_COURSES).setIndicator(getString(R.string.courses))
				.setContent(R.id.tab_2));

		// Area where the itens will be inserted.
		TabWidget widget = mTabHost.getTabWidget();
		for(int i = 0; i < widget.getChildCount(); i++) {

			// Generic view that will inflate the widget.
			View view = widget.getChildAt(i);

			// Text returned from the view in order to check if there is something inside.
			TextView textView = (TextView) view.findViewById(android.R.id.title);
			if(textView == null) {
				continue;
			}
			view.setBackgroundResource(R.drawable.tab_indicator_ab_light_green_acb);
		}
	}

	// Update the view when chaging tabs.
	@Override
	public void onTabChanged(String tabId) {
		assert (tabId != null) : "tabId must never be null";
		assert (tabId.equals("")) : "tabId must never be empty";

		if (TAB_INSTITUTIONS.equals(tabId)) {
			updateTab(tabId, R.id.tab_1);
			mCurrentTab = 0;
			return;
		}
		if (TAB_COURSES.equals(tabId)) {
			updateTab(tabId, R.id.tab_2);
			mCurrentTab = 1;
			return;
		}
	}

	// Setup basic information when creating optionsMenu.
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		assert (menu != null) : "menu must never be null";
		assert (inflater != null) : "inflater must never be null";

		inflater.inflate(R.menu.search_menu, menu);

		// Represents search choice selected on the option menu.
		MenuItem searchItem = menu.findItem(R.id.action_search);

		// Represents the view where the user can search in the options menu.
		mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		setupSearchView(searchItem);
		super.onCreateOptionsMenu(menu, inflater);
	}

	/*
	 *Set basic values of view for the search views, where the user can choose an option for
	 * filter.
	 */
	private void setupSearchView(MenuItem searchItem){
		assert (searchItem != null) : "searchItem must never be null";
		searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM |
				 						MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		mSearchView.setOnQueryTextListener(this);
	}

	/*
	 * Update the tabs with the specified values (TabId =  the identification of the selected tab.
	 * placeholder: Where the user is.)
	 */
	private void updateTab(String tabId, int placeholder) {

		assert(tabId != null) : "tabId must never be null";
		assert(placeholder < 0) : "placeholder must never be null";

		FragmentManager fm = getFragmentManager();
		if(fm.findFragmentByTag(tabId) == null) {
			if(tabId.equalsIgnoreCase(TAB_INSTITUTIONS)) {
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(0,2010), placeholder);
			} else if(tabId.equalsIgnoreCase(TAB_COURSES)) {
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(0,2010),placeholder);
			} else {
                /* Nothing to do" */
            }
		}
	}


    /**
     * Typing text in the search view, it searches for
     */
	@Override
	public boolean onQueryTextChange(String arg0) {
		assert (arg0 != null);

		if(arg0.length() >= 1) {
			if(mCurrentTab == 0){
				ArrayList<Bean> beans = getFilteredList(arg0, allInstitutions);
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(0, 2010,
						 	 					     castToInstitutions(beans)), R.id.tab_1);
			} else if(mCurrentTab == 1){
				ArrayList<Bean> beans = getFilteredList(arg0, allCourses);
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(0, 2010,
													 castToCourses(beans)), R.id.tab_2);
			}
		} else {
			if(mCurrentTab == 0) {
				beanCallbacks.onBeanListItemSelected(InstitutionListFragment.newInstance(0, 2010),
						                             R.id.tab_1);
			} else if(mCurrentTab == 1) {
				beanCallbacks.onBeanListItemSelected(CourseListFragment.newInstance(0, 2010),
						                             R.id.tab_2);
			} else {
                /* Nothing to do! */
            }
		}

		return false;
	}

	/**
	 * Converts a list of bens (generic objects)to a list of  institutions
	 *
	 * @param beans
	 * 				List of beans
	 *
	 * @return
	 * 				the new list of institutions with it's data.
	 */
	public ArrayList<Institution> castToInstitutions(ArrayList<Bean> beans) {
		assert (beans != null);

		ArrayList<Institution> institutions = new ArrayList<Institution>();

		for(Bean bean : beans) {
			institutions.add((Institution)bean);
		}

		return institutions;
	}

	/**
	 * Converts a list of bens (generic objects)to a list of  courses
	 *
	 * @param beans
	 * 				List of beans
	 *
	 * @return
	 * 				the new list of courses with it's data.
	 */
	public ArrayList<Course> castToCourses(ArrayList<Bean> beans) {
		assert (beans != null);

		// The list of institutions to be populated with beans data converted.
		ArrayList<Course> courses = new ArrayList<Course>();

		for(Bean bean : beans) {
			courses.add((Course)bean);
		}

		return courses;
	}


	/**
	 * Converts a list of beans ( generic objects)  to a list of courses with its data.
	 *
	 * @param filter
	 * 				Object filter
	 * @param list
	 * 				bean list
	 *
	 * @return
	 * 				the new list of courses with it's data.
	 */
	private ArrayList<Bean> getFilteredList(String filter, ArrayList<? extends Bean> list){
		assert (filter != null);
		assert (filter.equals(""));
		assert (list != null);

		//  List of beans to be stored and returned.
		ArrayList<Bean> beans = new ArrayList<Bean>();
		for(Bean bean : list) {
			if(bean.toString().toLowerCase().startsWith(filter.toLowerCase())) {
				beans.add(bean);
			}
		}
		return beans;
	}


	@Override
	public boolean onQueryTextSubmit(String arg0) {
		assert (arg0 != null);
		return false;
	}

}
