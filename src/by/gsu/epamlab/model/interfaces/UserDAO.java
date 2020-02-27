package by.gsu.epamlab.model.interfaces;

import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.exceptions.DaoException;

public interface UserDAO {
    public User insertUser(User user) throws DaoException;
    public User getUser(String userName, String password) throws DaoException;
}
