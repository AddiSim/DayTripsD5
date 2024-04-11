package com.model;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleStringProperty id = new SimpleStringProperty(this, "id");
    private SimpleStringProperty password = new SimpleStringProperty(this, "password");
    private SimpleStringProperty firstName = new SimpleStringProperty(this, "firstName");
    private SimpleStringProperty lastName = new SimpleStringProperty(this, "lastName");

    public User(String id, String password, String firstName, String lastName) {
        this.id.set(id);
        this.password.set(password);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }
}
