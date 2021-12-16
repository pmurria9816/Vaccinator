package com.assignment2.vaccinator.models;

import java.util.Date;

public class Appointment {

    private int user;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String hospital;
    private Date slot;
    private String time;
    private String vaccine;

    public Appointment() {
    }

    public Appointment(int user, String firstName, String lastName, int age, String email, String hospital, Date slot, String time, String vaccine) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.hospital = hospital;
        this.slot = slot;
        this.time = time;
        this.vaccine = vaccine;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public Date getSlot() {
        return slot;
    }

    public void setSlot(Date slot) {
        this.slot = slot;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", hospital='" + hospital + '\'' +
                ", slot=" + slot +
                ", time='" + time + '\'' +
                ", vaccine='" + vaccine + '\'' +
                '}';
    }
}
