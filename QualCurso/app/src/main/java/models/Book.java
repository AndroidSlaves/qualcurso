/*****************************
 * Class name: Book (.java)
 *
 * Purpose: Modeling characteristics from book and use methods that interacts with the database
 ****************************/

package models;

import android.database.SQLException;
import java.util.ArrayList;

public class Book extends Bean {
	private int id;
	private int integralText;
	private int chapters;
	private int collections;
	private int entries;

	/**
	 * Empty constructor for the Book.
	 */
	public Book() {
		this.id = 0;
		this.identifier = "books";
		this.relationship = "";
	}

	/**
	 * Construct the DrugstoreController with defined id.
	 *
	 * @param id
	 *              Identification number of the book that will be set at initialization.
	 */
	public Book(int id) {
		assert (id >= 0) : "Receive a negative treatment";
		this.id = id;
		this.identifier = "books";
		this.relationship = "";
	}

	/**
	 * Int type function. Used to access return identifier value..
	 *
	 * @return
     *              the book unique identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Void type. Used to access the variable id and store a value there.
	 *
	 * @param id
	 *              identification number of the book.
	 */
	public void setId(int id) {
		assert (id >= 0) : "Receive a negative treatment";
		this.id = id;
	}

	/**
	 * String type. Used to return the text with the name of the book.
	 *
	 * @return
     *              the book's name.
	 */
	public int getIntegralText() {
		return integralText;
	}

	/**
	 * Void type. Used to access the variable integralText and store a value there.
	 *
	 * @param integralText
	 *              the text name of the book.
	 */
	public void setIntegralText(int integralText) {
		assert (integralText >= 0) : "Receive a negative treatment";
		this.integralText = integralText;
	}

	/**
	 * String type. Used to return the number of chapters in the book.
	 *
	 * @return
     *              the number of chapters in the book.
	 */
	public int getChapters() {
		return chapters;
	}

	/**
	 * Void type. Used to access the variable chapters and store a value there.
	 *
	 * @param chapters
	 *              the number of chapters of the book.
	 */
	public void setChapters(int chapters) {
		assert (chapters >= 0) : "Receive a negative treatment";
		this.chapters = chapters;
	}

	/**
	 * String type. Used to return collections value.
	 *
	 * @return
     *              the number of collections of this book.
	 */
	public int getCollections() {
		return collections;
	}

	/**
	 * Void type. Used to access the variable collections and store a value there.
	 *
	 * @param collections
	 *              the number of collections of the book.
	 */
	public void setCollections(int collections) {
		assert (collections >= 0) : "Receive a negative treatment";
		this.collections = collections;
	}

	/**
	 * String type. Used to return the number of entries of this same book.
	 *
	 * @return
     *              the number of entries of the same book.
	 */
	public int getEntries() {
		return entries;
	}

	/**
	 * Void type. Used to access the variable entries and store a value there.
	 *
	 * @param entries
	 *              the number of entries of the same book.
	 */
	public void setEntries(int entries) {
		assert (entries >= 0) : "Receive a negative treatment";
		this.entries = entries;
	}

	/**
	 * Boolean type. Saves this book in the database.
	 *
	 * @return
     *              the result of the database, indicating if the saving was successful.
	 *
	 * @throws SQLException
     *              there maybe a problem saving book in the database.
	 */
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		result = gDB.insertBean(this);
		this.setId(Book.last().getId());

		return result;
	}

	/**
	 * Book type. Used to access the database and return the book identified by the given id.
	 *
	 * @param id
	 *              unique identifier of the wanted book.
	 *
	 * @return
     *              the book identified by the id parameter.
	 *
	 * @throws SQLException
     *              there maybe a problem getting book from database.
	 */
	public static Book get(int id) throws  SQLException {
		Book result = new Book(id);
		GenericBeanDAO gDB = new GenericBeanDAO();

		result = (Book) gDB.selectBean(result);

		return result;
	}

	/**
	 * Array of Books type. Used to access the database and return all the books in the database.
	 *
	 * @return
     *              the array of book with all books in the database.
	 *
	 * @throws SQLException
     *              there maybe a problem getting books from database.
	 */
	public static ArrayList<Book> getAll() throws  SQLException {
		Book type = new Book();
		ArrayList<Book> result = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.selectAllBeans(type,null)) {
			result.add((Book) b);
		}

		return result;
	}

	/**
	 * Int type. Counts the number of books in the database.
	 *
	 * @return
     *              the number of books in the database.
	 *
	 * @throws SQLException
     *              there maybe a problem accessing database.
	 */
	public static int count() throws  SQLException {
		Book type = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();

		return gDB.countBean(type);
	}

	/**
	 * Book type. Returns the first book present in the database.
	 *
	 * @return
     *              the first book in the database.
	 *
	 * @throws SQLException
     *              there maybe a problem accessing database.
	 */
	public static Book first() throws SQLException {
		Book result = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();

		result = (Book) gDB.firstOrLastBean(result, false);

		return result;
	}

	/**
	 * Book type. Returns the last book present in the database.
	 *
	 * @return
     *              the last book in the database.
	 *
	 * @throws SQLException
     *              there maybe a problem accessing database.
	 */
	public static Book last() throws SQLException {
		Book result = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();

		result = (Book) gDB.firstOrLastBean(result, true);

		return result;
	}

    /**
     * List of books. Returns all the books that a specified field corresponds to the specified
     * string value.
     *
     * @param field
     *              which field is going to serve as criteria for search.
     *
     * @param value
     *              value to look for in the books
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
	public static ArrayList<Book> getWhere(String field, String value, boolean like) throws  SQLException {
		assert (field != null) : "Receive a null treatment";
		assert (field != "") : "Receive a empty treatment";
		assert (value != null) : "Receive a null treatment";
		assert (value != "") : "Receive a null treatment";

		Book type = new Book();
		ArrayList<Book> result = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.selectBeanWhere(type, field, value, like, null)) {
			result.add((Book) b);
		}

		return result;
	}

    /**
     * Int type. Delete this book.
     *
     * @return
     *              the result returned from the database, if it was successfully deleted.
     *
     * @throws SQLException
     *              there maybe a problem accessing database.
     */
	public boolean delete() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		result = gDB.deleteBean(this);

		return result;
	}

    /**
     * String type. Gets the value inside the attribute with the given name in field.
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

		if(field.equals("_id")) {
			selectedField = Integer.toString(this.getId());
		} else if(field.equals("integral_text")) {
			selectedField = Integer.toString(this.getIntegralText());
		} else if (field.equals("chapters")) {
			selectedField = Integer.toString(this.getChapters());
		} else if(field.equals("collections")) {
			selectedField =  Integer.toString(this.getCollections());
		} else if(field.equals("entries")) {
			selectedField = Integer.toString(this.getEntries());
		} else {
			selectedField = "";
		}

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
		assert (data != "") : "Receive a null treatment";

		if (field.equals("_id")) {
			this.setId(Integer.parseInt(data));
		} else if (field.equals("integral_text")) {
			this.setIntegralText(Integer.parseInt(data));
		} else if (field.equals("chapters")) {
			this.setChapters(Integer.parseInt(data));
		} else if (field.equals("collections")) {
			this.setCollections(Integer.parseInt(data));
		} else if (field.equals("entries")) {
			this.setEntries(Integer.parseInt(data));
		} else {
			/* Nothing to do! */
		}

	}

    /**
     * Method that returns all the fields of a book.
     *
     * @return
     *              the list of fields.
     */
	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();

		addFields(fields);

		return fields;
	}

	private ArrayList<String> addFields (ArrayList<String> fields){
		fields.add("_id");
		fields.add("integral_text");
		fields.add("chapters");
		fields.add("collections");
		fields.add("entries");

		return fields;
	}
}
