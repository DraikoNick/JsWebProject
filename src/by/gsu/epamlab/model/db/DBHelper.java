package by.gsu.epamlab.model.db;

import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.exceptions.InitException;
import by.gsu.epamlab.model.utils.Loggers;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.db.DBConstants.*;

public class DBHelper implements AutoCloseable{

    private static final Logger LOGGER = Loggers.init(DBHelper.class.getName());

    private Connection connect;
    public DBHelper() throws DaoException {
        try{
            ConnectorDB.init();
            connect = ConnectorDB.getConnection();
        }catch ( InitException | SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERROR_DB_CONNECTION + NEW_LINE + e.getMessage());
        }
    }
    /*public DBHelper(Properties properties) throws DaoException {
        try{
            ConnectorDB.init(
                    properties.getProperty(DB_URL),
                    properties.getProperty(DB_USER),
                    properties.getProperty(DB_PASS));
            connect = ConnectorDB.getConnection();
        }catch (InitException | SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERROR_DB_CONNECTION + NEW_LINE + e.getMessage());
        }
    }*/
    public Statement getStatement() throws DaoException {
        Statement st = null;
        try{
            st = connect.createStatement();
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERROR_DB_QUERY+NEW_LINE+e.getMessage());
        }
        return st;
    }
    public PreparedStatement getPreparedStatement(String sql) throws DaoException {
        PreparedStatement ps = null;
        try{
            ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERROR_DB_QUERY+NEW_LINE+e.getMessage());
        }
        return ps;
    }
    public static void closeStatement(Statement statement){
        if(statement != null){
            try{
                statement.close();
            }catch (SQLException e){
                LOGGER.log( Level.SEVERE, e.toString(), e);
                System.err.println(ERROR_DB_CLOSE_RESOURCE+NEW_LINE+e.getMessage());
            }
        }
    }
    public static void closePreparedStatement(PreparedStatement statement){
        if(statement != null){
            try{
                statement.close();
            }catch (SQLException e){
                LOGGER.log( Level.SEVERE, e.toString(), e);
                System.err.println(ERROR_DB_CLOSE_RESOURCE+NEW_LINE+e.getMessage());
            }
        }
    }
    public static void closeResultSet(ResultSet rs){
        try{
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            System.err.println(ERROR_DB_CLOSE_RESOURCE+NEW_LINE+e.getMessage());
        }
    }
    public static void closeConnection(Connection cn){
        if(cn != null){
            try{
                cn.close();
            }catch(SQLException e){
                LOGGER.log( Level.SEVERE, e.toString(), e);
                System.err.println(ERROR_DB_CLOSE_RESOURCE+NEW_LINE+e.getMessage());
            }
        }
    }
    public void closeConnection(){
        closeConnection(connect);
    }
    public void close(){
        closeConnection();
    }
}//end DBHelper
