package com.rwidman.homesec.Model;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class Person {

    private String name;

    public Person (String name)
    {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
