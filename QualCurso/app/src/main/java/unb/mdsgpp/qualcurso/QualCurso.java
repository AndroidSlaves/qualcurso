/*****************************
 * Class name: QualCurso (.java)
 *
 * Purpose: Basic application that supports information about database and fragment connection.
 ****************************/

package unb.mdsgpp.qualcurso;

import android.app.Application;
import android.content.Context;


public class QualCurso extends Application {
	
	private static QualCurso instance;
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
     * gets the database name store in this application.
     *
     * @return
     *              the database name for QualCurso.
     */
	public String getDatabaseName(){
		return databaseName;
	}

    /**
     * gets a running instance of QualCurso class.
     *
     * @return
     *              an instance of QualCurso class.
     */
	public static QualCurso getInstance(){
		return instance;
	}
}
