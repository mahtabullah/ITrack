package net.irissoft.itrack;

import android.app.Application;

public class GlobalClass extends Application {

        private int _userid;
       private String _username;
       private static final String SERVER_URL="http://ap.irissoft.net/api/";
    //private static final String SERVER_URL="http://10.168.27.211/ITrack/api/";



    public GlobalClass() {
    }

    public void set_userid(int userid) {
        this._userid = userid;
    }

    public int get_userid() {
        return _userid;
    }

    public void set_username(String username) {
        this._username = username;
    }

    public String get_username() {
        return _username;
    }

    public String getSERVER_URL() {        return SERVER_URL;    }
}

