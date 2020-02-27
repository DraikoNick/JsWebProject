package by.gsu.epamlab.controllers.actions;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.fabrics.ActionOnTaskFactory;
import by.gsu.epamlab.model.fabrics.TaskDaoFabric;
import by.gsu.epamlab.model.interfaces.TaskDAO;
import by.gsu.epamlab.model.utils.Loggers;
import by.gsu.epamlab.model.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static by.gsu.epamlab.model.utils.Constants.*;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

@WebServlet({URL_ACTION})
public class ActionsController extends HttpServlet {
    private static final Logger LOGGER = Loggers.init(ActionsController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Optional<List<Task>> optionalTasks = Optional.ofNullable((List<Task>) session.getAttribute(PAR_TASKS_TO_CHANGE));
        List<Task> tasksToChange = new ArrayList<>();
        if(optionalTasks.isPresent()){
            tasksToChange = optionalTasks.get();
        }
        String jsonString = Utils.getJsonForActionPage(tasksToChange);
        LOGGER.log( Level.INFO, MSG_TASK_CHANGING + tasksToChange.toString());
        PrintWriter out = resp.getWriter();
        out.write(jsonString);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String actionName = req.getParameter(URL_PARAM_ACTION).toUpperCase();
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute(PAR_USER);
            Properties properties = (Properties) req.getServletContext().getAttribute(PROPERTIES_NAME);
            TaskDAO taskDao = TaskDaoFabric.getDaoFromFabric(properties);
            int[] checkedTasks = Arrays.stream(req.getParameterValues(PAR_IDS))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            List<Task> tasksToChange = ActionOnTaskFactory.getTasksFromListByIds(checkedTasks, taskDao.getTasks(user));
            session.setAttribute(PAR_TASKS_TO_CHANGE, tasksToChange);
            LOGGER.log( Level.INFO, MSG_TASK_CHANGING + tasksToChange.toString());
            if(actionName.compareTo(CHANGE_VAL)==0){
                req.getRequestDispatcher(URL_ACTION_HTML + URL_PARAM_ACTION_TYPE_CHANGE).forward(req, resp);
                return;
            }
            ActionOnTaskFactory.doActionWithTasks(actionName, tasksToChange, TaskDaoFabric.getDaoFromFabric(properties));
            LOGGER.log( Level.INFO, MSG_TASK_CHANGED + actionName + DELIMITER_TAB + tasksToChange.toString());
            resp.sendRedirect(req.getContextPath() + URL_MAIN_HTML + URL_PARAM_LIST_TYPE + TYPE_TODAY);
            return;
        }catch (DaoException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new ServletException(ERR_SERVER);
        }
    }
}
