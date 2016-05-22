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

	// Unic number that identifies the institution.
	private int id;

	// String that complements the institution.
	private String acronym;

    /** Each value of enum represents a table field from Course */
    private enum InstitutionFields {
        _id, acronym
    }

	public Institution() {
		this.id = 0;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	// Method contructor that instantiate the object with the given id attribute.
	public Institution(int id) {
		assert (id >=0) : "id must never be negative";

		this.id = id;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	/**
	 * @return
	 * 				The institution acronym.
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @param acronym
	 * 				Method that sets the institution acronym attribute.
	 */
	public void setAcronym(String acronym) {
		assert (acronym != null) : "acronym must never be null";

		this.acronym = acronym;
	}

	/**
	 * @param id
	 * 				Method that sets the institution id with a given value.
	 */
	public void setId(int id) {
		assert (id >=0) : "id must never be negative";

		this.id = id;
	}

	/**
	 * @return
	 * 				Method that returns the intitution Id.
	 */
	public int getId() {
        return id;
	}

	// Saves the institution entity with its data in the database.
	public boolean save() throws SQLException {
		boolean saveConfirmation = false;

		GenericBeanDAO gDB = new GenericBeanDAO();
		saveConfirmation = gDB.insertBean(this);
		this.setId(Institution.last().getId());

		return saveConfirmation;
	}

	// Method that adds a relationship between institution and course in the database.
	public boolean addCourse(Course course) throws SQLException {
		assert (course != null) : "course must never be null";

		boolean courseAdded = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		courseAdded = gDB.addBeanRelationship(this, course);

		return courseAdded;
	}

	// Method that get an institution entity from the database by the given ID.
	public static Institution get(final int ID) throws SQLException {
		assert (ID >= 0) : "id must never be negative";

		Institution institution = new Institution(ID);
		final GenericBeanDAO gDB = new GenericBeanDAO();
		institution = (Institution) gDB.selectBean(institution);

		return institution;
	}

	// Selects and returns all the institution entities from the database.
	public static ArrayList<Institution> getAll() throws SQLException {

		final Institution beanType = new Institution();
		ArrayList<Institution> listInstitutions = new ArrayList<Institution>();
		final GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean institutionBean : gDB.selectAllBeans(beanType,"acronym")) {
			listInstitutions.add((Institution) institutionBean);
		}

		return listInstitutions;
	}

	// Returns the number of institutions in the database.
	public static int count() throws SQLException {

		final Institution beanType = new Institution();
		final GenericBeanDAO gDB = new GenericBeanDAO();
        int institutionsCount = gDB.countBean(beanType);

		return institutionsCount;
	}

	// Method that gets and returns the first institution of the list.
	public static Institution first() throws SQLException {

		Institution institution = new Institution();
		final GenericBeanDAO gDB = new GenericBeanDAO();
		institution = (Institution) gDB.firstOrLastBean(institution, false);

		return institution;
	}

	// Method that gets and returns the last institution of the list.
	public static Institution last() throws SQLException {

		Institution institution = new Institution();
		final GenericBeanDAO gDB = new GenericBeanDAO();
		institution = (Institution) gDB.firstOrLastBean(institution, true);

		return institution;
	}

	// Method that returns all the courses relatade to this one institution.
	public ArrayList<Course> getCourses() throws SQLException {

		ArrayList<Course> courses = new ArrayList<Course>();
		final GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean courseBean : gDB.selectBeanRelationship(this, "course", "name")) {
			courses.add((Course) courseBean);
		}

		return courses;
	}

	// Method that returns all the courses relatade to this one institution on a single year.
	public ArrayList<Course> getCourses(int year) throws SQLException {
		assert (year > 1990) : "year must never be bigger than 1990";

		ArrayList<Course> courses = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean courseBean : gDB.selectBeanRelationship(this, "course", year,"name")) {
			courses.add((Course) courseBean);
		}

		return courses;
	}

	// Method that select Institutions by the acronym and return it.
	public static ArrayList<Institution> getWhere(final String FIELD, final String VALUE,
			final boolean LIKE) throws  SQLException {
		assert (FIELD != null) : "field must never be null";
		assert (VALUE != null) : "value must never be null";
		assert (FIELD != "") : "field must never be empty";
		assert (VALUE != "") : "value must never be null";

		final Institution beanType = new Institution();
		ArrayList<Institution> listInstitutions = new ArrayList<Institution>();
		final GenericBeanDAO gDB = new GenericBeanDAO();

		for(Bean institutionBean : gDB.selectBeanWhere(beanType, FIELD, VALUE, LIKE,"acronym")) {
			listInstitutions.add((Institution) institutionBean);
		}

		return listInstitutions;
	}

	// Get and returns a list of institutions filtered by a given type of search.
	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(final Search SEARCH)
			throws  SQLException {

		assert (SEARCH != null) : "search must never be null";
		assert (SEARCH.getIndicator() != null) : "search's indicator must never be null";
		assert (SEARCH.getYear() > 1990) : "search's year must never be smaller than 1990";

		ArrayList<Institution> listInstitutions = new ArrayList<Institution>();
		String sql = "SELECT i.* FROM institution AS i, evaluation AS e, articles AS a," +
				     " books AS b WHERE year=" + Integer.toString(SEARCH.getYear()) +
					 " AND e.id_institution = i._id" +
                     " AND e.id_articles = a._id" +
					 " AND e.id_books = b._id" +
                     " AND " + SEARCH.getIndicator().getValue();

		if(SEARCH.getMaxValue() == Search.SEARCH_MAXIMUM_VALUE) {
			sql += " >= " + Integer.toString(SEARCH.getMinValue());
		} else {
			sql += " BETWEEN " + Integer.toString(SEARCH.getMinValue()) +
			       " AND " + Integer.toString(SEARCH.getMaxValue());
		}
		sql += " GROUP BY i._id";

		final GenericBeanDAO gDB = new GenericBeanDAO();
        final ArrayList<Bean> runSql = gDB.runSql(new Institution(), sql);

		for(Bean institutionBean : runSql) {
			listInstitutions.add((Institution) institutionBean);
		}

		return listInstitutions;
	}

	// Get and returns a list of institutions filtered by a given type of search and its ID.
	public static ArrayList<Course> getCoursesByEvaluationFilter(final int id_institution, final Search search) throws  SQLException {

		assert(search.getYear() > 1990) : "search's year must never be smaller than 1990";
		assert (search != null) : "search must never be null";
		assert (search.getIndicator() != null) : "search's indicator must never be null";
		assert (search.getYear() > 1990) : "search's year must never be smaller than 1990";

		ArrayList<Course> listCourses = new ArrayList<Course>();
		String sql = "SELECT c.* FROM course AS c, evaluation AS e, articles AS a, books AS b " +
					 " WHERE e.id_institution=" + id_institution +
                     " AND e.id_course = c._id" +
					 " AND e.id_articles = a._id" +
                     " AND e.id_books = b._id" +
					 " AND year="+search.getYear() +
                     " AND "+search.getIndicator().getValue();
		
		if(search.getMaxValue() == Search.SEARCH_MAXIMUM_VALUE) {
			sql += " >= " + search.getMinValue();
		} else {
			sql += " BETWEEN " + search.getMinValue() + " AND " + search.getMaxValue();
		}

		sql += " GROUP BY c._id";

		final GenericBeanDAO gDB = new GenericBeanDAO();
        final ArrayList<Bean> runSql = gDB.runSql(new Course(), sql);

		for(Bean b : runSql) {
			listCourses.add((Course) b);
		}

		return listCourses;
	}

	// Delete this institution entity with its data in the database.
	public boolean delete() throws SQLException {
		boolean deletedInstitution = false;

		final GenericBeanDAO gDB = new GenericBeanDAO();

		for(Course course : this.getCourses()){
			gDB.deleteBeanRelationship(this,course);
		}

		deletedInstitution = gDB.deleteBean(this);

		return deletedInstitution;
	}

	// Gets and returns the data according to the specified field.
	@Override
	public String get(final String field) {
		assert (field != null) : "field must never be null";

        String fieldData = "";

		if(field.equals("_id")) {
            fieldData =  Integer.toString(this.getId());

		} else if(field.equals("acronym")) {
            fieldData = this.getAcronym();

		} else {
			/* Nothing to do! */
		}

        return fieldData;
	}

	// Sets the the specified data to the specified field in the institution entity.
	@Override
	public void set(final String field, final String data) {
		assert (field != null) : "field must never be null";
		assert (data != null) : "data must never be null";

		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));

		} else if(field.equals("acronym")) {
			this.setAcronym(data);

		} else {
		    /* Nothing to do. */
        }

	}

	// Return the list of strings corresponding to attributes of the institituton class.
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();

		fields.add(InstitutionFields._id.toString());
		fields.add(InstitutionFields.acronym.toString());

		return fields;
	}

	// Returns the acronym attribute of the institution entity.
	@Override
	public String toString() {
		return getAcronym();
	}

	// Instintution contructor method that instatiate the entity from an input archive.
	private Institution(final Parcel IN){
		assert (IN != null) : "in must never be null";

		this.id = IN.readInt();
		this.acronym = IN.readString();
		this.identifier = IN.readString();
		this.relationship = IN.readString();
	}

	// Returns 0, doing nothing.
	@Override
	public int describeContents() {
		return 0;
	}

	// Method that writes data to specified parcel.
	@Override
	public void writeToParcel(final Parcel DEST, final int FLAGS) {
		assert (DEST != null) : "dest must never be null";

		DEST.writeInt(this.id);
		DEST.writeString(this.acronym);
		DEST.writeString(this.identifier);
		DEST.writeString(this.relationship);
		
	}
	
	public static final Parcelable.Creator<Institution> CREATOR = new Parcelable
            .Creator<Institution>() {

		@Override
		public Institution createFromParcel(Parcel source) {
			assert (source != null)  : "source must never be null";

            Institution institution = new Institution(source);

			return institution;
		}

		@Override
		public Institution[] newArray(int size) {

            Institution[] institution = new Institution[size];

			return institution;
		}
	};

}
