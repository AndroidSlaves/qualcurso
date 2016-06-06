package unb.mdsgpp.qualcurso;

import java.text.SimpleDateFormat;
import java.util.List;

import models.Search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListHistoryAdapter extends ArrayAdapter<Search> {

	@SuppressLint("Assert")
	public ListHistoryAdapter(Context context, int resource, List<Search> items) {
		super(context, resource, items);
		assert (context != null) : "Receive the null context of treatment";
		assert (resource > 0) : "Treatment to lower value of resource";
	}

	TextView option = null;
	TextView year = null;
	TextView indicator = null;
	TextView firstValue = null;
	TextView secondValue = null;
	TextView searchDate = null;

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View contextView, ViewGroup parent) {
		View view = contextView;

		if (view == null) {
			LayoutInflater layoutInflater;
			layoutInflater = LayoutInflater.from(getContext());
			view = layoutInflater.inflate(R.layout.history_list_item, null);
		}else {
			/*Nothing to do */
		}

		Search search = getItem(position);

		if (search != null) {
			option = (TextView) view.findViewById(R.id.option);
			year = (TextView) view.findViewById(R.id.year);
			indicator = (TextView) view.findViewById(R.id.indicator);
			firstValue = (TextView) view.findViewById(R.id.firstValue);
			secondValue = (TextView) view.findViewById(R.id.secondValue);
			searchDate = (TextView) view.findViewById(R.id.searchDate);
			setListRow(search);
		}else {
			/*Nothing to do*/
		}

		assert (view != null) : "this view should never be null";
		return view;
	}

	/**
	 * It includes the data of the course and the institution in an item list of courses.
	 *
	 * @param search
	 */
	public void setListRow(Search search) {
		if (search.getOption() == Search.COURSE) {
			setItem(option, R.string.course);
		} else if (search.getOption() == Search.INSTITUTION) {
			setItem(option, R.string.institution);
		}
		setItem(year, Integer.toString(search.getYear()));
		setItem(indicator, search.getIndicator().getName());
		setItem(firstValue, Integer.toString(search.getMinValue()));

		// Full maximum of survey course return.
		int max = search.getMaxValue();
		if (max == -1) {
			setItem(secondValue, R.string.maximum);
		} else {
			setItem(secondValue, Integer.toString(max));
		}
		setItem(searchDate,
				SimpleDateFormat.getDateTimeInstance().format(search.getDate()));
	}

	public void setItem(TextView view, String data) {
		if (view != null) {
			view.setText(data);
		}else {
			/*Nothing to do */
		}
	}

	public void setItem(TextView view, int resId) {
		if (view != null) {
			view.setText(resId);
		}else {
			/*Nothing to do */
		}
	}
}
