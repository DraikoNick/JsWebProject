package by.gsu.epamlab.model.bin;

import static by.gsu.epamlab.model.utils.Constants.*;

public class User implements Comparable<User>{
    private int id;
    private String name;
    private String password;
    private String email;

    public User(){
        this(0,"","","");
    }
    public User(int id, String name, String password, String email){
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    public User(String name, String password){
        this(0, name, password, "");
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    protected String fieldsToString(){
        return id + DELIMITER_SEMICOLON + name + DELIMITER_SEMICOLON + password + DELIMITER_SEMICOLON + email;
    }
    @Override
    public String toString() {
        return fieldsToString();
    }
    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(obj==null){
            return false;
        }
        if(this.getClass() != obj.getClass()){
            return false;
        }
        User that = (User) obj;
        boolean result = false;
        if(compareTo(that) == 0){
            result = true;
        }
        return result;
    }

    @Override
    public int compareTo(User user) {
        return name.compareTo(user.getName());
    }
}

