package models;

import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Course extends Bean implements Parcelable{
	private int id;
	private String name;

	public Course() {
		this.id = 0;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	public Course(int id){
		assert (id >= 0) : "Receive a negative tratment";
		this.id = id;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		assert (id >= 0) : "Receive a negative tratment";
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		assert (name != null) : "Receive a null tratment";
		assert (name != "") : "Receive a empty tratment";
		assert (name.length() > 1) : "Receive a size tratment";
		this.name = name;
	}
	
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Course.last().getId());
		return result;
	}
	
	public boolean addInstitution(Institution institution) throws  SQLException {
		assert (institution != null) : "Receive a null tratment";
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.addBeanRelationship(this, institution);
		return result;
	}

	public static Course get(int id) throws SQLException {
		assert (id >= 0) : "Receive a negative tratment";
		Course result = new Course(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Course> getAll() throws  SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type,"name")) {
			result.add((Course) b);
		}
		return result;
	}

	public static int count() throws SQLException {
		Course type = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Course first() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Course last() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, true);
		return result;
	}

	public ArrayList<Institution> getInstitutions() throws 
			SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanRelationship(this, "institution", "acronym")) {
			institutions.add((Institution) b);
		}
		return institutions;
	}
	
	public ArrayList<Institution> getInstitutions(int year) throws SQLException {
		Calendar data = Calendar.getInstance();
		int yearData = data.get(Calendar.YEAR);
		assert (year > 0) : "Receive a negative tratment";
		assert (year <= yearData) : "Receive a max year tratment";
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanRelationship(this, "institution",year,"acronym")) {
			institutions.add((Institution) b);
		}
		return institutions;
}

	public static ArrayList<Course> getWhere(String field, String value, boolean like) throws  SQLException {
		assert (field != null) : "Receive a null tratment";
		assert (field != "") : "Receive a empty tratment";
		assert (value != null) : "Receive a null tratment";
		assert (value != "") : "Receive a null tratment";
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like,"name")) {
			result.add((Course) b);
		}
		return result;
	}
	
	public static ArrayList<Course> getCoursesByEvaluationFilter(Search search) throws  SQLException {
		assert (search != null) : "Receive a null tratment";
		ArrayList<Course> result = new ArrayList<Course>();
		String sql = "SELECT c.* FROM course AS c, evaluation AS e, articles AS a, books AS b "+
					" WHERE year="+Integer.toString(search.getYear())+
					" AND e.id_course = c._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND "+search.getIndicator().getValue();

		if(search.getMaxValue() == -1){
			sql+=" >= "+Integer.toString(search.getMinValue());
		}else{
			sql+=" BETWEEN "+Integer.toString(search.getMinValue())+" AND "+Integer.toString(search.getMaxValue());
		}
		sql+=" GROUP BY c._id";
		GenericBeanDAO
		gDB = new GenericBeanDAO();

		for (Bean b : gDB.runSql(new Course(), sql)){
			result.add((Course)b);
		}

		return result;
	}

	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(int id_course, Search search) throws  SQLException {
		assert (id_course > 0) : "Receive a negative trament";
		assert (search != null) : "Receive a null tratment";
		ArrayList<Institution> result = new ArrayList<Institution>();
		String sql = "SELECT i.* FROM institution AS i, evaluation AS e, articles AS a, books AS b "+
					" WHERE e.id_course="+Integer.toString(id_course)+
					" AND e.id_institution = i._id"+
					" AND e.id_articles = a._id"+
					" AND e.id_books = b._id"+
					" AND year="+Integer.toString(search.getYear())+
					" AND "+search.getIndicator().getValue();
		
		if(search.getMaxValue() == -1){
			sql+=" >= "+search.getMinValue();
		}else{
			sql+=" BETWEEN "+Integer.toString(search.getMinValue())+" AND "+Integer.toString(search.getMaxValue());
		}
		sql+=" GROUP BY i._id";
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.runSql(new Institution(), sql)){
			result.add((Institution)b);
		}
		return result;
	}

	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		for(Institution i : this.getInstitutions()){
			gDB.deleteBeanRelationship(this,i);
		}
		result = gDB.deleteBean(this);
		return result;
	}
	
	@Override
	public String get(String field) {
		assert (field != null) : "Receive a null tratment";
		assert (field != "") : "Receive a empty tratment";
		if(field.equals("_id")){
			return Integer.toString(this.getId());
		}else if(field.equals("name")){
			return this.getName();
		}else {
		return "";
		}
	}

	@Override
	public void set(String field, String data) {
		assert (field != null) : "Receive a null tratment";
		assert (field != "") : "Receive a empty tratment";
		assert (data != null) : "Receive a null tratment";
		assert (data != "") : "Receive a empty tratment";

		if(field.equals("_id")){
			this.setId(Integer.parseInt(data));
		}else if(field.equals("name")){
			this.setName(data);
		}else {
			/* Nothing to do! */
		}
	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("name");
		return fields;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	private Course(Parcel in){
		assert (in != null) : "Receive a null tratment";
		this.id = in.readInt();
		this.name = in.readString();
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
		assert (dest != null) : "Receive a null tratment";
		assert (flags > 0) : "Receive a negative tratment";
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.identifier);
		dest.writeString(this.relationship);
	}
	
	public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {

		@Override
		public Course createFromParcel(Parcel source) {
			assert (source != null) : "Receive a null tratment";
			return new Course(source);
		}

		@Override
		public Course[] newArray(int size) {
			assert (size != 0) : "Receive a size tratment";
			// TODO Auto-generated method stub
			return new Course[size];
		}
	};
	
}
