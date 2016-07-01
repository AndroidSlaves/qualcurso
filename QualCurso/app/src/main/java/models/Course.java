/**
 * Class name: Course (.java)
 *
 * Purpose: Modeling characteristics from Course and use methods to interacts with the database
 */

package models;

import android.database.SQLException;
import android.os.Parcel;
import android.os.Parcelable;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Calendar;


public class Course extends Bean implements Parcelable{
	// Unique identification number.
	private int id;
	// Name of the course given by the university.
	private String name;

	/**
	 * Default constructor that first setUp class attributes.
	 */
	public Course() {
		this.id = 0;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}

	/**
	 * Default constructor that first setUp class attributes with given id.
	 *
	 * @param id
	 * 				identifier for this course that will be set.
	 */
	public Course(int id){
		assert (id >= 0) : "Receive a negative treatment";
		this.id = id;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}

	/**
	 *
	 * @return
	 * 				the saved identification number.
	 */
	public int getId() {
		return id;
	}

	/**
	 *
	 * @param id
	 * 				identification number to be saved.
	 */
	public void setId(int id) {
		assert (id >= 0) : "Receive a negative treatment";
		this.id = id;
	}

	/**
	 *
	 * @return
	 * 				the name of the course.
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name
	 * 				the name of the course to be saved.
	 */
	public void setName(String name) {
		assert (name != null) : "Receive a null treatment";
		assert (name != "") : "Receive a empty treatment";
		assert (name.length() > 1) : "Receive a size treatment";

		this.name = name;
	}

	/**
	 * Saves this course in the bean database.
	 *
	 * @return
	 * 				the result coming from data base saying if it successful saved.
	 *
	 * @throws SQLException
	 * 				there maybe a problem inserting.
	 */
	public boolean save() throws  SQLException {
		boolean result = false;
        Assert.assertFalse(result);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		assert (gDB != null) : "Receive a null treatment";

		this.setId(Course.last().getId());

		return result;
	}

	/**
	 * Inserts institution in the database.
	 *
	 * @param institution
	 * 				institution to be added.
	 *
	 * @return
	 * 				the result coming from data base saying if it successful saved.
	 *
	 * @throws SQLException
	 * 				there maybe a problem connecting in the database.
	 */
	public boolean addInstitution(Institution institution) throws  SQLException {
		assert (institution != null) : "Receive a null treatment";

		boolean result = false;
        Assert.assertFalse(result);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.addBeanRelationship(this, institution);
        assert (gDB != null) : "Receive a null treatment";

		return result;
	}

	/**
	 * Get the course by its id.
	 *
	 * @param id
	 * 				id of the wanted course.
	 *
	 * @return
	 * 				the course with the given id.
	 *
	 * @throws SQLException
	 * 				there maybe a problem connecting to the database.
	 */
	public static Course get(int id) throws SQLException {
		assert (id >= 0) : "Receive a negative treatment";

		Course result = new Course(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.selectBean(result);
        assert (gDB != null) : "Receive a null treatment";
        assert (result != null) : "Receive a null treatment";

		return result;
	}

	/**
	 * Get all courses from database.
	 *
	 * @return
	 * 				the list with all courses.
	 *
	 * @throws SQLException
	 * 				there maybe a problem accessing database.
	 */
	public static ArrayList<Course> getAll() throws  SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean bean : gDB.selectAllBeans(type,"name")) {
			result.add((Course) bean);
		}

        assert (result != null) : "Receive a null treatment";

		return result;
	}

	/**
	 * Get the number of all courses in the database.
	 *
	 * @return
	 * 				the number of courses in database.
	 *
	 * @throws SQLException
	 * 				there maybe a problem connecting to database.
	 */
	public static int count() throws SQLException {
		Course type = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
        int countBean = gDB.countBean(type);
        assert (countBean >= 0) : "Receive a negative treatment";
		return countBean;
	}

	/**
	 * Returns the first course present in the database.
	 *
	 * @return
	 *              the first course in the database.
	 *
	 * @throws SQLException
	 *              there maybe a problem accessing database.
	 */
	public static Course first() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, false);
        assert (result != null) : "Receive a null treatment";
		return result;
	}

	/**
	 * Returns the last course present in the database.
	 *
	 * @return
	 *              the last course in the database.
	 *
	 * @throws SQLException
	 *              there maybe a problem accessing database.
	 */
	public static Course last() throws SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, true);
        assert (result != null) : "Receive a null treatment";
		return result;
	}

	/**
	 * Get all the institution that own this course.
	 *
	 * @return
	 * 				all the institutions connected to this course.
	 *
	 * @throws SQLException
	 * 				there maybe a problem connecting to database.
	 */
	public ArrayList<Institution> getInstitutions() throws SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean bean : gDB.selectBeanRelationship(this, "institution", "acronym")) {
			institutions.add((Institution) bean);
		}
        assert (institutions != null) : "Receive a null treatment";
		return institutions;
	}

	/**
	 * Get all the institution that own this course.
	 *
	 * @param year
	 * 				the year of the evaluation that that you want the institutions from.
	 *
	 * @return
	 * 				all the institutions connected to this course.
	 *
	 * @throws SQLException
	 * 				there maybe a problem connecting to database.
	 */
	public ArrayList<Institution> getInstitutions(int year) throws SQLException {
		final Calendar data = Calendar.getInstance();
		final int yearData = data.get(Calendar.YEAR);
		assert (year > 0) : "Receive a negative treatment";
		assert (year <= yearData) : "Receive a max year treatment";

		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean bean : gDB.selectBeanRelationship(this, "institution",year,"acronym")) {
			institutions.add((Institution) bean);
		}
        assert (institutions != null) : "Receive a null treatment";
		return institutions;
	}

	/**
	 * Returns all the courses that a specified field corresponds to the specified string value.
	 *
	 * @param field
	 *              which field is going to serve as criteria for search.
	 *
	 * @param value
	 *              value to look for in the course
	 *
	 * @param like
	 *              boolean that gives the sql string the LIKE command
	 *
	 * @return
	 *              the result of the database operation, if it returned successful.
	 *
	 * @throws SQLException
	 *              there maybe a problem accessing database.
	 */
	public static ArrayList<Course> getWhere(String field, String value, boolean like) throws  SQLException {
		assert (field != null) : "Receive a null treatment";
		assert (field.equals("")) : "Receive a empty treatment";
		assert (value != null) : "Receive a null treatment";
		assert (value.equals("")) : "Receive a null treatment";

		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean bean : gDB.selectBeanWhere(type, field, value, like,"name")) {
			result.add((Course) bean);
		}
        assert (result != null) : "Receive a null treatment";
		return result;
	}

	/**
	 * Search for course using criteria of a filter present in the search paramenter.
	 *
	 * @param search
	 * 				determines the criteria of search.
	 *
	 * @return
	 * 				the courses retrieved from database.
	 *
	 * @throws SQLException
	 * 				there maybe a problem accessing database.
	 */
	public static ArrayList<Course> getCoursesByEvaluationFilter(Search search) throws  SQLException {
		assert (search != null) : "Receive a null tratment";

		ArrayList<Course> result = new ArrayList<Course>();

		String sql = "SELECT c.* FROM course AS c, evaluation AS e, articles AS a, books AS b "+
					" WHERE year=" + Integer.toString(search.getYear()) +
					" AND e.id_course = c._id" +
					" AND e.id_articles = a._id" +
					" AND e.id_books = b._id" +
					" AND " + search.getIndicator().getValue();

		if(search.getMaxValue() == -1) {
			sql += " >= " + Integer.toString(search.getMinValue());
		} else {
			sql += " BETWEEN " + Integer.toString(search.getMinValue()) + " AND " +
					Integer.toString(search.getMaxValue());
		}

		sql += " GROUP BY c._id";
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean bean : gDB.runSql(new Course(), sql)){
			result.add((Course)bean);
		}

        assert (result != null) : "Receive a null treatment";
		return result;
	}

    /**
     * Search for course using criteria of a filter present in the search paramenter.
     *
     * @param search
     * 				determines the criteria of search.
     *
     * @param id_course
     *              identifier of the course that is being searched.
     * @return
     * 				the courses retrieved from database.
     *
     * @throws SQLException
     * 				there maybe a problem accessing database.
     */
	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(int id_course, Search search) throws  SQLException {
		assert (id_course > 0) : "Receive a negative treatment";
		assert (search != null) : "Receive a null treatment";

		ArrayList<Institution> result = new ArrayList<Institution>();
		String commandSql = "SELECT i.* FROM institution AS i, evaluation AS e, articles AS a, books AS b "+
					" WHERE e.id_course=" + Integer.toString(id_course) +
					" AND e.id_institution = i._id" +
					" AND e.id_articles = a._id" +
					" AND e.id_books = b._id" +
					" AND year=" + Integer.toString(search.getYear())+
					" AND " + search.getIndicator().getValue();

        String sql = null;
		if(search.getMaxValue() == -1) {
			sql = commandSql + " >= " + search.getMinValue();
		} else {
			sql = commandSql + " BETWEEN " + Integer.toString(search.getMinValue()) + " AND " +
					Integer.toString(search.getMaxValue());
		}
        Assert.assertNotSame(commandSql, sql);

		commandSql += " GROUP BY i._id";
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.runSql(new Institution(), commandSql)){
			result.add((Institution) b);
		}
        assert (result != null) : "Receive a null treatment";
		return result;
	}

    /**
     * Delete this course from database.
     *
     * @return
     *              the result returned from the database, if it was successfully deleted.
     *
     * @throws SQLException
     *              there maybe a problem accessing database.
     */
	public boolean delete() throws  SQLException {
		boolean isBeanDeleted = false;
		GenericBeanDAO genericBeanDao = new GenericBeanDAO();

		for(Institution i : this.getInstitutions()){
			genericBeanDao.deleteBeanRelationship(this, i);
		}
		isBeanDeleted = genericBeanDao.deleteBean(this);

        assert (genericBeanDao != null) : "Receive a null treatment";
        Assert.assertNotNull(isBeanDeleted);

		return isBeanDeleted;
	}

    /**
     * Gets the value inside the attribute with the given name in field.
     *
     * @param field
     *              attribute to be consulted.
     *
     * @return
     *              the value inside the specified field (variable).
     */
	@Override
	public String get(String field) {
		assert (field != null) : "Receive a null treatment";
		assert (field != "") : "Receive a empty treatment";

		String selectedField = "";

		if (field.equals("_id")){
			selectedField = Integer.toString(this.getId());
		} else if(field.equals("name")){
			selectedField = this.getName();
		} else {
			selectedField = "";
		}

        Assert.assertNotNull(selectedField);

		return selectedField;
	}

    /**
     * String type. Sets the value inside the attribute with the given name in field.
     *
     * @param field
     *              attribute to be consulted.
     *
     * @param data
     *              value that will be set on the specified field.
     */
	@Override
	public void set(String field, String data) {
		assert (field != null) : "Receive a null treatment";
		assert (field != "") : "Receive a empty treatment";
		assert (data != null) : "Receive a null treatment";
		assert (data != "") : "Receive a empty treatment";

		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} else if(field.equals("name")){
			this.setName(data);
		} else {
			/* Nothing to do! */
		}

	}

    /**
     * Method that returns all the fields of a course.
     *
     * @return
     *              the list of fields.
     */
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();

		fields.add("_id");
		fields.add("name");

        assert (fields != null) : "Receive a null treatment";

		return fields;
	}

    /**
     *
     * @return
     *              name of the course.
     */
	@Override
	public String toString() {
        return getName();
	}

    /**
     * Transform a parcel into a course.
     *
     * @param in
     *              parcel to receive data.
     */
	private Course(Parcel in){
		assert (in != null) : "Receive a null treatment";

		this.id = in.readInt();
		this.name = in.readString();
		this.identifier = in.readString();
		this.relationship = in.readString();
	}

    /**
     * Nothing to do.
     *
     * @return
     *              zero.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Transform course to parcel
     *
     * @param dest
     *                  parcel to e written.
     *
     * @param flags
     *                  obligatory enter.
     */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		assert (dest != null) : "Receive a null treatment";
		assert (flags > 0) : "Receive a negative treatment";

		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.identifier);
		dest.writeString(this.relationship);

        assert (dest != null) : "Receive a null treatment";
	}
	
	public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {

		@Override
		public Course createFromParcel(Parcel source) {
			assert (source != null) : "Receive a null tratment";
            Course course = new Course(source);
			return course;
		}

		@Override
		public Course[] newArray(int size) {
			assert (size != 0) : "Receive a size tratment";
            Course[] courseArray = new Course[size];
			return courseArray;
		}
	};
	
}
