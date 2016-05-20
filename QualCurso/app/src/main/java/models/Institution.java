/*****************************
 * Class name: Institution (.java)
 *
 * Purpose: Class that represents a generic institution entity with its attributes.
 *****************************/

package models;

import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Institution extends Bean implements Parcelable {
	private int id;
	private String acronym;

	public Institution() {
		this.id = 0;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	public Institution(int id) {
		assert (id >=0) : "id must never be negative";

		this.id = id;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		assert (acronym != null) : "acronym must never be null";

		this.acronym = acronym;
	}

	public void setId(int id) {
		assert (id >=0) : "id must never be negative";

		this.id = id;
	}

	public int getId() {
        return id;
	}

	public boolean save() throws SQLException {
		boolean result = false;

		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Institution.last().getId());

		return result;
	}
	
	public boolean addCourse(Course course) throws SQLException {
		assert (course != null) : "course must never be null";

		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.addBeanRelationship(this, course);
		return result;
	}

	public static Institution get(int id) throws SQLException {
		assert (id >= 0) : "id must never be negative";

		Institution result = new Institution(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Institution) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Institution> getAll() throws SQLException {
		Institution type = new Institution();
		ArrayList<Institution> result = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type,"acronym")) {
			result.add((Institution) b);
		}
		return result;
	}

	public static int count() throws SQLException {
		Institution type = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Institution first() throws SQLException {
		Institution result = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Institution) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Institution last() throws 
			SQLException {
		Institution result = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Institution) gDB.firstOrLastBean(result, true);
		return result;
	}

	public ArrayList<Course> getCourses() throws 
			SQLException {
		ArrayList<Course> courses = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanRelationship(this, "course","name")) {
			courses.add((Course) b);
		}
		return courses;
	}
	
	public ArrayList<Course> getCourses(int year) throws 
			SQLException {
		assert (year > 1990) : "year must never be bigger than 1990";

		ArrayList<Course> courses = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanRelationship(this, "course", year,"name")) {
			courses.add((Course) b);
		}
		return courses;
	}
	
	

	public static ArrayList<Institution> getWhere(String field, String value,
			boolean like) throws  SQLException {
		assert (field != null) : "field must never be null";
		assert (value != null) : "value must never be null";
		assert (field != "") : "field must never be empty";
		assert (value != "") : "value must never be null";

		Institution type = new Institution();
		ArrayList<Institution> result = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like,"acronym")) {
			result.add((Institution) b);
		}
		return result;
	}

	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(Search search) throws  SQLException {

		assert (search != null) : "search must never be null";
		assert (search.getIndicator() != null) : "search's indicator must never be null";
		assert (search.getYear() > 1990) : "search's year must never be smaller than 1990";

		ArrayList<Institution> result = new ArrayList<Institution>();
		String sql = "SELECT i.* FROM institution AS i, evaluation AS e, articles AS a, books AS b "+
					" WHERE year="+Integer.toString(search.getYear())+
					" AND e.id_institution = i._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND "+search.getIndicator().getValue();

		if(search.getMaxValue() == -1){
			sql+=" >= "+Integer.toString(search.getMinValue());
		}else{
			sql+=" BETWEEN "+Integer.toString(search.getMinValue())+" AND "+Integer.toString(search.getMaxValue());
		}
		sql+=" GROUP BY i._id";
		GenericBeanDAO
		gDB = new GenericBeanDAO();

		for (Bean b : gDB.runSql(new Institution(), sql)){
			result.add((Institution)b);
		}

		return result;
	}

	public static ArrayList<Course> getCoursesByEvaluationFilter(int id_institution, Search search) throws  SQLException {

		assert(search.getYear() > 1990) : "search's year must never be smaller than 1990";
		assert (search != null) : "search must never be null";
		assert (search.getIndicator() != null) : "search's indicator must never be null";
		assert (search.getYear() > 1990) : "search's year must never be smaller than 1990";

		ArrayList<Course> result = new ArrayList<Course>();
		String sql = "SELECT c.* FROM course AS c, evaluation AS e, articles AS a, books AS b "+
					" WHERE e.id_institution="+id_institution+
					" AND e.id_course = c._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND year="+search.getYear()+
					" AND "+search.getIndicator().getValue();
		
		if(search.getMaxValue() == -1){
			sql+=" >= "+search.getMinValue();
		}else{
			sql+=" BETWEEN "+search.getMinValue()+" AND "+search.getMaxValue();
		}
		sql+=" GROUP BY c._id";
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.runSql(new Course(), sql)){
			result.add((Course)b);
		}
		return result;
	}
	
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		for(Course c : this.getCourses()){
			gDB.deleteBeanRelationship(this,c);
		}
		result = gDB.deleteBean(this);
		return result;
	}

	@Override
	public String get(String field) {
		assert (field != null) : "field must never be null";

		if (field.equals("_id")) {
			return Integer.toString(this.getId());
		} else if (field.equals("acronym")) {
			return this.getAcronym();
		} else {
			return "";
		}
	}

	@Override
	public void set(String field, String data) {
		assert (field != null) : "field must never be null";
		assert (data != null) : "data must never be null";
		
		if (field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} else if (field.equals("acronym")) {
			this.setAcronym(data);
		} else {/* Nothing to do. */}

	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("acronym");
		return fields;
	}

	@Override
	public String toString() {
		return getAcronym();
	}
	
	private Institution(Parcel in){
		assert (in != null) : "in must never be null";

		this.id = in.readInt();
		this.acronym = in.readString();
		this.identifier = in.readString();
		this.relationship = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		assert (dest != null) : "dest must never be null";

		dest.writeInt(this.id);
		dest.writeString(this.acronym);
		dest.writeString(this.identifier);
		dest.writeString(this.relationship);
		
	}
	
	public static final Parcelable.Creator<Institution> CREATOR = new Parcelable.Creator<Institution>() {

		@Override
		public Institution createFromParcel(Parcel source) {
			assert (source != null)  : "source must never be null";
			return new Institution(source);
		}

		@Override
		public Institution[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Institution[size];
		}
	};
}
