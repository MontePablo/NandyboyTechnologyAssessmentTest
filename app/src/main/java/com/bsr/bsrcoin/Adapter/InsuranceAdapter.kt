package com.bsr.bsrcoin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.Models.InsuranceModel
import com.bsr.bsrcoin.MysqlConst.Constants
import com.bsr.bsrcoin.R
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class InsuranceAdapter(contextx: Context, private var ref:updateafterdecicion):RecyclerView.Adapter<InsuranceAdapter.LoanInsuranceViewHolder>(){
    var insuranceList= arrayListOf<InsuranceModel>()
//    lateinit var ref: updateafterdecicion
    lateinit var contextx:Context
    class LoanInsuranceViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvinstatus:TextView=view.findViewById(R.id.instat)
        val tvinamount:TextView=view.findViewById(R.id.inamount)
        val tvintype:TextView=view.findViewById(R.id.instype)
        val tvinduration:TextView=view.findViewById(R.id.indur)
        val tvinwallet:TextView=view.findViewById(R.id.inwall)
        val tvincurrency:TextView=view.findViewById(R.id.incurrency)
        val accept: Button =view.findViewById(R.id.inaccpt)
        val reject: Button=view.findViewById(R.id.inrejct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanInsuranceViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.insurancecard,parent,false)
        return LoanInsuranceViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoanInsuranceViewHolder, position: Int) {
        val  insurancem=insuranceList[position]
        holder.tvinstatus.text=insurancem.getInstatus()
        if(!(insurancem.getInstatus().equals("Pending")))
        {
            holder.accept.visibility=View.INVISIBLE
            holder.reject.visibility=View.INVISIBLE
        }
        holder.tvinamount.text=insurancem.getInamt()
        holder.tvintype.text=insurancem.getType()
        holder.tvinduration.text=insurancem.getDuration()
        holder.tvinwallet.text=insurancem.getWallet()
        holder.tvincurrency.text=insurancem.getCurrency()

        holder.accept.setOnClickListener {
            //TODO: Code to be executed when loan/insurance accepted
            updateinsurance("1",insurancem.insuranceId)
        }
        holder.reject.setOnClickListener {
            //TODO:Code to be executed when loan/insurance rejected
            updateinsurance("3",insurancem.insuranceId)
        }
    }

    override fun getItemCount(): Int {
        return insuranceList.size
    }
    fun updateList(updateList:List<InsuranceModel>,cont: Context){
        this.contextx=cont
        insuranceList.clear()
        insuranceList.addAll(updateList)
        notifyDataSetChanged()
    }
    fun getInterfaceRef(r:updateafterdecicion)
    { ref=r }

    fun updateinsurance(decision:String,inid:String){
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_insuraceApproval,
            Response.Listener { response ->
                ref.afterdecision()
            },
            Response.ErrorListener { error -> //progressDialog.dismiss();
                val e = error.toString()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["dec"] = decision
                params["inid"]=inid
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(contextx)
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int { return 5000 }
            override fun getCurrentRetryCount(): Int { return 5000 }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
            }
        }
        requestQueue.add(stringRequest)

    }
    interface updateafterdecicion{
        abstract fun afterdecision()
    }
}