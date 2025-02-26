package edu.cs4730.bottomnavigationviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * and example use the BottomNavigationView and Navigation.
 * https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView
 * https://material.io/develop/android/components/bottom-navigation-view/
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        /* if not using arch navigation, then you need to implement this.
        navView.setOnItemSelectedListener(
            new BottomNavigationView.OnItemSelectedListener() {

                public boolean onNavigationItemSelected(MenuItem item) {
                    //setup the fragments here.
                    int id = item.getItemId();
                    if (id == R.id.action_first) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new OneFragment()).commit();
                        item.setChecked(true);
                        return true;
                    } else if (id == R.id.action_second) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new TwoFragment()).commit();
                        item.setChecked(true);
                    } else if (id == R.id.action_third) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new threeFragment()).commit();
                        item.setChecked(true);
                    }

                    return false;
                }
            }

        );*/


        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        //Note for this to work with arch Navigation, these id must be the same id in menu.xml and the nav_graph.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.action_first, R.id.action_second, R.id.action_third)
            .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavHostFragment navHostFragment =
            (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        //In order to have badges, you need to use the Theme.MaterialComponents.DayNight  (doesn't have to be daynight, but MaterialComponents).
        BadgeDrawable badge = navView.getOrCreateBadge(R.id.action_second);
        badge.setNumber(12);  //should show a 12 in the "badge" for the second one.
        badge.setVisible(true);


    }
}
