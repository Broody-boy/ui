package edu.cs4730.supportdesigndemo;

import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/*
 * This example is to show many of the features available in the support design library.
 * http://android-developers.blogspot.com/2015/05/android-design-support-library.html
 *
 * Many of them are in the fragments.
 *
 * But one feature is the show here (and xml) is the NavigationView in the Navigation Draw.
 * It allows the user to create a layout similar to google's play nav drawer.  With a layout
 * at the top and a set of items via a menu xml file.
 *
 */


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerlayout;
    private NavigationView mNavigationView;
    FragmentManager fragmentManager;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //use the v7.toolbar instead of the default one.
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //standard navigation drawer setup.
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this,  // host activity
            mDrawerlayout,  //drawerlayout object
            toolbar,  //toolbar
            R.string.drawer_open,  //open drawer description  required!
            R.string.drawer_close) {  //closed drawer description

            //called once the drawer has closed.
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Categories");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            //called when the drawer is now open.
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //To disable the icon for the drawer, change this to false
        //mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerlayout.addDrawerListener(mDrawerToggle);

        //this ia the support Navigation view.
        mNavigationView = findViewById(R.id.navview);
        //setup a listener, which acts very similar to how menus are handled.
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //we could just as easily call onOptionsItemSelected(menuItem) and how it deal with it.
                int id = menuItem.getItemId();
                if (id == R.id.navigation_item_1) {
                    //load fragment
                    if (!menuItem.isChecked()) {  //only need to do this if fragment is already loaded.
                        menuItem.setChecked(true);  //make sure to check/highlight the item.
                        fragmentManager.beginTransaction().replace(R.id.container, new SnackBarFragment()).commit();
                    }
                    mDrawerlayout.closeDrawers();  //close the drawer, since the user has selected it.
                    return true;
                } else if (id == R.id.navigation_item_2) {
                    //load fragment
                    if (!menuItem.isChecked()) {  //only need to do this if fragment is already loaded.
                        menuItem.setChecked(true); //make sure the item is checked/highlighted
                        Log.v(TAG, "fab fragment?");
                        fragmentManager.beginTransaction().replace(R.id.container, new FABFragment()).commit();
                    }
                    //now close the nav drawer.
                    mDrawerlayout.closeDrawers();
                    return true;
                } else if (id == R.id.navigation_item_3) {
                    //load fragment
                    if (!menuItem.isChecked()) {  //only need to do this if fragment is already loaded.
                        menuItem.setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.container, new SB_FABFragment()).commit();
                    }
                    mDrawerlayout.closeDrawers();
                    return true;
                } else if (id == R.id.navigation_item_4) {
                    //load fragment
                    if (!menuItem.isChecked()) {  //only need to do this if fragment is already loaded.
                        menuItem.setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.container, new TextInputLayoutFragment()).commit();
                    }
                    mDrawerlayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });

        //finally deal with the fragments.
        fragmentManager = getSupportFragmentManager();
        //first instance, so the default is zero.
        fragmentManager.beginTransaction().replace(R.id.container, new SnackBarFragment()).commit();
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
