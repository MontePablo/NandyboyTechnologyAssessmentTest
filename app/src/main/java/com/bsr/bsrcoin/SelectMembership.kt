package com.bsr.bsrcoin

import android.icu.number.IntegerWidth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.MysqlConst.Constants
import kotlinx.android.synthetic.main.activity_memebership.*
import kotlinx.android.synthetic.main.activity_select_membership.*
import org.json.JSONArray
import org.json.JSONException
import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SelectMembership : AppCompatActivity() {
    val MEM:String="member"
    var mid=0
    var user_id:String=""
    var cost=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_membership)
        val spf= getSharedPreferences(MEM,MODE_PRIVATE)
        mid=spf.getInt("mshipid",0)
        user_id= SharedPrefmanager.getInstance(this@SelectMembership).keyId

        pay_mcost.setOnClickListener {
            val c= Calendar.getInstance();
            val y=c.get(Calendar.YEAR)
            var m=c.get(Calendar.MONTH)
            val d=c.get(Calendar.DATE)
            m+=1
            val currentdate="$y-$m-$d"

            val sdf = SimpleDateFormat("yyyy-MM-dd")    //sdf is SimpleDateFormat Object
            var date: Date? = null
            try {
                date = sdf.parse(currentdate)                 //date from string to --> Date Format
            }
            catch (e: ParseException)
            { e.printStackTrace() }

            var millisecond_date = date!!.time               //total time till this date from 01/01/1970 [in ms].
            //31540000000 is 1 year in milliseconds
            millisecond_date+=31540000000         //TODO Adding 1 year to the start_date to get membership ending_date
            val ending_date= sdf.format(millisecond_date.toDouble())
            Toast.makeText(this,"$currentdate | $ending_date | $user_id | $mid",Toast.LENGTH_SHORT).show()

            val price=cost
            val mp = MembershipPayment(this,currentdate,ending_date,Integer.toString(mid),user_id,price)
            mp.makepayment()
        }
        getMembershipDetails()
    }

    private fun getMembershipDetails() {
        Toast.makeText(this, "Asking membership", Toast.LENGTH_LONG).show()
        memcost.text="try the cost"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_membershipDetail,
            Response.Listener { response ->
                Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                try {
                    val jsonarray = JSONArray(response)
                    for(i in 0 until jsonarray.length()){
                        val jsonobject = jsonarray.getJSONObject(i)
                        val id = jsonobject.getInt("memId")
                        val mname = jsonobject.getString("memName")
                        val mcost = jsonobject.getInt("cost")
                        cost=mcost.toString()
                        memname.text=mname
                        memcost.text="PRICE    Rs "+mcost.toString()
                        var img_resource=R.drawable.tiara
                        if(id==1)
                        img_resource=R.drawable.memcrown
                        else if(id==2)
                            img_resource=R.drawable.crownplatinum
                        else if(id==3)
                            img_resource=R.drawable.crowngld

                        typeimg.setImageResource(img_resource)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> //progressDialog.dismiss();
                Toast.makeText(this, "unsuccessful", Toast.LENGTH_LONG).show()
                val e = error.toString()
                Toast.makeText(this, "unsuccessful", Toast.LENGTH_LONG).show()
                Toast.makeText(this, e, Toast.LENGTH_LONG).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                Log.d("mem","here post $mid")
                params["mid"]=Integer.toString(mid)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 5000
            }

            override fun getCurrentRetryCount(): Int {
                return 5000
            }

            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        }
        requestQueue.add(stringRequest)
    }
}