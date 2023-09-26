package com.bsr.bsrcoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.cardview.widget.CardView

class CoinData : AppCompatActivity() {
    var COIN:String = "coinhistory"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Log.d("coin", "listenedxx" )
        //--------Declare a shared preference
        val shrd_pref_variable= getSharedPreferences(COIN,MODE_PRIVATE);
        //--------Declare a editor
        var editor= shrd_pref_variable.edit();
        //--------Put value in editor
        var coin1: CardView = findViewById<CardView>(R.id.coin1)
        var coin2:CardView = findViewById<CardView>(R.id.coin2)
        var coin3:CardView = findViewById<CardView>(R.id.coin3)
        var coin4:CardView = findViewById<CardView>(R.id.coin4)
        var coin5:CardView = findViewById<CardView>(R.id.coin5)

        coin1.setOnClickListener {
            editor.putInt("coinselected",1);  //putInt(),putBoolean()
            editor.apply();
            callGraphActivity();
        }
        coin2.setOnClickListener {
            editor.putInt("coinselected",2);  //putInt(),putBoolean()
            editor.apply();
            callGraphActivity();
        }
        coin3.setOnClickListener {
            editor.putInt("coinselected",3);  //putInt(),putBoolean()
            editor.apply();
            callGraphActivity();
        }
        coin4.setOnClickListener {
            editor.putInt("coinselected",4);  //putInt(),putBoolean()
            editor.apply();
            callGraphActivity();
        }
        coin5.setOnClickListener {
            editor.putInt("coinselected",5);  //putInt(),putBoolean()
            editor.apply();
            callGraphActivity();
        }

    }
    fun callGraphActivity()
    {
        var intent = Intent(this@CoinData,CoinGraph::class.java)
        startActivity(intent)
    }
}