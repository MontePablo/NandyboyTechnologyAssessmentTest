package com.bsr.bsrcoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bsr.bsrcoin.Adapter.ContactsAdapter;
import com.bsr.bsrcoin.Models.contacts;
import com.bsr.bsrcoin.MysqlConst.Constants;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminContacts extends AppCompatActivity {
    EditText edtuid;
    ContactsAdapter ca;
    ArrayList<contacts> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_contacts);
        Button btnGetContact= findViewById(R.id.btnGetContacts);
        RecyclerView rv= findViewById(R.id.rvcontact);
        edtuid=findViewById(R.id.edtuid);

        arrayList= new ArrayList<>();
        ca= new ContactsAdapter(this,arrayList);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setAdapter(ca);

        btnGetContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(edtuid.toString().equals(""))) {
                    getContactData();
                }
            }
        });


    }

    private void getContactData() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.url_getContacts,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray= new JSONArray(response);
                            for(int i=0;i<jsonarray.length();i++)
                            {
                                JSONObject jsonObject= jsonarray.getJSONObject(i);
                                String name= jsonObject.getString("contact_name");
                                String phone=jsonObject.getString("contact_phnum");
                                String uid=jsonObject.getString("userId");

                                contacts con= new contacts(uid,name,phone);
                                arrayList.add(con);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ca.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String e= error.toString();}
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid",edtuid.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(AdminContacts.this);
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
            public void retry(VolleyError error) throws VolleyError {}
        });
        requestQueue.add(stringRequest);
    }

}