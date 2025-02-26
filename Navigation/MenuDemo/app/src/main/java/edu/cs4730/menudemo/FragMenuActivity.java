package edu.cs4730.menudemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;

/**
 * So this activity shows hwo the menu can change based on the fragments
 * which is FragMenu1 and FragMenu2 will show extra menu items.
 */

public class FragMenuActivity extends AppCompatActivity {
    FragMenu1 one;
    FragMenu2 two;
    boolean isfrag1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmenu);

        //turn on up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup the two fragments, and then add one to screen.
        one = new FragMenu1();
        two = new FragMenu2();
        getSupportFragmentManager().beginTransaction()
            .add(R.id.text_container, one).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_fragmenu, menu);

        if (isfrag1) {
            menu.findItem(R.id.frag1).setEnabled(false);
            menu.findItem(R.id.frag2).setEnabled(true);
        } else {
            menu.findItem(R.id.frag1).setEnabled(true);
            menu.findItem(R.id.frag2).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent upIntent = new Intent(this, MainActivity.class);
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, upIntent);
            return true;
        } else if (id == R.id.frag1) {
            if (!isfrag1) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.text_container, one).commit();
                isfrag1 = true;
                supportInvalidateOptionsMenu();
            }
            return true;
        } else if (id == R.id.frag2) {
            if (isfrag1) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.text_container, two).commit();
                isfrag1 = false;
                supportInvalidateOptionsMenu();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
