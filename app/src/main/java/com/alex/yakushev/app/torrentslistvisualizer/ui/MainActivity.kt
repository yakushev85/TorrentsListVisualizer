package com.alex.yakushev.app.torrentslistvisualizer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alex.yakushev.app.torrentslistvisualizer.R
import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo
import com.alex.yakushev.app.torrentslistvisualizer.ui.detailed.DetailedActivity
import com.alex.yakushev.app.torrentslistvisualizer.ui.list.ListFragment
import com.alex.yakushev.app.torrentslistvisualizer.ui.list.ListFragment.OnFragmentInteractionListener

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager

        if (fm.findFragmentById(R.id.main_fragment_container) == null) {
            fm.beginTransaction()
                    .add(R.id.main_fragment_container, ListFragment())
                    .commit()
        }
    }

    override fun onFragmentInteraction(movieInfo: MovieInfo) {
        val intent = Intent(this, DetailedActivity::class.java)
        intent.putExtra(MovieInfo::class.java.name, movieInfo)
        startActivity(intent)
    }
}