package edu.cs4730.menudemo;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.app.NavUtils;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * This example shows how to use a viewpager2 with button menu items to change the pager.
 */

public class ViewPagerButtonMenuActivity extends AppCompatActivity {
    PageFragment one = null, two = null, three = null, four = null, five = null;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    myFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpagerbuttonmenu);

        //turn on up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            one = (PageFragment) fragmentManager.getFragment(savedInstanceState, "ONE");
            two = (PageFragment) fragmentManager.getFragment(savedInstanceState, "TWO");
            three = (PageFragment) fragmentManager.getFragment(savedInstanceState, "THREE");
            four = (PageFragment) fragmentManager.getFragment(savedInstanceState, "FOUR");
            five = (PageFragment) fragmentManager.getFragment(savedInstanceState, "FIVE");
            //since survived, need to clean up or I can't add them to the pagers adapter again.
            FragmentTransaction remove = fragmentManager.beginTransaction();
            remove.remove(one);
            remove.remove(two);
            remove.remove(three);
            remove.remove(four);
            remove.remove(five);
            if (!remove.isEmpty()) {
                remove.commit();
                fragmentManager.executePendingTransactions();
            }
        } else {
            one = PageFragment.create(1);
            two = PageFragment.create(2);
            three = PageFragment.create(3);
            four = PageFragment.create(4);
            five = PageFragment.create(5);
        }

        viewPager = findViewById(R.id.pager);
        mPagerAdapter = new myFragmentPagerAdapter(this);
        viewPager.setAdapter(mPagerAdapter);
        //viewPager.setCurrentItem(2);
        //we need to know when a page has changed, so we can change/fix the next/previous/finish buttons
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                supportInvalidateOptionsMenu();
            }
        });
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);  //dont show all the tabs, which is MODE_FIXED.
        new TabLayoutMediator(tabLayout,
            viewPager,
            new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.setText("This Page is " + (position + 1));
                }
            }
        ).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(viewPager.getCurrentItem() > 0);
        //help since this attribute is ignored in the xml by api <11.  ie 2.3.3
        //but the change is should xml, use app: instead of android is the ActionBarActivity can read it.
        //see xml
        //MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_previous),MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
            (viewPager.getCurrentItem() == mPagerAdapter.getItemCount() - 1)
                ? R.string.action_finish
                : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate "up" the demo structure to the launchpad activity.
            // See http://developer.android.com/design/patterns/navigation.html for more.
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;

        } else if (item.getItemId() == R.id.action_previous) {
            // Go to the previous step in the wizard. If there is no previous step,
            // setCurrentItem will do nothing.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            return true;
        } else if (item.getItemId() == R.id.action_next) {
            // Advance to the next step in the wizard. If there is no next step, setCurrentItem
            // will do nothing.
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class myFragmentPagerAdapter extends FragmentStateAdapter {
        int PAGE_COUNT = 5;

        public myFragmentPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return one;
                case 1:
                    return two;
                case 2:
                    return three;
                case 3:
                    return four;
                case 4:
                    return five;
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {

            return PAGE_COUNT;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.putFragment(outState, "ONE", one);
        fragmentManager.putFragment(outState, "TWO", two);
        fragmentManager.putFragment(outState, "THREE", three);
        fragmentManager.putFragment(outState, "FOUR", four);
        fragmentManager.putFragment(outState, "FIVE", five);
    }


    public static class PageFragment extends Fragment {
        public static final String ARG_PAGE = "ARG_PAGE";

        private int mPage;

        public static PageFragment create(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            PageFragment fragment = new PageFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = requireArguments().getInt(ARG_PAGE);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_page, container, false);
            TextView textView = (TextView) view;
            textView.setText("Fragment #" + mPage);
            return view;
        }
    }
}
