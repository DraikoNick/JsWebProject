package by.gsu.epamlab.model.utils;

import by.gsu.epamlab.model.exceptions.DaoException;
import org.apache.commons.fileupload.FileItem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.utils.Constants.*;
import static java.lang.System.out;

public class FileUpDownLoadUtils {
    private static final Logger LOGGER = Loggers.init(FileUpDownLoadUtils.class.getName());

    public static String doUpload(List<FileItem> items, String filePath) throws DaoException {
        try{
            Iterator<FileItem> iter = items.iterator();
            String fileName = "";
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (!item.isFormField()) {
                    fileName = item.getName();
                    if("".compareTo(fileName) != 0){
                        item.write(new File(filePath + fileName));
                        LOGGER.log( Level.WARNING, MSG_UPLOAD + fileName);
                        return fileName;
                    }
                }
            }
            return fileName;
        }catch (Exception e){
            throw new DaoException(e.getMessage());
        }
    }

    public static void doDownLoad(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        int i;
        while ((i = fis.read()) != -1){
            out.write(i);
        }
        fis.close();
        LOGGER.log( Level.WARNING, MSG_DOWNLOAD + fileName);
    }
}
