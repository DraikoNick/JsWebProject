package by.gsu.epamlab.model.fabrics;

import by.gsu.epamlab.model.bin.Task;
import by.gsu.epamlab.model.bin.User;
import by.gsu.epamlab.model.comparators.TaskDateComparator;
import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.interfaces.TaskDAO;
import by.gsu.epamlab.model.utils.TimeUtils;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static by.gsu.epamlab.model.utils.ConstantsJSP.*;
import static by.gsu.epamlab.model.utils.TimeUtils.*;

public class SectionFactory {
    private static Date today = new Date(new java.util.Date().getTime());
    private static final String NAME_TODAY = "Today";
    private static final String NAME_TOMORROW = "Tomorrow";
    private static final String NAME_SOMEDAY = "Someday";
    private static final String NAME_FIXED = "Fixed";
    private static final String NAME_RECBYN = "Recycle Bin";


    public enum SectionKind {
        TODAY (NAME_TODAY){
            @Override
            List<Task> sortList(List<Task> list) {
                TaskStatusFabric.TaskStatus status = TaskStatusFabric.TaskStatus.TODO;
                return sortListByParameters(list, status, TimeUtils.datePlusDays(today, -ONE_YEAR_MIL), today);
            }
            @Override
            Map<String, String> getButtonsMap(Map<String, String> map) {
                return Stream.of(
                        new AbstractMap.SimpleEntry<>(DONE_KEY, DONE_VAL),
                        new AbstractMap.SimpleEntry<>(CHANGE_KEY, CHANGE_VAL),
                        new AbstractMap.SimpleEntry<>(DELETE_KEY, DELETE_VAL))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        },
        TOMORROW (NAME_TOMORROW){
            @Override
            List<Task> sortList(List<Task> list) {
                TaskStatusFabric.TaskStatus status = TaskStatusFabric.TaskStatus.TODO;
                return sortListByParameters(list, status, today, datePlusDays(today, TWO_DAY_MIL));
            }
            @Override
            Map<String, String> getButtonsMap(Map<String, String> map) {
                return SectionKind.TODAY.getButtonsMap(map);
            }
        },
        SOMEDAY (NAME_SOMEDAY){
            @Override
            List<Task> sortList(List<Task> list) {
                Date afterTomorrow = datePlusDays(today, TWO_DAY_MIL);
                Date oneYearLater = datePlusDays(today, ONE_YEAR_MIL);
                TaskStatusFabric.TaskStatus status = TaskStatusFabric.TaskStatus.TODO;
                return sortListByParameters(list, status, afterTomorrow, oneYearLater);
            }
            @Override
            Map<String, String> getButtonsMap(Map<String, String> map) {
                return SectionKind.TODAY.getButtonsMap(map);
            }
        },
        FIXED (NAME_FIXED){
            @Override
            List<Task> sortList(List<Task> list) {
                Date oneYearBefore = datePlusDays(today, -ONE_YEAR_MIL);
                Date oneYearLater = datePlusDays(today, ONE_YEAR_MIL);
                TaskStatusFabric.TaskStatus status = TaskStatusFabric.TaskStatus.FIXED;
                return sortListByParameters(list, status, oneYearBefore, oneYearLater);
            }
            @Override
            Map<String, String> getButtonsMap(Map<String, String> map) {
                return Stream.of(
                        new AbstractMap.SimpleEntry<>(RESTORE_KEY, RESTORE_VAL),
                        new AbstractMap.SimpleEntry<>(DELETE_KEY, DELETE_VAL))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        },
        RECBYN (NAME_RECBYN){
            @Override
            List<Task> sortList(List<Task> list) {
                Date oneYearBefore = datePlusDays(today, -ONE_YEAR_MIL);
                Date oneYearLater = datePlusDays(today, ONE_YEAR_MIL);
                TaskStatusFabric.TaskStatus status = TaskStatusFabric.TaskStatus.DELETED;
                return sortListByParameters(list, status, oneYearBefore, oneYearLater);
            }
            @Override
            Map<String, String> getButtonsMap(Map<String, String> map) {
                return Stream.of(
                        new AbstractMap.SimpleEntry<>(RESTORE_KEY, RESTORE_KEY),
                        new AbstractMap.SimpleEntry<>(DELETE_0_KEY, DELETE_0_VAL))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        };

        private String name;
        SectionKind(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        abstract List<Task> sortList(List<Task> list);
        abstract Map<String, String> getButtonsMap(Map<String, String> map);
    }

    public static List<Task> getTasks(SectionKind sectionKind, User user, TaskDAO taskDao) throws DaoException {
        today = new Date(new java.util.Date().getTime());
        return sectionKind.sortList(taskDao.getTasks(user));
    }
    private static List<Task> sortListByParameters(List<Task> list, TaskStatusFabric.TaskStatus taskStatus, Date dateFrom, Date dateTo){
        List<Task> tasks = new ArrayList<>();
        list.sort(new TaskDateComparator());
        for(Task task:list){
            if(task.getDate().compareTo(dateFrom)>=0 && task.getDate().compareTo(dateTo)<=0){
                if(task.getStatus() == taskStatus){
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public static Map<String, String> getButtonsForEachTask(SectionKind sectionKind){
        return sectionKind.getButtonsMap(new LinkedHashMap<>());
    }
    public static Map<String, String> getButtonsForSection(SectionKind sectionKind){
        Map<String, String> map = getButtonsForEachTask(sectionKind);
        if(map.containsKey(CHANGE_KEY)){
            map.remove(CHANGE_KEY);
        }
        return map;
    }
}
