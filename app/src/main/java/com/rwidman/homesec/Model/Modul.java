package com.rwidman.homesec.Model;

/**
 * Created by J.Ringler on 23.09.2015.
 */
public class Modul {

    private String name;
    private String state;
    private boolean camera;

    public Modul(String name, String state, boolean camera)
    {
        this.name = name;
        this.state = state;
        this.camera = camera;
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

    public boolean hasCamera() {
        return camera;
    }

    public void setCamera(boolean hasCamera){
        this.camera = hasCamera;
    }
}
