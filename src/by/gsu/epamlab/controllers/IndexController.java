package by.gsu.epamlab.controllers;

import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.db.ConnectorDB;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.fabrics.UserDaoFabric;
import by.gsu.epamlab.model.interfaces.UserDAO;
import by.gsu.epamlab.model.utils.Loggers;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.utils.Constants.*;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

@WebServlet(
        urlPatterns = {URL_INDEX, URL_LOGIN, URL_REGIN},
        initParams = {
                @WebInitParam(name = PROPERTIES_NAME, value = PROPERTIES_FILE)
        },
        loadOnStartup = 1
)
public class IndexController extends HttpServlet {
    private static final Logger LOGGER = Loggers.init(IndexController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> optionalUser = Optional.ofNullable((User) req.getSession().getAttribute(PAR_USER));
        PrintWriter out = resp.getWriter();
        if(optionalUser.isPresent()){
            out.print(OPEN + USER + optionalUser.get().getName() + ERROR + CLOSE);
            LOGGER.log( Level.INFO, MSG_LOGIN + optionalUser.get().getName());
            return;
        }
        out.print(OPEN + USER + ERROR + CLOSE);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Properties properties = (Properties) req.getServletContext().getAttribute(PROPERTIES_NAME);
        try{
            UserDAO userDao = UserDaoFabric.getDaoFromFabric(properties);
            String username = req.getParameter(PAR_NAME);
            String password = req.getParameter(PAR_PASS);
            String email    = req.getParameter(PAR_EMAIL);
            Optional<User> optionalUser;
            if(email != null){      //its registration
                optionalUser = Optional.ofNullable(userDao.insertUser(new User(0, username, password, email)));
            }else {                 //its login
                optionalUser = Optional.ofNullable(userDao.getUser(username, password));
            }
            PrintWriter out = resp.getWriter();
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                req.getSession().setAttribute(PAR_USER, user);
                out.print(OPEN + USER + optionalUser.get().getName() + ERROR + CLOSE);
                LOGGER.log( Level.INFO, MSG_LOGIN + user.getName());
                resp.sendRedirect(URL_INDEX_HTML);
                return;
            }
            String messageToLog = ERR_USER_NOT_REGISTERED + username;
            out.print(OPEN + USER + ERROR + messageToLog + CLOSE);
            LOGGER.log( Level.WARNING, messageToLog);
            //TODO: how to sent error?
            resp.sendRedirect( URL_INDEX_HTML+ URL_PARAM_LOGIN);
        }catch (DaoException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new ServletException(ERR_SERVER);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        String propertiesName = config.getInitParameter(PROPERTIES_NAME);
        InputStream input = config.getServletContext().getResourceAsStream(URL_RES + propertiesName);
        Properties properties = new Properties();
        try {
            properties.load(input);
            input.close();
            ConnectorDB.setProperties(properties);
            config.getServletContext().setAttribute(PROPERTIES_NAME, properties);
        } catch (IOException e) {
            throw new ServletException(ERR_INIT_PROPERTIES);
        }
    }
}
