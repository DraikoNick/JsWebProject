package by.gsu.epamlab.model.comparators;

import by.gsu.epamlab.model.bin.Task;
import java.util.Comparator;

public class TaskIdComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.compareTo(o2);
    }
}
