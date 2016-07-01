/*****************************
 * Class name: AboutFragment (.java)
 *
 * Purpose: Screen that shows information about the QualCurso application.
 ****************************/

package unb.mdsgpp.qualcurso;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junit.framework.Assert;

public class AboutFragment extends Fragment {
	BeanListCallbacks beanCallbacks;

    /**
     * Empty Constructor.
     */
	public AboutFragment() {
        super();
	}

    /**
     * Method that creates and returns a new instance of the AboutFragment Class.
     *
     * @return
     *              a new instance of AboutFragment class.
     */
	public static AboutFragment newInstance() {
		AboutFragment aboutFragment = new AboutFragment();
		return aboutFragment;
	}

    /**
     * Determines what happens on the fragment attach operation. Native method.
     *
     * @param activity
     *              activity which it is being attached to.
     */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        assert (activity != null) : "Receive a null treatment";

		try {
			beanCallbacks = (BeanListCallbacks) activity;
		} catch(ClassCastException exceptionClass) {
			throw new ClassCastException(activity.toString() + " must implement BeanListCallbacks.");
		}
	}

    /**
     * Destroy beansCallBack on detach operation.
     */
	@Override
	public void onDetach() {
		super.onDetach();
		beanCallbacks = null;
        Assert.assertNull(beanCallbacks);
	}

    /**
     * Activity must do method. Determines what is done when fragment created.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     *              rootview
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.about_fragment, container, false);

        Assert.assertNotNull(rootView);

		return rootView;
	}
}
