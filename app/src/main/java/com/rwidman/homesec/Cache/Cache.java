package com.rwidman.homesec.Cache;

import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.Model.LogEntry;
import com.rwidman.homesec.Model.Modul;
import com.rwidman.homesec.Model.Person;

import java.util.List;

/**
 * Created by J.Ringler on 15.09.2015.
 */
public class Cache {

    private static Cache cache;

    private List<Modul> moduls;
    private List<Person> persons;
    private List<Access> accesses;
    private List<LogEntry> logEntries;

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
