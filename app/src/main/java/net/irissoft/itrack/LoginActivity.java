package net.irissoft.itrack;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    Button btnLogin;
    EditText ETuserid, ETpass;
    GPSTracker gps;
    String Username = "";
    String password = "";
    String id,Name;
    GlobalClass gs = new GlobalClass();
    String url = gs.getSERVER_URL() + "checkuser.php";

    String user = "";
    String pass = "";

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETuserid = findViewById(R.id.user);
        ETpass = findViewById(R.id.password);
        //ETuserid.setText("mahtab");
        //ETpass.setText("12345");

        gps = new GPSTracker(LoginActivity.this);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gps.canGetLocation()) {


                    Username = ETuserid.getText().toString().trim();
                    password = ETpass.getText().toString().trim();
                    if (Username.equals("") || password.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill All Field", Toast.LENGTH_SHORT).show();
                    } else {
                        usercheck(Username, password);
                        //   Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    gps.showSettingsAlert();
                }
            }
        });
    }

    public void usercheck(final String user, final String password) {

        GlobalClass Gs = new GlobalClass();
        //  Toast.makeText(getApplicationContext(), user + pass, Toast.LENGTH_LONG).show();

     //  String url = "http://10.168.27.211/ITrack/api/checkuser.php";

        //String url = Gs.getSERVER_URL() + "checkuser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logincheck(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AllError", error.toString());
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", user);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setShouldCache(false);
        queue.getCache().clear();

        queue.add(stringRequest);

    }

    public void Logincheck(String data) {


        if (data.equals("NODATA")) {
            Log.e("NODATA", data);
            AlertDialogMassage("InCorrect Username and Password ");
            ETuserid.requestFocus();


        } else {
            try {
                Log.e("DATA", data);
                JSONObject obj = new JSONObject(data);
                id = obj.getString("id");
                Name = obj.getString("Name");

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                alertDialog.setTitle("iTrack");
                alertDialog.setMessage("Welcome "+Name);

                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent I=new Intent(LoginActivity.this,MainActivity.class);
                        I.putExtra("User_id", id);
                        I.putExtra("User_name", Name);
                        startActivity(I);
                        finish();
                    }
                });

                alertDialog.show();


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
        }


    }


    public void AlertDialogMassage(String massage) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("iTrack");
        alertDialog.setMessage(massage);

        alertDialog.setCancelable(true);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
    }
}
