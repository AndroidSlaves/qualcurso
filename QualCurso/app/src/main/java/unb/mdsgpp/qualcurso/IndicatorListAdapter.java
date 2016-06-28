/************************
 * Class name: IndicatorListAdapter (.java)
 *
 * Purpose: Indicator allows an object to use services of other View objects.
 ************************/

package unb.mdsgpp.qualcurso;

import helpers.Indicator;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IndicatorListAdapter extends ArrayAdapter<HashMap<String,String>> {

    // Set the class name for use in log.
    private static String CLASS_NAME_FOR_LOG = "IndicatorListAdapter - ";

	public static String INDICATOR_VALUE = "indicatorValue";
	public static String VALUE = "value";
	private int itemLayout = 0;

	/**
	 * Method that builds objects with attributes deriving from the parent class.
	 * @param context
	 *				View context where adapter is instantiated.
	 * @param resource
	 * 				Numerical value to list index.
	 * @param items
	 * 				List of items.
	 */
	@SuppressLint("Assert")
	public IndicatorListAdapter(Context context, int resource, List<HashMap<String,String>> items) {
		super(context, resource, items);

        assert (context != null) : "Receive the null context of treatment";
        assert (resource > 0) : "Treatment to lower value of resource";

        this.itemLayout = resource;
	}

	/**
	 * Responsible for inflating the screen to the user identifying the items ids by returning the
	 * screen.
	 * @param position
	 * 				Position of arraylist.
	 * @param convertView
	 * 				The new layout of screen.
	 * @param parent
	 * 				The modeling view.
	 * @return
	 * 				The new defined view.
	 */
	@SuppressLint("Assert")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        assert (position >= 0) : "Treatment for lower position of an item in the list";
        assert (parent != null) : "Receive the null treatment";

		View view = convertView;

        view = nullView(convertView);

        // Getting indicators in their positions.
		HashMap<String,String> hashMap = getItem(position);

		if(hashMap != null) {
			TextView indicator = (TextView) view.findViewById(R.id.indicator);
			TextView indicatorText = (TextView) view.findViewById(R.id.indicator_text);

        	if(indicator != null) {
            	indicator.setText(hashMap.get(VALUE));
        	} else {
        	    /*Nothing to do*/
            }

        	if(indicatorText != null) {
        		indicatorText.setText(Indicator.getIndicatorByValue(hashMap.get(INDICATOR_VALUE)).getName());
        	} else {
        	    /*Nothing to do*/
            }

    	} else {
    	    /*Nothing to do*/
        }
    	return view;
	}

    /**
     * This method is responsible to check if the view is null or not.
     *
     * @param view
     * @return view
     */
    private View nullView(View view) {
        final boolean viewNotInitialized = (view == null);

        if(viewNotInitialized) {

            // Inflating the view.
            LayoutInflater inflateView;
            inflateView = LayoutInflater.from(getContext());
            view = inflateView.inflate(itemLayout, null);

            Log.i(CLASS_NAME_FOR_LOG + "nullView", "View sucesfully initialized!");
        } else {
            Log.w(CLASS_NAME_FOR_LOG + "nullView", "View not initialized!");
        }

        return view;
    }
}