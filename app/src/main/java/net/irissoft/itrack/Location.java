package net.irissoft.itrack;

/**
 * Created by Mahtab on 08-Oct-17.
 */

public class Location {

    int _id;
    int _userid;
    String _latitude;
    String _longitude;
    String _created_at;

    public Location() {

    }

    public Location(int id, int userid, String latitude, String longitude, String created_at) {
        this._id = id;
        this._userid = userid;
        this._latitude = latitude;
        this._longitude = longitude;
        this._created_at = created_at;
    }

    public Location(int userid, String latitude, String longitude, String created_at) {
        this._userid = userid;
        this._latitude = latitude;
        this._longitude = longitude;
        this._created_at = created_at;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public void set_userid(int userid) {
        this._userid = userid;
    }

    public int get_userid() {
        return _userid;
    }

    public String get_latitude() {
        return _latitude;
    }

    public void set_latitude(String latitude) {
        this._latitude = latitude;
    }

    public String get_longitude() {
        return _longitude;
    }

    public void set_longitude(String longitude) {
        this._longitude = longitude;
    }

    public String get_created_at() {
        return _created_at;
    }

    public void set_created_at(String created_at) {
        this._created_at = created_at;
    }

}
