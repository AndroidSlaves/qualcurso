package qualcurso;

import unb.mdsgpp.qualcurso.CourseListFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestCourseListFragment extends AndroidTestCase{

        @Override
        public void testAndroidTestCaseSetupProperly() {
            super.testAndroidTestCaseSetupProperly();
        }
        //Must fail
        /*
        public void testShouldGetNewInstanceOfCourseListFragmentFail(){
        Fragment fragment = CourseListFragment.newInstance(1,2007);
        CourseListFragment courseListFragment = (CourseListFragment) fragment;
        assertEquals(2, courseListFragment.getArguments().getInt("idInstitution"));
        }*/

        public void testShouldGetNewInstanceOfCourseListFragment() {
            Fragment fragment = CourseListFragment.newInstance(1, 2007);
            CourseListFragment courseListFragment = (CourseListFragment) fragment;
            assertEquals(1, courseListFragment.getArguments().getInt("idInstitution"));
        }
}
