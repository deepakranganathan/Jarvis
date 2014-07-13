package com.maelstrom;

public class Contact {
    Integer id;
    String firstName;
    String lastName;
    boolean inOut;
    
    
    public Contact() {
        this.id = -1;
        this.firstName = "Unknown";
        this.lastName = "Unknown";
        this.inOut = false;
    }
    
    public Contact(String firstName, String lastName, boolean inOut) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.inOut = inOut;
    }
    
    public Contact(int id, String firstName, String lastName, boolean inOut) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.inOut = inOut;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public boolean isInOut() {
        return inOut;
    }
    public void setInOut(boolean inOut) {
        this.inOut = inOut;
    }
}
