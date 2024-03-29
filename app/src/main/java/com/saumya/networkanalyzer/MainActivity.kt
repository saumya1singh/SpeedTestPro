package com.saumya.networkanalyzer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.net.TrafficStats
import java.net.URL
import java.sql.Time
import java.util.*
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.DialogInterface
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {


    private val buttonClick = AlphaAnimation(1f, 0.2f)
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_graph -> {

                startActivity(Intent(baseContext, GraphActivity::class.java ))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_speed -> {
                startActivity(Intent(Intent(baseContext,SpeedActivity::class.java)))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        getSubtype()
        getOperator()
        getDownloadSpeed()
        getSpeedandName()
        mainback.setOnClickListener {
            it.startAnimation(buttonClick)
            AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                        onBackPressed()
                    }
                })
                .create()
                .show()
        }
    }

    private fun getSubtype(): Boolean {
        //-----------------Get the Type of Network-------------------
        val mConnectivityManager: ConnectivityManager? =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mTelephony: TelephonyManager? = null
        val info: NetworkInfo = mConnectivityManager!!.getActiveNetworkInfo()
        if (info == null || !mConnectivityManager?.backgroundDataSetting!!) {
            return false
        }
        val netType = info.type
        val netSubtype = info.subtype
        if (netType == ConnectivityManager.TYPE_WIFI) {
               wifi.isFocusable=true
            wifi.isFocusableInTouchMode=true
            wifi.setColorFilter(Color.WHITE)

            mobiledata.visibility = View.INVISIBLE
            tvdata.visibility=View.INVISIBLE

        } else if (netType == ConnectivityManager.TYPE_MOBILE
            && netSubtype == TelephonyManager.NETWORK_TYPE_UMTS
            && !mTelephony!!.isNetworkRoaming()
        ) {
            mobiledata.isFocusable=true
            mobiledata.isFocusableInTouchMode=true
            wificontainer.visibility=View.GONE
            wifi.visibility=View.GONE
            return info.isConnected()

        } else {
            return false
        }
        return true
    }

   private fun getOperator(){
       //------------Get the Operator Name--------------------
//        val manager = baseContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        val carrierName = manager.networkOperatorName
       val wifiManager: WifiManager= baseContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
       val wifiInfo:WifiInfo = wifiManager.connectionInfo
       val name=wifiInfo.ssid
         tvoperator.text=name
         tvoperator.isFocusable=true
    }

    private fun getDownloadSpeed(){
        //-------------Get the Network Download Speed----------------------------
        val startTime: Long
        val startBytes : Long
        val endTime:Long
        val endBytes:Long
        val totalBytes:Long
        val downTime:Long
        try {
            val `is` = URL("http://www.domain.com/ubuntu-linux.iso").openStream()
            val buf = ByteArray(1024)
            var n = 0
            startBytes = TrafficStats.getTotalRxBytes() /*gets total bytes received so far*/
            startTime = System.nanoTime()
            while (n < 200) {
                `is`.read(buf)
                n++
            }
            endTime = System.nanoTime()
            endBytes = TrafficStats.getTotalRxBytes() /*gets total bytes received so far*/
            downTime= endTime - startTime
            totalBytes = endBytes - startBytes

            downSpeed.text=totalBytes.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getSpeedandName(){
        //----------------This method gives the Normal Network Speed-----------------
        val wifiManager: WifiManager= baseContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo:WifiInfo = wifiManager.connectionInfo
        val speedMbps = wifiInfo.linkSpeed
        tvSpeed.text=speedMbps.toString()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }


}
