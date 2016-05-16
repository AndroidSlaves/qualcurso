/****************************
 * Interface name: BeanListCallbacks (.java)
 *
 * Purpose: Declares prototypes of some methods. They will be used to show beans on screen.
 *****************************/

package unb.mdsgpp.qualcurso;

import models.Search;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

public interface BeanListCallbacks {
    /**
     * Must show beans on fragment.
     * @param fragment
     */
	void onBeanListItemSelected(Fragment fragment);

    /**
     * Must show beans on fragment determined by container.
     * @param fragment
     * @param container
     */
	void onBeanListItemSelected(Fragment fragment, int container);

    /**
     * When bean clicked, must show search on screen.
     * @param search
     * @param bean
     */
	void onSearchBeanSelected(Search search, Parcelable bean);
}
