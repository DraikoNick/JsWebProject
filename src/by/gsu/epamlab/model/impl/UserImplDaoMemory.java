package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.comparators.UserNameComparator;
import by.gsu.epamlab.model.interfaces.UserDAO;
import by.gsu.epamlab.model.utils.Loggers;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserImplDaoMemory implements UserDAO {
    private static final Logger LOGGER = Loggers.init(UserImplDaoMemory.class.getName());

    private static List<User> users = getInitialization();
    private static int lastId;

    private static List<User> getInitialization(){
        lastId = 1;
        List<User> users = new ArrayList<>(
                Arrays.asList(
                        new User(lastId++, "Nikita", "pass", "email1@mail.com"),
                        new User(lastId++, "Tanya", "root", "email2@mail.com"),
                        new User(lastId++, "Denis", "123", "email3@mail.com"),
                        new User(lastId++, "root", "root", "email4@mail.com"),
                        new User(lastId++, "Olga", "root", "email5@mail.com"),
                        new User(lastId++, "Masha", "123", "email6@mail.com")
                )
        );
        return users;
    }

    @Override
    public User insertUser(User user){
        synchronized (this) {
            if (getUser(user.getName(), user.getPassword()) == null) {
                user.setId(lastId++);
                users.add(user);
            } else {
                user = null;
            }
        }
        LOGGER.log( Level.INFO, user.toString());
        return user;
    }

    @Override
    public User getUser(String userName, String password){
        User guest = null;
        users.sort(new UserNameComparator());
        int index = Collections.binarySearch(users, new User(userName, password), new UserNameComparator());
        if(index > 0){
            User user = users.get(index);
            if(user.getPassword().equals(password)){
                guest = user;
                LOGGER.log( Level.INFO, guest.toString());
            }
        }
        return guest;
    }
}
