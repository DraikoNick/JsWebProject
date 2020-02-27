package by.gsu.epamlab.model.fabrics;

import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.impl.TaskImplDaoDB;
import by.gsu.epamlab.model.impl.TaskImplDaoMemory;
import by.gsu.epamlab.model.interfaces.TaskDAO;
import by.gsu.epamlab.model.utils.Loggers;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static by.gsu.epamlab.model.utils.Constants.DELIMITER_TAB;
import static by.gsu.epamlab.model.utils.ConstantsJSP.PROPERTIES_RES_TASKS;
import static org.apache.taglibs.standard.functions.Functions.toUpperCase;

public class TaskDaoFabric {
    private static final Logger LOGGER = Loggers.init(TaskDaoFabric.class.getName());

    private enum TaskDaoImplKind {
        MEMORY{
            @Override
            TaskDAO getImpl() {
                return new TaskImplDaoMemory();
            }
        },
        DATABASE{
            @Override
            TaskDAO getImpl() {
                return new TaskImplDaoDB();
            }
        };
        abstract TaskDAO getImpl();
    }
    public static TaskDAO getDaoFromFabric(Properties properties) throws DaoException {
        try{
            TaskDaoImplKind taskDAOImplKind = TaskDaoImplKind.valueOf(toUpperCase(properties.getProperty(PROPERTIES_RES_TASKS)));
            LOGGER.log( Level.INFO, taskDAOImplKind.toString() + DELIMITER_TAB + properties);
            return taskDAOImplKind.getImpl();
        }catch (IllegalArgumentException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(e.getMessage());
        }
    }
}
