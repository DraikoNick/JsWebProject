package by.gsu.epamlab.model.fabrics;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.comparators.TaskIdComparator;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.interfaces.TaskDAO;
import java.util.*;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

public class ActionOnTaskFactory {
    public enum ActionKind{
        DONE{
            @Override
            void doActionOnTask(Task task, TaskDAO taskDAO) throws DaoException {
                task.setStatus(TaskStatusFabric.TaskStatus.FIXED);
                taskDAO.updateTask(task);
            }
        },
        CHANGE{
            @Override
            void doActionOnTask(Task task, TaskDAO taskDAO) throws DaoException {
                taskDAO.updateTask(task);
            }
        },
        DELETE{
            @Override
            void doActionOnTask(Task task, TaskDAO taskDAO) throws DaoException {
                task.setStatus(TaskStatusFabric.TaskStatus.DELETED);
                taskDAO.updateTask(task);
            }
        },
        DELETE0{    //delete forever, without restore
            @Override
            void doActionOnTask(Task task, TaskDAO taskDAO) throws DaoException {
                taskDAO.deleteTask(task);
            }
        },
        RESTORE{
            @Override
            void doActionOnTask(Task task, TaskDAO taskDAO) throws DaoException {
                task.setStatus(TaskStatusFabric.TaskStatus.TODO);
                taskDAO.updateTask(task);
            }
        },
        ADD{
            @Override
            void doActionOnTask(Task task, TaskDAO taskDAO) throws DaoException {
                taskDAO.insertTask(task);
            }
        };
        abstract void doActionOnTask(Task task, TaskDAO taskDAO) throws DaoException;
    }
    public static void doActionWithTasks(String action, List<Task> tasks, TaskDAO taskDAO) throws DaoException {
        ActionKind actionKind = ActionKind.valueOf(toUpperCase(action));
        for(Task task:tasks){
            actionKind.doActionOnTask(task, taskDAO);
        }
    }
    public static List<Task> getTasksFromListByIds(int[] ids, List<Task> tasks){
        List<Task> results = new ArrayList<>();
        tasks.sort(new TaskIdComparator());
        for (int id:ids){
            Task task = new Task();
            task.setId(id);
            int index = Collections.binarySearch(tasks, task, new TaskIdComparator());
            results.add(tasks.get(index));
        }
        return results;
    }
}
