package com.rwidman.homesec.Model;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class Access {

    private String name;
    private String state;

    public Access(String name, String state)
    {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
