package com.bsr.bsrcoin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bsr.bsrcoin.Fragments.DepositMainFragment
import com.bsr.bsrcoin.Fragments.Insurancefragview
import com.bsr.bsrcoin.Fragments.SendMoneyFragment
import com.bsr.bsrcoin.Fragments.ViewLoanFragment

class AgentFrame : AppCompatActivity() {
    val funtionid = "0"    //0 means deposit , 1 means fund transfer ,2 means Insurance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_frame)
        val funtionid = intent.getStringExtra("functionId")

        if(funtionid.equals("0"))//Deposit
        {
            supportActionBar?.setTitle("DEPOSIT")
            supportFragmentManager.beginTransaction().replace(R.id.agentFrame, DepositMainFragment()).commit()
        }
        else if(funtionid.equals("1"))
        {
            supportActionBar?.setTitle("FUNDS")
            supportFragmentManager.beginTransaction().replace(R.id.agentFrame, SendMoneyFragment()).addToBackStack(null).commit()
        }
        else if(funtionid.equals("2"))
        {
            supportActionBar?.setTitle("INSURANCE")
            supportFragmentManager.beginTransaction().replace(R.id.agentFrame, Insurancefragview()).commit()
        }
        else
        {
            supportActionBar?.setTitle("LOAN")
            supportFragmentManager.beginTransaction().replace(R.id.agentFrame, ViewLoanFragment()).commit()
        }
    }
}