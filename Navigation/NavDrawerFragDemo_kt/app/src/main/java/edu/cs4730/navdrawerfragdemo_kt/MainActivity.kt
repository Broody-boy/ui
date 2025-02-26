package edu.cs4730.navdrawerfragdemo_kt

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout


class MainActivity : AppCompatActivity(), titlefrag.OnFragmentInteractionListener {
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    lateinit var myTextFrag: textFrag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        // enable ActionBar app icon to behave as action to toggle nav drawer
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = object : ActionBarDrawerToggle(
            this,  /* host Activity */
            mDrawerLayout,  /* DrawerLayout object */
            R.string.drawer_open,  /* "open drawer" description for accessibility */
            R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            override fun onDrawerClosed(view: View) {
                supportActionBar!!.setTitle(R.string.app_name)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                supportActionBar!!.title = "Categories"
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle)

        //get the textFrag from the support manager;
        myTextFrag = supportFragmentManager.findFragmentById(R.id.frag_text) as textFrag
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        return if (mDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(id: Int) {
        //use the setter in textFrag to change the text.
        myTextFrag.setText(id)
        mDrawerLayout.closeDrawers()
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig)
    }
}
