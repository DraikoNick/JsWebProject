package by.gsu.epamlab.model.utils;

public class ConstantsDB {
    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASS = "db.password";

    //error
    public static final String ERR_DAO = "Data Access Object Error: ";
    public static final String ERR_DAO_DB = ERR_DAO + "Cannot insert/get DAO. ";

    //tables and fields
    public static final String TAB_USERS = "users";
    public static final String TAB_USER_ID = "user_id";
    public static final String TAB_USER_NAME = "user_name";
    public static final String TAB_USER_PASS = "user_password";
    public static final String TAB_USER_MAIL = "user_email";
    public static final String TAB_TASKS = "tasks";
    public static final String TAB_TASKS_ID = "tasks_id";
    public static final String TAB_TASKS_NAME = "tasks_name";
    public static final String TAB_TASKS_DATE = "tasks_date";
    public static final String TAB_TASKS_STATUS = "tasks_status";
    public static final String TAB_TASKS_USER = "user_id";

    //queries
    public static final String SQL_INSERT_USER =
            "INSERT INTO "+ TAB_USERS +
                " (" + TAB_USER_NAME + ", " + TAB_USER_PASS + ", " + TAB_USER_MAIL + ") " +
                "VALUES (?, ?, ?)";
    public static final String SQL_SELECT_USER =
            "SELECT * FROM " + TAB_USERS +
                " WHERE " + TAB_USER_NAME + " = ? AND " + TAB_USER_PASS + " = ?";
    public static final String SQL_CHECK_USER_NAME =
            "SELECT user_id FROM " + TAB_USERS +
                    " WHERE " + TAB_USER_NAME + " = ? ";

    public static final String SQL_INSERT_TASK =
            "INSERT INTO "+ TAB_TASKS +
                    " (" + TAB_TASKS_NAME + ", " + TAB_TASKS_DATE + ", " + TAB_TASKS_STATUS + ", " + TAB_TASKS_USER + ", " + "filename" + ") " +
                    "VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_TASKS =
            "SELECT * FROM " + TAB_TASKS +
                " WHERE " + TAB_TASKS_USER + " = ? ";
    public static final String SQL_UPDATE_TASK =
            "UPDATE " + TAB_TASKS +
            " SET " + TAB_TASKS_NAME   + " = ?, " +
                      TAB_TASKS_DATE   + " = ?, " +
                      TAB_TASKS_STATUS + " = ?, " +
                          " filename " + " = ? " +
                " WHERE (" + TAB_TASKS_ID + " = ?)";
    public static final String SQL_DELETE_TASK =
            "DELETE FROM " + TAB_TASKS +
                    " WHERE " + TAB_TASKS_ID + " = ? ";
}
