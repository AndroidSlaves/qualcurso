/*****************************
 * Class name: Book (.java)
 *
 * Purpose: Modeling characteristics from book and use methods that interacts with the database.
 ****************************/

package models;

import android.database.SQLException;

import java.util.ArrayList;

public class Book extends Bean {
	// Unique identifier of the book.
	private int id = 0;
	// Name title text of the book.
	private int integralText = 0;
	// Number of chapter of the book.
	private int chapters = 0;
	// Number of collections containing this book.
	private int collections = 0;
	// Number of entries of the same book.
	private int entries = 0;
	// Constant with fields names.
	final String FIELDS_NAMES[] = {"_id", "integral_text", "chapters", "collections", "entries"};

	/**
	 * Empty constructor for the Book. Set basic default information about the book.
	 */
	public Book() {
		this.id = 0;
		this.identifier = "books";
		this.relationship = "";
	}

	/**
	 * Construct the Book with defined id.
	 *
	 * @param id
	 *              Identification number of the book that will be set at initialization.
	 */
	public Book(int id) {
		assert (id <= 0) : "Receive a negative treatment";
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
        assert (id >= 0) : "Receive a negative treatment";
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
        assert (chapters >= 0) : "Receive a negative treatment";
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
        assert (collections >= 0) : "Receive a negative treatment";
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
        assert (entries >= 0) : "Receive a negative treatment";
        return entries;
	}

	/**
	 * Void type. Used to access the variable entries and store a value there.
	 *
	 * @param entries
	 *              the number of entries of the same book.
	 */
	public void setEntries(int entries) {
		assert (entries <= 0) : "Receive a negative treatment";
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
		boolean saveResult = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		saveResult = gDB.insertBean(this);
		this.setId(Book.last().getId());

		return saveResult;
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
		Book bookById = new Book(id);
		GenericBeanDAO gDB = new GenericBeanDAO();

		bookById = (Book) gDB.selectBean(bookById);

        assert (bookById != null) : "bookById never be null";
		return bookById;
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
		ArrayList<Book> listOfAllBooks = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.selectAllBeans(type,null)) {
			listOfAllBooks.add((Book) b);
		}

        assert (listOfAllBooks != null) : "listOfAllBooks never be null";
		return listOfAllBooks;
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
        int count = gDB.countBean(type);

        assert (count >= 0):"Receive a negative treatment";
		return count;
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
		Book firstBook = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();

		firstBook = (Book) gDB.firstOrLastBean(firstBook, false);

		return firstBook;
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
		Book lastBook = new Book();
		GenericBeanDAO gDB = new GenericBeanDAO();

		lastBook = (Book) gDB.firstOrLastBean(lastBook, true);

		return lastBook;
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
		ArrayList<Book> foundBooks = new ArrayList<Book>();
		GenericBeanDAO gDB = new GenericBeanDAO();

		for (Bean b : gDB.selectBeanWhere(type, field, value, like, null)) {
			foundBooks.add((Book) b);
		}

		return foundBooks;
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
		boolean deleteResult = false;
		GenericBeanDAO gDB = new GenericBeanDAO();

		deleteResult = gDB.deleteBean(this);

		return deleteResult;
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

		if(field.equals(FIELDS_NAMES[0])) {
			selectedField = Integer.toString(this.getId());
		} else if(field.equals(FIELDS_NAMES[1])) {
			selectedField = Integer.toString(this.getIntegralText());
		} else if (field.equals(FIELDS_NAMES[2])) {
			selectedField = Integer.toString(this.getChapters());
		} else if(field.equals(FIELDS_NAMES[3])) {
			selectedField =  Integer.toString(this.getCollections());
		} else if(field.equals(FIELDS_NAMES[4])) {
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



		if (field.equals(FIELDS_NAMES[0])) {
			this.setId(Integer.parseInt(data));
		} else if (field.equals(FIELDS_NAMES[1])) {
			this.setIntegralText(Integer.parseInt(data));
		} else if (field.equals(FIELDS_NAMES[2])) {
			this.setChapters(Integer.parseInt(data));
		} else if (field.equals(FIELDS_NAMES[3])) {
			this.setCollections(Integer.parseInt(data));
		} else if (field.equals(FIELDS_NAMES[4])) {
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

		fields.add(FIELDS_NAMES[0]);
		fields.add(FIELDS_NAMES[1]);
		fields.add(FIELDS_NAMES[2]);
		fields.add(FIELDS_NAMES[3]);
		fields.add(FIELDS_NAMES[4]);

		return fields;
	}
}
