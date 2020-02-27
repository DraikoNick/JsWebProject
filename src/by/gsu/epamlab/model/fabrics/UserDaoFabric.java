package by.gsu.epamlab.model.fabrics;

import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.interfaces.UserDAO;
import by.gsu.epamlab.model.impl.UserImplDaoDB;
import by.gsu.epamlab.model.impl.UserImplDaoMemory;
import by.gsu.epamlab.model.utils.Loggers;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.utils.Constants.*;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;
import static org.apache.taglibs.standard.functions.Functions.toUpperCase;

public class UserDaoFabric {
    private static final Logger LOGGER = Loggers.init(UserDaoFabric.class.getName());

    private enum UserDaoImplKind {
        MEMORY{
            @Override
            UserDAO getImpl() {
                return new UserImplDaoMemory();
            }
        },
        DATABASE{
            @Override
            UserDAO getImpl() {
                return new UserImplDaoDB();
            }
        };
        abstract UserDAO getImpl();
    }
    public static UserDAO getDaoFromFabric(Properties properties) throws DaoException {
        try{
            UserDaoImplKind userDAOImplKind = UserDaoImplKind.valueOf(toUpperCase(properties.getProperty(PROPERTIES_RES_USERS)));
            LOGGER.log(Level.INFO, userDAOImplKind + DELIMITER_TAB + properties);
            return userDAOImplKind.getImpl();
        }catch (IllegalArgumentException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(e.getMessage());
        }
    }
}
