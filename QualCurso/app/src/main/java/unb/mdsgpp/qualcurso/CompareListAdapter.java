/************************
 * Class name: CompareListAdapter (.java)
 *
 * Purpose: Comparison screen presentation institutions.
 ************************/

package unb.mdsgpp.qualcurso;

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

import junit.framework.Assert;

import helpers.Indicator;

public class CompareListAdapter extends ArrayAdapter<HashMap<String, String>> {

	public static String INDICATOR_VALUE = "indicatorValue";
	public static String FIRST_VALUE = "firstValue";
	public static String SECOND_VALUE = "secondValue";
	public static String IGNORE_INDICATOR = "ignoreIndicator";
	public static String FALSE = "false";

    // Used to Log system.
    final String TAG = CompareListAdapter.class.getSimpleName();

	@SuppressLint("Assert")
	public CompareListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);

		assert (context != null) : "Receive the null context of treatment";
		assert (textViewResourceId > 0) : "Treatment to lower value";
	}

	@SuppressLint("Assert")
	public CompareListAdapter(Context context, int resource, List<HashMap<String, String>> items) {
		super(context, resource, items);

		assert (context != null) : "Receive the null context of treatment";
		assert (resource > 0) : "Treatment to lower value of resource";
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if(view == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(getContext());
			view = layoutInflater.inflate(R.layout.compare_show_list_item, null);
            Log.i(TAG, "View inflate with LayoutInflater when was null");
		} else {
            Assert.assertNotNull(view);
            Log.i(TAG, "View is not null");
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
			    /* Nothing to do! */
            }

			if(firstIndicatorTextView != null || secondIndicatorTextView != null) {
				int first = Integer.parseInt(hashMap.get(FIRST_VALUE));
				int second = Integer.parseInt(hashMap.get(SECOND_VALUE));
				firstIndicatorTextView.setText(Integer.toString(first));
				secondIndicatorTextView.setText(Integer.toString(second));

				if(hashMap.get(IGNORE_INDICATOR).equals(FALSE)) {

                    int comparisonIndicators = compareIndicators(first, second);
                    Assert.assertNotNull(comparisonIndicators);
                    assert (comparisonIndicators > 0) : "ComparisonIndcator - Receive the null of treatment";

                    switch(comparisonIndicators){
                        case 1: firstBiggerSecond(firstIndicatorTextView,secondIndicatorTextView);
                            break;
                        case 2: secondBiggerFirst(firstIndicatorTextView, secondIndicatorTextView);
                            break;
                        case 3: secondEqualsFirst(firstIndicatorTextView, secondIndicatorTextView);
                            break;
                        default: Log.w(TAG, "There was no comparison between the indicators.");
                            break;
                    }
				} else {
					secondIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
							.getColor(R.color.white));
					firstIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
							.getColor(R.color.white));
				}
			}

		} else {
            Log.i(TAG, "HashMap is null");
        }

		return view;
	}

    public static int compareIndicators (int first, int second) {
        int comparisonIndicators = 0;

        if(first > second){
            comparisonIndicators = 1;
        } else if (second > first) {
            comparisonIndicators = 2;
        } else {
            comparisonIndicators = 3;
        }

        return comparisonIndicators;
    }

    private static void firstBiggerSecond(TextView firstIndicatorTextView,
                                          TextView secondIndicatorTextView) {

        firstIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
                .getColor(R.color.light_green));
        secondIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
                .getColor(R.color.smooth_red));
    }

    private static void secondBiggerFirst(TextView firstIndicatorTextView,
                                          TextView secondIndicatorTextView) {

        secondIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
                .getColor(R.color.light_green));
        firstIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
                .getColor(R.color.smooth_red));
    }

    private static void secondEqualsFirst(TextView firstIndicatorTextView,
                                          TextView secondIndicatorTextView) {

        secondIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
                .getColor(R.color.white));
        firstIndicatorTextView.setBackgroundColor(QualCurso.getInstance().getResources()
                .getColor(R.color.white));
    }

}
