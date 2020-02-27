package by.gsu.epamlab.controllers.actions;

import by.gsu.epamlab.model.utils.FileUpDownLoadUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

import static by.gsu.epamlab.model.utils.ConstantsJSP.*;

@WebServlet(URL_DOWNLOAD)
public class DownloadController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DOWNLOAD STARTED");
        Properties properties = (Properties) req.getServletContext().getAttribute(PROPERTIES_NAME);
        String path = properties.getProperty(PROPERTIES_USERS_DATA);
        String filename = req.getParameter(PAR_FILE);
        resp.setContentType(CONTENT_TYPE);
        resp.setHeader(CONTENT_DISPOSITION,CONTENT_ATTACHMENT + filename + CONTENT_ATTACHMENT_END);
        System.out.println(CONTENT_ATTACHMENT + filename + CONTENT_ATTACHMENT_END);
        FileUpDownLoadUtils.doDownLoad(path + filename);    //Logger inside
    }
}
