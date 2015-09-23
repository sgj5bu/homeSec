package com.rwidman.homesec.Model;


public class LogEntry {

    int id;
    String moduleName;
    String topic;
    String time;
    String text;
    String eventID;

    public LogEntry(int id,String moduleName,String topic, String time, String text, String eventID){
        this.id = id;
        this.moduleName = moduleName;
        this.topic = topic;
        this.time = time;
        this.text = text;
        this.eventID = eventID;
    }

}