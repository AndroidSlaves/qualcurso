package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;

import unb.mdsgpp.qualcurso.QualCurso;
import android.database.SQLException;
import android.text.format.DateFormat;
import helpers.Indicator;

public class Search extends Bean{
	public static int COURSE = 0;
	public static int INSTITUTION = 1;

	private int id;
	private Date date;
	private int year;
	private int option;
	private Indicator indicator;
	private int minValue;
	private int maxValue;


	public Search() {
		this.id = 0;
		this.identifier="search";
		this.relationship="";
	}

	public Search(int id) {
		assert (id >= 0) : "id must be positive integer.";

		this.id = id;
		this.identifier="search";
		this.relationship="";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		assert (date.getDay() > 0 && date.getDay() < 32) : "Day value must be between 1 and 31";
		assert (date.getMonth() > 0 && date.getMonth() < 13) : "Month value must be between 1 and 12";
		assert (date.getYear() > 0 && date.getYear() < 3000) : "Year value must be between 0 and 3000";

		this.date = date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		assert (year > 0 && year < 3000) : "Year value must be between 0 and 3000";

		this.year = year;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		assert (option >=0 && option <=1) : "Option is defined only for 0 and 1 values.";

		this.option = option;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		assert (indicator != null) : "indicator can't be null";

		this.indicator = indicator;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		assert (minValue > 0) : "minimum value must be positive.";

		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		assert (maxValue > 0) : "maximum value must be positive.";

		this.maxValue = maxValue;
	}

	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		if(Search.count()>=10){
			Search.first().delete();
		}

		result = gDB.insertBean(this);
		this.setId(Search.last().getId());

		return result;
	}

	public static Search get(int id) throws SQLException {
		assert (id > 0) : "id must be positive.";

		Search result = new Search(id);
		GenericBeanDAO gDB = new GenericBeanDAO();	
		result = (Search) gDB.selectBean(result);

		return result;
	}

	public static ArrayList<Search> getAll() throws  SQLException {
		Search type = new Search();
		ArrayList<Search> result = new ArrayList<Search>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Search) b);
		}

		return result;
	}

	public static int count() throws  SQLException {
		Search type = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();

		return gDB.countBean(type);
	}

	public static Search first() throws SQLException {
		Search result = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Search) gDB.firstOrLastBean(result, false);

		return result;
	}

	public static Search last() throws SQLException {
		Search result = new Search();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Search) gDB.firstOrLastBean(result, true);

		return result;
	}

	public static ArrayList<Search> getWhere(String field, String value, boolean like) throws  SQLException {
		assert (field != null) : "field must never be null.";
		assert (field != "") : "field name must not be empty.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (value != null) : "value must never be null.";
		assert (value != "") : "value name must not be empty.";
		assert (value.length() >= 1) : "value name must have at least one character.";

		Search type = new Search();
		ArrayList<Search> result = new ArrayList<Search>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.selectBeanWhere(type, field, value, like,null)) {
			result.add((Search) b);
		}
		return result;
	}

	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.deleteBean(this);

		return result;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String get(String field) {
		assert (field != null) : "field must never be null.";
		assert (field != "") : "field name must not be empty.";
		assert (field.length() >= 1) : "field name must have at least one character.";

		String contentFromFields = "";

		if(field.equals("_id")) {
			contentFromFields = Integer.toString(this.getId());
		}else if(field.equals("date")) {
			contentFromFields = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).format(this.date);
		}else if (field.equals("year")) {
			contentFromFields = Integer.toString(this.getYear());
		}else if(field.equals("option")) {
			contentFromFields = Integer.toString(this.getOption());
		}else if(field.equals("indicator")) {
			contentFromFields = this.getIndicator().getValue();
		}else if(field.equals("min_value")) {
			contentFromFields = Integer.toString(this.getMinValue());
		}else if(field.equals("max_value")) {
			contentFromFields = Integer.toString(this.getMaxValue());
		}else {/*Nothing to do*/}

		return contentFromFields;
	}

	@Override
	public void set(String field, String data){
		assert (field != null) : "field must never be null.";
		assert (field != "") : "field name must not be empty.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (data != null) : "data must never be null.";
		assert (data != "") : "data value must not be empty.";
		assert (data.length() >= 1) : "data value must have at least one character.";

		if(field.equals("_id")){
			this.setId(Integer.parseInt(data));
		}else if(field.equals("date")){
			Date dateData = null;
			try {
				dateData = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.setDate(dateData);
		}else if (field.equals("year")) {
			this.setYear(Integer.parseInt(data));
		}else if (field.equals("option")) {
			this.setOption(Integer.parseInt(data));
		}else if (field.equals("indicator")) {
			this.setIndicator(Indicator.getIndicatorByValue(data));
		}else if (field.equals("min_value")) {
			this.setMinValue(Integer.parseInt(data));
		}else if (field.equals("max_value")) {
			this.setMaxValue(Integer.parseInt(data));
		}

	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();

		fields.add("_id");
		fields.add("date");
		fields.add("year");
		fields.add("option");
		fields.add("indicator");
		fields.add("min_value");
		fields.add("max_value");

		return fields;
	}

	@Override
	public int getId() {
		return this.id;
	}

}
