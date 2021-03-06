/*****************************
 * Class name: Article (.java)
 *
 * Purpose: Article is a Class that represents information about Articles, like, number of
 * published journals and conferences publications.
 *****************************/

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

    /**
     * Get the id of an Article. The id is unique for each Article.
     *
     * @return int
     */
	public int getId() {
		assert (id >= 0) : "id must be positive";
		return id;
	}

    /**
     * Set the id of Article. This id will be used in Database as unique number.
     * @param id
     */
	public void setId(int id) {
		assert (id >= 0) : "id must be positive";

		this.id = id;
	}

    /**
     * Get the quantity of published journals by Article.
     *
     * @return int
     *              Number of publications in journals.
     */
	public int getPublishedJournals() {
		assert (publishedJournals >= 0) : "publishedJournals must be positive";
		return publishedJournals;
	}

    /**
     * Set the number of published journals.
     *
     * @param publishedJournals
     */
	public void setPublishedJournals(int publishedJournals) {
		assert (publishedJournals >= 0) : "publishedJournals must be positive";

		this.publishedJournals = publishedJournals;
	}

    /**
     * Get the number of published conference journals to show on screen.
     *
     * @return int
     */
	public int getPublishedConferenceProceedings() {
		assert (publishedConferenceProceedings >= 0) : "publishedConferenceProceedings " +
				"must be positive";
		return publishedConferenceProceedings;
	}

    /**
     * Set the number (quantity) of published conference journals.
     *
     * @param publishedConferenceProceedings
     */
	public void setPublishedConferenceProceedings(int publishedConferenceProceedings) {
		assert (publishedConferenceProceedings >= 0) : "publishedConferenceProceedings " +
				"must be positive";

		this.publishedConferenceProceedings = publishedConferenceProceedings;
	}

    /**
     * Save an unique ID on Database, and verify if save was successful.
     *
     * @throws SQLException
     *
     * @return boolean
     */
	public boolean save() throws SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		result = gDB.insertBean(this);
		this.setId(Article.last().getId());

        return result;
	}

    /**
     * Get an Article previously saved on database.
     * It creates a new object article, and receives a Bean object selected by GenericBeanDAO.
     *
     * @param id:Integer
     *
     * @return Article
     *
     * @throws SQLException
     */
	public static Article get(final Integer id) throws  SQLException {
		assert (id >= 0) : "id must be positive";

		Article retrievedArticle = new Article(id);
		GenericBeanDAO gDB = new GenericBeanDAO();

        retrievedArticle = (Article) gDB.selectBean(retrievedArticle);

		assert (retrievedArticle != null):"Receive a null treatment";
        return retrievedArticle;
	}

    /**
     * Create an List of all Articles previously saved on Database.
     *
     * @return ArrayList<Article>
     *
     * @throws SQLException
     */
	public static ArrayList<Article> getAll() throws  SQLException {
		Article beanType = new Article();
		ArrayList<Article> listArticles = new ArrayList<Article>();
		GenericBeanDAO gBD = new GenericBeanDAO();
        ArrayList<Bean> listBeans = gBD.selectAllBeans(beanType, null);

		for (Bean bean : listBeans) {
			listArticles.add((Article) bean);
		}

		assert (listArticles != null) : "Receive a null treatment";
		assert (listArticles.size() > 0) : "Receive a size treatment";
		return listArticles;
	}

    /**
     * Get the number of Articles on Database.
     *
     * @return int
     *
     * @throws SQLException
     */
	public static int count() throws  SQLException {
		Article type = new Article();
		GenericBeanDAO gDB = new GenericBeanDAO();

        int countArticles = gDB.countBean(type);

		assert (countArticles > 0) : "Receive a negative tratment";
		return countArticles;
	}

    /**
     * Get first Article saved on Database.
     *
     * @return Article
     *
     * @throws SQLException
     */
	public static Article first() throws SQLException {
		Article resultFirstArticle = new Article();
		final GenericBeanDAO gDB = new GenericBeanDAO();

		resultFirstArticle = (Article) gDB.firstOrLastBean(resultFirstArticle, false);

		assert (resultFirstArticle != null) : "Receive a null treatment";
		return resultFirstArticle;
	}

    /**
     * Get last Article saved on Database.
     *
     * @return Article
     *
     * @throws SQLException
     */
	public static Article last() throws SQLException {
		Article resultLastArticle = new Article();
		final GenericBeanDAO gDB = new GenericBeanDAO();

		resultLastArticle = (Article) gDB.firstOrLastBean(resultLastArticle, true);

		assert (resultLastArticle != null) : "Receive a null treatment";
		return resultLastArticle;
	}

    /**
     * Get an Article from Database, with attributes specified by field, value
     * and the boolean that represents user like something.
     *
     * @param field:String
     *
     * @param value:String
     *
     * @param like:boolean
     *
     * @return ArrayList<Article>
     *          ArrayList com o resultado de busca
     *
     * @throws SQLException
     */
	public static ArrayList<Article> getWhere(String field, String value, boolean like) 
			throws  SQLException {
		assert (field != null) : "field must never be null.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (value != null) : "value must never be null.";
		assert (value.length() >= 1) : "value name must have at least one character.";

		Article type = new Article();
		ArrayList<Article> result = new ArrayList<Article>();
        final GenericBeanDAO gDB = new GenericBeanDAO();
        ArrayList<Bean> listBeans = gDB.selectBeanWhere(type, field, value, like, null);

		for(Bean bean : listBeans) {
			result.add((Article) bean);
		}

		return result;
	}

    /**
     * Delete an Article from Database.
     *
     * @return boolean
     *          Results confirm the deletion of the article in the database.
     *
     * @throws SQLException
     */
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.deleteBean(this);
		return result;
	}

    /**
     * Returns corresponding number the field parameter.
	 *
     * @param field
	 *
     * @return String
     */
	@Override
	public String get(String field) {
		assert (field != null) : "field must never be null.";
		assert (field.length() >= 1) : "field name must have at least one character.";

        String contentInField = "";

        if(field.equals("_id")) {
			contentInField = Integer.toString(this.getId());
		} else if(field.equals("published_journals")) {
			contentInField = Integer.toString(this.getPublishedJournals());
		} else if(field.equals("published_conference_proceedings")) {
			contentInField =Integer.toString(this.getPublishedConferenceProceedings());
		} else {
		    /*Nothing to do.*/
        }
		assert (contentInField != null) : "Receive a null treatment";
	    return contentInField;
    }

    /**
     * Stores the number requested by parameter.
     *
     * @param field
	 * 				Object field
     * @param data
	 * 				Object data
     */
	@Override
	public void set(String field, String data) {
		assert (field != null) : "field must never be null.";
		assert (field.length() >= 1) : "field name must have at least one character.";
		assert (data != null) : "data must never be null.";
		assert (data.length() >= 1) : "data value must have at least one character.";

		if(field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} else if(field.equals("published_journals")) {
			this.setPublishedJournals(Integer.parseInt(data));
		} else if(field.equals("published_conference_proceedings")) {
			this.setPublishedConferenceProceedings(Integer.parseInt(data));
		} else {/*Nothing to do.*/}
	}

    /**
     * Implements Bean method "fieldsList" to be used on GenericBeanDAO.
     *
     * @return ArrayList<String>
     */
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();

		fields.add("_id");
		fields.add("published_journals");
		fields.add("published_conference_proceedings");

		assert (fields != null) : "Receive a null treatment";
        return fields;
	}
}
