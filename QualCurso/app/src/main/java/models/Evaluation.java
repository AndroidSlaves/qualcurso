/*****************************
 * Class name: Evaluation (.java)
 *
 * Purpose: Class that represents a single generic evaluation, which holds all the data necessary.
 *****************************/

package models;

import android.database.SQLException;

import java.util.ArrayList;

public class Evaluation extends Bean {

	private int id;
	private int idInstitution;
	private int idCourse;
	private int year;
	private String modality;
	private int masterDegreeStartYear;
	private int doctorateStartYear;
	private int triennialEvaluation;
	private int permanentTeachers;
	private int theses;
	private int dissertations;
	private int idArticles;
	private int idBooks;
	private int artisticProduction;

	public Evaluation() {
		this.id = 0;
		this.identifier = "evaluation";
		this.relationship = "";
	}

	public Evaluation(int id) {
		assert (id >= 0) : "id must never be negative";

		this.id = id;
		this.identifier = "evaluation";
		this.relationship = "";
	}

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
		} else{/* Nothing to do. */}
		

	}

	@Override
	public ArrayList<String> fieldsList() {
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
	
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Evaluation.last().getId());
		return result;
	}

	public static Evaluation get(int id) throws  SQLException {
		assert (id >=0) : "id must never be negative";

		Evaluation result = new Evaluation(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Evaluation> getAll()
			throws  SQLException {
		Evaluation type = new Evaluation();
		ArrayList<Evaluation> result = new ArrayList<Evaluation>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Evaluation) b);
		}
		return result;
	}

	public static int count() throws  SQLException {
		Evaluation type = new Evaluation();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Evaluation first() throws 
			SQLException {
		Evaluation result = new Evaluation();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Evaluation last() throws 
			SQLException {
		Evaluation result = new Evaluation();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Evaluation) gDB.firstOrLastBean(result, true);
		return result;
	}

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
	
	public static Evaluation getFromRelation(int idInstitution, int idCourse, int year){
		assert (idInstitution >= 0) : "idInstitution must never be negative";
		assert (idCourse >= 0) : "idCourse must never be negative";;
		assert (year > 1990) : "year must never be smaller than 1990";

		Evaluation result = new Evaluation();

		result.setIdInstitution(idInstitution);
		result.setIdCourse(idCourse);
		result.setYear(year);

		ArrayList<String> simplefields = new ArrayList<String>();
		ArrayList<String> fields = new ArrayList<String>();

		fields.add("id_institution");
		fields.add("id_course");
		fields.add("year");

		simplefields.add("id_institution");
		simplefields.add("id_course");

		GenericBeanDAO gDB = new GenericBeanDAO();
		ArrayList<Bean> restricted = gDB.selectFromFields(result,fields,null);
		if(restricted.size() != 0){
			result = (Evaluation)restricted.get(0);
		}else{
			ArrayList<Bean> beans = gDB.selectFromFields(result, simplefields,null);
			result = (Evaluation)beans.get(beans.size()-1);
		}
		return result;
	}
	
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.deleteBean(this);
		return result;
	}

	public int getId() {
        return id;
	}

	public void setId(int id) {
		assert (id >= 0) : "id must never be negative";
		this.id = id;
	}

	public int getIdInstitution() {
        return idInstitution;
	}

	public void setIdInstitution(int idInstitution) {
		assert (idInstitution >= 0) : "idInstitution must never be negative";
		this.idInstitution = idInstitution;
	}

	public int getIdCourse() {
        return idCourse;
	}

	public void setIdCourse(int idCourse) {
		assert (idCourse >= 0) : "idCourse must never be negative";
		this.idCourse = idCourse;
	}

	public int getYear() {
        return year;
	}

	public void setYear(int year) {
		assert (year > 2000) : "year must never be smaller than 1990";
		this.year = year;
	}

	public String getModality() {
        return modality;
	}

	public void setModality(String modality) {
		assert (modality != null) : "modality must never be null";
		this.modality = modality;
	}

	public int getMasterDegreeStartYear() {
        return masterDegreeStartYear;
	}

	public void setMasterDegreeStartYear(int masterDegreeStartYear) {
		assert (masterDegreeStartYear > 1900) : "masterDegreeStartYear must never be "
		                                       +"bigger than 1990";

		this.masterDegreeStartYear = masterDegreeStartYear;
	}

	public int getDoctorateStartYear() {
        return doctorateStartYear;
	}

	public void setDoctorateStartYear(int doctorateStartYear) {
		assert (doctorateStartYear > 1900) : "masterDegreeStartYear must never be "
		                                       +"bigger than 1990";
		
		this.doctorateStartYear = doctorateStartYear;
	}

	public int getTriennialEvaluation() {
        return triennialEvaluation;
	}

	public void setTriennialEvaluation(int triennialEvaluation) {
		assert (triennialEvaluation >= 0) : "triennialEvaluation must never be negative";

		this.triennialEvaluation = triennialEvaluation;
	}

	public int getPermanentTeachers() {
        return permanentTeachers;
	}

	public void setPermanentTeachers(int permanentTeachers) {
		assert (permanentTeachers >= 0) : "permanentTeachers must never be negative";

		this.permanentTeachers = permanentTeachers;
	}

	public int getTheses() {
        return theses;
	}

	public void setTheses(int theses) {
		assert (theses >= 0) : "theses must never be negative";
		
		this.theses = theses;
	}

	public int getDissertations() {
        return dissertations;
	}

	public void setDissertations(int dissertations) {
		assert (dissertations >= 0) : "dissertations must never be negative";
		
		this.dissertations = dissertations;
	}

	public int getIdArticles() {
        return idArticles;
	}

	public void setIdArticles(int idArticles) {
		assert (idArticles >= 0) : "idArticles must never be negative";
		
		this.idArticles = idArticles;
	}

	public int getIdBooks() {
        return idBooks;
	}

	public void setIdBooks(int idBooks) {
		assert (idBooks >= 0) : "idBooks must never be negative";
		
		this.idBooks = idBooks;
	}

	public int getArtisticProduction() {
        return artisticProduction;
	}

	public void setArtisticProduction(int artisticProduction) {
		assert (artisticProduction >= 0) : "artisticProduction must never be negative";

		this.artisticProduction = artisticProduction;
	}

}
