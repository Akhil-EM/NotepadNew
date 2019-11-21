package com.example.notepadnew;

public class User {
    private String id;
    private String Name;
    private String pass;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String name, String pass) {
        this.id = id;
        Name = name;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getPass() {
        return pass;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
