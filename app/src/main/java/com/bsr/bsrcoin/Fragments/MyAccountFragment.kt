package com.bsr.bsrcoin.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.*
import com.bsr.bsrcoin.MysqlConst.Constants
import com.bsr.bsrcoin.databinding.FragmentMyAccountBinding
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.synthetic.main.fragment_my_account.*
import org.json.JSONObject


class MyAccountFragment : Fragment(),View.OnClickListener {

    private val TAG = "MyAccountFragment"
    private lateinit var v: View
    private lateinit var binding : FragmentMyAccountBinding
    var con: Context?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "Myaccount  1", Toast.LENGTH_SHORT).show()
        con=context
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater,container,false)
        v = inflater.inflate(R.layout.fragment_my_account, container, false)
        v.findViewById<TextView>(R.id.lastlogintime).text = SharedPrefmanager.getInstance(context).
                                                            getLl(
                                                                SharedPrefmanager.getInstance(
                                                                    context
                                                                ).keyId)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, "Myaccount  2", Toast.LENGTH_SHORT).show()

        val userDpUri = SharedPrefmanager.getInstance(context)
            .dpUri(SharedPrefmanager.getInstance(context).keyId)
        if(!(userDpUri.equals(""))){
            val uri = Uri.parse(userDpUri)
            view.findViewById<CircularImageView>(R.id.userdp).setImageURI(uri)
        }

        Toast.makeText(context, "Myaccount  3", Toast.LENGTH_SHORT).show()

        val userAcc = view.findViewById<TextView>(R.id.accNumber)
        userAcc.text = SharedPrefmanager.getInstance(context).keyAccount

        view.findViewById<CircularImageView>(R.id.userdp).setOnClickListener(this)
        view.findViewById<CardView>(R.id.myac).setOnClickListener(this)
        view.findViewById<CardView>(R.id.pay_bill).setOnClickListener(this)
        view.findViewById<CardView>(R.id.user).setOnClickListener(this)
        view.findViewById<CardView>(R.id.chequeServ).setOnClickListener(this)
        view.findViewById<CardView>(R.id.fund_transfer).setOnClickListener(this)
        view.findViewById<CardView>(R.id.fundMgmt).setOnClickListener(this)
        view.findViewById<CardView>(R.id.converter).setOnClickListener(this)
        view.findViewById<CardView>(R.id.coin).setOnClickListener(this) // from here subham started
        view.findViewById<CardView>(R.id.membershipc).setOnClickListener(this)
        view.findViewById<CardView>(R.id.ActivateRef).setOnClickListener(this)
        val ll=view.findViewById<LinearLayout>(R.id.linearAgentAdmin)

        val isAgent = SharedPrefmanager.getInstance(context).isAgent
        val inflator= context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if(isAgent==true)
        {
            val inflator= context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val Card=inflator.inflate(R.layout.agentcorner,null) as RelativeLayout
            Card.setOnClickListener {
                val intent = Intent(context,AgentCorner::class.java)
                startActivity(intent)
            }
            ll.addView( Card,ll.childCount)
        }
        else if(SharedPrefmanager.getInstance(context).isAdmin)
        {

            val Card=inflator.inflate(R.layout.admin_contacts,null) as RelativeLayout
            Card.setOnClickListener {
                val intent = Intent(context,AdminContacts::class.java)
                startActivity(intent)
            }
            ll.addView( Card,ll.childCount)
        }

        Toast.makeText(context, "Myaccount  4", Toast.LENGTH_SHORT).show()

        if(SharedPrefmanager.getInstance(requireContext().applicationContext).isAdmin){
            setHasOptionsMenu(true)
        }
        sendProfileRequest(SharedPrefmanager.getInstance(context).keyId)

    }

    override fun onClick(p0: View?) {
        Toast.makeText(context, "Myaccount  onclick", Toast.LENGTH_SHORT).show()

        when(p0?.id){
            R.id.fund_transfer -> { parentFragmentManager.beginTransaction().
                                    replace(R.id.Frame, SendMoneyFragment()).addToBackStack(null).commit()}
            R.id.myac -> {parentFragmentManager.beginTransaction()
                            .replace(R.id.Frame, MyAccFragment()).addToBackStack(null).commit()}
            R.id.pay_bill -> {
                Toast.makeText(context,"Will be implemented soon...",Toast.LENGTH_SHORT).show()}
            R.id.chequeServ -> {
                parentFragmentManager.beginTransaction().replace(R.id.Frame, ChequeFragment()).addToBackStack(null).commit() }
            R.id.fundMgmt -> {
                parentFragmentManager.beginTransaction().replace(R.id.Frame,FundManagementFragment()).addToBackStack(null).commit()

            }

            R.id.user -> {parentFragmentManager.beginTransaction()
                .replace(R.id.Frame, UserFragment()).addToBackStack(null).commit()}
            R.id.userdp ->{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.Frame, UserFragment()).addToBackStack(null).commit()
            }
            R.id.converter -> {
                parentFragmentManager.beginTransaction().replace(R.id.Frame, Convertor()).commit()
            }
            R.id.coin->{
                Log.d("coin", "listened" )
                val intent = Intent(context,CoinData::class.java)
                Log.d("coin", "listened2" )
                startActivity(intent)
            }
            R.id.membershipc->{
                Log.d("mem", "listened" )
                val intent = Intent(context,Memebership::class.java)
                Log.d("mem", "listened2" )
                startActivity(intent)

            }
            R.id.ActivateRef->{
                Log.d("mem", "listened444" )
                val intent = Intent(context,ActivateReferal::class.java)
                Log.d("mem", "listened666" )
                startActivity(intent)
            }
//            R.id.agentcorner->{
//                val intent = Intent(context,AgentCorner::class.java)
//                Log.d("agnt", "agentlistened" )
//                startActivity(intent)
//            }
//            R.id.query ->
//            {
//                val intent = Intent(context,ChatActivity::class.java)
//                startActivity(intent)
//            }
            }
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.request, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.addMoneyRequest -> {
                parentFragmentManager.beginTransaction().replace(R.id.Frame, AddMoneyRequestFragment()).addToBackStack(null).commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sendProfileRequest(userId: String?) {
        val url = Constants.url_get_userInfo + "?id=$userId"
        val queue = Volley.newRequestQueue(context)

        val request = StringRequest(Request.Method.GET, url,{ response ->
            val res = JSONObject(response)
            val array = res.getJSONArray("user")
            val obj = array.getJSONObject(0)

            val balance = obj.getInt("wallet1") + obj.getInt("wallet2") + obj.getInt("wallet3")
                            + obj.getInt("wallet4") + obj.getInt("wallet5")

            Log.e(TAG, "sendProfileRequest: $response" )
            v.findViewById<TextView>(R.id.username).text = "   " + obj.getString("name")
            v.findViewById<TextView>(R.id.Accbalance).text = "₹ "+ balance.toString()
//            if(obj.getString("lastLogin")==""){
//                v.findViewById<LinearLayout>(R.id.lastloginstat).visibility = View.GONE
//
//            }
//            else{
//                v.findViewById<TextView>(R.id.lastlogintime).visibility= View.VISIBLE
//                v.findViewById<TextView>(R.id.lastlogintime).text = obj.getString("lastLogin")
//            }



        },{error -> Log.e(TAG, "sendProfileRequest: $error" )})

        queue.add(request)

    }

}
