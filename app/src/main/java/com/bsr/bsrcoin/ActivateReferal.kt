package com.bsr.bsrcoin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.MysqlConst.Constants
import kotlinx.android.synthetic.main.activity_activate_referal.*
import kotlinx.android.synthetic.main.activity_select_membership.*
import org.json.JSONArray
import org.json.JSONException
import org.w3c.dom.Text

class ActivateReferal : AppCompatActivity() {

    //Amount to be rewarded
    var N= 258
    var uidList:ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate_referal)
        checkIfActivated()
        Log.d("ref","Activating One ")
    }

    //CHECKING IF THE USER IS HAVING HIS/HER REFERAL ACTIVATED OR NOT
    fun checkIfActivated() {
        linearlayoutReferal.removeAllViews()
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_checkRefActivation,
            Response.Listener { response ->
                Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                var res=response.toString()
                if(!(res.equals(""))) {
                    try {
                        val jsonarray = JSONArray(response)
                        for (i in 0 until jsonarray.length()) {
                            val jsonobject = jsonarray.getJSONObject(i)
                            var activated = jsonobject.getString("activated")
                            if (activated.equals("1")) {
                                val inflator= getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                                val referralCard=inflator.inflate(R.layout.referalcode,null)
                                linearlayoutReferal.addView(referralCard,linearlayoutReferal.childCount)
                                val tv=referralCard.findViewById<TextView>(R.id.tvrcode)
                                //GETTING THE REFERAL CODE
                                val stringRequest: StringRequest = object : StringRequest(
                                    Method.POST,
                                    Constants.url_getUserReferalCode,
                                    Response.Listener { response ->
                                            try {
                                                val jsonarray = JSONArray(response)
                                                for (i in 0 until jsonarray.length()) {
                                                    val jsonobject = jsonarray.getJSONObject(i)
                                                    var rcode = jsonobject.getString("referal_code")
                                                    tv.setText("REFERAL CODE  "+rcode)
                                                }
                                            } catch (e: JSONException) { e.printStackTrace() }
                                    },
                                    Response.ErrorListener { error ->
                                        val e = error.toString()
                                    }
                                ) {
                                    @Throws(AuthFailureError::class)
                                    override fun getParams(): Map<String, String>? {
                                        val params: MutableMap<String, String> = HashMap()
                                        params["uid"]=SharedPrefmanager.getInstance(this@ActivateReferal).keyId
                                        return params
                                    }
                                }
                                val requestQueue = Volley.newRequestQueue(this)
                                stringRequest.retryPolicy = object : RetryPolicy {
                                    override fun getCurrentTimeout(): Int { return 5000 }
                                    override fun getCurrentRetryCount(): Int { return 5000 }
                                    @Throws(VolleyError::class)
                                    override fun retry(error: VolleyError) {} }
                                requestQueue.add(stringRequest)

                            } else {
                                val inflator= getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                                val pay_FORreferralCard=inflator.inflate(R.layout.pay_for_referal,null)
                                linearlayoutReferal.addView(pay_FORreferralCard,linearlayoutReferal.childCount)
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                else
                {
                    val inflator= getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val pay_FORreferralCard=inflator.inflate(R.layout.pay_for_referal,null)
                    linearlayoutReferal.addView(pay_FORreferralCard,linearlayoutReferal.childCount)
                }
            },
            Response.ErrorListener { error ->
                val e = error.toString()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["uid"]=SharedPrefmanager.getInstance(this@ActivateReferal).keyId
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 5000 }
            override fun getCurrentRetryCount(): Int { return 5000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {} }
        requestQueue.add(stringRequest)
    }



    //CONTAINS THE LIST OF USERS IN THE REFERAL CHAIN
    var mp: MutableMap<String, String> = HashMap()

    //GET THE REFERAL CHAIN OF USERS AND THE PROCESS THEM
    private fun getUserData_AND_ProcessReferal() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_getAllUser,
            Response.Listener { response ->
                Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                try {
                    val jsonarray = JSONArray(response)
                    for(i in 0 until jsonarray.length()){
                        val jsonobject = jsonarray.getJSONObject(i)
                        var value= jsonobject.getString("refered_by")
                        val key = jsonobject.getInt("userId").toString()
                        mp.put(key,value);
                    }
                    processing_referal()
                    updateRefActivation()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> val e = error.toString() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
//                Log.d("mem","here post $mid")
//                params["mid"]=Integer.toString(mid)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 5000 }
            override fun getCurrentRetryCount(): Int { return 5000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {}
        }
        requestQueue.add(stringRequest)
    }

    //UPDATING USER'S REFERAL ACTIVATION CODE TO 1
    private fun updateRefActivation() {
       //TODO("UPDATE USER'S REFERAL ACTIVATION CODE TO 1")
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_updateReferral,
            Response.Listener { response -> Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                              checkIfActivated()
                              },
            Response.ErrorListener { error -> val e = error.toString() }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["uid"]=SharedPrefmanager.getInstance(this@ActivateReferal).keyId
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 5000 }
            override fun getCurrentRetryCount(): Int { return 5000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {}
        }
        requestQueue.add(stringRequest)
    }


    //HERE WE WILL FORM THE QUERY  REQUIRED TO UPDATE WALLET6 OF THE USERS
    fun processing_referal()
    {
        var query="update user SET wallet6=CASE "
        val user_id= SharedPrefmanager.getInstance(this@ActivateReferal).keyId
        var ptr: String? = user_id
        while (N>2 && !ptr.equals("null")) {
            var reward:String=N.toString()
            query = query+" WHEN userId="+ptr+" Then wallet6+"+reward+" "
            ptr = mp[ptr]
            N=N/2
        }
        query = query + " ELSE wallet6 END ;"
        processref(query)
    }

    private fun processref(query:String) {

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_ProcessReferal,
            Response.Listener { response ->
                Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                try {
                    val jsonarray = JSONArray(response)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> //progressDialog.dismiss();
                val e = error.toString()
                Toast.makeText(this, "unsuccessful", Toast.LENGTH_LONG).show()
                Toast.makeText(this, e, Toast.LENGTH_LONG).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
//                Log.d("mem","here post $mid")
                params["query"]=query
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 5000 }
            override fun getCurrentRetryCount(): Int { return 5000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {} }
        requestQueue.add(stringRequest)
    }

    // CALLED WHEN THE PAYMENT BUTTON IS CLICKED
    fun clicked(view:View)
    {
        //TODO   CALL THE PAYMENT METHOD TO GET THE PAYMENT FROM THE USER
        //TODO   ON SUCCESSFUL PAYMENT RUN THE `getUserDate_AND_ProcessReferal()` FUNCTION
        this.getUserData_AND_ProcessReferal()
    }

}