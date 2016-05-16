/*****************************
 * Abstract Class name: Bean (.java)
 *
 * Purpose:
 *****************************/

package models;

import java.util.ArrayList;

public abstract class Bean {
	protected String identifier;
	protected String relationship;

    /**
     * Get information about object. This method will be Override on
     * classes that inherit Bean.
     *
     * @param field
     * @return
     */
	public abstract String get(String field);

    /**
     * Set informations about some object.
     *
     * @param field
     * @param data
     */
	public abstract void set(String field, String data);

    /**
     * Create an ArrayList with fields of Database.
     *
     * @return ArrayList<String>
     */
	public abstract ArrayList<String> fieldsList();

    /**
     * Get id of object saved on database.
     *
     * @return int
     */
	public abstract int getId();

    /**
     * Set id of object that will be saved on database.
     *
     * @param id
     */
	public abstract void setId(int id);

}