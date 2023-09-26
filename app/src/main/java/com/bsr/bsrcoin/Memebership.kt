package com.bsr.bsrcoin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.MysqlConst.Constants
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_memebership.*
import kotlinx.android.synthetic.main.activity_select_membership.*
import org.json.JSONArray
import org.json.JSONException
import java.text.ParseException
import java.util.*

class Memebership : AppCompatActivity() {
    val MEM:String="member"

    var user_id:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memebership)
        val spf= getSharedPreferences(MEM,MODE_PRIVATE)
        var editor= spf.edit();

        //GETTING USER ID FROM PREFERENCE
        user_id= SharedPrefmanager.getInstance(this@Memebership).keyId
        //CALLING THIS FUNCTION TO VALIDATE MEMBERSHIP
        MemebershipValidation();

        mem1.setOnClickListener{
            editor.putInt("mshipid",1);
            editor.apply();
            getMembership()
        }
        mem2.setOnClickListener {
            editor.putInt("mshipid",2);
            editor.apply();
            getMembership()
        }
        mem3.setOnClickListener {
            editor.putInt("mshipid",3);
            editor.apply();
            getMembership()
        }
        mem4.setOnClickListener {
            editor.putInt("mshipid",4);
            editor.apply();
            getMembership()
        }
    }
    fun getMembership()
    {
        val intent = Intent(this,SelectMembership::class.java)
        startActivity(intent)
    }
    //USING THIS FUNCTION TO CHECK IF THE USER HAVE ANY MEMBERSHIP OR NOT
    fun MemebershipValidation(){
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_verifyMembership,
            Response.Listener { response ->
                Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                try {
                    val jsonarray = JSONArray(response)
                    var len= jsonarray.length()
                    if(jsonarray.length()>0) // IF SIZE GREATER THAN 0 , MEANS MEMBERSHIP IS THERE
                    {
                        //SETTING OTHER VIEWS TO INVISIBLE AS CHOOSING ANOTHER MEMBERSHIP IS NOT ALLOWED IF ALREADY HAVING MEMBERSHIP
                        linearl2.visibility= View.INVISIBLE

                        val jsonobject = jsonarray.getJSONObject(len-1)
                        val id=jsonobject.getInt("mem_id")
                        val mname = jsonobject.getString("memName")
                        val sdate = jsonobject.getString("start_date")
                        val edate = jsonobject.getString("end_date")

                        val inflater= getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        var memCardView:View=inflater.inflate(R.layout.membercard,null) as RelativeLayout
                        var txtmemname:TextView= memCardView.findViewById(R.id.txtmem)
                        var txtsdate:TextView= memCardView.findViewById(R.id.sdate)
                        var txtedate:TextView= memCardView.findViewById(R.id.edate)
                        var imgmem:ImageView=memCardView.findViewById(R.id.imgmem)
                        txtmemname.text=mname
                        txtsdate.text="From  "+sdate
                        txtedate.text="Till     "+edate
                        var img_resource=R.drawable.tiara
                        if(id==1)
                            img_resource=R.drawable.memcrown
                        else if(id==2)
                            img_resource=R.drawable.crownplatinum
                        else if(id==3)
                            img_resource=R.drawable.crowngld
                        imgmem.setImageResource(img_resource)
                        Log.d("mem","going to insert into linear view")
                        linearl1.addView(memCardView, linearl1.childCount)
                    }
                    else { }
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
                params["uid"] = user_id
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 5000 }
            override fun getCurrentRetryCount(): Int { return 5000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        }
        requestQueue.add(stringRequest)
    }
}