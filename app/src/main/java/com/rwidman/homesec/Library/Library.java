package com.rwidman.homesec.Library;

import android.util.Log;

import com.rwidman.homesec.Fragments.AccessFragment;
import com.rwidman.homesec.Fragments.LogEntryFragment;
import com.rwidman.homesec.Fragments.ModulFragment;
import com.rwidman.homesec.Fragments.PersonFragment;
import com.rwidman.homesec.Fragments.ProfileFragment;
import com.rwidman.homesec.Model.Access;
import com.rwidman.homesec.Model.Modul;
import com.rwidman.homesec.Tasks.CloseAccessTask;
import com.rwidman.homesec.Tasks.GetAccessesTask;
import com.rwidman.homesec.Tasks.GetLogsTask;
import com.rwidman.homesec.Tasks.GetModulesTask;
import com.rwidman.homesec.Tasks.GetPersonsTask;
import com.rwidman.homesec.Tasks.GetProfilesTask;
import com.rwidman.homesec.Tasks.OpenAccessTask;
import com.rwidman.homesec.Tasks.TakePhotoTask;


public class Library {

    private static Library lib;
    private int Port;

    public static String makeOrder(String order){
        return "1#gateway#remote#"+order+"#[]";
    }

    public static String makeOrder(String order,String parameter){
        return "1#gateway#remote#"+order+"#['"+parameter+"']";
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

    public void loadAccesses(AccessFragment context) {
        Log.d("Load", "Try starting accesstask");
        GetAccessesTask t = new GetAccessesTask(context, getPort());
        t.execute();
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

    public void openAccess(AccessFragment context, Access a) {
        Log.d("Open", "Try starting openaccesstask");
        OpenAccessTask t = new OpenAccessTask(context, getPort());
        t.execute(a);
    }

    public void closeAccess(AccessFragment context, Access a) {
        Log.d("Close", "Try starting closeaccesstask");
        CloseAccessTask t = new CloseAccessTask(context, getPort());
        t.execute(a);
    }

    public void takePhoto(ModulFragment context, Modul m){
        Log.d("Photo", "Try starting phototask");
        TakePhotoTask t = new TakePhotoTask(context, getPort());
        t.execute(m);
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
