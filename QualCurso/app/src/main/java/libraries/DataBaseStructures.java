/************************
 * Class name: DataBaseStructures (.java)
 *
 * Purpose: This class is responsible for making all the operations with the database in regard of
 *              the course information.
 ***********************/

package libraries;

import android.database.SQLException;

public class DataBaseStructures extends DataBase {

    /**
     * Empty constructor
     */
    public DataBaseStructures() throws SQLException {
        super();
    }

    /**
     * Opens the SQL connection and creates all the other tables by calling all the build
     * functions in this class.
     *
     * @throws SQLException
     */
    public void initDB() throws SQLException {
            this.openConnection();

            final String createTableSQL = "CREATE TABLE IF NOT EXISTS 'android_metadata' (locale TEXT)";
            final String insertValuesSQL = "INSERT INTO android_metadata VALUES ('pt_BR')";

            this.database.execSQL(createTableSQL);
            this.database.execSQL(insertValuesSQL);

            this.buildTableArticles();
            this.buildTableEvaluation();
            this.buildTableCourse();
            this.buildTableCoursesInstitutions();
            this.buildTableInstitution();
            this.buildTableBooks();
            this.buildTableSearch();

            this.closeConnection();
    }

    /**
     * Destroy all the tables and close database connection with SQL.
     *
     * @throws SQLException
     */
    public void dropDB() throws SQLException {
    	this.openConnection();

        final String dropTableSQL = "DROP TABLE IF EXISTS ";

        this.database.execSQL(dropTableSQL + "course");
        this.database.execSQL(dropTableSQL + "institution");
        this.database.execSQL(dropTableSQL + "courses_institutions");
        this.database.execSQL(dropTableSQL + "articles");
        this.database.execSQL(dropTableSQL + "books");
        this.database.execSQL(dropTableSQL + "evaluation");
        this.database.execSQL(dropTableSQL + "android_metadata");
        this.database.execSQL(dropTableSQL + "search");

        this.closeConnection();
    }

    /**
     * Creates database table for the courses and its attributes.
     *
     * @throws SQLException
     */
    private void buildTableCourse() throws SQLException {

        String sql_createCourse = "CREATE TABLE IF NOT EXISTS 'course' ('_id' INTEGER PRIMARY KEY "
                                  + "AUTOINCREMENT,'name' TEXT NOT NULL)";

        this.database.execSQL(sql_createCourse);
    }

    /**
     * Creates database table for the institution and its attributes.
     *
     * @throws SQLException
     */
    private void buildTableInstitution() throws SQLException {
    	String sql_createInstitution = "CREATE TABLE IF NOT EXISTS 'institution' ('_id' INTEGER " +
                                       "PRIMARY KEY AUTOINCREMENT,'acronym' TEXT NOT NULL)";

    	this.database.execSQL(sql_createInstitution);
    }

    /**
     * Creates database table that relates institution with its courses.
     *
     * FIXME add foreign key support to database. E.g.:
     * FOREIGN KEY(id_instituicao) REFERENCES institution(id)
     *
     * @throws SQLException
     */
    private void buildTableCoursesInstitutions() throws SQLException {
    	String sql_createCoursesInstitutions = "CREATE TABLE IF NOT EXISTS 'courses_institutions' "
                                               + "('id_institution' INTEGER NOT NULL," +
                                               "'id_course' INTEGER NOT NULL)";

    	this.database.execSQL(sql_createCoursesInstitutions);
    }

    /**
     * Creates database table for the articles and its attributes.
     *
     * @throws SQLException
     */
    private void buildTableArticles() throws SQLException {
    	String sql_buildArticles = "CREATE TABLE IF NOT EXISTS 'articles' ('_id' INTEGER PRIMARY " +
                                   "KEY AUTOINCREMENT,'published_journals' INTEGER," +
                                   "'published_conference_proceedings'" + " INTEGER)";

    	this.database.execSQL(sql_buildArticles);
    }

    /**
     * Creates database table for the books and its attributes.
     *
     * @throws SQLException
     */
    private void buildTableBooks() throws SQLException {
    	String sql_buildBooks = "CREATE TABLE IF NOT EXISTS 'books' ('_id' INTEGER PRIMARY KEY " +
                                "AUTOINCREMENT,'integral_text' INTEGER,'chapters' INTEGER," +
                                "'collections' INTEGER," + "'entries' INTEGER)";

    	this.database.execSQL(sql_buildBooks);
    }

    /**
     * Creates database table for the evaluations and its attributes.
     *
     * FIXME add foreign key support to database. E.g.:
     * FOREIGN KEY(id_artigos) REFERENCES articles(id)
     *
     * @throws SQLException
     */
    private void buildTableEvaluation() throws SQLException {
    	String sql_buildEvaluation = "CREATE TABLE IF NOT EXISTS 'evaluation' (" +
    		                         "'_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
    		                         "'id_institution' INTEGER NOT NULL," +
    		                         "'id_course' INTEGER NOT NULL," +
    		                         "'year' INTEGER NOT NULL," +
    		                         "'modality' TEXT NOT NULL," +
    		                         "'master_degree_start_year' INTEGER," +
    		                         "'doctorate_start_year' INTEGER," +
    		                         "'triennial_evaluation' INTEGER NOT NULL," +
    		                         "'permanent_teachers' INTEGER," +
    		                         "'theses' INTEGER," +
    		                         "'dissertations' INTEGER," +
    		                         "'id_articles' INTEGER NOT NULL," +
    		                         "'id_books' INTEGER," +
    		                         "'artistic_production' INTEGER)";

    	this.database.execSQL(sql_buildEvaluation);
    }

    /**
     * Creates database table for the records of the searches.
     *
     * @throws SQLException
     */
    private void buildTableSearch() throws SQLException {
    	String sql_buildSearch = "CREATE TABLE IF NOT EXISTS 'search' (" +
    		                     "'_id' INTEGER PRIMARY KEY AUTOINCREMENT," + "'date' DATETIME," +
                                 "'year' INTEGER," + "'option' INTEGER," + "'indicator' TEXT," +
    			                 "'min_value' INTEGER," + "'max_value' INTEGER)";

    	this.database.execSQL(sql_buildSearch);
    }
}
