package qualcurso;

import unb.mdsgpp.qualcurso.AboutFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestAboutFragment extends AndroidTestCase {
    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }

    //Must Fail(red)
    /*
    public void testShouldGetNewInstanceOfCompareChooseFragmentFail() {
        Fragment fragment = new AboutFragment();
        //it will fail because it couldn't be null
        AboutFragment aboutFragment = (AboutFragment) fragment;
        assertNull(aboutFragment);
    }
    */

    //Must pass (green)
    public void testShouldGetNewInstanceOfCompareChooseFragment() {
        Fragment fragment = new AboutFragment();
        //must be not null
        AboutFragment aboutFragment = (AboutFragment) fragment;
        assertNotNull(aboutFragment);
    }
}

