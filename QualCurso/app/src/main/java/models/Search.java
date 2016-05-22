/*****************************
 * Class name: Search (.java)
 *
 * Purpose: Search is a class that realize searches by some Indicators.
 *****************************/

package models;

import android.database.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import helpers.Indicator;

public class Search extends Bean{
	public static final int COURSE = 0;
	public static final int INSTITUTION = 1;
    public static final int MAX_SEARCHES_SAVE = 10;
	public static final int SEARCH_MAXIMUM_VALUE = -1;

	// Unique identification number for search.
	private int id = 0;
	// Date of the search.
	private Date date = null;
	// Year of the evaluation searched.
	private int year = 0;
	// Types of search.
	private int option = 0;
	// Indicator related to search.
	private Indicator indicator = null;
	// Min value for search list.
	private int minValue = 0;
	// Max value for search list.
	private int maxValue = 0;

    // Each value of enum represents a table field from Course
    private enum SearchFields {
        _id, date, year, option, indicator, min_value, max_value
    }
	/**
	 * Empty constructor for the Search. Set basic default information about the search.
	 */
	public Search() {
		this.id = 0;
		this.identifier = "search";
		this.relationship = "";
	}
	/**
	 * Construct the Search with defined id.
	 *
	 * @param id
	 *              Identification number of the search that will be set at initialization.
	 */
	public Search(int id) {
		assert (id >= 0) : "id must be positive integer.";

		this.id = id;
		this.identifier = "search";
		this.relationship = "";
	}

    /**
     * Get the date stored. Will be used on search.
     *
     * @return date
     */
	public Date getDate() {
		final Calendar dates = Calendar.getInstance();
		final Date today = dates.getTime();
		assert (today != null) : "Receive a null treatment";

		return date;
	}

    /**
     * Store the date. Will be used on search.
     *
     * @param date
     */
	public void setDate(Date date) {

		final Calendar dates = Calendar.getInstance();
		final Date today = dates.getTime();
		assert (today != null) : "Receive a null treatment";

		this.date = date;
	}

    /**
     * Get the year stored. Will be used on search.
     *
     * @return year
     *              Number integer of the year when the indicator was analyzed.
     */
	public int getYear() {
		final Calendar data = Calendar.getInstance();
		final int yearData = data.get(Calendar.YEAR);
		assert (year > 0) : "Receive a negative treatment";
		assert (year <= yearData) : "Receive a max year treatment";

		return year;
	}

    /**
     * Store the year. Will be used on search.
     *
     * @param year
     *              Number integer of the year when the indicator was analyz
     */
	public void setYear(int year) {

		final Calendar data = Calendar.getInstance();
		final int yearData = data.get(Calendar.YEAR);
		assert (year > 0) : "Receive a negative treatment";
		assert (year <= yearData) : "Receive a max year treatment";

		this.year = year;
	}

    /**
     * Get the search option user selects.
     *
     * @return option.
     */
	public int getOption() {
		return option;
	}

    /**
     * Class attribute Date setter.
     *
     * @param option
     */
	public void setOption(int option) {
		assert (option >= 0) : "Option is defined only for 0  values.";
		assert (option <= 1) : "Option is defined only for 1 values.";

		this.option = option;
	}

    /**
     * Get the indicators.
     *
     * @return indicator
     */
	public Indicator getIndicator() {
		assert (indicator != null) : "Receive a null treatment";

		return indicator;
	}

    /**
     * Set the incicators.
     *
     * @param indicator
     */
	public void setIndicator(Indicator indicator) {
		assert (indicator != null) : "indicator can't be null";

		this.indicator = indicator;
	}

    /**
     * Get the minimum value of items listed.
     *
     * @return minValue
     */
	public int getMinValue() {
		assert (minValue > 0) : "minimum value must be positive.";

		return minValue;
	}

    /**
     * Set the minimum value of items listed.
     *
     * @param minValue
     */
	public void setMinValue(final int minValue) {
		assert (minValue > 0) : "minimum value must be positive.";

		this.minValue = minValue;
	}

    /**
     * Get the maximum value of items listed.
	 *
     * @return maxValue
     */
	public int getMaxValue() {
		assert (maxValue > 0) : "maximum value must be positive.";

		return maxValue;
	}

    /**
     * Set the maximum value of items listed.
     *
     * @param maxValue
     */
	public void setMaxValue(final int maxValue) {
		assert (maxValue > 0) : "maximum value must be positive.";

		this.maxValue = maxValue;
	}

    /**
     * Verify if the data was saved.
     *
     * @return
     *              the confirmation storage in the database.
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public boolean save() throws SQLException {
		boolean resultOfSaving = false;
		final GenericBeanDAO gDB = new GenericBeanDAO();

        /**
         * If the total os searchers stored in the database is higher than the save limit
         * delete the actual first search in the database as it is the old one saved
         */
		if(Search.count() >= MAX_SEARCHES_SAVE) {
			Search.first().delete();
		} else {
            //Nothing to do.
        }

		resultOfSaving = gDB.insertBean(this);
		this.setId(Search.last().getId());

		return resultOfSaving;
	}

    /**
     * Search by id and return a generic bean.
     *
     * @param ID
     *			Id to be searched for.
     * @return searchById
     * 			search with specified ID.
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public static Search get(final int ID) throws SQLException {
		assert (ID > 0) : "id must be positive.";

		Search searchById = new Search(ID);
		GenericBeanDAO gDB = new GenericBeanDAO();
		searchById = (Search) gDB.selectBean(searchById);

		return searchById;
	}

    /**
     * Return an list of searches made by user.
     *
     * @return
	 * 				all the searches.
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public static ArrayList<Search> getAll() throws  SQLException {
		final Search beanType = new Search();
		ArrayList<Search> allSearches = new ArrayList<Search>();
		final GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean searchBean : gDB.selectAllBeans(beanType,null)) {
			allSearches.add((Search) searchBean);
		}

		return allSearches;
	}

    /**
     * Count the number of beans generated.
     *
     * @return
	 * 				the number of Searches.
     *
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public static int count() throws  SQLException {
		final Search type = new Search();
		final GenericBeanDAO gDB = new GenericBeanDAO();
		int numberOfSearches = gDB.countBean(type);

		assert (numberOfSearches >= 0) : "Receive a null treatment";
		return numberOfSearches;
	}

    /**
     * Get the first bean created.
     *
     * @return firstSearch
     *				search found in the first position.
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public static Search first() throws SQLException {
		Search firstSearch = new Search();
		final GenericBeanDAO gDB = new GenericBeanDAO();
		firstSearch = (Search) gDB.firstOrLastBean(firstSearch, false);

		return firstSearch;
	}

    /**
     * Get the last Bean created.
     *
     * @return lastSearch
     *				the search found in the last position.
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public static Search last() throws SQLException {
		Search lastSearch = new Search();
		final GenericBeanDAO gDB = new GenericBeanDAO();
		lastSearch = (Search) gDB.firstOrLastBean(lastSearch, true);

		return lastSearch;
	}

    /**
     * Search for all the searches with a determined value in a determined field.
     *
     * @param FIELD
	 * 				field to be searched.
     * @param VALUE
	 * 				value to be searched for.
     * @param LIKE
	 * 				param for where.
     *
     * @return listOfFoundInSearch
     *				the list of searches found with the value in the specified field
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public static ArrayList<Search> getWhere(final String FIELD, final String VALUE,
                                             final boolean LIKE) throws SQLException {

		assert (FIELD != null) : "field must never be null.";
		assert (FIELD != "") : "field name must not be empty.";
		assert (FIELD.length() >= 1) : "field name must have at least one character.";
		assert (VALUE != null) : "value must never be null.";
		assert (VALUE != "") : "value name must not be empty.";
		assert (VALUE.length() >= 1) : "value name must have at least one character.";

		final Search type = new Search();
		ArrayList<Search> listOfFoundInSearch = new ArrayList<Search>();
		final GenericBeanDAO gDB = new GenericBeanDAO();

        ArrayList<Bean> selectBean = gDB.selectBeanWhere(type, FIELD, VALUE, LIKE,null);

		for (Bean searchBean : selectBean) {
			listOfFoundInSearch.add((Search) searchBean);
		}

		return listOfFoundInSearch;
	}

    /**
     * Delete this instance of search.
     *
     * @return
	 * 				the result of the database about the deletion.
     *
     * @throws SQLException
	 * 				there maybe a problem connecting to database.
     */
	public boolean delete() throws SQLException {
		final GenericBeanDAO gDB = new GenericBeanDAO();
        boolean resultDelete = false;

		resultDelete = gDB.deleteBean(this);

		return resultDelete;
	}

	@Override
	public void setId(final int ID) {
		assert (ID > 0) : "Receive a negative treatment.";
		this.id = ID;
	}

    /**
     * Get specified data of field.
     *
     * @param FIELD
	 * 				field that it wants the data.
     *
     * @return
	 * 				the value of the specified field
     */
	@Override
	public String get(final String FIELD) {
		assert (FIELD != null) : "field must never be null.";
		assert (FIELD != "") : "field name must not be empty.";
		assert (FIELD.length() >= 1) : "field name must have at least one character.";

		String contentFromFields = "";
        final SearchFields fieldName = SearchFields.valueOf(FIELD);

        switch(fieldName) {
            case _id: contentFromFields = Integer.toString(this.getId());
                break;
            case date: contentFromFields = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.US).format(this.date);
                break;
            case year: contentFromFields = Integer.toString(this.getYear());
                break;
            case option: contentFromFields = Integer.toString(this.getOption());
                break;
            case indicator: contentFromFields = this.getIndicator().getValue();
                break;
            case min_value: contentFromFields = Integer.toString(this.getMinValue());
                break;
            case max_value: contentFromFields = Integer.toString(this.getMaxValue());
                break;
            default:
                /*Nothing to do*/
        }

		assert (contentFromFields != null) : "Receive a null treatment";
		return contentFromFields;
	}

    /**
     * Set field with specified data of search.
     *
     * @param FIELD
	 * 				Field to be set.
     * @param DATA
	 * 				Data to be set to field.
     */
	@Override
	public void set(final String FIELD, final String DATA){
		assert (FIELD != null) : "field must never be null.";
		assert (FIELD != "") : "field name must not be empty.";
		assert (FIELD.length() >= 1) : "field name must have at least one character.";
		assert (DATA != null) : "data must never be null.";
		assert (DATA != "") : "data value must not be empty.";
		assert (DATA.length() >= 1) : "data value must have at least one character.";
        
        final SearchFields fieldName = SearchFields.valueOf(FIELD);

        switch(fieldName) {
            case _id:
                this.setId(Integer.parseInt(DATA));
                break;
            case date:
                Date dateData = null;
                try {
                    dateData = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).parse(DATA);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                this.setDate(dateData);
                break;
            case year:
                this.setYear(Integer.parseInt(DATA));
                break;
            case option:
                this.setOption(Integer.parseInt(DATA));
                break;
            case indicator:
                this.setIndicator(Indicator.getIndicatorByValue(DATA));
                break;
            case min_value:
                this.setMinValue(Integer.parseInt(DATA));
                break;
            case max_value:
                this.setMaxValue(Integer.parseInt(DATA));
                break;
            default:
                /*Nothing to do*/
        }
    }

    /**
     * Generate a list of fields of this entity.
     *
     * @return
	 * 				List of string of the fields.
     */
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();

		fields.add(SearchFields._id.toString());
		fields.add(SearchFields.date.toString());
		fields.add(SearchFields.year.toString());
		fields.add(SearchFields.option.toString());
		fields.add(SearchFields.indicator.toString());
        fields.add(SearchFields.min_value.toString());
		fields.add(SearchFields.max_value.toString());

		assert (fields != null) : "field must never be null.";
		assert (fields.size() >= 1) : "field name must have at least one character.";
		return fields;
	}

	@Override
	public int getId() {
		assert (id > 0) : "Receive a negative treatment";
		return this.id;
	}

}
