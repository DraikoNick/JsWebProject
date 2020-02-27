package by.gsu.epamlab.model.impl;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.comparators.TaskIdComparator;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.interfaces.TaskDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskImplDaoMemory implements TaskDAO {
    //private static final Logger LOGGER = Loggers.init(TaskImplDaoDB.class.getName());

    private static List<Task> tasks = getInitialization();
    private static int lastId;

    private static List<Task> getInitialization() {
        List<Task> tasks = null;
        try{
            lastId = 1;
            tasks = new ArrayList<Task>(
                    Arrays.asList(
                            new Task(lastId++, "doSomething1", "2020-02-07", "TODO",1),
                            new Task(lastId++, "doSomething2", "2020-02-08", "DELETED",1),
                            new Task(lastId++, "doSomething3", "2020-02-09", "DELETED",1),
                            new Task(lastId++, "doSomething4", "2020-02-10", "DELETED",1),
                            new Task(lastId++, "doSomething5", "2020-02-10", "FIXED",2),
                            new Task(lastId++, "doSomething6", "2020-02-06", "FIXED",2),
                            new Task(lastId++, "doSomething7", "2020-02-05", "FIXED",2),
                            new Task(lastId++, "doSomething8", "2020-02-11", "FIXED",3),
                            new Task(lastId++, "doSomething9", "2020-02-11", "FIXED",3),
                            new Task(lastId++, "doSomethin10", "2020-02-12", "TODO",3),
                            new Task(lastId++, "do13", "2020-02-13", "TODO",4),
                            new Task(lastId++, "do08", "2020-02-08", "TODO",4),
                            new Task(lastId++, "do07", "2020-02-07", "TODO",4),
                            new Task(lastId++, "fx07", "2020-02-07", "FIXED",4),
                            new Task(lastId++, "fx072", "2020-02-07", "FIXED",4),
                            new Task(lastId++, "fx15", "2020-02-15", "FIXED",4),
                            new Task(lastId++, "de07", "2020-02-07", "DELETED",4),
                            new Task(lastId++, "de14", "2020-02-14", "DELETED",4),
                            new Task(lastId++, "de08", "2020-02-08", "DELETED",4),
                            new Task(lastId++, "do132", "2020-02-13", "TODO",4),
                            new Task(lastId++, "do14", "2020-02-14", "TODO",4),
                            new Task(lastId++, "do15", "2020-02-15", "TODO",4),
                            new Task(lastId++, "doSomethin14", "2020-02-16", "TODO",5),
                            new Task(lastId++, "doSomethin15", "2020-02-17", "TODO",5),
                            new Task(lastId++, "doSomethin16", "2020-02-18", "TODO",5),
                            new Task(lastId++, "doSomethin17", "2020-02-19", "TODO",6),
                            new Task(lastId++, "doSomethin18", "2020-02-20", "TODO",6)
                    )
            );
        }catch (DaoException e){
            //LOGGER.log( Level.SEVERE, e.toString(), e);
        }
        return tasks;
    }

    @Override
    public void insertTask(Task task) {
        task.setId(lastId++);
        //LOGGER.log( Level.INFO, task.toString());
        tasks.add(task);
    }

    @Override
    public List<Task> getTasks(User user) {
        //LOGGER.log( Level.INFO, user.getName());
        List<Task> list = new ArrayList<>();
        for (Task task:tasks){
            if(task.getUserId() == user.getId()){
                list.add(task);
            }
        }
        return list;
    }

    @Override
    public void updateTask(Task task) {
        //LOGGER.log( Level.INFO, task.toString());
        tasks.sort(new TaskIdComparator());
        int index = Collections.binarySearch(tasks, task, new TaskIdComparator());
        tasks.set(index, task);
    }

    @Override
    public void deleteTask(Task task) throws DaoException {
        //LOGGER.log( Level.WARNING, task.toString());
        tasks.sort(new TaskIdComparator());
        int index = Collections.binarySearch(tasks, task, new TaskIdComparator());
        tasks.remove(index);
    }

}
