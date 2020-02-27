package by.gsu.epamlab.model.interfaces;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.exceptions.DaoException;

import java.util.List;

public interface TaskDAO {
    public void insertTask(Task task) throws DaoException;
    public List<Task> getTasks(User user) throws DaoException;
    public void updateTask(Task task) throws DaoException;
    public void deleteTask(Task task) throws DaoException;
}
