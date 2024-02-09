package edu.uw.ischool.scottng.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.uw.ischool.scottng.quizdroid.fragments.TopicListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TopicListFragment())
                .commit()
        }
    }
}