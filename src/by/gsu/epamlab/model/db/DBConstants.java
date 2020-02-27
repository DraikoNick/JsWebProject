package by.gsu.epamlab.model.db;

public class DBConstants {
    //database properties
    public static final String DB_PROPERTIES = "by.gsu.epamlab/model/db/database";
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASS = "db.password";
    public static final String DB_DRIVER = "db.driver";
    //errors
    public static final String ERROR_DB = "ERROR DB: ";
    public static final String ERROR_DB_CONNECTION = ERROR_DB + "Can not connect to DataBase.";
    public static final String ERROR_DB_CLOSE_RESOURCE = ERROR_DB + "Can not close resource.";
    public static final String ERROR_DB_QUERY = ERROR_DB + "Can not make query to database.";
    //delimiters
    public static final String NEW_LINE = System.lineSeparator();
}
