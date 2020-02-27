package by.gsu.epamlab.controllers.actions;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.fabrics.ActionOnTaskFactory;
import by.gsu.epamlab.model.fabrics.TaskDaoFabric;
import by.gsu.epamlab.model.utils.FileUpDownLoadUtils;
import by.gsu.epamlab.model.utils.Loggers;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.fabrics.ActionOnTaskFactory.ActionKind.CHANGE;
import static by.gsu.epamlab.model.utils.Constants.ERR_SERVER;
import static by.gsu.epamlab.model.utils.Constants.MSG_TASK_CHANGED;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

@WebServlet(URL_ACTION_CHANGE)
public class ActionChangeController extends HttpServlet {
    private static final Logger LOGGER = Loggers.init(ActionChangeController.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try{
            List<FileItem> items = new ServletFileUpload(
                    new DiskFileItemFactory())
                    .parseRequest(req);
            Iterator<FileItem> iter = items.iterator();
            iter.next();
            int taskId = Integer.parseInt(iter.next().getString());
            String taskName = iter.next().getString();  //PAR_TASK_NAME
            String taskDate = iter.next().getString();  //PAR_TASK_DATE
            String taskStatus = iter.next().getString();  //PAR_TASK_STATUS
            Properties properties = (Properties) req.getServletContext().getAttribute(PROPERTIES_NAME);
            String fileName = FileUpDownLoadUtils
                    .doUpload(items, properties.getProperty(PROPERTIES_USERS_DATA));
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(PAR_USER);
            List<Task> changesTasks = (List<Task>) session.getAttribute(PAR_TASKS_TO_CHANGE);
            Task newTask = new Task(taskId, taskName, taskDate, taskStatus, user.getId(), fileName);
            changesTasks.set(0, newTask);
            ActionOnTaskFactory.doActionWithTasks(CHANGE.toString(), changesTasks, TaskDaoFabric.getDaoFromFabric(properties));
            LOGGER.log( Level.WARNING, MSG_TASK_CHANGED + newTask.toString());
            resp.sendRedirect(req.getContextPath() + URL_MAIN_HTML + URL_PARAM_LIST_TYPE + TYPE_TODAY);
            return;
        }catch (DaoException | IOException | FileUploadException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new ServletException(ERR_SERVER);
        }
    }
}
