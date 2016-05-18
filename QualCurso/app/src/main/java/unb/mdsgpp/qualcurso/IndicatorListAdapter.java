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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IndicatorListAdapter extends ArrayAdapter<HashMap<String,String>> {

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

		View v = convertView;

		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(itemLayout, null);
		}

		HashMap<String,String> h = getItem(position);

		if (h != null) {
			TextView indicator = (TextView) v.findViewById(R.id.indicator);
			TextView indicatorText = (TextView) v.findViewById(R.id.indicator_text);

        	if (indicator != null) {
            	indicator.setText(h.get(VALUE));
        	}
        	if (indicatorText != null) {
        		indicatorText.setText(Indicator.getIndicatorByValue(h.get(INDICATOR_VALUE)).getName());
        	}
    	}

    	return v;
	}
}