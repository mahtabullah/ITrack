package net.irissoft.itrack;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShowAllDataActivity extends AppCompatActivity {

    Button showall, clearall, syncall;
    Handler h = new Handler();

    TableLayout tl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_data);


        tl = (TableLayout) findViewById(R.id.dataTable);
        showdataTableheader();
        showallData();
        showall = (Button) findViewById(R.id.showall);
        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tl.removeAllViews();
                showdataTableheader();
                showallData();
            }
        });


        syncall = (Button) findViewById(R.id.syncall);
        syncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // h.removeCallbacks(autosync);
              //  h.postDelayed(autosync, 0);
                startsync();

            }
        });


        clearall = (Button) findViewById(R.id.clearall);
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteallData();


            }
        });


    }



    void showallData() {
        Database DBH = new Database(getApplicationContext());
        String data = "";
        List<Location> Conts = DBH.showallLocation();

        for (Location Cont : Conts) {
            data += "Id: " + Cont.get_id() + " latitude: " + Cont.get_latitude() + "longitude: " + Cont.get_longitude() + "Date: " + Cont.get_created_at() + "\n";

            showdataTable(Cont.get_id(), Cont.get_userid(), Cont.get_latitude(), Cont.get_longitude(), Cont.get_created_at(), Cont.get_Sync_status());
        }
        // Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
    }

    void DeleteallData() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Delete item?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Database DBH = new Database(getApplicationContext());
                DBH.deleteAll();
                tl.removeAllViews();
                Toast.makeText(getApplicationContext(), "Row Delete All Successfully: ", Toast.LENGTH_SHORT).show();

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }

        });
        alertDialog.show();

    }


    void showdataTableheader() {
        TableRow tableRow = new TableRow(getApplicationContext());
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        tableRow.setBackgroundColor(Color.parseColor("#ffffff"));
        tableRowParams.setMargins(2, 2, 2, 2);
        tableRow.setLayoutParams(tableRowParams);


        TextView tvId = new TextView(getApplicationContext());
        TextView tvUserId = new TextView(getApplicationContext());
        TextView tvlatitude = new TextView(getApplicationContext());
        TextView tvlongitude = new TextView(getApplicationContext());
        TextView tvDate = new TextView(getApplicationContext());
        TextView tvSync = new TextView(getApplicationContext());
        TextView tvAction = new TextView(getApplicationContext());
        TextView tvAction2 = new TextView(getApplicationContext());


        tvId.setText("ID");
        tvId.setTextColor(Color.BLACK);

        tvUserId.setText("UserId");
        tvUserId.setTextColor(Color.BLACK);


        tvlatitude.setText("Latitude");
        tvlatitude.setTextColor(Color.BLACK);

        tvlongitude.setText("Longitude");
        tvlongitude.setTextColor(Color.BLACK);

        tvDate.setText("Date");
        tvDate.setTextColor(Color.BLACK);

        tvSync.setText("Sync");
        tvSync.setTextColor(Color.BLACK);

        tvAction.setText("Show");
        tvAction.setTextColor(Color.BLACK);

        tvAction2.setText("Delete");
        tvAction2.setTextColor(Color.BLACK);


        tl.addView(tableRow);

        tableRow.addView(tvId);
        tableRow.addView(tvUserId);
        tableRow.addView(tvlatitude);
        tableRow.addView(tvlongitude);
        tableRow.addView(tvDate);
        tableRow.addView(tvSync);
        tableRow.addView(tvAction);
        tableRow.addView(tvAction2);


    }

    void showdataTable(int id, int userid, String lat, String lon, String Date, int Sync_data) {

        TableRow tableRow = new TableRow(getApplicationContext());
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableRow.setBackgroundColor(Color.parseColor("#ffffff"));
        tableRowParams.setMargins(2, 2, 2, 2);
        tableRow.setLayoutParams(tableRowParams);

        TextView tvId = new TextView(getApplicationContext());
        TextView tvUserId = new TextView(getApplicationContext());
        TextView tvlatitude = new TextView(getApplicationContext());
        TextView tvlongitude = new TextView(getApplicationContext());
        TextView tvDate = new TextView(getApplicationContext());
        TextView tvSync = new TextView(getApplicationContext());


        Button Btnshow = new Button(getApplicationContext());
        Btnshow.setText("Show & Sync");
        Btnshow.setId(id);
        Btnshow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        Btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = v.getId();
                getLocationbyid(id);


            }
        });
        Button BtnDelete = new Button(getApplicationContext());
        BtnDelete.setText("X");
        BtnDelete.setId(id);
        BtnDelete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = v.getId();
                //  getLocationbyid(id);
                deletesingleLocation(id, v);

            }
        });

        tvId.setText("id-" + Integer.toString(id));
        tvId.setTextColor(Color.BLACK);
        tvId.setPadding(5, 0, 5, 0);

        tvUserId.setText(Integer.toString(userid));
        tvUserId.setTextColor(Color.BLACK);

        tvlatitude.setText(lat);
        tvlatitude.setTextColor(Color.BLACK);
        tvlatitude.setPadding(5, 0, 5, 0);

        tvlongitude.setText(lon);
        tvlongitude.setTextColor(Color.BLACK);
        tvlongitude.setPadding(5, 0, 5, 0);

        tvDate.setText(Date);
        tvDate.setTextColor(Color.BLACK);
        tvDate.setPadding(5, 0, 5, 0);

        tvSync.setText(Integer.toString(Sync_data));
        tvSync.setTextColor(Color.BLACK);
        tvSync.setPadding(5, 0, 5, 0);


        tl.addView(tableRow);

        tableRow.addView(tvId);
        tableRow.addView(tvUserId);
        tableRow.addView(tvlatitude);
        tableRow.addView(tvlongitude);
        tableRow.addView(tvDate);
        tableRow.addView(tvSync);
        tableRow.addView(Btnshow);
        tableRow.addView(BtnDelete);


    }

    void deletesingleLocation(final int id, final View v) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Delete item?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Database dbh = new Database(getApplicationContext());
                dbh.deleteLocationbyid(id);  //data delete


                final TableRow parent = (TableRow) v.getParent();  //row delet
                int id = v.getId();
                tl.removeView(parent);
                Toast.makeText(getApplicationContext(), "Row Delete Successfully ID : " + Integer.toString(id), Toast.LENGTH_SHORT).show();

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }

        });
        alertDialog.show();


    }

    void getLocationbyid(int id) {
        Database DBH = new Database(getApplicationContext());
        locationSync ls = new locationSync(getApplicationContext());


        List<Location> Conts = DBH.getLocationbyidnitsync(id);

        for (Location Cont : Conts) {
            ls.LocalToServerSingleSync(Integer.toString(Cont.get_id()), Integer.toString(Cont.get_userid()), Cont.get_latitude(), Cont.get_longitude(), Cont.get_created_at());
            //data = "Id: " + Cont.get_id() + " latitude: " + Cont.get_latitude() + "longitude: " + Cont.get_longitude() + "Date: " + Cont.get_created_at() + "\n";
        }
        //  Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
    }

    void Syncall() {
        Database DBH = new Database(getApplicationContext());
        locationSync ls = new locationSync(getApplicationContext());
        GlobalClass GS = new GlobalClass();

        List<Location> Conts = DBH.getAutoLocationsync();

        for (Location Cont : Conts) {
            ls.LocalToServerSingleSync(Integer.toString(Cont.get_id()), Integer.toString(Cont.get_userid()), Cont.get_latitude(), Cont.get_longitude(), Cont.get_created_at());
        }


    }

    void startsync() {
        h.removeCallbacks(autosync);
        h.postDelayed(autosync, 5000);
    }
    void stopsync() {
        Toast.makeText(getApplicationContext(),"Auto Sync Stoped",Toast.LENGTH_SHORT).show();
        h.removeCallbacks(autosync);
    }

    Runnable autosync = new Runnable() {
        @Override
        public void run() {
            Syncall();
            h.postDelayed(this, 10000);
        }
    };


}
