package com.bsr.bsrcoin;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.common.Method;
import com.bsr.bsrcoin.MysqlConst.Constants;

import java.util.HashMap;
import java.util.Map;

public class MembershipPayment {
    Context context;
    String startdate;
    String enddate;
    String memid;
    String userid;
    String price;
    public MembershipPayment(Context context, String startdate, String enddate, String memid, String userid,String price) {
        this.context = context;
        this.startdate = startdate;
        this.enddate = enddate;
        this.memid = memid;
        this.userid = userid;
        this.price=price;
    }
    public void makepayment()
    {  StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            Constants.url_insert_NewMembership_Detail,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
    ){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("uid",userid);
            params.put("mid", memid);
            params.put("startdate",startdate);
            params.put("enddate",enddate);
            params.put("price",price);
            return params;
        }
    };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(stringRequest);
    }
}
