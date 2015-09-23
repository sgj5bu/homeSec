package com.rwidman.homesec.Cache;

import android.util.Log;

import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.Model.LogEntry;
import com.rwidman.homesec.Model.Modul;
import com.rwidman.homesec.Model.Person;
import com.rwidman.homesec.Model.Profile;
import com.rwidman.homesec.Tasks.GetModulesTask;

import java.util.ArrayList;
import java.util.List;


public class Cache {

    private static Cache cache;
    public static int Port;

    private List<Modul> moduls = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Access> accesses = new ArrayList<>();
    private List<LogEntry> logEntries = new ArrayList<>();
    private List<Profile> profiles = new ArrayList<>();


    public List<Modul> getModuls(ModulFragment context) {
        Log.d("Cache", "Try starting modulestask");
        GetModulesTask t = new GetModulesTask(context, Port);
        try
        {
            moduls = t.execute().get();
        } catch (Exception e)
        {
            e.printStackTrace();
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
            logEntries.add(new LogEntry(Integer.toString(i),"Modul"+i,"Topic"+i,"15.09.23_11-48-47_","Meldungstext"+i,"_ID"+i));
        }
        return logEntries;
    }

    public List<Profile> getProfiles() {
        for (int i = 0;i<5;i++){
            profiles.add(new Profile("Profile"+i,i==2));
        }
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
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
