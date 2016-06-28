/*****************************
 * Class name: QualCurso (.java)
 *
 * Purpose: Basic application that supports information about database and fragment connection.
 ****************************/

package unb.mdsgpp.qualcurso;

import android.app.Application;

public class QualCurso extends Application {

	// Unique instance of the Qualcurso application. There cannot be more than one instance.
	private static QualCurso instance;
	// Database name is defined so there can be a connection with the database.
	private static String databaseName;

    /**
     * Default constructor. Sets database name.
     */
	public QualCurso(){
		databaseName = "database.sqlite3.db";
		instance = this;
	}

    /**
     * Sets the database name if it needs change.
     *
     * @param databaseName
     *              database name that will be changed to.
     */
	public void setDatabaseName(String databaseName){
		assert (databaseName != null) : "Receive a null tratment";

		this.databaseName = databaseName;
	}

    /**
     * Gets the database name store in this application.
     *
     * @return
     *              the database name for QualCurso.
     */
	public String getDatabaseName() {
		return databaseName;
	}

    /**
     * Gets a running instance of QualCurso class.
     *
     * @return
     *              an instance of QualCurso class.
     */
	public static QualCurso getInstance() {
		return instance;
	}
}
