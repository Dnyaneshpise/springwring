package com.learn.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String name;
    private String role;
    private int experience;


    public User(String name, String role, int experience) {
        this.name = name;
        this.role = role;
        this.experience = experience;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }
}