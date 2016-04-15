package models;

import android.database.SQLException;
import java.util.ArrayList;

public class Article extends Bean {
	private int id;
	private int publishedJournals;
	private int publishedConferenceProceedings;

	public Article() {
		this.id = 0;
		this.identifier = "articles";
		this.relationship = "";
	}

	public Article(Integer id) {
		assert (id >= 0) : "id must be positive";

		this.id = id;
		this.identifier = "articles";
		this.relationship = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		assert (id >= 0) : "id must be positive";

		this.id = id;
	}

	public int getPublishedJournals() {
		return publishedJournals;
	}

	public void setPublishedJournals(int publishedJournals) {
		assert (publishedJournals >= 0) : "publishedJournals must be positive";

		this.publishedJournals = publishedJournals;
	}

	public int getPublishedConferenceProceedings() {
		return publishedConferenceProceedings;
	}

	public void setPublishedConferenceProceedings(int publishedConferenceProceedings) {
		assert (publishedConferenceProceedings >= 0) : "publishedConferenceProceedings " +
				"must be positive";

		this.publishedConferenceProceedings = publishedConferenceProceedings;
	}


	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Article.last().getId());
		return result;
	}

	public static Article get(Integer id) throws  SQLException {
		assert (id >= 0) : "id must be positive";

		Article result = new Article(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Article) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Article> getAll() throws  SQLException {
		Article type = new Article();
		ArrayList<Article> result = new ArrayList<Article>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Article) b);
		}
		return result;
	}

	public static int count() throws  SQLException {
		Article type = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Article first() throws 
			SQLException {
		Article result = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Article) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Article last() throws 
			SQLException {
		Article result = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Article) gDB.firstOrLastBean(result, true);
		return result;
	}

	public static ArrayList<Article> getWhere(String field, String value, boolean like) 
			throws  SQLException {
		assert (field != null) : "field must never be null.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (value != null) : "value must never be null.";
		assert (value.length() >= 1) : "value name must have at least one character.";

		Article type = new Article();
		ArrayList<Article> result = new ArrayList<Article>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like, null)) {
			result.add((Article) b);
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
	public String get(String field) {
		assert (field != null) : "field must never be null.";
		assert (field.length() >= 1) : "field name must have at least one character.";

		if(field.equals("_id")) {
			return Integer.toString(this.getId());
		}
		
		else if(field.equals("published_journals")) {
			return Integer.toString(this.getPublishedJournals());
		}
		
		else if (field.equals("published_conference_proceedings")) {
			return Integer.toString(this.getPublishedConferenceProceedings());
		}
		
		else {
			return "";
		}
	}

	@Override
	public void set(String field, String data) {
		assert (field != null) : "field must never be null.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (data != null) : "data must never be null.";
		assert (data.length() >= 1) : "data value must have at least one character.";

		if (field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} 
		
		else if (field.equals("published_journals")) {
			this.setPublishedJournals(Integer.parseInt(data));
		}
		
		else if (field.equals("published_conference_proceedings")) {
			this.setPublishedConferenceProceedings(Integer.parseInt(data));
		}
		
		else {

		}
		
	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("published_journals");
		fields.add("published_conference_proceedings");
		return fields;
	}
}
