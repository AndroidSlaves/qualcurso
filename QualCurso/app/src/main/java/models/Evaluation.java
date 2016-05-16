/*****************************
 * Class name: Evaluation (.java)
 *
 * Purpose: Class that represents a single generic evaluation, which holds all the data necessary.
 *****************************/

package models;

import android.database.SQLException;

import java.util.ArrayList;

public class Evaluation extends Bean {

	//Unic identification number for the evaluation.
	private int id;

	// Unic identification number of the institution in which the evaluation is connected.
	private int idInstitution;

	// Unic identification number of the course in which the evaluation is connected.
	private int idCourse;

	// Year when the evaluation was executed.
	private int year;

	// What modality the evaluation was executed.
	private String modality;

	// When the master degree started existing in that Institution.
	private int masterDegreeStartYear;

	// When the doctor degree started existing in that institution.
	private int doctorateStartYear;

	// How many evaluations were executed in the triennial period.
	private int triennialEvaluation;

	// How many permanent teachers there is in the evaluated institution.
	private int permanentTeachers;

	// How many theses were defended in that institution.
	private int theses;

	// How many dissertations were produced and aproved in that institutions.
	private int dissertations;

	// How many articles werer produced and published from that institution.
	private int idArticles;

	// How many books were written and published in the institution.
	private int idBooks;

	// How many artistic produtions were financed and produced in that institution.
	private int artisticProduction;

	// Creates an instance of the Evaluation with its default initial values.
	public Evaluation() {
		this.id = 0;
		this.identifier = "evaluation";
		this.relationship = "";
	}

	// Creates an instance of the Evaluation with the given id and the initial values.
	public Evaluation(int id) {
		assert (id >= 0) : "id must never be negative";

		this.id = id;
		this.identifier = "evaluation";
		this.relationship = "";
	}

	/**
	 * Gets the value of the given field.
	 * @param field get the where the information was populate.
	 *
	 * @return one field occupied with one information equivalent or just empty
	 */
	@Override
	public String get(String field) {
		assert (field != null) : "field must never be null";

		if (field.equals("_id")) {
			return Integer.toString(this.getId());
		} else if (field.equals("id_institution")) {
			return Integer.toString(this.getIdInstitution());
		} else if (field.equals("id_course")) {
			return Integer.toString(this.getIdCourse());
		} else if (field.equals("year")) {
			return Integer.toString(this.getYear());
		} else if (field.equals("modality")) {
			return getModality();
		} else if (field.equals("master_degree_start_year")) {
			return Integer.toString(this.getMasterDegreeStartYear());
		} else if (field.equals("doctorate_start_year")) {
			return Integer.toString(this.getDoctorateStartYear());
		} else if (field.equals("triennial_evaluation")) {
			return Integer.toString(this.getTriennialEvaluation());
		} else if (field.equals("permanent_teachers")) {
			return Integer.toString(this.getPermanentTeachers());
		} else if (field.equals("theses")) {
			return Integer.toString(this.getTheses());
		} else if (field.equals("dissertations")) {
			return Integer.toString(this.getDissertations());
		} else if (field.equals("id_articles")) {
			return Integer.toString(this.getIdArticles());
		} else if (field.equals("id_books")) {
			return Integer.toString(this.getIdBooks());
		} else if (field.equals("artistic_production")) {
			return Integer.toString(this.getArtisticProduction());
		} else
			return "";

	}

	/**
	 * Sets a given field with a given value.
	 *
	 * @param field
	 * 		  Used to take the field that will be populate
	 * @param data
	 * 		  Used to take the data that will populate the field
	 */
	@Override
	public void set(String field, String data){
		assert (field != null) : "field must never be null";
		assert (data != null) : "data must never be null";

		if (field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} else if (field.equals("id_institution")) {
			this.setIdInstitution(Integer.parseInt(data));
		} else if (field.equals("id_course")) {
			this.setIdCourse(Integer.parseInt(data));
		} else if (field.equals("year")) {
			this.setYear(Integer.parseInt(data));
		} else if (field.equals("modality")) {
			this.setModality(data);
		} else if (field.equals("master_degree_start_year")) {
			this.setMasterDegreeStartYear(Integer.parseInt(data));
		} else if (field.equals("doctorate_start_year")) {
			this.setDoctorateStartYear(Integer.parseInt(data));
		} else if (field.equals("triennial_evaluation")) {
			this.setTriennialEvaluation(Integer.parseInt(data));
		} else if (field.equals("permanent_teachers")) {
			this.setPermanentTeachers(Integer.parseInt(data));
		} else if (field.equals("theses")) {
			this.setTheses(Integer.parseInt(data));
		} else if (field.equals("dissertations")) {
			this.setDissertations(Integer.parseInt(data));
		} else if (field.equals("id_articles")) {
			this.setIdArticles(Integer.parseInt(data));
		} else if (field.equals("id_books")) {
			this.setIdBooks(Integer.parseInt(data));
		} else if (field.equals("artistic_production")) {
			this.setArtisticProduction(Integer.parseInt(data));
		} else{
		/* Nothing to do! */
		}
		

	}

	/**
	 * @return the ArrayList with all the fields names.
	 */
	@Override
	public ArrayList<String> fieldsList() {

		// List containing all the fields strings.
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("id_institution");
		fields.add("id_course");
		fields.add("year");
		fields.add("modality");
		fields.add("master_degree_start_year");
		fields.add("doctorate_start_year");
		fields.add("triennial_evaluation");
		fields.add("permanent_teachers");
		fields.add("theses");
		fields.add("dissertations");
		fields.add("id_articles");
		fields.add("id_books");
		fields.add("artistic_production");
		return fields;
	}

	/**
	 * Calls the Bean to do the database connection and sabe the data in the class.
	 *
	 * @return True, if the database was save.
	 * @throws SQLException
	 */
	public boolean save() throws  SQLException {

		//Result to be given in the end.
		boolean result = false;

		// Instance of the GenericBeanDAO which will make the connection with the database.
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Evaluation.last().getId());
		return result;
	}

	/**
	 * Method that receives an id (unic identification number) and gets the element from the
	 * database
	 *
	 * @param id
	 *        initialize attibutes
	 *
	 * @return True, if it was stored
	 * @throws SQLException
	 */
	public static Evaluation get(int id) throws  SQLException {
		assert (id >=0) : "id must never be negative";

		// Result to be given in the end.
		Evaluation result = new Evaluation(id);

		// Instance of the GenericBeanDAO which will make the connection with the database
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.selectBean(result);
		return result;
	}

	/**
	 * @returnan ArrayList with all the evaluation objects stored in the database.
	 * @throws SQLException
	 */
	public static ArrayList<Evaluation> getAll() throws  SQLException {
		Evaluation type = new Evaluation();

		// Result to be given in the end.
		ArrayList<Evaluation> result = new ArrayList<Evaluation>();

		// Instance of the GenericBeanDAO which will make the connection with the database
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Evaluation) b);
		}
		return result;
	}

	/**
	 * Get the number of evaluation objects stored in the database.
	 *
	 * @return
	 * @throws SQLException
	 */
	public static int count() throws  SQLException {
		Evaluation type = new Evaluation();

		// Instance of the GenericBeanDAO which will make the connection with the database
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	/**
	 * Get the first evaluation object stored in the database.
	 *
	 * @return True, if the first object was stored.
	 * @throws SQLException
	 */
	public static Evaluation first() throws SQLException {

		// Result to be given in the end.
		Evaluation result = new Evaluation();

		// Instance of the GenericBeanDAO which will make the connection with the database.
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.firstOrLastBean(result, false);
		return result;
	}

	/**
	 * Get the last evaluation object stored in the database.
	 *
	 * @return True, if the last object was stored.
	 * @throws SQLException
	 */
	public static Evaluation last() throws SQLException {

		// Result to be given in the end.
		Evaluation result = new Evaluation();

		// Instance of the GenericBeanDAO which will make the connection with the database.
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.firstOrLastBean(result, true);
		return result;
	}

	/**
	 * Get where the evaluation is at the database with the field and its value.
	 *
	 * @param field
	 * 	      Get the field in the context.
	 * @param value
	 * 		  Get the data into the field.
	 * @param like
	 *
	 * @return True, when it find the field.
	 * @throws SQLException
	 */
	public static ArrayList<Evaluation> getWhere(String field, String value, boolean like) 
			throws  SQLException {
		assert (field != null) : "field must never be null";
		assert (value != null) : "value must never be null";
		assert (field != "") : "field must never be empty";

		Evaluation type = new Evaluation();
		ArrayList<Evaluation> result = new ArrayList<Evaluation>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like,null)) {
			result.add((Evaluation) b);
		}
		return result;
	}

	/**
	 * Create evaluation object and compares with the database evaluation objects.
	 *
	 * @param idInstitution
	 * 		  Take the idInstitution
	 * @param idCourse
	 * 		  Take the idCourse
	 * @param year
	 * 		  Take the year
	 *
	 * @return True, if the object was compared
	 */
	public static Evaluation getFromRelation(int idInstitution, int idCourse, int year){
		assert (idInstitution >= 0) : "idInstitution must never be negative";
		assert (idCourse >= 0) : "idCourse must never be negative";;
		assert (year > 1990) : "year must never be smaller than 1990";

		// Instance of Evaluation that will be set with the given values.
		Evaluation result = new Evaluation();

		result.setIdInstitution(idInstitution);
		result.setIdCourse(idCourse);
		result.setYear(year);

		// Arraylist that represent the fields to be compared in the most simple form.
		ArrayList<String> simplefields = new ArrayList<String>();

		// Arraylist that represent the fields to be compared.
		ArrayList<String> fields = new ArrayList<String>();

		fields.add("id_institution");
		fields.add("id_course");
		fields.add("year");

		simplefields.add("id_institution");
		simplefields.add("id_course");

		GenericBeanDAO gDB = new GenericBeanDAO();

		// Received bean entity from the database.
		ArrayList<Bean> restricted = gDB.selectFromFields(result,fields,null);
		if(restricted.size() != 0){
			result = (Evaluation)restricted.get(0);
		}else{
			ArrayList<Bean> beans = gDB.selectFromFields(result, simplefields,null);
			result = (Evaluation)beans.get(beans.size()-1);
		}
		return result;
	}

	/**
	 * Method to delete a evaluation object from the database.
	 *
	 * @return True, if the data was deleted.
	 * @throws SQLException
	 */
	public boolean delete() throws  SQLException {

		// Result to be given in the end.
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		// Instance of the GenericBeanDAO which will make the connection with the database
		result = gDB.deleteBean(this);
		return result;
	}

	/**
	 * Get the unic identification number of the evaluation object.
	 *
	 * @return the unic identification number of the evaluation object.
	 */
	public int getId() {
        return id;
	}

	/**
	 * Set a new unic identification number of the evaluation object.
	 *
	 * @param id
	 * 		  take id attribute.
	 */
	public void setId(int id) {
		assert (id >= 0) : "id must never be negative";
		this.id = id;
	}

	/**
	 * @return the institution identification number found in the evaluation object.
	 */
	public int getIdInstitution() {
        return idInstitution;
	}

	/**
	 * @param idInstitution
	 * 		  take the id of the institution.
	 */
	public void setIdInstitution(int idInstitution) {
		assert (idInstitution >= 0) : "idInstitution must never be negative";
		this.idInstitution = idInstitution;
	}

	/**
	 * @return course ID present in evaluation object.
	 */
	public int getIdCourse() {
        return idCourse;
	}

	/**
	 * @param idCourse
	 * 		  Set the course ID present in the evaluation object.
	 */
	public void setIdCourse(int idCourse) {
		assert (idCourse >= 0) : "idCourse must never be negative";
		this.idCourse = idCourse;
	}

	/**
	 * @return the Year when the evaluation was executed.
	 */
	public int getYear() {
        return year;
	}

	/**
	 * @param year
	 * 		  Set the Year of the that evaluation.
	 */
	public void setYear(int year) {
		assert (year > 2000) : "year must never be smaller than 1990";
		this.year = year;
	}

	/**
	 * @return the modality of the executed evaluation.
	 */
	public String getModality() {
        return modality;
	}

	/**
	 *
	 * @param modality
	 *        The modality of the executed evaluation.
	 */
	public void setModality(String modality) {
		assert (modality != null) : "modality must never be null";
		this.modality = modality;
	}

	/**
	 * @return the year when the master degree started existing in that institution.
	 */
	public int getMasterDegreeStartYear() {
        return masterDegreeStartYear;
	}

	/**
	 * @param masterDegreeStartYear
	 *        Set the year when the master degree started existing in that institution.
	 */
	public void setMasterDegreeStartYear(int masterDegreeStartYear) {
		assert (masterDegreeStartYear > 1900) : "masterDegreeStartYear must never be "
		                                       +"bigger than 1990";

		this.masterDegreeStartYear = masterDegreeStartYear;
	}

	/**
	 * @return the year when the doctorate degree started existing in that institution.
	 */
	public int getDoctorateStartYear() {
        return doctorateStartYear;
	}

	/**
	 * @param doctorateStartYear
	 *        Set the year when the doctorate degree started existing in that institution.
	 */
	public void setDoctorateStartYear(int doctorateStartYear) {
		assert (doctorateStartYear > 1900) : "masterDegreeStartYear must never be "
		                                       +"bigger than 1990";
		
		this.doctorateStartYear = doctorateStartYear;
	}

	/**
	 * @return the number of evaluations in the triennial period.
	 */
	public int getTriennialEvaluation() {
        return triennialEvaluation;
	}

	/**
	 * @param triennialEvaluation
	 *        Set the number of evaluations in the triennial period.
	 */
	public void setTriennialEvaluation(int triennialEvaluation) {
		assert (triennialEvaluation >= 0) : "triennialEvaluation must never be negative";

		this.triennialEvaluation = triennialEvaluation;
	}

	/**
	 * @return the number of permanent teachers in the institution.
	 */
	public int getPermanentTeachers() {
        return permanentTeachers;
	}

	/**
	 * @param permanentTeachers
	 *        Set and returns the number of permanent teachers in the institution.
	 */
	public void setPermanentTeachers(int permanentTeachers) {
		assert (permanentTeachers >= 0) : "permanentTeachers must never be negative";

		this.permanentTeachers = permanentTeachers;
	}

	/**
	 * @return the number of theses produced in the institution.
	 */
	public int getTheses() {
        return theses;
	}

	/**
	 * @param theses
	 *        Set the number of theses produced in the institution.
	 */
	public void setTheses(int theses) {
		assert (theses >= 0) : "theses must never be negative";
		
		this.theses = theses;
	}

	/**
	 * @return the number of dissertations produced in the institution.
	 */
	public int getDissertations() {
        return dissertations;
	}

	/**
	 * @param dissertations
	 *        Set the number of dissertations produced in the institution.
	 */
	public void setDissertations(int dissertations) {
		assert (dissertations >= 0) : "dissertations must never be negative";
		
		this.dissertations = dissertations;
	}

	/**
	 * @return the number of articles published by members of the institution.
	 */
	public int getIdArticles() {
        return idArticles;
	}

	/**
	 * @param idArticles
	 *        Set the number of articles published by members of the institution.
	 */
	public void setIdArticles(int idArticles) {
		assert (idArticles >= 0) : "idArticles must never be negative";
		
		this.idArticles = idArticles;
	}

	/**
	 * @return the number of books published by the university
	 */
	public int getIdBooks() {
        return idBooks;
	}

	/**
	 * @param idBooks
	 *        Set the number of books published by the academic institution.
	 */
	public void setIdBooks(int idBooks) {
		assert (idBooks >= 0) : "idBooks must never be negative";
		
		this.idBooks = idBooks;
	}

	/**
	 * @return the number of artistic prodution financed and produced in the institution.
	 */
	public int getArtisticProduction() {
        return artisticProduction;
	}

	/**
	 * @param artisticProduction
	 * 		  Set the number of artistic prodution financed and produced in the institution.
	 */
	public void setArtisticProduction(int artisticProduction) {
		assert (artisticProduction >= 0) : "artisticProduction must never be negative";

		this.artisticProduction = artisticProduction;
	}

}
