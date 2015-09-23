package com.rwidman.homesec.Cache;

import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.Model.LogEntry;
import com.rwidman.homesec.Model.Modul;
import com.rwidman.homesec.Model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.Ringler on 15.09.2015.
 */
public class Cache {

    private static Cache cache;

    private List<Modul> moduls = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Access> accesses = new ArrayList<>();
    private List<LogEntry> logEntries = new ArrayList<>();

    public List<Modul> getModuls() {
        return moduls;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Access> getAccesses() {
        return accesses;
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public static Cache getInstance()
    {
        if(cache == null)
        {
            cache = new Cache();
        }
        return cache;
    }
}
