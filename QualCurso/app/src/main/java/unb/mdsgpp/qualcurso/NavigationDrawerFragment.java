/*****************************
 * Class name: NavigationDrawerFragment (.java)
 *
 * Purpose: Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 ****************************/

package unb.mdsgpp.qualcurso;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import junit.framework.Assert;

public class NavigationDrawerFragment extends Fragment {

    // Remember the position of the selected item.
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    /* Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this. */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    // A pointer to the current callbacks instance (the Activity).
    private NavigationDrawerCallbacks mCallbacks = null;
    // Helper component that ties the action bar to the navigation drawer.
    private ActionBarDrawerToggle mDrawerToggle = null;
    // Object that helps the layout.
    private DrawerLayout mDrawerLayout = null;
    // Object that helps the layout of the listview.
    private ListView mDrawerListView = null;
    // Object that comports the fragment views.
    private View mFragmentContainerView = null;
    // Selected item on the list.
    private int mCurrentSelectedPosition = 0;
    // Saved state from last used.
    private boolean mFromSavedInstanceState = false;
    // Learner of the Drawer.
    private boolean mUserLearnedDrawer = false;

    private static final String TAG = "QuickNotesNavigationDrawer";

    /**
     * Empty constructor
     */
    public NavigationDrawerFragment() {

    }

    /**
     * Method that executes on the creation of this view.
     *
     * @param savedInstanceState
     *              Obligatory parameter of original method.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        assert (savedInstanceState != null) : "Receive a null treatment";
        super.onCreate(savedInstanceState);

        /* Read in the flag indicating whether or not the user has demonstrated awareness of the
         drawer. See PREF_USER_LEARNED_DRAWER for details. */
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
        boolean changed = true;

        if (savedInstanceState != null) {
        	if(mCurrentSelectedPosition != savedInstanceState.getInt(STATE_SELECTED_POSITION)) {
        		mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        		changed = true;

        	} else {
        		changed = false;
        	}
            mFromSavedInstanceState = true;

        } else {
            Assert.assertNull(savedInstanceState);
        }

        // Select either the default item (0) or the last selected item.
        if(changed == true){
        	selectItem(mCurrentSelectedPosition);
        } else {
            /* Nothing to do! */
        }
    }

    /**
     * Set menu option when activity is created.
     *
     * @param savedInstanceState
     *              Obligatory parameter.
     */
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        assert (savedInstanceState != null) : "Receive a null tratment";
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    /**
     * Method that is called when view is created.
     *
     * @param inflater
     *              Obligatory parameter.
     * @param container
     *              Obligatory parameter.
     * @param savedInstanceState
     *              Obligatory parameter.
     * @return mDrawerListView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        mDrawerListView.setAdapter(new ArrayAdapter<String>(
                getActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_section1),
                        getString(R.string.title_section2),
                        getString(R.string.title_section3),
                        getString(R.string.title_section4),
                        getString(R.string.title_section5),
                }));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        return mDrawerListView;
    }

    /**
     * Determines if the drawer is ready.
     *
     * @return
     *              a boolean that represents if the drawer is ready.
     */
    public boolean isDrawerOpen() {
        boolean isOpen = false;

        if(mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView)){
            isOpen = true;
        } else {
            isOpen = false;
        }
        return isOpen;
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId
     *              The android:id of this fragment in its activity's layout.
     * @param drawerLayout
     *              The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if(isAdded()) {
                    getActivity().supportInvalidateOptionsMenu();
                } else {
                    /* Nothing to Do. */
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();

                } else {
                    /* Nothing to do! */
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        if(mDrawerListView != null) {
        	if(position != mCurrentSelectedPosition) {
        		mDrawerListView.setItemChecked(position, true);
        	} else {
                    /* Nothing to do! */
            }
        }  else {
                    /* Nothing to do! */
        }

        if(mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        } else {
                    /* Nothing to do! */
        }

        if(mCallbacks != null) {
        	if(position != mCurrentSelectedPosition) {
            	mCallbacks.onNavigationDrawerItemSelected(position);
    		} else if(mCurrentSelectedPosition == 0) {
    			mCallbacks.onNavigationDrawerItemSelected(position);
    		}  else {
                    /* Nothing to do! */
            }

        }  else {
                    /* Nothing to do! */
        }

        mCurrentSelectedPosition = position;
    }

    @Override
    public void onAttach(Activity activity) {
        assert (activity != null) : "Receive a null treatment";
        super.onAttach(activity);

        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException exceptionClass) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        assert (outState != null) : "Receive a null treatment";
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        assert (item != null) : "Receive a null treatment";

        boolean isItemSelected = false;
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            isItemSelected = true;
        } else {
            isItemSelected = super.onOptionsItemSelected(item);
        }

        return isItemSelected;
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);        
        actionBar.setTitle(getFormatedTitle(getString(R.string.app_name)));
    }
    
    public CharSequence getFormatedTitle(CharSequence charSequence){
        /**
         * Returns the string representation of the unsigned integer value represented by the argument
         * in hexadecimal (base 16).
         */
        final int START = 2;

		int actionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
        final CharSequence FORMATED_TITLE = Html.fromHtml("<font color='#" +
                Integer.toHexString(actionBarTitleColor).substring(START) + "'><b>" + charSequence + "</b></font>");

		return FORMATED_TITLE;
	}

    private ActionBar getActionBar() {
        ActionBar supportActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

        return supportActionBar;
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
