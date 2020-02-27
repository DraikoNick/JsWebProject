package by.gsu.epamlab.model.comparators;

import by.gsu.epamlab.model.bin.User;

import java.util.Comparator;

public class UserNameComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
