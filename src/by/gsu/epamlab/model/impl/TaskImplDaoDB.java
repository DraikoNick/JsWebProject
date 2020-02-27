package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.db.DBHelper;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.fabrics.TaskStatusFabric;
import by.gsu.epamlab.model.interfaces.TaskDAO;
import by.gsu.epamlab.model.utils.Loggers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static by.gsu.epamlab.model.utils.ConstantsDB.*;

public class TaskImplDaoDB implements TaskDAO {
    private static final Logger LOGGER = Loggers.init(TaskImplDaoDB.class.getName());

    @Override
    public void insertTask(Task task) throws DaoException {
        try (   DBHelper dbHelper = new DBHelper();
                PreparedStatement pr = dbHelper.getPreparedStatement(SQL_INSERT_TASK); ){
            pr.setString(1, task.getName());
            pr.setDate(2, task.getDate());
            pr.setString(3, task.getStatus().toString());
            pr.setInt(4, task.getUserId());
            pr.setString(5, task.getFileName());
            int result = pr.executeUpdate();
            task.setId(result);
            LOGGER.log( Level.INFO, task.toString());
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERR_DAO_DB + e.getMessage());
        }
    }

    @Override
    public List<Task> getTasks(User user) throws DaoException {
        try (   DBHelper dbHelper = new DBHelper();
                PreparedStatement pr = dbHelper.getPreparedStatement(SQL_SELECT_TASKS); ){
            pr.setInt(1, user.getId());
            List<Task> tasks = new ArrayList<>();
            try (ResultSet rs = pr.executeQuery()){
                while (rs.next()){
                    Task task = new Task(
                            rs.getInt(1),   //id
                            rs.getString(2),//name
                            rs.getDate(3),  //date
                            TaskStatusFabric.getTaskStatus(rs.getString(4)),
                            rs.getInt(5)    //userId
                    );
                    task.setFileName(rs.getString(6));
                    tasks.add(task);
                }
            }
            return tasks;
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERR_DAO_DB + e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) throws DaoException {
        try (DBHelper dbHelper = new DBHelper();
             PreparedStatement pr = dbHelper.getPreparedStatement(SQL_UPDATE_TASK); ){
               pr.setString(1, task.getName());
               pr.setDate(2, task.getDate());
               pr.setString(3, task.getStatus().toString());
               pr.setString(4, task.getFileName());
               pr.setInt(5, task.getId());
               task.setId(pr.executeUpdate());
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERR_DAO_DB + e.getMessage());
        }
    }

    @Override
    public void deleteTask(Task task) throws DaoException {
        try (DBHelper dbHelper = new DBHelper();
             PreparedStatement pr = dbHelper.getPreparedStatement(SQL_DELETE_TASK); ){
            pr.setInt(1, task.getId());
            pr.executeUpdate();
        }catch (SQLException e){
            LOGGER.log( Level.SEVERE, e.toString(), e);
            throw new DaoException(ERR_DAO_DB + e.getMessage());
        }
    }
}
