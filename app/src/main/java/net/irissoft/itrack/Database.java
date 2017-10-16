package net.irissoft.itrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahtab on 08-Oct-17.
 */

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;                     //DATABASE_VERSION
    private static final String DATABASE_NAME = "itrack";  //DATABASE_NAME
    Context Contex;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE Loaction (id INTEGER PRIMARY KEY,userid INTEGER ,latitude  TEXT, longitude TEXT,created_at DATETIME DEFAULT CURRENT_TIMESTAMP,Sync_status INTEGER )";
        db.execSQL(CREATE_CONTACTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Loaction");

        onCreate(db);
    }

    public void allLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userid", location.get_userid()); // userid
        values.put("latitude", location.get_latitude()); // latitude
        values.put("longitude", location.get_longitude()); // longitude
        values.put("created_at", location.get_created_at()); // time
        values.put("Sync_status", location.get_Sync_status()); // time
        db.insert("Loaction", null, values);
        db.close();
    }

    public List<Location> showallLocation(){
        List<Location>AllLocationlist=new ArrayList<>() ;

        String selectQuery = "SELECT * FROM Loaction";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location locations = new Location();
                locations.set_id(Integer.parseInt(cursor.getString(0)));
                locations.set_userid(Integer.parseInt(cursor.getString(1)));
                locations.set_latitude(cursor.getString(2));
                locations.set_longitude(cursor.getString(3));
                locations.set_created_at(cursor.getString(4));
                locations.set_Sync_status(Integer.parseInt(cursor.getString(5)));
                // Adding contact to list
                AllLocationlist.add(locations);
            } while (cursor.moveToNext());
        }
        db.close();

        return AllLocationlist;
    }

    public void deleteAll(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete("Loaction",null,null);
        db.close();
    }

    public void deleteLocationbyid(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete("Loaction","id="+id,null);
        db.close();
    }

    public List<Location> getLocationbyid(int id){

        List<Location>SingleLocationlist=new ArrayList<>() ;

        String selectQuery = "SELECT * FROM Loaction where id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location locations = new Location();
                locations.set_id(Integer.parseInt(cursor.getString(0)));
                locations.set_userid(Integer.parseInt(cursor.getString(1)));
                locations.set_latitude(cursor.getString(2));
                locations.set_longitude(cursor.getString(3));
                locations.set_created_at(cursor.getString(4));
                // Adding contact to list
                SingleLocationlist.add(locations);
            } while (cursor.moveToNext());
        }
        db.close();

        return SingleLocationlist;

    }
    public List<Location> getLocationbyidnitsync(int id){

        List<Location>SingleLocationlist=new ArrayList<>() ;

        String selectQuery = "SELECT * FROM Loaction where id="+id+" AND Sync_status=0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location locations = new Location();
                locations.set_id(Integer.parseInt(cursor.getString(0)));
                locations.set_userid(Integer.parseInt(cursor.getString(1)));
                locations.set_latitude(cursor.getString(2));
                locations.set_longitude(cursor.getString(3));
                locations.set_created_at(cursor.getString(4));
                locations.set_Sync_status(Integer.parseInt(cursor.getString(5)));
                // Adding contact to list
                SingleLocationlist.add(locations);
            } while (cursor.moveToNext());
        }
        db.close();

        return SingleLocationlist;

    }
    public void SendLocationbyid(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery = "SELECT * FROM id="+id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        db.close();
    }
    public int updatbyid(int id) {
        SQLiteDatabase db=this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("Sync_status",1);

        db.update("Loaction", cv, "id ="+id, null);
        db.close();
        return 1;
    }


    public List<Location> getAutoLocationsync(){

        List<Location>SingleLocationlist=new ArrayList<>() ;

        String selectQuery = "SELECT * FROM Loaction where Sync_status=0 LIMIT 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location locations = new Location();
                locations.set_id(Integer.parseInt(cursor.getString(0)));
                locations.set_userid(Integer.parseInt(cursor.getString(1)));
                locations.set_latitude(cursor.getString(2));
                locations.set_longitude(cursor.getString(3));
                locations.set_created_at(cursor.getString(4));
                locations.set_Sync_status(Integer.parseInt(cursor.getString(5)));
                // Adding contact to list
                SingleLocationlist.add(locations);
            } while (cursor.moveToNext());
        }
        db.close();
        return SingleLocationlist;

    }

}
