package com.bsr.bsrcoin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.bsr.bsrcoin.Fragments.*
import com.bsr.bsrcoin.MysqlConst.Constants
import com.bsr.bsrcoin.databinding.ActivityHomeScreenBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    lateinit var DrawerLayout:DrawerLayout
    lateinit var Toolbar:Toolbar
    lateinit var Frame:FrameLayout
    lateinit var NavigationView:NavigationView
    lateinit var floatingActionButton : FloatingActionButton
    lateinit var binding: ActivityHomeScreenBinding

    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        initialise()
        setUpActionBar()


        var depoboolread = "true"
        var insuboolread = "true"
        var shareboolread = "true"
        var loanboolread = "true"

        findViewById<FloatingActionButton>(R.id.query).setOnClickListener {
            val intent = Intent(this@MainActivity,ChatActivity::class.java)
            startActivity(intent)
        }

        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,DrawerLayout,R.string.open_drawer,R.string.close_drawer)
        DrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        openMyaccount()
        Toast.makeText(this@MainActivity, "point1", Toast.LENGTH_SHORT).show()

        if(SharedPrefmanager.getInstance(this).isAdmin) {
            NavigationView.menu.clear()
            NavigationView.inflateMenu(R.menu.navigation_admin)
        }else{
            NavigationView.menu.clear()
            NavigationView.inflateMenu(R.menu.navigation_menu)
        }

        val id: String = SharedPrefmanager.getInstance(this).keyUsernameName + ChatActivity.salt + SharedPrefmanager.getInstance(this).keyId

        floatingActionButton.setOnClickListener{v ->
            if(SharedPrefmanager.getInstance(this).isAgent)
            {
                val intent = Intent(this, AdminChatUser::class.java)
                intent.putExtra("id",id)
                startActivity(intent)
            }
            else
            {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("id",id)
                startActivity(intent)
            }
        }
        Toast.makeText(this@MainActivity, "point2", Toast.LENGTH_SHORT).show()

        NavigationView.setNavigationItemSelectedListener {
            val frame=R.id.Frame

            val queue = Volley.newRequestQueue(this)
            val url = Constants.url_PermissionRead
            val JsonObjectRequest= JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener{response ->
                    var deporesponse = response["deposit"] as String
                    var insuranceresponse = response["insurance"] as String
                    var loanresponse = response["loan"] as String
                    var shareresponse = response["share"] as String

                    depoboolread=deporesponse.toString()
                    insuboolread=insuranceresponse.toString()
                    loanboolread=loanresponse.toString()
                    shareboolread=shareresponse.toString()
                },
                Response.ErrorListener { error ->

                }
            )
            queue.add(JsonObjectRequest)
            when(it.itemId)
        {
            R.id.Perm ->{
                supportFragmentManager.beginTransaction().replace(frame,Permissions()).commit()
                supportActionBar?.title="Permissions"
            }
            R.id.my_account->{openMyaccount()}
//            R.id.home->{openHome()}
            R.id.transactions->{supportFragmentManager.beginTransaction().replace(frame,TransactionsFragment()).commit()
                supportActionBar?.title="Transactions"}
            R.id.loans->{
                if(SharedPrefmanager.getInstance(this.applicationContext).isAdmin)
                    supportFragmentManager.beginTransaction().replace(frame, ViewLoanFragment()).commit()
                else
                    if (loanboolread=="true") {
                        supportFragmentManager.beginTransaction().replace(frame, LoanMainFragment())
                            .commit()
                        supportActionBar?.title = "Loans"
                    }
                else{
                        Toast.makeText(this@MainActivity, "Access Denied", Toast.LENGTH_SHORT).show()
                    }
            }
            R.id.insuranceagent->{
                if(SharedPrefmanager.getInstance(this.applicationContext).isAdmin)
                    supportFragmentManager.beginTransaction().replace(frame,ViewInsuranceFragment()).commit()
                else
                    if (insuboolread=="true") {
                        supportFragmentManager.beginTransaction()
                            .replace(frame, InsuranceMainFragment()).commit()

                        supportActionBar?.title = "Insurance"
                    }
                else{
                        Toast.makeText(this@MainActivity, "Access Denied", Toast.LENGTH_SHORT).show()
                    }
            }
            R.id.bond->{
                supportFragmentManager.beginTransaction().replace(frame, BondMainFragment()).commit()
                supportActionBar?.title="Bond"
            }
                R.id.depositagent->{
                    if (depoboolread=="true"){
                    supportFragmentManager.beginTransaction().replace(frame, DepositMainFragment()).commit()
                    supportActionBar?.title="Deposit"
                }
                else{
                    Toast.makeText(this@MainActivity, "Access Denied", Toast.LENGTH_SHORT).show()
                }
                }
                R.id.Share->{
                    if (depoboolread=="true") {
                        supportFragmentManager.beginTransaction().replace(frame, ShareMainFragment()).commit()
                        supportActionBar?.title = "Share"
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Access Denied", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.Contacts->{
                    val intent= Intent(this,UserContacts::class.java)
                    startActivity(intent)
                }
                R.id.logout->{
                    val alertDialog=AlertDialog.Builder(this@MainActivity)
                    alertDialog.setTitle("Exit?")
                    alertDialog.setMessage("Are you sure you want to exit app?")
                    alertDialog.setPositiveButton("Yes"){text,listener->
                        if(SharedPrefmanager.getInstance(this@MainActivity.applicationContext).logout()) {
                            ActivityCompat.finishAffinity(this)
                        }

                    }
                    alertDialog.setNegativeButton("No"){text,listener->
                        openMyaccount()
                    }
                    alertDialog.setOnCancelListener{
                        openMyaccount()
                    }
                    alertDialog.show()
                }
        }
            DrawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        Toast.makeText(this@MainActivity, "point3s", Toast.LENGTH_SHORT).show()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home)
        {
            DrawerLayout.openDrawer(GravityCompat.START)

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpActionBar() {
        Toast.makeText(this@MainActivity, "point onactionbar", Toast.LENGTH_SHORT).show()

        setSupportActionBar(Toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount==0){
            super.onBackPressed()
        }
        else{
            supportFragmentManager.popBackStack()
        }

    }

    private fun openHome() {
        NavigationView.setCheckedItem(R.id.home)
        supportFragmentManager.beginTransaction().replace(R.id.Frame, MainFragment()).commit()
        supportActionBar?.title="Home"
    }

    private fun initialise() {
        Toast.makeText(this@MainActivity, "point initialise", Toast.LENGTH_SHORT).show()

        val sharedinstance = SharedPrefmanager.getInstance(this@MainActivity)
        DrawerLayout=findViewById(R.id.DrawerLayout)
        Toolbar=findViewById(R.id.Toolbar)
        Frame=findViewById(R.id.Frame)
        NavigationView=findViewById(R.id.NavigationView)
        val header = NavigationView.getHeaderView(0)
        header.findViewById<TextView>(R.id.tvUserName).text = sharedinstance.keyUsernameName
        header.findViewById<TextView>(R.id.tvUserAccount).text = sharedinstance.keyAccount
        if(sharedinstance.dpUri(sharedinstance.keyId)!=""){
        header.findViewById<ImageView>(R.id.ivUser).setImageURI(Uri.parse(sharedinstance.dpUri(sharedinstance.keyId)))}
        header.findViewById<TextView>(R.id.tvemail).text = sharedinstance.keyEmail

        floatingActionButton = findViewById(R.id.query)
    }
     private fun openMyaccount(){
         Toast.makeText(this@MainActivity, "point openMyaccount", Toast.LENGTH_SHORT).show()

         NavigationView.setCheckedItem(R.id.my_account)
        supportFragmentManager.beginTransaction().replace(R.id.Frame, MyAccountFragment()).commit()
        supportActionBar?.title="My Account"

    }

//    private fun viewUserDetails(view: View){
//        supportActionBar?.title="User Details"
//        setContentView(R.layout.user_data_screen)
//    }
}