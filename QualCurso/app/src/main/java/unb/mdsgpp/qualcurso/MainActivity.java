/*****************************
 * Class name: MainActivity (.java)
 *
 * Purpose: First and primary activity that connects with the other screens and the rest of the
 * components.
 ****************************/
package unb.mdsgpp.qualcurso;

import models.Course;
import models.Institution;
import models.Search;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Spanned;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, BeanListCallbacks, OnQueryTextListener {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment navigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence screenTitle = "";
	private int drawerPosition = 10;
	public static String DRAWER_POSITION = "drawerPosition";

	public static String CURRENT_TITLE = "currentTitle";

	//private SearchView mSearchView;

    /**
     * Set up global variables on initiation.
     *
     * @param savedInstanceState
     *              instance saved.
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(savedInstanceState != null){
			drawerPosition = savedInstanceState.getInt(DRAWER_POSITION);
			screenTitle = savedInstanceState.getCharSequence(CURRENT_TITLE);
		}else{
			
			screenTitle = getFormatedTitle(getTitle());
		}
		navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		// Set up the drawer.
		navigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(DRAWER_POSITION, drawerPosition);
        outState.putCharSequence(CURRENT_TITLE, screenTitle);
        super.onSaveInstanceState(outState);
    }

    /**
     * get text with the color of the title in hexadecimal formatted char sequence.
     *
     * @param stringTitle
     *              text that will be formatted.
     * @return
     *              formatted text.
     */
	public CharSequence getFormatedTitle(CharSequence stringTitle){
		int actionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
		return Html.fromHtml("<font color='#"+Integer.toHexString(actionBarTitleColor).substring(2)+"'><b>"+stringTitle+"</b></font>");
	}


    /**
     * Creates a new fragment based on the option clicked.
     *
     * @param position
     *              position of the click
     */
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		//fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.container)).addToBackStack(null).commit();
		ActionBar actionBar = getSupportActionBar();	
		Fragment fragment = null;
		String formatedTitle = "";
		switch (position) {
			case 0:
				formatedTitle = getString(R.string.title_section1);
				fragment = new TabsFragment();
				drawerPosition = 0;
				break;
			case 1:
				formatedTitle = getString(R.string.title_section2);
				fragment = new SearchByIndicatorFragment();
				drawerPosition = 1;
				break;
			case 2:
				formatedTitle = getString(R.string.title_section3);
				fragment = new RankingFragment();
				drawerPosition = 2;
				break;
			case 3:
				formatedTitle = getString(R.string.title_section4);
				fragment = new HistoryFragment();
				drawerPosition = 3;
				break;
			case 4:
				formatedTitle = getString(R.string.title_section5);
				fragment = new CompareChooseFragment();
				drawerPosition = 4;
				break;

			default:
				fragment = null;
				break;
			}		
		if(fragment != null){
			actionBar.setTitle(getFormatedTitle(formatedTitle));
			screenTitle = getFormatedTitle(formatedTitle);
			if(fragment instanceof TabsFragment){
				fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,fragment).commit();
			}else{
				fragmentManager
				.beginTransaction()
				.replace(R.id.container,fragment).addToBackStack(null).commit();
			}
		}
	}

    /*
	public void onSectionAttached(int number) {
		switch (number) {
		//Nothing
		}
	}*/

    /**
     * Brings back the action bar with the options to the user.
     */
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(screenTitle);
	}

    /**
     * Restore action bar when OptionsMenu is opened.
     *
     * @param menu
     *              specified menu that is opened.
     * @return
     *              if operation was successful.
     */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean operation = false;

		if (!navigationDrawerFragment.isDrawerOpen()) {

			/* Only show items in the action bar relevant to this screen if the drawer is not
			showing. Otherwise, let the drawer decide what to show in the action bar.*/
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();

			operation = true;
		} else {
			operation = super.onCreateOptionsMenu(menu);
		}

		return operation;
	}

	/*private void setupSearchView(MenuItem searchItem){
		searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		mSearchView.setOnQueryTextListener(this);
	}*/

    /**
     * Opens application fragment depending on the selected item.
     *
     * @param item
     *              selected menu item.
     * @return
     *              a recursive boolean. The recursive loop is broken with the selected item is recognized.
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/* Handle action bar item clicks here. The action bar will automatically handle clicks on
		the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.*/

		boolean isItemClicked = false;

        switch(item.getItemId()) {
	 		case R.id.action_about:
				aboutApplication();
				isItemClicked = true;
				break;

			case R.id.action_exit:
				closeApplication();
				isItemClicked = true;

		}

		return isItemClicked;
	}

    /**
     * Finishes application entirely.
     */
	private void closeApplication() {
		finish();
		System.exit(1);
	}

    /**
     * Opens about fragment, containing the info, when option selected.
     */
	private void aboutApplication() {
		onBeanListItemSelected(AboutFragment.newInstance());
	}

    /**
     * Adds the selected fragment to a fragment record maneger, FragmentManeger class.
     *
     * @param fragment
     *              open intended fragment.
     */
	@Override
	public void onBeanListItemSelected(Fragment fragment) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction()
						.replace(R.id.container,
								fragment).addToBackStack(null).commit();
	}

    /**
     * Adds the selected fragment to a fragment record maneger, FragmentManeger class.
     *
     * @param fragment
     *              open intended fragment.
     * @param container
     *              container id that this fragment is being attached to.
     */
	@Override
	public void onBeanListItemSelected(Fragment fragment, int container) {
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager
				.beginTransaction()
				.replace(container,
						fragment).commit();
	}

    /**
     * Search by course of institution.
     *
     * @param search
     *              instance of search with the specifics of the entry of the user.
     *
     * @param bean
     *              criteria for type of search.
     */
	@Override
	public void onSearchBeanSelected(Search search, Parcelable bean) {
		if(bean instanceof Institution){
			onBeanListItemSelected(CourseListFragment.newInstance(((Institution)bean).getId(),
					search.getYear(),
					Institution.getCoursesByEvaluationFilter(((Institution)bean).getId(),search)));
		}else if(bean instanceof Course){
			onBeanListItemSelected(InstitutionListFragment.newInstance(((Course)bean).getId(),
					search.getYear(),
					Course.getInstitutionsByEvaluationFilter(((Course)bean).getId(),search)));
		}		
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
