package net.irissoft.itrack;

import android.app.Application;

public class GlobalClass    extends Application {

        private int _userid;
        private String _username;

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
}

