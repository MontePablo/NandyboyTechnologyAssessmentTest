package com.bsr.bsrcoin.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.Adapter.InsuranceAdapter
import com.bsr.bsrcoin.Models.InsuranceModel
import com.bsr.bsrcoin.MysqlConst.Constants
import com.bsr.bsrcoin.R
import com.bsr.bsrcoin.SharedPrefmanager
import kotlinx.android.synthetic.main.activity_memebership.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class Insurancefragview : Fragment(),InsuranceAdapter.updateafterdecicion {
    var list: ArrayList<InsuranceModel> = ArrayList<InsuranceModel>()
    lateinit  var adapter:InsuranceAdapter
    lateinit var username:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_insurancefragview, container, false)
        val rv=view.findViewById<RecyclerView>(R.id.insurrecycler)
        rv.layoutManager= LinearLayoutManager(context)
        rv.setHasFixedSize(true)
        var con:Context= requireContext()
        adapter= InsuranceAdapter(con,this)
//        adapter.getInterfaceRef(this)
        rv.adapter=adapter
        username=SharedPrefmanager.getInstance(context).keyUsernameName
        getInsuranceData()
    return view
    }
    fun getInsuranceData(){
        Log.d("abcd","entered getdata")
        list.clear()
        Log.d("abcd","here")
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_insuracelist,
            Response.Listener { response ->
                try {
                    val jsonarray = JSONArray(response)
                    var len= jsonarray.length()
                    for(i in 0 until len )
                    {
                        val jsonobject= jsonarray.getJSONObject(i)
                        val username= jsonobject.getString("name")
                        val inid=jsonobject.getString("insuranceId")
                        var instatus=jsonobject.getString("insurance_status")
                        if(instatus.equals("0"))
                            instatus="Pending"
                        else if(instatus.equals("1"))
                            instatus="Agent Accepted"
                        else if(instatus.equals("2"))
                            instatus="Accepted"
                        else
                            instatus="Rejected"
                        val inamt=jsonobject.getString("insurance_amount")
                        val intype=jsonobject.getString("insurance_type")
                        val inwallet=jsonobject.getString("wallet")
                        val indur=jsonobject.getString( "duration")
                        val incurr=jsonobject.getString("currency")
                        "insuranceId"
                        var inmdl = InsuranceModel(inid,instatus,inamt,inwallet,indur,intype,incurr)
                        list.add(inmdl)
                    }
                    Log.d("abcd","in response")

                    adapter.updateList(list,requireContext())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> //progressDialog.dismiss();
                val e = error.toString()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                Log.d("abcd","in params ->>"+username)
                params["aname"] = username
                return params
            }
        }
        Log.d("abcd","request")
        val requestQueue = Volley.newRequestQueue(context)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 5000 }
            override fun getCurrentRetryCount(): Int { return 5000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun afterdecision() {
        Log.d("abcd","interface reached")
        this.getInsuranceData()
    }
}