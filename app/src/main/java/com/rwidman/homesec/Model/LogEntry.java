package com.rwidman.homesec.Model;


public class LogEntry {

    private int id;
    private String moduleName;
    private String topic;
    private String time;
    private String text;
    private String eventID;

    public LogEntry(int id,String moduleName,String topic, String time, String text, String eventID){
        this.setId(id);
        this.setModuleName(moduleName);
        this.setTopic(topic);
        this.setTime(time);
        this.setText(text);
        this.setEventID(eventID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}