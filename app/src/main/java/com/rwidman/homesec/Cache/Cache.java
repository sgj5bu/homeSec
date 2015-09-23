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

    private static List<Modul> moduls = new ArrayList<>();
    private static List<Person> persons = new ArrayList<>();
    private static List<Access> accesses = new ArrayList<>();
    private static List<LogEntry> logEntries = new ArrayList<>();

    public static List<Modul> getModuls() {
        return moduls;
    }

    public static List<Person> getPersons() {
        return persons;
    }

    public static List<Access> getAccesses() {
        return accesses;
    }

    public static List<LogEntry> getLogEntries() {
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
