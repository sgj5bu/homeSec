package com.rwidman.homesec.Library;

import android.util.Log;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Fragments.LogEntryFragment;
import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Fragments.PersonFragment;
import com.rwidman.homesec.Fragments.ProfileFragment;
import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.Model.LogEntry;
import com.rwidman.homesec.Model.Person;
import com.rwidman.homesec.Model.Profile;
import com.rwidman.homesec.Tasks.GetLogsTask;
import com.rwidman.homesec.Tasks.GetModulesTask;
import com.rwidman.homesec.Tasks.GetPersonsTask;
import com.rwidman.homesec.Tasks.GetProfilesTask;

import java.util.ArrayList;
import java.util.List;


public class Library {

    private static Library lib;
    private int Port;

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

    public void loadPersons(PersonFragment context) {
        Log.d("Load", "Try starting personstask");
        GetPersonsTask t = new GetPersonsTask(context, getPort());
        t.execute();
    }

    public List<Access> loadAccesses(AccessFragment context) {
        for (int i = 0;i<5;i++){
            accesses.add(new Access("Access"+1,"offenOderZu"));
        }
        return accesses;
    }

    public void loadLogEntries(LogEntryFragment context) {
        Log.d("Load", "Try starting logtask");
        GetLogsTask t = new GetLogsTask(context, getPort());
        t.execute();
    }

    public void loadProfiles(ProfileFragment context) {
        Log.d("Load", "Try starting profiletask");
        GetProfilesTask t = new GetProfilesTask(context, getPort());
        t.execute();
    }

    public static Library getInstance()
    {
        if(lib == null)
        {
            lib = new Library();
        }
        return lib;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }
}
