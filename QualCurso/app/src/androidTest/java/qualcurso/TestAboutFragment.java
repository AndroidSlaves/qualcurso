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
    public void testShouldGetNewInstanceOfCompareChooseFragment() {
        Fragment fragment = new AboutFragment();
        AboutFragment aboutFragment = (AboutFragment) fragment;
        assertNull(aboutFragment);
    }
}

