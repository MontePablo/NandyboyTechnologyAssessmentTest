package com.bsr.bsrcoin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bsr.bsrcoin.MysqlConst.Constants
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import org.json.JSONArray
import org.json.JSONException
import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun DP(a: Int, b: Int): DataPoint {
    return DataPoint(a.toDouble(), b.toDouble())
}
class CoinGraph : AppCompatActivity() {
    var COIN = "coinhistory"
    var coinnum:Int=1
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    lateinit var graphView:GraphView
    var minx:Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_graph)
        Log.d("coin", "graph1" )

        val shrd_pref_variable= getSharedPreferences(COIN,MODE_PRIVATE);
        coinnum = shrd_pref_variable.getInt("coinselected",1);
        graphView= findViewById<GraphView>(R.id.graph)


        var dateString1 = "2014-8-13"
        var date1: Date? = null
        try { date1 = sdf.parse(dateString1)
        } catch (e: ParseException) { e.printStackTrace() }
        minx = date1!!.time


        graphView.title = "Coin history"
        graphView.titleColor = R.color.purple_200
        graphView.titleTextSize = 18f
        graphView.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                if (isValueX) {
                    val formatter: Format = SimpleDateFormat("dd|MM|yy")
                    return formatter.format(value)
                }
                return super.formatLabel(value, isValueX)
            }
        }

        Log.d("coin", "graph3" )

        graphView.viewport.scrollToEnd()
        graphView.viewport.isXAxisBoundsManual = true //giving X axis bound
        graphView.viewport.isScalable = true // activate horizontal zooming and scrolling
        graphView.viewport.isScrollable = true // activate horizontal scrolling
        graphView.viewport.setScalableY(true) // activate horizontal and vertical zooming and scrolling
        graphView.viewport.setScrollableY(true)
        Log.d("coin", "graph4" )

        Log.d("coin", "graph5" )
        insertHistoryInGraph()

    }


    fun insertHistoryInGraph() {
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            Constants.url_coinhistory,
            Response.Listener { response ->
                Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                try {
                    val jsonarray = JSONArray(response)
                    var dp = arrayOfNulls<DataPoint>(jsonarray.length())
                    var maxx:Long=0
                    for (i in 0 until jsonarray.length()) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val value = jsonobject.getInt("value")
                        val dateString = jsonobject.getString("changed_date")

                        var date:Date?=null
                        try { date = sdf.parse(dateString)
                        } catch (e: ParseException) { e.printStackTrace() }
                        val d: Long = date!!.time
                        maxx=d

                        dp[i]=DataPoint(d.toDouble(),value.toDouble())
                    }
                    var mini:Double = maxx.toDouble()-26890000000.0
                    val series = LineGraphSeries(dp)
                    graphView.viewport.setMinX(mini)
                    graphView.viewport.setMaxX(maxx.toDouble())
                    graphView.addSeries(series)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> //                        progressDialog.dismiss();
                val e = error.toString()
                Toast.makeText(this, "unsuccessful", Toast.LENGTH_LONG).show()
                Toast.makeText(this, e, Toast.LENGTH_LONG).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["cid"] = Integer.toString(coinnum)
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