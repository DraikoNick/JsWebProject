package by.gsu.epamlab.model.bin;

import by.gsu.epamlab.model.exceptions.DaoException;
import by.gsu.epamlab.model.fabrics.TaskStatusFabric;
import by.gsu.epamlab.model.fabrics.TaskStatusFabric.*;
import by.gsu.epamlab.model.utils.TimeUtils;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static by.gsu.epamlab.model.utils.Constants.*;

public class Task implements Comparable<Task>{
    private int id;
    private String name;
    private Date date;
    private TaskStatus status;
    private int userId;
    private String fileName;

    public Task(){
        this(0,"",new Date(0,0,0), TaskStatus.TODO, 0);
    }
    public Task(int id, String name, Date date, TaskStatus status, int userId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.status = status;
        this.userId = userId;
    }
    public Task(int id, String name, String date, String statusName, int userId) throws DaoException {
        try{
            this.id = id;
            this.name = name;
            this.status = TaskStatusFabric.getTaskStatus(statusName);
            this.userId = userId;
            setDate(date);
        }catch (ParseException e){
            throw new DaoException(ERR_PARSE_DATE + e.getMessage());
        }
    }
    public Task(int id, String name, String date, String statusName, int userId, String fileName) throws DaoException {
        this(id, name, date, statusName,userId);
        setFileName(fileName);
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_RU_PATTERN);
        java.util.Date uDate = null;
        try {
            uDate = sdf.parse(date);
            System.out.println(uDate);
        } catch (ParseException e) {
            sdf = new SimpleDateFormat(DATE_SQL_PATTERN);
            uDate = sdf.parse(date);
            System.out.println(uDate);
        }
        this.date = new Date(uDate.getTime());
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Date getDate() {
        return date;
    }
    public String getFormattedDate(){
        return TimeUtils.formatDate(date);
    }
    public TaskStatus getStatus() {
        return status;
    }
    public int getUserId() {
        return userId;
    }
    public String getFileName() {
        return fileName;
    }

    public String fieldsToString(){
        return id + DELIMITER_SEMICOLON + name + DELIMITER_SEMICOLON + getFormattedDate() + DELIMITER_SEMICOLON + status + DELIMITER_SEMICOLON + fileName;
    }

    @Override
    public String toString() {
        return fieldsToString();
    }
    @Override
    public int compareTo(Task o) {
        return id - o.getId();
    }
}
