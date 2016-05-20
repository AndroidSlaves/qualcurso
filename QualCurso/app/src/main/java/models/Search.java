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
import java.util.Date;
import java.util.Locale;

import helpers.Indicator;

public class Search extends Bean{
	public static final int COURSE = 0;
	public static final int INSTITUTION = 1;
    public static final int MAX_SEARCHES_SAVE = 10;

	private int id = 0;
	private Date date = null;
	private int year = 0;
	private int option = 0;
	private Indicator indicator = null;
	private int minValue = 0;
	private int maxValue = 0;

    // Each value of enum represents a table field from Course
    private enum SearchFields {
        _id, date, year, option, indicator, min_value, max_value
    }

	public Search() {
		this.id = 0;
		this.identifier = "search";
		this.relationship = "";
	}

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
		return date;
	}

    /**
     * Store the date. Will be used on search.
     *
     * @param date
     */
	public void setDate(Date date) {
		assert (date.getDay() > 0) : "Day value must be between 1 and 31";
		assert (date.getDay() < 32) : "Day value must be between 1 and 31";
		assert (date.getMonth() > 0) : "Month value must be between 1 and 12";
		assert (date.getMonth() < 13) : "Month value must be between 1 and 12";
		assert (date.getYear() > 0) : "Year value must be between 0 and 3000";
		assert (date.getYear() < 3000) : "Year value must be between 0 and 3000";

		this.date = date;
	}

    /**
     * Get the year stored. Will be used on search.
     *
     * @return year
     *              Number integer of the year when the indicator was analyzed.
     */
	public int getYear() {
		return year;
	}

    /**
     * Store the year. Will be used on search.
     *
     * @param year
     *              Number integer of the year when the indicator was analyz
     */
	public void setYear(int year) {
		assert (year > 0) : "Year value must be between 0 and 3000";
		assert (year < 3000) : "Year value must be between 0 and 3000";

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
		assert (option >=0) : "Option is defined only for 0 and 1 values.";
		assert (option <=1) : "Option is defined only for 0 and 1 values.";

		this.option = option;
	}

    /**
     * Get the indicators.
     *
     * @return indicator
     */
	public Indicator getIndicator() {
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
     * @return maxValue
     */
	public int getMaxValue() {
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
     * @return resultOfSaving
     *              Returns the confirmation storage in the database
     * @throws SQLException
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
     * @param id
     *
     * @return searchById
     *
     * @throws SQLException
     */
	public static Search get(final int id) throws SQLException {
		assert (id > 0) : "id must be positive.";

		Search searchById = new Search(id);
		GenericBeanDAO gDB = new GenericBeanDAO();	
		searchById = (Search) gDB.selectBean(searchById);

		return searchById;
	}

    /**
     * Return an list of searches made by user.
     *
     * @return allSearches;
     *
     * @throws SQLException
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
     * @return numberOfSearches
     *
     * @throws SQLException
     */
	public static int count() throws  SQLException {
		final Search type = new Search();
		final GenericBeanDAO gDB = new GenericBeanDAO();
		int numberOfSearches = gDB.countBean(type);

		return numberOfSearches;
	}

    /**
     * Get the first bean created.
     *
     * @return firstSearch
     *
     * @throws SQLException
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
     *
     * @throws SQLException
     */
	public static Search last() throws SQLException {
		Search lastSearch = new Search();
		final GenericBeanDAO gDB = new GenericBeanDAO();
		lastSearch = (Search) gDB.firstOrLastBean(lastSearch, true);

		return lastSearch;
	}

    /**
     * Generate a list with values of field, value, like. It's a kind of search.
     *
     * @param field
     * @param value
     * @param like
     *
     * @return listOfFoundInSearch
     *
     * @throws SQLException
     */
	public static ArrayList<Search> getWhere(final String field, final String value,
                                             final boolean like) throws SQLException {
		assert (field != null) : "field must never be null.";
		assert (field != "") : "field name must not be empty.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (value != null) : "value must never be null.";
		assert (value != "") : "value name must not be empty.";
		assert (value.length() >= 1) : "value name must have at least one character.";

		final Search type = new Search();
		ArrayList<Search> listOfFoundInSearch = new ArrayList<Search>();
		final GenericBeanDAO gDB = new GenericBeanDAO();

        ArrayList<Bean> selectBean = gDB.selectBeanWhere(type, field, value, like,null);

		for (Bean searchBean : selectBean) {
			listOfFoundInSearch.add((Search) searchBean);
		}

		return listOfFoundInSearch;
	}

    /**
     * Delete some bean on context.
     *
     * @return resultDelete
     *
     * @throws SQLException
     */
	public boolean delete() throws SQLException {
		final GenericBeanDAO gDB = new GenericBeanDAO();
        boolean resultDelete = false;

		resultDelete = gDB.deleteBean(this);

		return resultDelete;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

    /**
     * Get the ID of search.
     *
     * @param field
     *
     * @return contentFromFields
     */
	@Override
	public String get(final String field) {
		assert (field != null) : "field must never be null.";
		assert (field != "") : "field name must not be empty.";
		assert (field.length() >= 1) : "field name must have at least one character.";

		String contentFromFields = "";
        final SearchFields fieldName = SearchFields.valueOf(field);

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

		return contentFromFields;
	}

    /**
     * Set the ID of search by field and data.
     *
     * @param field
     * @param data
     */
	@Override
	public void set(final String field, final String data){
		assert (field != null) : "field must never be null.";
		assert (field != "") : "field name must not be empty.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (data != null) : "data must never be null.";
		assert (data != "") : "data value must not be empty.";
		assert (data.length() >= 1) : "data value must have at least one character.";

        final SearchFields fieldName = SearchFields.valueOf(field);

        switch(fieldName) {
            case _id:
                this.setId(Integer.parseInt(data));
                break;
            case date:
                Date dateData = null;
                try {
                    dateData = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).parse(data);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                this.setDate(dateData);
                break;
            case year:
                this.setYear(Integer.parseInt(data));
                break;
            case option:
                this.setOption(Integer.parseInt(data));
                break;
            case indicator:
                this.setIndicator(Indicator.getIndicatorByValue(data));
                break;
            case min_value:
                this.setMinValue(Integer.parseInt(data));
                break;
            case max_value:
                this.setMaxValue(Integer.parseInt(data));
                break;
            default:
                /*Nothing to do*/
        }
    }

    /**
     * Generate a list of fields of database.
     *
     * @return ArrayList<String> fieldsList
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

		return fields;
	}

	@Override
	public int getId() {
		return this.id;
	}

}
