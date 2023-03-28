package com.android.cloud

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.cloud.player.Fragment1
import com.android.cloud.player.Fragment2


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var fm: FragmentManager? = supportFragmentManager
    private var ft: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        tv1 = findViewById<View>(R.id.music_menu) as TextView
        tv2 = findViewById<View>(R.id.menu_album) as TextView
        tv1!!.setOnClickListener(this)
        tv2!!.setOnClickListener(this)

        fm = supportFragmentManager
        ft = fm!!.beginTransaction()
        ft!!.replace(R.id.content, Fragment1())
        ft!!.commit()
    }

    override fun onClick(v: View) {
        ft = fm!!.beginTransaction()
        when (v.id) {
            R.id.music_menu -> ft!!.replace(R.id.content, Fragment1())

            R.id.menu_album -> ft!!.replace(R.id.content, Fragment2())

            else -> {}
        }
        ft!!.commit()
    }
}