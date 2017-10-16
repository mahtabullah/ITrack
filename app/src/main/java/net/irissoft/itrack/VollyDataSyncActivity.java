package net.irissoft.itrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VollyDataSyncActivity extends AppCompatActivity {

    Button savebtn, syncbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volly_data_sync);

        //int local_id;

        syncbtn = (Button) findViewById(R.id.sync);
        syncbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationSync ls=new locationSync(getApplicationContext());
                ls.LocalToServerSingleSync("1","1","1","1","1");
            }
        });

        savebtn = (Button) findViewById(R.id.savebtn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String local_id = "1", emp_id = "1", latitude = "Test", longitude = "Test", time = "Test";
                // String url = "http://10.168.27.211/ITrack/api/insertData.php";
                String url = "http://10.168.27.211/ITrack/api/insertLocation.php";


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AllError", error.toString());
                        Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("local_id", local_id);
                        params.put("emp_id", emp_id);
                        params.put("latitude", latitude);
                        params.put("longitude", longitude);
                        params.put("time", time);

                        return params;
                    }

                };
                RequestQueue queue = Volley.newRequestQueue(VollyDataSyncActivity.this);

                queue.add(stringRequest);
            }
        });


    }
}
