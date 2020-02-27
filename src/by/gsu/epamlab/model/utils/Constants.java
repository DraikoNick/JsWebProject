package by.gsu.epamlab.model.utils;

public class Constants {
    //delimiters
    public final static String DELIMITER_SEMICOLON = ";";
    public final static String DELIMITER_TAB = "\t";
    public final static String DELIMITER_LINE = System.lineSeparator();
    //patterns
    public final static String DATE_RU_PATTERN = "dd.MM.yyyy";
    public final static String DATE_SQL_PATTERN = "yyyy-MM-dd";

    //strings
    public final static String STR_USER = "user";
    //errors
    public final static String ERR = " ERROR: ";
    public final static String ERR_SERVER = ERR + "error accessing server, database. ";
    public final static String ERR_USER_NOT_REGISTERED = ERR + STR_USER + " do not registered or password not right. ";
    public final static String ERR_USER_EXIST = ERR + STR_USER + " already exist. ";
    public final static String ERR_INIT_PROPERTIES = ERR_SERVER + " Server not configured. ";
    public final static String ERR_PARSE_DATE = ERR + " Can not parse task date: ";
    //messages
    public final static String MSG_UPLOAD = "File uploaded. ";
    public final static String MSG_DOWNLOAD = "File downloaded. ";
    public final static String MSG_LOGIN = "User login. ";
    public final static String MSG_LOGOUT = "User logout. ";
    public final static String MSG_REGISTER = "New user registered. ";
    public final static String MSG_TASKS = "Tasks:  ";
    public final static String MSG_SECTION = "Section:  ";
    public final static String MSG_TASK_ADDED = "Task added. ";
    public final static String MSG_TASK_CHANGING = "Task for changing. ";
    public final static String MSG_TASK_CHANGED = "Task changed. ";
    public final static String MSG_FILTER = "FILTER: ";
}