/*****************************
 * Class name: GenericBeanDAO (.java)
 *
 * Purpose: Generic Dao class that connects to the SQL database by executing generic SQL commands.
 *****************************/

package models;

import android.database.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import junit.framework.Assert;

import libraries.DataBase;

public class GenericBeanDAO extends DataBase {
    private SQLiteStatement sqLiteStatement;

    private enum DatabaseTablesNames {
        institution, course, books, articles, evaluation, search
    }

    /**
     * Empty constructor
     */
	public GenericBeanDAO() throws SQLException {
		super();
	}

	/**
	 * Method that opens a connection with the database and executes SQL code to get ordered table
	 * of values.
	 *
	 * @param bean
	 *				Object bean
	 * @param tableName
	 * 				Object table
	 * @param orderField
	 * 				Object orderField
	 *
	 * @return beans
	 * 				Objects Beans got from the database with ordered table of values.
	 *
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String tableName, String orderField)
			throws SQLException {

		assert (bean != null) : "bean must never be null";
		assert (bean.identifier != null) : "bean's identifier must never be null";
	    assert (tableName != null) : "table must never be null";
	    assert (!tableName.equals("")) : "table must never be empty";
	    assert (orderField != null) : "orderField must never be null";

		//It is the sql command line to be executed in the database.
		String searchQuery = "SELECT c.* FROM " + tableName + " as c, " + bean.relationship +
				     " as ci " + "WHERE ci.id_" + bean.identifier + "= ? AND ci.id_"
                      + tableName + " = c._id GROUP BY c._id";

        // Order the beans by some field if this order field is passed.
		if(orderField != null){
			searchQuery += " ORDER BY " + orderField;
		} else {
            /* Nothing to do! */
        }

        this.openConnection();

        // Execute the SQL search query by passing the base bean id and the evaluation year
        Cursor databaseCursor = this.database.rawQuery(
                searchQuery,
                new String[]{
                        Integer.toString(bean.getId())
                }
        );

        // get the bean result list by the database query
        ArrayList<Bean> beanResultList = getBeanListByDatabaseCursor(databaseCursor, tableName);

		this.closeConnection();

		return beanResultList;
	}

	/**
	 * Method that opens a connection with the database and executes SQL code to get ordered  table
	 * of values, but this time with the year.
	 *
	 * @param bean
	 *				Object bean
	 * @param table
	 * 				Object table
	 * @param year
	 * 				Object year
	 * @param orderField
	 * 				Object orderField
	 * @return
	 * 				Object beans with the SQL code got ordered  table of values, but this time with
	 * 				the year.
	 *
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String table, int year,
												  String orderField) throws SQLException {

	    assert (bean != null) : "bean must never be null";
		assert (bean.identifier != null) : "bean's identifier must never be null";
	    assert (table != null) : "table must never be null";
	    assert (table != "") : "table must never be empty";
	    assert (orderField != null) : "orderField must never be null";
	    assert (year > 2000);

        // SQL that get all beans by its relation and year
		String searchQuery = "SELECT c.* FROM " + table + " as c, " + "evaluation as ci " +
                "WHERE ci.id_" + bean.identifier + "= ? AND ci.id_" + table + " = c._id AND ci.year " +
                "= ? GROUP BY c._id";

		if(orderField != null){
			searchQuery += " ORDER BY " + orderField;
		}

        this.openConnection();

        // Execute the SQL search query by passing the base bean id and the evaluation year
        Cursor databaseCursor = this.database.rawQuery(
                searchQuery,
                new String[]{
                        Integer.toString(bean.getId()),
                        Integer.toString(year)
                }
        );

        // Get the bean result list by the database query
        ArrayList<Bean> beanResultList = getBeanListByDatabaseCursor(databaseCursor, table);

		this.closeConnection();

		return beanResultList;
	}

	/**
	 * Method that opens connection with the database and select objects according to their fields.
	 *
	 * @param bean
	 *				Object bean.
	 * @param searchBeanFields
	 * 				Object fields.
	 * @param orderField
	 * 				Object orderField.
	 *
	 * @return
	 * 				selected objects according to their fields.
	 *
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectFromFields(Bean bean, ArrayList<String> searchBeanFields, String orderField)
			throws SQLException {		

	    assert(bean != null) : "bean must never be null";
		assert(bean.identifier != null) : "bean's identifier must never be null";
	    assert(searchBeanFields != null) : "table must never be null";
	    assert(orderField != null) : "orderField must never be null";

		// List that will be filled with the values of the searchInBean
        ArrayList<String> searchBeanValueList = new ArrayList<String>();

        String searchQuerySql = "";
        for(String field : searchBeanFields) {
            searchQuerySql += " " + field + " = ? AND";
            searchBeanValueList.add(bean.get(field));
        }

        // Remove the last AND of the sql
        searchQuerySql = searchQuerySql.substring(0, searchQuerySql.length() - 3);

        if(orderField != null) {
            searchQuerySql += " ORDER BY " + orderField;

        } else {
            /* Nothing to do ! */
        }

        // Convert the ArrayList to a String array to be used by in the database query
        String[] searchBeanValues = new String[searchBeanValueList.size()];
        searchBeanValues = searchBeanValueList.toArray(searchBeanValues);

        this.openConnection();

        // Make the database query searching into the bean.identifier table
        Cursor databaseCursor = this.database.query(
                bean.identifier, null, searchQuerySql, searchBeanValues,
                null, null, null
        );

        // get the bean result list by the database query
        ArrayList<Bean> beanResultList = getBeanListByDatabaseCursor(
                databaseCursor, bean.identifier
        );

        this.closeConnection();

        return beanResultList;
	}

	/**
	 * Takes a bean object, treat it and insert in the database.
	 *
	 * @param bean
	 * 				Object bean
	 *
	 * @return
	 * 				True, if the bean was inserted in database.
	 *
	 * @throws SQLException
	 */
	public boolean insertBean(Bean bean) throws SQLException {
		assert (bean != null) : "bean object must never be null";
		assert (bean.identifier != null) : "bean's identifiers must never be null";

		this.openConnection();

		String beanInsertValues = "";

		// List of field strings that will be changed to insert the bean.
		ArrayList<String> beanFieldsList = bean.fieldsList();
		beanFieldsList.remove(0); // Remove id from the list

		// It is the sql command line to be executed in the database to insert the bean.
		String insertSQLQuery = "INSERT INTO " + bean.identifier + "(";

		for(String field : beanFieldsList) {
			insertSQLQuery += field + ",";
			beanInsertValues += "?,";
		}

        // Remove the last "," from the string
		insertSQLQuery = insertSQLQuery.substring(0, insertSQLQuery.length() - 1);
		beanInsertValues = beanInsertValues.substring(0, beanInsertValues.length() - 1);

        // Concatenate the insert values into the sql query.
		insertSQLQuery += ") VALUES(" + beanInsertValues + ")";

        // Prepare the statement for the insertion
		this.sqLiteStatement = this.database.compileStatement(insertSQLQuery);

        // Start at 1 because 0 is the id field, which is generated automatically
        int fieldIndex = 1;

		for (String field : beanFieldsList) {
			this.sqLiteStatement.bindString(fieldIndex, bean.get(field));
			fieldIndex++;
		}

		// Result that will be returned in the end.
		long result = this.sqLiteStatement.executeInsert();
		this.sqLiteStatement.clearBindings();

		this.closeConnection();

        boolean resultInsertBean = false;
        if(result != -1) {
            resultInsertBean = true;
        } else {
            resultInsertBean = false;
        }

		return resultInsertBean;
	}

	/**
	 * Method that creates a relationship between beans.
	 *
	 * @param parentBean
	 * 				Object parentBean
	 * @param childBean
	 * 				Object childBean.
	 *
	 * @return
	 * 				True, if the relationship was made.
	 * @throws SQLException
	 */
	public boolean addBeanRelationship(Bean parentBean, Bean childBean) throws SQLException {
		assert (parentBean != null) : "parentBean must never be null";
		assert (parentBean.identifier != null) : "parentBean's identifier must never be null";
		assert (parentBean.relationship != null) : "parentBean's relationship must never be null";
		assert (childBean != null) : "childBean must never be null";
		assert (childBean.identifier != null) : "childBean' identifier must never be null";

		this.openConnection();

		// It is the sql command line to be executed in the database to insert a relationship.
		String sql = "INSERT INTO " + parentBean.relationship + "(id_" + parentBean.identifier +
				     ",id_" + childBean.identifier + ") VALUES(?,?)";
		this.sqLiteStatement = this.database.compileStatement(sql);
		this.sqLiteStatement.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.sqLiteStatement.bindString(2, childBean.get(childBean.fieldsList().get(0)));
		long result = this.sqLiteStatement.executeInsert();
		this.sqLiteStatement.clearBindings();
		this.closeConnection();
		return (result != -1) ? true : false;
	}

	/**
	 * Delete relationship between two beans
	 *
	 * @param parentBean
	 * 				Object parentBean.
	 * @param childBean
	 * 				Object childBean.
	 *
	 * @return
	 * 				True, if it was deleted.
	 *
	 * @throws SQLException
	 */
	public boolean deleteBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		assert (parentBean != null) : "parentBean must never be null";
		assert (parentBean.identifier != null) : "parentBean's identifier must never be null";
		assert (parentBean.relationship != null) : "parentBean's relationship must never be null";
		assert (childBean != null) : "childBean must never be null";
		assert (childBean.identifier != null) : "childBean' identifier must never be null";

		this.openConnection();

		// It is the sql command line to be executed in the database.
		String sql = "DELETE FROM " + parentBean.relationship + "  WHERE id_"
				     + parentBean.identifier + " = ? AND id_" + childBean.identifier + " = ?";
		this.sqLiteStatement = this.database.compileStatement(sql);
		this.sqLiteStatement.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.sqLiteStatement.bindString(2, childBean.get(childBean.fieldsList().get(0)));
		int result = this.sqLiteStatement.executeUpdateDelete();
		this.sqLiteStatement.clearBindings();
		this.closeConnection();
		return (result == 1) ? true : false;
	}

	/**
	 * Method that gets a bean from the database tables.
	 *
	 * @param bean
	 * 				Object bean
	 *
	 * @return
	 * 				A bean from the database tables.
	 *
	 * @throws SQLException
	 */
	public Bean selectBean(Bean bean) throws SQLException {
		assert (bean != null) : "bean must never be null";
		assert (bean.identifier != null) : "bean's identifier must never be null";

		this.openConnection();

		// receives the value to be returned in the end of the function.
		Bean result = null;

		// It is the sql command line that will execute the select command.
		String sql = "SELECT * FROM " + bean.identifier + " WHERE "
				     + bean.fieldsList().get(0) + " = ?";

		// Represents the interator for the database table being accessed.
		Cursor cursor = this.database.rawQuery(sql, new String[]{bean.get(bean.fieldsList()
                .get(0))});
		if (cursor.moveToFirst()) {
			result = init(bean.identifier);
			for (String s : bean.fieldsList()) {
				result.set(s, cursor.getString(cursor.getColumnIndex(s)));
			}
		}

		this.closeConnection();
		return result;
	}

	/**
	 * Retrieves all beans from database by an specified order.
	 *
	 * @param type
	 * 				Object type.
	 * @param orderField
	 * 				Object orderField.
	 *
	 * @return
	 * 				Beans from database by an specified order.
	 *
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectAllBeans(Bean type, String orderField) throws SQLException {
		assert (type != null) : "type must never be null";
		assert (type.identifier != null) : "type's identifier must never be null";
	    assert (orderField != null) : "orderField must never be null";

		this.openConnection();

		// List of beans to be returned in the end.
		ArrayList<Bean> beans = new ArrayList<Bean>();

		// Represents the interator for the database table being accessed.
		Cursor cursor = this.database.query(type.identifier, null, null, null, null, null,
                orderField);
		while (cursor.moveToNext()) {

			// Represent an temporary Bean object that will be added to the Arraylist beans
			Bean bean = init(type.identifier);

			for (String s : type.fieldsList()) {
				bean.set(s, cursor.getString(cursor.getColumnIndex(s)));
			}
			beans.add(bean);
		}
		this.closeConnection();
		return beans;
	}

	public ArrayList<String[]> runSql(String sql) throws SQLException {
		assert (sql != null) : "sql must never be null";
		assert (sql != "") : "sql must never be empty";

		this.openConnection();
		ArrayList<String[]> result = new ArrayList<String[]>();

		Cursor cs = this.database.rawQuery(sql, null);
		while (cs.moveToNext()) {
			String [] data = new String[cs.getColumnCount()];

			for(int i = 0; i < cs.getColumnCount(); i++) {
				data[i] = cs.getString(i);
			}
			
			result.add(data);
		}

		this.closeConnection();
		return result;
	}
	
	
	public ArrayList<Bean> runSql(Bean type, String sql) throws SQLException {
		assert (type != null) : "type must never be null";
		assert (type.identifier != null) : "type's identifier must never be null";
		assert (sql != null) : "sql must never be null";
		assert (sql != "") : "sql must never be empty";

		this.openConnection();
		ArrayList<Bean> result = new ArrayList<Bean>();
		Cursor cursor = this.database.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			Bean bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, cursor.getString(cursor.getColumnIndex(s)));
			}
			result.add(bean);
		}
		this.closeConnection();
		return result;
	}

	/**
	 * @param type
	 * 				Object type
	 * @return
	 * 				the number of beans present in the database.
	 * @throws SQLException
	 */
	public Integer countBean(Bean type) throws SQLException {
		assert (type != null) : "type must never be null";
		assert (type.identifier != null) : "type's identifier must never be null";

		this.openConnection();

		// Integer count: Contains the number of beans.
		Integer count = 0;

		// It is the sql command line that will execute the select command.
		String sql = "SELECT * FROM " + type.identifier;

		// Represents the interator for the database table being accessed.
		Cursor cursor = this.database.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			count = cursor.getCount();
		}

		this.closeConnection();
		return count;
	}

	/**
	 * @param type
	 * 				Object type.
	 * @param last
	 * 				Object last.
	 * @return
	 * 				first or the last bean dependending on the boolean parameter `last`.
	 *
	 * @throws SQLException
	 */
	public Bean firstOrLastBean(Bean type, boolean last) throws SQLException {
		assert (type != null) : "type must never be null";
		assert (type.identifier != null) : "type's identifier must never be null";

		// receives the value to be returned in the end of the function.
		Bean bean = null;

		// It is the sql command line that will execute the select command.
		String sql = "SELECT * FROM " + type.identifier + " ORDER BY "
				     + type.fieldsList().get(0);

		if (!last) {
			sql += " LIMIT 1";
		}else {
			sql += " DESC LIMIT 1";
		}

		this.openConnection();

		// Represents the interator for the database table being accessed.
		Cursor cursor = this.database.rawQuery(sql,null);

		if (cursor.moveToFirst()) {
			bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, cursor.getString(cursor.getColumnIndex(s)));
			}
		}
		this.closeConnection();

		return bean;
	}

	/**
	 * Method that get beans by selected fields, order type and characteristics.
	 *
	 * @param returnFields
	 * 				Object returnFields.
	 * @param orderedBy
	 * 				Object orderedBy
	 * @param condition
	 * 				Object condition
	 * @param groupBy
	 * 				Object groupBy
	 * @param desc
	 * 				Object desc
	 *
	 * @return
	 * 				Order type and characteristics.
	 */
	public ArrayList<HashMap<String, String>> selectOrdered(ArrayList<String> returnFields,
															String orderedBy, String condition,
															String groupBy, boolean desc){

		assert(orderedBy != null) : "orderedBy must never be null";
		assert(condition != null) : "condition must never be null";
		assert(groupBy != null) : "groupBy must never be null";
		assert(returnFields != null) : "returnFields must never be null";

		// String with all the fields to do the select.
		String fields = "";
		for(String s : returnFields){
			fields+=s+",";
		}
		fields = fields.substring(0, fields.length()-1);

		// It is the sql command line that will execute the select command.
		String sql = "SELECT "+ fields + " FROM course AS c,institution AS i , evaluation AS e, " +
				     "articles AS a, books AS b" + " WHERE id_institution = i._id " +
				     "AND id_course = c._id AND id_articles = a._id AND id_books = b._id ";
		if(condition != null){
			sql+="AND "+condition+" ";
		}
		if(groupBy != null){
			sql+=" GROUP BY "+groupBy;
		}
		if(orderedBy != null){
			sql+=" ORDER BY "+orderedBy;
		}
		if(desc){
			sql+=" DESC";
		} else {
			sql+=" ASC";
		}

		// Hash map with the value from the retreived from the database.
		ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
		this.openConnection();

		// Represents the interator for the database table being accessed.
		Cursor cursor = this.database.rawQuery(sql,null);
		while (cursor.moveToNext()) {
			HashMap<String, String> hash = new HashMap<String, String>();
			for(String s : returnFields){
				hash.put(s, cursor.getString(cursor.getColumnIndex(s)));
			}
			hash.put("order_field", orderedBy);
			values.add(hash);
		}
		this.closeConnection();
		return values;
	}

	/**
	 * Method that selects bean according to a field.
	 *
	 * @param type
	 * 				Object type.
	 * @param field
	 * 				Object field.
	 * @param value
	 * 				Object value.
	 * @param use_like
	 * 				Object use_like.
	 * @param orderField
	 * 				Object orderField.
	 *
	 * @return
	 * 				the selected bean.
	 *
	 * @throws SQLException
	 */
	public ArrayList<Bean> selectBeanWhere(Bean type, String field, String value, boolean use_like,
										   String orderField) throws SQLException {
		assert (type != null) : "type must never be null";
		assert (type.identifier != null) : "type's identifier must never be null";
		assert (field != null) : "field must never be null";
		assert (value != null) : "value must never be null";
		assert (orderField != null) : "orderField must never be null";

		this.openConnection();

		// Array of beans to be selected according to the condition.
		ArrayList<Bean> beans = new ArrayList<Bean>();

		// It is the sql command line that will execute the select command.
		String sql = "SELECT * FROM " + type.identifier + " WHERE ";

		// Represents the interator for the database table being accessed.
		Cursor cursor;
		if (!use_like) {
			cursor = this.database.query(type.identifier, null,
					field + " = ?",
					new String[]{value},
					null, null, orderField);
		}else {
			cursor = this.database.query(type.identifier, null,
					field + " LIKE ?",
					new String[]{"%" + value + "%"},
					null, null, orderField);
		}
		while (cursor.moveToNext()) {
			Bean bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, cursor.getString(cursor.getColumnIndex(s)));
			}
			beans.add(bean);
		}
		this.closeConnection();

		return beans;
	}

	/**
	 * Delete a selected bean from the database.
	 *
	 * @param bean
	 * 				Object bean.
	 *
	 * @return
	 * 				the bean selected deleted.
	 *
	 * @throws SQLException
	 */
	public boolean deleteBean(Bean bean) throws SQLException {
		assert (bean != null) : "bean must never be null";
		assert (bean.identifier != null) : "bean's identifier must never be null";

		this.openConnection();

		// It is the sql command line that will execute the select command.
		String sql = "DELETE FROM "+bean.identifier+ " WHERE "+bean.fieldsList().get(0)+" = ?";
		this.sqLiteStatement = this.database.compileStatement(sql);
		this.sqLiteStatement.bindString(1, bean.get(bean.fieldsList().get(0)));
		int result = this.sqLiteStatement.executeUpdateDelete();
		this.sqLiteStatement.clearBindings();
		this.closeConnection();

		return (result == 1) ? true : false;
	}

	/**
	 * Starts the BeanIdentifier by creating a new model object.
	 *
	 * @param beanIdentifier
	 * 				Object beanIdentifier.
	 *
	 * @return
	 * 				The beanIdentifier started.
	 */
	public Bean init(String beanIdentifier) {
		assert (beanIdentifier != null) : "beanIdentifier must never be null";

		// Object that will be instantiated.
		Bean object = null;

		if (beanIdentifier.equals("institution")) {
			object = new Institution();
		} 
		
		else if (beanIdentifier.equals("course")) {
			object = new Course();
		}
		
		else if (beanIdentifier.equals("books")) {
			object = new Book();
		}
		
		else if (beanIdentifier.equals("articles")) {
			object = new Article();
		}
		
		else if (beanIdentifier.equals("evaluation")) {
			object = new Evaluation();
		}

		else if (beanIdentifier.equals("search")) {
			object = new Search();
		}

		return object;
	}

    /**
     *  Iterate through the query result and add each one to the result list
     *
     * @return ArrayList<Bean>
     */
    private ArrayList<Bean> getBeanListByDatabaseCursor(final Cursor databaseCursor,
                                                        final String tableName) {
        Assert.assertNotNull(databaseCursor); // The database cursor can't be blank
        Assert.assertEquals(true, tableName.length() != 0); // The table name can't be blank

        final ArrayList<Bean> beanResultList = new ArrayList<Bean>(); // The result list

        // Instance a bean by its table name, to get its table fields
        final Bean beanModel = initializeModelAsBean(tableName);

        // Collect the bean table fields
        final ArrayList<String> beanFieldList = beanModel.fieldsList();

        // Iterate through the query result and add each one to the result list
        while (databaseCursor.moveToNext()) {
            // initializeModelAsBean initialize a bean by its model identifier
            final Bean modelInstanceAsBean = initializeModelAsBean(tableName);

            // Set each field of the bean with tha data from the query
            for (final String beanFieldName : beanFieldList) {
                modelInstanceAsBean.set(
                        beanFieldName,
                        databaseCursor.getString(databaseCursor.getColumnIndex(beanFieldName))
                );
            }

            // Add the bean to the result list
            beanResultList.add(modelInstanceAsBean);
        }

        return beanResultList;
    }

    /**
     * initializeModelAsBeanialize(construct) a bean by its identifier.
     * It return a model instance with a Bean cast.
     *
     * @return Bean
     */
    public Bean initializeModelAsBean(final String beanIdentifier) {
        Assert.assertEquals(true, beanIdentifier.length() != 0); // The model name can't be blank

        // Get the table name(in the tables names enum) by the bean identifier
        final DatabaseTablesNames tableName = DatabaseTablesNames.valueOf(beanIdentifier);
        Bean modelInstanceAsBean = null; // The result bean

        // Verify witch model should been initialized
        switch (tableName) {
            case institution:
                modelInstanceAsBean = new Institution();
                break;

            case course:
                modelInstanceAsBean = new Course();
                break;

            case books:
                modelInstanceAsBean = new Book();
                break;

            case articles:
                modelInstanceAsBean = new Article();
                break;

            case evaluation:
                modelInstanceAsBean = new Evaluation();
                break;

            case search:
                modelInstanceAsBean = new Search();
                break;

            default:
                // Do noting
                break;
        }

        return modelInstanceAsBean;
    }
}
