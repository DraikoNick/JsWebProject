package by.gsu.epamlab.model.fabrics;

public class TaskStatusFabric {

    public enum TaskStatus {
        TODO(1),            //1
        FIXED(2),          //2
        DELETED(3);      //3

        private int id;
        TaskStatus(int id){
            this.id = id;
        }
    }

    public static TaskStatus getTaskStatus(String statusName){
        try{
            return TaskStatus.valueOf(statusName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return TaskStatus.TODO;
        }
    }
}
