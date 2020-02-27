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
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.fabrics.ActionOnTaskFactory.ActionKind.ADD;
import static by.gsu.epamlab.model.fabrics.TaskStatusFabric.TaskStatus.TODO;
import static by.gsu.epamlab.model.utils.Constants.*;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

@WebServlet(URL_ACTION_ADD)
public class ActionAddController extends HttpServlet {
    private static final Logger LOGGER = Loggers.init(ActionAddController.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Properties properties = (Properties) req.getServletContext().getAttribute(PROPERTIES_NAME);
            List<FileItem> items = new ServletFileUpload(
                    new DiskFileItemFactory())
                    .parseRequest(req);
            Iterator<FileItem> iter = items.iterator();
            iter.next();
            String taskName = iter.next().getString();  //PAR_TASK_NAME
            String taskDate = iter.next().getString();  //PAR_TASK_DATE
            String fileName = FileUpDownLoadUtils
                    .doUpload(items, properties.getProperty(PROPERTIES_USERS_DATA));
            User user = (User) req.getSession().getAttribute(PAR_USER);
            Task task = new Task(0,taskName,taskDate,TODO.toString(),user.getId(),fileName);
            List<Task> tasksToChange = new ArrayList<>();
            tasksToChange.add(task);
            ActionOnTaskFactory
                    .doActionWithTasks(ADD.toString(), tasksToChange, TaskDaoFabric.getDaoFromFabric(properties));
            LOGGER.log( Level.INFO, MSG_TASK_ADDED + task.toString());
            resp.sendRedirect(req.getContextPath() + URL_MAIN_HTML + URL_PARAM_LIST_TYPE + TYPE_TODAY);
        }catch (DaoException | FileUploadException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new ServletException(ERR_SERVER + e.getMessage());
        }
    }
}
