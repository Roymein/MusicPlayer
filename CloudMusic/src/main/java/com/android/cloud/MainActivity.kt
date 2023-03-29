package com.android.cloud

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.cloud.player.AlbumFragment
import com.android.cloud.player.MusicFragment


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mTvMusicMenu: TextView? = null
    private var mTvAlbumMenu: TextView? = null
    private var fragmentManager: FragmentManager? = supportFragmentManager
    private var transaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        mTvMusicMenu = findViewById<View>(R.id.music_menu) as TextView
        mTvAlbumMenu = findViewById<View>(R.id.menu_album) as TextView
        mTvMusicMenu!!.setOnClickListener(this)
        mTvAlbumMenu!!.setOnClickListener(this)

//        fragmentManager = supportFragmentManager
        transaction = fragmentManager!!.beginTransaction()
        transaction!!.replace(R.id.content, MusicFragment())
        transaction!!.commit()
    }

    override fun onClick(v: View) {
        transaction = fragmentManager!!.beginTransaction()
        when (v.id) {
            R.id.music_menu -> transaction!!.replace(
                R.id.content, MusicFragment()
            )

            R.id.menu_album -> transaction!!.replace(
                R.id.content, AlbumFragment()
            )

            else -> {}
        }
        transaction!!.commit()
    }
}