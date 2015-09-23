package com.rwidman.homesec.Model;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class Profile {

    private String name;
    private boolean isActive;

    public Profile(String name, boolean isActive)
    {
        this.setName(name);
        this.setIsActive(isActive);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
