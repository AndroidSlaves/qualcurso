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

	private static final String DATABASE_NAME = "database.sqlite3.db";
	private static final int DATABASE_VERSION = 1;
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
