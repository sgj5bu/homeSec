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
        for (int i = 0;i<5;i++){
            moduls.add(new Modul("Modul"+i,"anOderAus",i%2==0));
        }
        return moduls;
    }

    public List<Person> getPersons() {
        for (int i = 0;i<5;i++){
            persons.add(new Person("Person"+i));
        }
        return persons;
    }

    public List<Access> getAccesses() {
        for (int i = 0;i<5;i++){
            accesses.add(new Access("Access"+1,"offenOderZu"));
        }
        return accesses;
    }

    public List<LogEntry> getLogEntries() {
        for (int i = 0;i<5;i++){
            logEntries.add(new LogEntry(i,"Modul"+i,"Topic"+i,"15.09.23_11-48-47_","Meldungstext"+i,"_ID"+i));
        }
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
