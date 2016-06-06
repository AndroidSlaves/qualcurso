/************************
 * Class name: CompareListAdapter (.java)
 *
 * Purpose: Comparison screen presentation institutions.
 ************************/

// Package of file.
package unb.mdsgpp.qualcurso;

// Java API imports.
import java.util.HashMap;
import java.util.List;

// Android API imports.
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// Project imports.
import helpers.Indicator;

public class CompareListAdapter extends ArrayAdapter<HashMap<String, String>> {

	public static String INDICATOR_VALUE = "indicatorValue";
	public static String FIRST_VALUE = "firstValue";
	public static String SECOND_VALUE = "secondValue";
	public static String IGNORE_INDICATOR = "ignoreIndicator";
	public static String FALSE = "false";

	@SuppressLint("Assert")
	public CompareListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);

		assert (context != null) : "Receive the null context of treatment";
		assert (textViewResourceId > 0) : "Treatment to lower value";
	}

	@SuppressLint("Assert")
	public CompareListAdapter(Context context, int resource,
			List<HashMap<String, String>> items) {
		super(context, resource, items);

		assert (context != null) : "Receive the null context of treatment";
		assert (resource > 0) : "Treatment to lower value of resource";
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if(view == null) {
			LayoutInflater layoutInflater;
			layoutInflater = LayoutInflater.from(getContext());
			view = layoutInflater.inflate(R.layout.compare_show_list_item, null);
		} else {
			/* Nothing to do */
		}

		HashMap<String, String> hashMap = getItem(position);

		if(hashMap != null) {
			TextView indicatorNameTextView = (TextView) view
					.findViewById(R.id.compare_indicator_name);
			TextView firstIndicatorTextView = (TextView) view
					.findViewById(R.id.compare_first_institution_indicator);
			TextView secondIndicatorTextView = (TextView) view
					.findViewById(R.id.compare_second_institution_indicator);

			if(indicatorNameTextView != null) {
				indicatorNameTextView.setText(Indicator.getIndicatorByValue(
						hashMap.get(INDICATOR_VALUE)).getName());
			} else {
				/* Nothing to do */
			}
			if(firstIndicatorTextView != null || secondIndicatorTextView != null) {
				int first = Integer.parseInt(hashMap.get(FIRST_VALUE));
				int second = Integer.parseInt(hashMap.get(SECOND_VALUE));
				firstIndicatorTextView.setText(Integer.toString(first));
				secondIndicatorTextView.setText(Integer.toString(second));
				if (hashMap.get(IGNORE_INDICATOR).equals(FALSE)) {
					if(first > second) {
						firstIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.light_green));
						secondIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.smooth_red));
					} else if(second > first) {
						secondIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.light_green));
						firstIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.smooth_red));
					} else {
						secondIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.white));
						firstIndicatorTextView.setBackgroundColor(QualCurso
								.getInstance().getResources()
								.getColor(R.color.white));
					}
				} else {
					secondIndicatorTextView.setBackgroundColor(QualCurso
							.getInstance().getResources()
							.getColor(R.color.white));
					firstIndicatorTextView.setBackgroundColor(QualCurso
							.getInstance().getResources()
							.getColor(R.color.white));
				}
			}
		} else {
			/* Nothing to do */
		}
		assert (view != null) : "this view should never be null";
		return view;
	}

}
