package com.bsr.bsrcoin

import android.content.Context
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
import kotlinx.android.synthetic.main.activity_agent_wallet.*
import kotlinx.android.synthetic.main.activity_memebership.*
import org.json.JSONArray
import org.json.JSONException
import java.text.ParseException
import java.util.*

class agentWallet : AppCompatActivity() {
    var user_id="1";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_wallet)
        user_id = SharedPrefmanager.getInstance(this@agentWallet).keyId
        Log.d("agen","getting wallet data")

        getWalletData();
    }

    private fun getWalletData() {
        Log.d("agen","inside wallet data")

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_AgentWallet,
            Response.Listener { response ->
                Log.d("agen","wallet successful")
                try {
                    Log.d("agen","getting array")
                    var s= response.toString()
                    Log.d("agen","got array"+response)
                    val jsonarray = JSONArray(response)
                    Log.d("agen","got array")

                    for (i in 0 until jsonarray.length()) {
                        Log.d("agen","in loop")
                        val jsonobject = jsonarray.getJSONObject(i)
                        val walletname:String=jsonobject.getString("wallet_name");
                        val walletcoin:String=jsonobject.getString("coin");
                        Log.d("agen","got data")
                        if(walletname.equals("wallet1"))
                        {
                            var str="WALLET1"+"   "+walletcoin;
                            wall1.setText(str)
                        }
                        else if(walletname.equals("wallet2"))
                        {
                            var str="WALLET2"+"   "+walletcoin;
                            wall2.setText(str)
                        }
                        else if(walletname.equals("wallet3"))
                        {
                            var str="WALLET3"+"   "+walletcoin;
                            wall3.setText(str)
                        }else if(walletname.equals("wallet4"))
                        {
                            var str="WALLET4"+"   "+walletcoin;
                            wall4.setText(str)
                        }else if(walletname.equals("wallet5"))
                        {
                            var str="WALLET5"+"   "+walletcoin;
                            wall5.setText(str)
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.d("agen","error in response")
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
                Log.d("agen","inserting userid")
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