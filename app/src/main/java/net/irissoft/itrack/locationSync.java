package net.irissoft.itrack;

import android.content.Context;
import android.util.Log;
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


public class locationSync {
    private final Context mContext;

    GlobalClass gs = new GlobalClass();
    String url = gs.getSERVER_URL() + "insertLocation.php";


    public locationSync(Context mContext) {
        this.mContext = mContext;

       // Toast.makeText(mContext,url,Toast.LENGTH_LONG).show();
    }


    public void updatbyid(String id){
        Database DBH = new Database(mContext);
        int i=DBH.updatbyid(Integer.parseInt(id));
    //    Toast.makeText(mContext, "Send Suessfully "+i, Toast.LENGTH_SHORT).show();
    }


    public void LocalToServerSingleSync(final String local_id, final String emp_id, final String latitude, final String longitude, final String time) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        updatbyid(local_id);
                        Toast.makeText(mContext, "Send Suessfully"+response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AllError", error.toString());
            //   Toast.makeText(mContext, "That didn't send!", Toast.LENGTH_SHORT).show();


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
        RequestQueue queue = Volley.newRequestQueue(mContext);
        stringRequest.setShouldCache(false);

        queue.add(stringRequest);

    }
}
