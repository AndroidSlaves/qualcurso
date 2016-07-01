/************************
 * Class name: Indicator (.java)
 *
 * Purpose: Class que handles the date of the indicators used to institutions rating.
 ************************/

package helpers;

import android.annotation.SuppressLint;

import junit.framework.Assert;

import java.util.ArrayList;

import models.Article;
import models.Book;
import models.Evaluation;

import unb.mdsgpp.qualcurso.QualCurso;
import unb.mdsgpp.qualcurso.R;

public class Indicator {
    private String name = EMPTY;
	private String value = EMPTY;

	public static final String DEFAULT_INDICATOR = "defaultIndicator";
	public static final String EMPTY = "";

	/**
	 * Build indicator with name and value.
	 *
	 * @param name
	 * 				Name of bookmark.
	 * @param value
	 * 				Value of bookmark.
	 */
	@SuppressLint("Assert")
	public Indicator(String name, String value) {

		assert (name != null) : "Received null name";
		assert (name.length() > 1) : "Treatment to minor of character in a name";
		assert (!name.equals("")) : "Treatment empty name";
		assert (value != null) : "Received null value";
		assert (value.length() > 1) : "Treatment to minor of character in a value ";
		assert (!value.equals("")) : "Treatment empty value";

		this.name = name;
		this.value = value;
	}

	/**
	 * @return
	 * 				Name of indicator.
	 */
	public String getName() {
		assert (name != null) : "name must never be null";
		return name;
	}

	/**
	 * @param name
	 * 				Name of bookmark.
	 */
	public void setName(String name) {
		assert(name !=null) : "name must never be null";
		this.name = name;
	}

	/**
	 * @return
	 * 				Value of Indicator.
	 */
	public String getValue() {
        assert (value != null) : "value must never be null";
		return value;
	}

	/**
	 * @param value
	 * 				Value of bookmark.
	 */
	public void setValue(String value) {
        this.value = value;
	}

	/**
	 * Get the bookmark name given overriding the toString () default Java
	 *
	 * @return
	 * 				Name of Indicator.
	 */
	@Override
	public String toString() {
        String name = this.getName();

        assert (name != null) : "name must never be null";
		return name;
	} 

	/**
	 * Get all the indicators assigned to evaluation, book and article and places on a list.
	 *
	 * @return
	 * 				list of indicators.
	 */
	public static ArrayList<Indicator> getIndicators() {
		String [] indicatorList = QualCurso.getInstance().getResources().getStringArray(R.array.indicator);
		ArrayList<Indicator> result = new ArrayList<Indicator>();

		// Attribute INDICATOR List
		final int INDICATOR = 0;
		final int CONCEPT_YEAR = 1;
		final int YEAR_START_MASTERS = 2;
		final int YEAR_START_DOCTORATE = 3;
		final int AVERAGE_ANNUAL_TEACHER = 4;
		final int THESES_DEFENDED = 5;
		final int DISSERTATIONS = 6;
		final int ARTWORK = 7;
		final int NUMBER_OF_BOOK_CHAPTERS = 8;
		final int NUMBER_FULL_TEXT = 9;
		final int NUMBER_COLLECTIONS = 10;
		final int NUMBER_ENTRIES = 11;
		final int NUMBER_ARTICLES = 12;
		final int NUMBER_WORK = 13;

        // Attribute INDICATOR List
        final int ID = 0;
        final int ID_INSTITUTION = 1;
        final int ID_COURSE = 2;
        final int YEAR = 3;
        final int MODALITY = 4;
        final int MASTER_DEGREE_START_YEAR = 5;
        final int DOCTORATE_START_YEAR = 6;
        final int TRIENNIAL_EVALUATION = 7;
        final int PERMANENT_EVALUATION = 8;
        final int THESES = 9;
        final int DISSERTATIONS_EVALUATION = 10;
        final int ID_ARTICLES = 11;
        final int ID_BOOKS = 12;
        final int ARTISTIC_PRODUCTION = 13;

		// Creating indicator objects
		result.add(new Indicator(indicatorList[INDICATOR], DEFAULT_INDICATOR));
		result.add(new Indicator(indicatorList[CONCEPT_YEAR],
                new Evaluation().fieldsList().get(TRIENNIAL_EVALUATION)));
		result.add(new Indicator(indicatorList[YEAR_START_MASTERS],
                new Evaluation().fieldsList().get(MASTER_DEGREE_START_YEAR)));
		result.add(new Indicator(indicatorList[YEAR_START_DOCTORATE],
                new Evaluation().fieldsList().get(DOCTORATE_START_YEAR)));
		result.add(new Indicator(indicatorList[AVERAGE_ANNUAL_TEACHER],
                new Evaluation().fieldsList().get(PERMANENT_EVALUATION)));
		result.add(new Indicator(indicatorList[THESES_DEFENDED],
                new Evaluation().fieldsList().get(THESES)));
		result.add(new Indicator(indicatorList[DISSERTATIONS],
                new Evaluation().fieldsList().get(DISSERTATIONS_EVALUATION)));
		result.add(new Indicator(indicatorList[ARTWORK],
                new Evaluation().fieldsList().get(ARTISTIC_PRODUCTION)));
		result.add(new Indicator(indicatorList[NUMBER_OF_BOOK_CHAPTERS],
                new Book().fieldsList().get(ID_COURSE)));
		result.add(new Indicator(indicatorList[NUMBER_FULL_TEXT],
                new Book().fieldsList().get(ID_INSTITUTION)));
		result.add(new Indicator(indicatorList[NUMBER_COLLECTIONS],
                new Book().fieldsList().get(YEAR)));
		result.add(new Indicator(indicatorList[NUMBER_ENTRIES],
                new Book().fieldsList().get(MODALITY)));
		result.add(new Indicator(indicatorList[NUMBER_ARTICLES],
                new Article().fieldsList().get(ID_INSTITUTION)));
		result.add(new Indicator(indicatorList[NUMBER_WORK],
                new Article().fieldsList().get(ID_COURSE)));

        assert (result != null) : "this list must never be null";

        final int FIRST = 0;
        final int LAST = NUMBER_WORK;

        assert (result.get(FIRST) != null) : "should have first element";
        assert (result.get(LAST) != null) : "should have last element";

		return result;
	}

	/**
	 * Search an indicator value in the indicator list and returns the index.
	 *
	 * @param value
	 * 				Bookmark value to be searched on list.
	 * @return
	 * 				An indicator with @param value.
	 */
	public static Indicator getIndicatorByValue(String value) {
		Indicator indicator = null;

		for(Indicator ind : getIndicators()) {
			assert (ind.getValue() != null) : "this value is used";

			if(ind.getValue().equals(value)) {
				indicator = ind;
			} else {
				/* Nothing to do. */
			}
		}

        assert (indicator != null) : "Indicator must not be null";

		return indicator;
	}
}
