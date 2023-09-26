package com.bsr.bsrcoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.MysqlConst.Constants
import kotlinx.android.synthetic.main.activity_agent_corner.*
import org.json.JSONArray
import java.util.HashMap

class AgentCorner : AppCompatActivity() {
    lateinit var Userid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_corner)
        Userid =SharedPrefmanager.getInstance(this).keyId
        checkAgentAllowance()
        agentwallet.setOnClickListener {
               val intent= Intent(this,agentWallet::class.java)
                startActivity(intent)
        }
        agentdeposit.setOnClickListener{
            val intent = Intent(this,AgentFrame::class.java)
            intent.putExtra("functionId","0")
            startActivity(intent)
        }
        agentfund.setOnClickListener {
            val intent = Intent(this,AgentFrame::class.java)
            intent.putExtra("functionId","1")
            startActivity(intent)
        }
        agentinsurance.setOnClickListener{
            val intent = Intent(this,AgentFrame::class.java)
            intent.putExtra("functionId","2")
            startActivity(intent)
        }
        agentloan.setOnClickListener {
            val intent = Intent(this,AgentFrame::class.java)
            intent.putExtra("functionId","3")
            startActivity(intent)
        }

    }

    private fun checkAgentAllowance() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_agentApproval,
            Response.Listener { response ->
               var jsonarray= JSONArray(response)
               for(i in 0 until jsonarray.length())
               {
                   var jsonobject=jsonarray.getJSONObject(i)
                   var feature =jsonobject.getString("feature")
                   var allowed=jsonobject.getString("allowed")
                   if(feature.equals("1"))
                   {
                       if(allowed.equals("yes"))
                           agentloan.visibility= View.VISIBLE
                   }
                   else
                   {
                       if(allowed.equals("yes"))
                           agentinsurance.visibility= View.VISIBLE
                   }
               }
            },
            Response.ErrorListener { error -> //progressDialog.dismiss();
                val e = error.toString()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["uid"] = Userid
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