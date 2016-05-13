/************************
 * Class name: Indicator (.java)
 *
 * Purpose: Class que handles the date of the indicators used to institutions rating.
 ************************/

// Package of file.
package helpers;

// Android API imports.
import android.annotation.SuppressLint;

// Java API imports.
import java.util.ArrayList;

// Project imports.
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

	@SuppressLint("Assert")
	public Indicator(String name, String value) {
		assert (name != null) : "Received null name";
		assert (name.length() > 1) : "Treatment to minor of character in a name";
		assert (name != "") : "Treatment empty name";
        assert (value != null) : "Received null value";
        assert (value.length() > 1) : "Treatment to minor of character in a value ";
        assert (value != "") : "Treatment empty value";

		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.getName();
	} 

	public static ArrayList<Indicator> getIndicators() {
		String [] indicatorList = QualCurso.getInstance().getResources().getStringArray(R.array.indicator);
		ArrayList<Indicator> result = new ArrayList<Indicator>();

		// Attribute indicator List
		int indicator = 0;
		int conceptYear = 1;
		int yearStartMaster = 2;
		int yearStartDoctoral = 3;
		int averageAnnualTeachers = 4;
		int thesesDefended = 5;
		int dissertations = 6;
		int artwork = 7;
		int numberBookChapters = 8;
		int numberFullTexts = 9;
		int numberCollections = 10;
		int numberEntries = 11;
		int numberArticles = 12;
		int numberWork = 13;


		result.add(new Indicator(indicatorList[indicator], DEFAULT_INDICATOR));
		result.add(new Indicator(indicatorList[conceptYear], new Evaluation().fieldsList().get(7)));
		result.add(new Indicator(indicatorList[yearStartMaster], new Evaluation().fieldsList().get(5)));
		result.add(new Indicator(indicatorList[yearStartDoctoral], new Evaluation().fieldsList().get(6)));
		result.add(new Indicator(indicatorList[averageAnnualTeachers], new Evaluation().fieldsList().get(8)));
		result.add(new Indicator(indicatorList[thesesDefended], new Evaluation().fieldsList().get(9)));
		result.add(new Indicator(indicatorList[dissertations], new Evaluation().fieldsList().get(10)));
		result.add(new Indicator(indicatorList[artwork], new Evaluation().fieldsList().get(13)));
		result.add(new Indicator(indicatorList[numberBookChapters], new Book().fieldsList().get(2)));
		result.add(new Indicator(indicatorList[numberFullTexts], new Book().fieldsList().get(1)));
		result.add(new Indicator(indicatorList[numberCollections], new Book().fieldsList().get(3)));
		result.add(new Indicator(indicatorList[numberEntries], new Book().fieldsList().get(4)));
		result.add(new Indicator(indicatorList[numberArticles], new Article().fieldsList().get(1)));
		result.add(new Indicator(indicatorList[numberWork], new Article().fieldsList().get(2)));

		return result;
	}

	// Search an indicator value in the indicator list and returns the index.
	public static Indicator getIndicatorByValue(String value) {
		Indicator indicator = null;
		for(Indicator ind : getIndicators()) {
			if(ind.getValue().equals(value)) {
				indicator = ind;
				break;
			} else {
				//Nothing to do
			}
		}

		return indicator;
	}
}
