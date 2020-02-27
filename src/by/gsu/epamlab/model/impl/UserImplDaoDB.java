package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.db.DBHelper;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.interfaces.UserDAO;
import by.gsu.epamlab.model.utils.Loggers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.utils.ConstantsDB.*;

public class UserImplDaoDB implements UserDAO {

    private static final Logger LOGGER = Loggers.init(UserImplDaoDB.class.getName());

    @Override
    public User insertUser(User user) throws DaoException{
        try(    DBHelper dbHelper = new DBHelper();
                PreparedStatement psInsert = dbHelper.getPreparedStatement(SQL_INSERT_USER);
                PreparedStatement psSelect = dbHelper.getPreparedStatement(SQL_CHECK_USER_NAME)){
            psSelect.setString(1, user.getName());
            psInsert.setString(1, user.getName());
            psInsert.setString(2, user.getPassword());
            psInsert.setString(3, user.getEmail());
            synchronized (this){        //because get and insert user. And in that moment another user with name like this trying to regIn
                try(ResultSet rs = psSelect.executeQuery()){
                    if (rs.next()){
                        return null;        //if user exist? then we throw null
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                    throw new SQLException(e);
                }
                user.setId(psInsert.executeUpdate());   //but if not exist - insert user and get key of user from DB
            }
            LOGGER.log( Level.INFO, user.getName());
            return user;
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERR_DAO_DB + e.getMessage());
        }
    }
    @Override
    public User getUser(String userName, String password) throws DaoException{
        try (   DBHelper dbHelper = new DBHelper();
                PreparedStatement psSelect = dbHelper.getPreparedStatement(SQL_SELECT_USER)){
            psSelect.setString(1, userName);
            psSelect.setString(2, password);
            User user = null;
            try(ResultSet rs = psSelect.executeQuery()){
                if (rs.next()){
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setEmail(rs.getString(4));
                    LOGGER.log( Level.INFO, user.getName());
                }
            }
            return user;
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERR_DAO_DB + e.getMessage());
        }
    }
}
