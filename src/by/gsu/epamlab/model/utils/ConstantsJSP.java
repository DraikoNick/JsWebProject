package by.gsu.epamlab.model.utils;

public class ConstantsJSP {
    //sections factory constants
    public final static String DONE_KEY = "Done";
    public final static String DONE_VAL = DONE_KEY.toUpperCase();
    public final static String CHANGE_KEY = "Change";
    public final static String CHANGE_VAL = CHANGE_KEY.toUpperCase()+0;
    public final static String DELETE_KEY = "Delete";
    public final static String DELETE_VAL = DELETE_KEY.toUpperCase();
    public final static String DELETE_0_KEY = "Delete Without Restore";
    public final static String DELETE_0_VAL = DELETE_VAL+0;
    public final static String RESTORE_KEY = "Restore";
    public final static String RESTORE_VAL = RESTORE_KEY.toUpperCase();
    //logs
    public final static String LOG_FILE = "WEB_PROJECT_LOGS.log";
    public final static String LOG_PATH = "logs/";
    //properties
    public final static String PROPERTIES_FILE = "web.properties";
    public final static String PROPERTIES_NAME = "properties";
    public final static String PROPERTIES_RES_USERS = "resource.with.users";
    public final static String PROPERTIES_RES_TASKS = "resource.with.tasks";
    public final static String PROPERTIES_USERS_DATA = "users.data.path";
    //path
    public final static String URL_RES = "/WEB-INF/res/";
    public final static String URL_ACTIONS = "/actions/*";
    public final static String URL_ACTION_ADD = "/actionAdd";
    public final static String URL_ACTION_CHANGE = "/actionChange";
    public final static String URL_ACTION = "/action";
    public final static String URL_SECTION = "/section";
    public final static String URL_ACTION_HTML = "/action.html";
    public final static String URL_MAIN_HTML = "/main.html";
    public final static String URL_DOWNLOAD = "/download";
    public final static String URL_INDEX_HTML = "/web/index.html";
    public final static String URL_INDEX = "/index";
    public final static String URL_LOGIN = "/login";
    public final static String URL_LOGOUT = "/logout";
    public final static String URL_REGIN = "/regin";

    public final static String CONTENT_TYPE = "APPLICATION/OCTET-STREAM";
    public final static String CONTENT_DISPOSITION = "Content-Disposition";
    public final static String CONTENT_ATTACHMENT = "attachment; filename=\"";
    public final static String CONTENT_ATTACHMENT_END = "\"";

    public final static String URL_PARAM_LIST_TYPE = "?list_type=";
    public final static String URL_PARAM_ACTION_TYPE_CHANGE = "?action=change";
    public final static String URL_PARAM_LOGIN = "?login=yes";
    public final static String URL_PARAM_ACTION = "action";
    public final static String PAR_LIST_TYPE = "list_type";
    public final static String TYPE_TODAY = "today";
    public final static String REFERER_NAME = "referer";
    public final static String PAR_USER = "user";
    public final static String PAR_TASKS_TO_CHANGE = "taskstochange";
    public final static String PAR_IDS = "idTask";
    public final static String PAR_FILE = "file";

    public final static String PAR_NAME = "username";
    public final static String PAR_PASS = "password";
    public final static String PAR_EMAIL = "email";

    //json
    public final static String KEY = "key";
    public final static String VAL = "value";
    public final static String BTN_EACH = "btnEach";
    public final static String BTN_SECT = "btnSection";
    public final static String SECTIONS = "sections";
    public final static String COMA = ",";
    public final static String OPEN = "{";
    public final static String END = "}";
    public final static String CLOSE = "\"}";
    public final static String TASKS = "\"tasks\" : ";
    public final static String USER = "\"username\" : \"";
    public final static String ERROR = "\", \"error\": \"";
    public final static String TO_CHANGE = "{\"tasksToChange\" : ";

}
