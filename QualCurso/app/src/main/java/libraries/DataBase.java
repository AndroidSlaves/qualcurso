/*****************************
 * Class name: DataBase (.java)
 *
 * Purpose: Basic module for database connection. It`s only function is to get the readable database.
 ****************************/

package libraries;

import unb.mdsgpp.qualcurso.QualCurso;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBase extends SQLiteAssetHelper{

	// Name of the database. Useful for the connection.
	private static final String DATABASE_NAME = "database.sqlite3.db";
	// Version of the database. It increases if the database model changes due to data demand.
	private static final int DATABASE_VERSION = 1;
	// Instance of the connection with the database.
	protected SQLiteDatabase database;

    /**
     * Basic database constructor.
     */
	public DataBase() {
		super(QualCurso.getInstance(), QualCurso.getInstance().getDatabaseName(), null, DATABASE_VERSION);
	}

    /**
     * Creates connection with the database, making possible to make sql operations.
     */
	protected void openConnection(){
		database = this.getReadableDatabase();
	}

    /**
     * Closes the open instance of the database.
     */
	protected void closeConnection(){
		database.close();
	}
	
}
