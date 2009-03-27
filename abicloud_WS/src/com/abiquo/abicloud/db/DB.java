/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is available at http://www.abiquo.com/.....
 * 
 * The Initial Developer of the Original Code is Soluciones Grid, S.L. (www.abiquo.com),
 * Consell de Cent 296 principal 2�, 08007 Barcelona, Spain.
 * No portions of the Code have been created by third parties. 
 * All Rights Reserved.
 * 
 * Contributor(s): ______________________________________.
 * 
 * Graphical User Interface of this software may be used under the terms
 * of the Common Public Attribution License Version 1.0 (the  "CPAL License",
 * available at http://cpal.abiquo.com), in which case the provisions of CPAL
 * License are applicable instead of those above. In relation of this portions 
 * of the Code, a Legal Notice according to Exhibits A and B of CPAL Licence 
 * should be provided in any distribution of the corresponding Code to Graphical
 * User Interface.
 */
package com.abiquo.abicloud.db;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.spec.PSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * Class responsable for database operations in abicloud_WS
 * 
 * @author aodachi
 */
public class DB
{

    // The keys to the sql string that will be used for different database operations

    /**
     * Adds a new virtual app entry in the table virtualapp
     */
    public static final String CREATE_NEW_APP = "virtualApp.addNewEntry";

    public static final String GET_APPS = "virtualApp.getEntries";

    public static final String UPDATE_APP_STATE = "virtualApp.updateState";

    public static final String DEL_APP = "virtualApp.deleteEntry";

    // public static final String UPDATE_APP_XML = "virtualApp.updateXML";

    public static final String GET_APP_MACHINE_STATES = "virtualAppMachineState.getEntries";

    public static final String UPDATE_APP_MACHINE_STATE = "virtualAppMachineState.updateEntry";

    public static final String CREATE_NEW_APP_MACHINE_STATE = "virtualAppMachineState.addEntry";

    public static final String DEL_APP_MACHINE_STATES = "virtualAppMachineState.deleteEntries";

    public static final String POWER_ON = "PowerOn";

    public static final String POWER_OFF = "PowerOff";

    public static final Logger logger = LoggerFactory.getLogger(DB.class);

    private static Properties properties = new Properties();

    // Load the properties for this class
    static
    {

        try
        {
            properties.loadFromXML(DB.class.getClassLoader().getResourceAsStream(
                "resources/db.sql.xml"));
        }
        catch (Exception e)
        {
            logger.error("Unable to load properties files", e);
        }

    }

    /** Determines whether or not the sql statements should be logged **/
    private static final boolean SHOW_SQL = new Boolean(properties.getProperty("showSQL", "true"));

    /**
     * Loads the derby embedded driver and creates/loads the database If the database is being
     * created for the first time and not been booted up then the corresponding tables in the
     * database will be created.
     * 
     * @param derbySystemHome String containing the value to which the System property
     *            <code>derby.system.home</code> will be set to
     */
    public static void createDatabaseAndTables(String derbySystemHome)
    {

        String dbName = properties.getProperty("db.name");

        // First set the derby.system.home property before loading the driver
        System.setProperty("derby.system.home", derbySystemHome);

        logger.info("Creating the derby database: " + dbName + " in " + derbySystemHome);

        try
        {

            Connection con = null;
            boolean commit = true;

            try
            {

                Class.forName(properties.getProperty("db.driver"));

                logger.info("Database driver loaded");

                // Now create tables if they don't exits
                con =
                    DriverManager.getConnection(properties.getProperty("db.url") + ";create=true");

                logger.info("Connected and created the database: " + dbName);

                con.setAutoCommit(false);

                try
                {

                    // If there are no tables in the database then it has just been create so we
                    // proceed and create the tables
                    if (!con.getMetaData().getTables(dbName, null, null, new String[] {"TABLE"})
                        .next())
                    {

                        Statement stmt;

                        for (String table : properties.getProperty("db.tables").split(","))
                        {

                            logger.info("Creating the table: " + table + " ...");

                            stmt = con.createStatement();

                            // Create the table
                            stmt.execute(properties.getProperty(table + ".createTable"));

                            // Now create indices for this table
                            String tablesIndexList =
                                properties.getProperty("db.table." + table + ".indexes");

                            if (tablesIndexList != null && tablesIndexList.length() > 0)
                            {
                                for (String tableIndex : tablesIndexList.split(","))
                                    stmt.execute(properties.getProperty(tableIndex));
                            }

                            logger.info("Successfully created the table: " + table);
                        }

                    }
                    else
                    {
                        logger.info("No tables to create");
                    }

                }
                catch (SQLException e)
                {

                    commit = false;
                    logger.error("SQLException occurred", e);

                }

            }
            finally
            {

                if (con != null)
                {

                    if (commit)
                        con.commit();
                    else
                        con.rollback();
                }
            }

        }
        catch (ClassNotFoundException e)
        {
            logger.error("Unable to load database driver", e);
        }
        catch (SQLException e)
        {
            logger.error("SQLException occurred while trying to close the database connection", e);
        }

    }

    /**
     * Shuts down the database A successful shutdown results in an SQLException and no other
     * exception is obtained.
     */
    public static void shutdownDatabase()
    {

        try
        {

            logger.info("Shutting down the derby database ...");

            // the shutdown=true attribute shuts down Derby
            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        }
        catch (SQLException e)
        {

            // We got the expected exception
            // Note that for single database shutdown, the expected
            // SQL state is "08006", and the error code is 45000.
            // if the error code or SQLState is different then the shutdown failed
            if (e.getErrorCode() == 50000 && "XJ015".equals(e.getSQLState()))
                logger.info("Derby shut down successfully");
            else
                logger.error("Derby did not shut down.", e);

        }

    }

    /**
     * Returns to the string value of property identified by <code>key</code> in the
     * <code>Properties</code> object of this class. with the place holders replaced by values
     * supplied in the array <code>placeHolderValues</code>
     * 
     * @param key the property key
     * @param placeHolderValues an array of <code>Objects</code> to replace placeHolder in the
     *            property string to be returned
     * @return string value in this property list with the specified key value.
     */
    public static final String createSQL(String key, Object... placeHolderValues)
    {

        String sql = properties.getProperty(key);

        // Substitute the place holder with the actual values
        sql = MessageFormat.format(sql, placeHolderValues);

        return sql;
    }

    /**
     * Converts a DOM structure to a <code>String</code>
     * 
     * @param doc <code>Document</code> whose <code>XML</code> structure is to be converted to
     *            <code>String</code>
     * @return the xml structure as <code>String</code>
     */
    public static final String convertXMLToString(Document doc)
    {

        String str = "";

        try
        {

            StringWriter sw = new StringWriter();

            // Create a transformer object and set the indent property to true
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(sw));

            str = sw.toString();

        }
        catch (Exception e)
        {
            logger.error("Error trying to convert the XML document to a string", e);
        }

        return str;

    }

    /**
     * Creates a database connection
     * 
     * @return a reference to a <code>Connection</code> object
     * @throws java.sql.SQLException
     */
    private Connection createConnection() throws SQLException
    {

        Connection con = DriverManager.getConnection(properties.getProperty("db.url"));
        con.setAutoCommit(false);

        return con;
    }

    /**
     * Closes the database connection
     * 
     * @param commit boolean indicating whether or not to commit the changes (true) to the database
     *            or do a rollback (false)
     * @throws java.sql.SQLException
     */
    private void closeConnection(Connection con, boolean commit) throws SQLException
    {

        if (con != null)
        {

            if (commit)
            {
                logger.info("Committing changes to the database ...");
                con.commit();
                logger.info("Committed changes to the database.");
            }
            else
            {
                logger.info("Rolling back changes to the database ...");
                con.rollback();
                logger.info("Rolled back changes to the database.");
            }

            con.close();
        }
    }

    /**
     * Queries the database with the <code>SQL</code> query provided by <code>sql</code>. Each row
     * is put in a HashMap&lt;String,Object&gt; where the keys are table column names <br>
     * and the values are the corredponding table column values for the row. The
     * HashMap&lt;String,Object&gt;(s) created is(are) put in a an <code>ArrayList</code> and
     * returned
     * 
     * @param sql query used in querying the database
     * @return <code>ArrayList&lt;HashMap&lt;String,Object&gt;&gt;</code> containg the records in
     *         the database
     */
    public ArrayList<HashMap<String, Object>> queryDB(String sql)
    {

        Connection con = null;
        ArrayList<HashMap<String, Object>> rows = new ArrayList();
        boolean commit = true;

        try
        {

            try
            {

                con = this.createConnection();

                this.showSQL(sql);

                try
                {

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int columnCount = rsmd.getColumnCount();

                    HashMap<String, Object> row;

                    while (rs.next())
                    {

                        row = new HashMap<String, Object>();

                        String columnName;
                        Object obj;

                        // get all the columns and store the column_names and preparedStmtColValues
                        // as a name/columnValue pair in a hash
                        for (int i = 1; i <= columnCount; ++i)
                        {

                            columnName = rsmd.getColumnName(i);

                            obj = rs.getObject(columnName);

                            if (obj != null)
                                row.put(columnName, obj.getClass().cast(obj));

                            row.put(columnName, obj);

                        }

                        rows.add(row);
                    }

                }
                catch (SQLException e)
                {
                    commit = false;
                    logger.error("SQLException occurred while trying to query the database", e);
                }
                catch (Exception e)
                {
                    commit = false;
                    logger.error("Exception occurred while trying to query the database", e);
                }

            }
            finally
            {

                this.closeConnection(con, commit);
            }

        }
        catch (SQLException e)
        {
            logger.error(
                "SQLException occurred while trying to open or close the database connection", e);
        }

        return rows;

    }

    /**
     * Updates the database
     * 
     * @param sql query used to update information in the database
     * @return boolean indicating a successful operation <code>true</code> or not
     */
    public boolean updateDB(String sql)
    {

        Connection con = null;
        boolean success = true;

        try
        {

            try
            {

                this.showSQL(sql);

                con = this.createConnection();

                try
                {

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();

                }
                catch (SQLException e)
                {
                    success = false;
                    logger.error("SQLException occurred while trying to update the database", e);
                }
                catch (Exception e)
                {
                    success = false;
                    logger.error("Exception occurred while trying to update the database", e);
                }

            }
            finally
            {
                this.closeConnection(con, success);
            }

        }
        catch (SQLException e)
        {

            logger.error(
                "SQLException occurred while trying to open or close the database connection", e);
        }

        return success;

    }

    private void showSQL(String sql)
    {

        if (DB.SHOW_SQL)
        {
            logger.info(sql);
        }
    }

    @Override
    public String toString()
    {
        return com.abiquo.abicloud.utils.ToString.toString(this);
    }

    public static void main(String[] args)
    {

        logger.info("Testing Derby DB setup");

        try
        {
            /* */
            // DB.createDatabaseAndTables("/opt/apache-tomcat-6.0.18/webapps/abicloud_WS/WEB-INF/classes/db");
            DB.createDatabaseAndTables("db");
            DB db = new DB();

            /*
             * db.updateDB("INSERT INTO \"virtualApp\"(\"idVirtualApp\",\"xmlDoc\") VALUES('1','xxx')"
             * );
             * db.updateDB("INSERT INTO \"virtualAppMachineState\"(\"idVirtualAppMachine\") VALUES('3')"
             * );
             * db.updateDB("INSERT INTO \"virtualAppMachineState\"(\"idVirtualAppMachine\") VALUES('4')"
             * );
             */

            logger.info("--- The virtual appliances");
            com.abiquo.abicloud.utils.ToString.print(db
                .queryDB(properties.getProperty(DB.GET_APPS)));

            logger.info("--- The virtual machine states");
            com.abiquo.abicloud.utils.ToString.log(db.queryDB(properties
                .getProperty(DB.GET_APP_MACHINE_STATES)));

            DB.shutdownDatabase();

        }
        catch (Exception e)
        {
            logger.error("Exception in main", e);
        }

    }

}
