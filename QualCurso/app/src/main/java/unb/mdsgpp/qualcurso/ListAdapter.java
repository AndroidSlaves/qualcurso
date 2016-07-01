/************************
 * Class name: ListAdapter (.java)
 *
 * Purpose: Arranges items formulating a list for the user.
 ************************/

package unb.mdsgpp.qualcurso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.HashMap;
import java.util.List;

public class ListAdapter extends ArrayAdapter<HashMap<String, String>> {

    // Used to Log system.
    final String TAG = ListAdapter.class.getSimpleName();

	/**
	 * Building an object if it receives the ID of the parent class TextView.
	 *
	 * @param context
	 * 				Where this screen will be instantiated.
	 * @param textViewResourceId
	 * 				Unique identifier of textView component.
	 */
	@SuppressLint("Assert")
	public ListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		assert (context != null) : "Receive the null context of treatment";
		assert (textViewResourceId > 0) : "Treatment to lower value";
	}

	/**
	 * Building an object if it receives the resource and the items list of the parent class.
	 *
	 * @param context
	 * 				Where this screen will be instantiated.
	 * @param resource
	 * 				Unique identifier of list.
	 * @param items
	 * 				Arraylist of items.
	 */
	@SuppressLint("Assert")
	public ListAdapter(Context context, int resource, List<HashMap<String, String>> items) {
		super(context, resource, items);

		assert (context != null) : "Receive the null context of treatment";
		assert (resource > 0) : "Treatment to lower value of resource";
	}

	/**
	 *
	 * @param position
	 * 				Arraylist index.
	 * @param convertView
	 * 				The new view that will be inflated.
	 * @param parent
	 * 				View pattern.
	 * @return
	 * 				Screen populated with arraylist info.
	 */
	@SuppressLint({"Assert", "InflateParams"})
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		assert (position >= 0) : "Treatment for lower position of an item in the list";
		assert (parent != null) : "Receive the null treatment";

		View view = convertView;

		if(view == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(getContext());
			assert (layoutInflater != null) : "layout should not be null";
			view = layoutInflater.inflate(R.layout.list_item, null);
            Log.d(TAG, "ConvertView inflated by components.");

		} else {
		    /* Nothing to do! */
        }

		HashMap<String, String> hashMap = getItem(position);

		if(hashMap != null) {

			TextView rank = (TextView) view.findViewById(R.id.position);
			TextView institutionName = (TextView) view.findViewById(R.id.university);
			TextView value = (TextView) view.findViewById(R.id.data);

			ImageView trophy = (ImageView) view.findViewById(R.id.trophyIcon);

			final String institution = "acronym";
			final String valueInstitution = "order_field";

        	if(rank != null) {
            	rank.setText(Integer.toString(position + 1));
        	} else {
        	    /* Nothing to do! */
            }

        	if(trophy != null) {
        		trophy.setImageDrawable(getTrophyImage(position + 1));
        	} else {
        	    /* Nothing to do! */
            }

        	if(institutionName != null) {
        		institutionName.setText(hashMap.get(institution));
        	} else {
        	    /* Nothing to do! */
            }

        	if(value != null) {
            	value.setText(hashMap.get(hashMap.get(valueInstitution)));
        	} else {
        	    /* Nothing to do! */
            }

    	} else {
    	    /* Nothing to do! */
        }
        Log.d(TAG, "HashMap built.");

		assert (view != null) : "view should not be null";
    	return view;
	}

	/**
	 * Show trophy image according array index.
	 *
	 * @param position
	 * 				ArrayList index.
	 * @return
	 * 				Image of golden, silver or bronze trophy.
	 */
	@SuppressLint("Assert")
	public Drawable getTrophyImage(int position) {
		assert (position >= 1) : "Treatment for lower position of an item in the list";

		Drawable trophy = null;

		switch(position) {
			case 1:
				trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.gold_trophy);
				assert (trophy != null) : "trophy should be found";
				break;

			case 2:
				trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.silver_trophy);
				assert (trophy != null) : "trophy should be found";
				break;

			case 3:
				trophy = QualCurso.getInstance().getResources().getDrawable(R.drawable.bronze_trophy);
				assert (trophy != null) : "trophy should be found";
				break;

			default:
				Assert.assertNull(trophy);
				break;
		}

		return trophy;
	}
}