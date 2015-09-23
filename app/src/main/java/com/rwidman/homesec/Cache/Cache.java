package com.rwidman.homesec.Cache;

import android.util.Log;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Fragments.LogEntryFragment;
import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Fragments.PersonFragment;
import com.rwidman.homesec.Fragments.ProfileFragment;
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
    private int Port;

    private List<Modul> moduls = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Access> accesses = new ArrayList<>();
    private List<LogEntry> logEntries = new ArrayList<>();
    private List<Profile> profiles = new ArrayList<>();


    public static String makeOrder(String order){
        return "1#gateway#remote#"+order+"#[]";
    }

    public void loadModuls(ModulFragment context) {
        Log.d("Cache", "Try starting modulestask");
        GetModulesTask t = new GetModulesTask(context, getPort());
        t.execute();
    }

    public List<Person> loadPersons(PersonFragment context) {
        for (int i = 0;i<5;i++){
            persons.add(new Person("Person"+i));
        }
        return persons;
    }

    public List<Access> loadAccesses(AccessFragment context) {
        for (int i = 0;i<5;i++){
            accesses.add(new Access("Access"+1,"offenOderZu"));
        }
        return accesses;
    }

    public List<LogEntry> loadLogEntries(LogEntryFragment context) {
        for (int i = 0;i<5;i++){
            logEntries.add(new LogEntry(Integer.toString(i),"Modul"+i,"Topic"+i,"15.09.23_11-48-47_","Meldungstext"+i,"_ID"+i));
        }
        return logEntries;
    }

    public List<Profile> loadProfiles(ProfileFragment context) {
        for (int i = 0;i<5;i++){
            profiles.add(new Profile("Profile"+i,i==2));
        }
        return profiles;
    }

    public static Cache getInstance()
    {
        if(cache == null)
        {
            cache = new Cache();
        }
        return cache;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }
}
