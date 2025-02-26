package edu.cs4730.lotsoflists_kt

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * complex example of a navigation drawer with recyclerview.  Plus you can add more items
 * to the list and add more categories (ie new lists) as well.
 *
 *
 * The list data is all handled in the ViewModel, ListsViewModel.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var mDrawerlayout: DrawerLayout
    private lateinit var mDrawerList: ListView
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: myAdapter
    lateinit var fab: FloatingActionButton
    lateinit var addBut: FloatingActionButton
    var IsCatInput = false
    lateinit var mViewModel: ListsViewModel
    lateinit var catAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //use the androidx toolbar instead of the default one.
        toolbar = findViewById<View>(R.id.app_bar) as Toolbar
        setSupportActionBar(toolbar)
        mViewModel = ViewModelProvider(this)[ListsViewModel::class.java]

        //setup the RecyclerView
        mRecyclerView = findViewById<View>(R.id.list) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        //setup the adapter, which is myAdapter, see the code.
        mAdapter = myAdapter(null, R.layout.my_row, this) //observer will fix the null
        //add the adapter to the recyclerview
        mRecyclerView.adapter = mAdapter
        mViewModel.listLD.observe(
            this
        ) { myList -> mAdapter.newData(myList) }


        //swipe listener for the recycler view
        //ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT |ItemTouchHelper.LEFT) {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    //likely allows to for animations?  or moving items in the view I think.
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    //called when it has been animated off the screen.  So item is no longer showing.
                    //use ItemtouchHelper.X to find the correct one.
                    if (direction == ItemTouchHelper.RIGHT) {
                        //Toast.makeText(getBaseContext(),"Right?", Toast.LENGTH_SHORT).show();
                        val item =
                            viewHolder.absoluteAdapterPosition //think this is where in the array it is.
                        //((myAdapter)viewHolder).myName.getText();
                        mViewModel.removeItem(item)
                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
        fab = findViewById<View>(R.id.fab) as FloatingActionButton
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //yes this code is kind of a mess.   And better I got some of from here:
            //https://github.com/Suleiman19/Android-Material-Design-for-pre-Lollipop/tree/master/MaterialSample/app/src/main/java/com/suleiman/material/activities
            //in the fabhideactivity.java
            var scrollDist = 0
            private var isVisible = true
            private val HIDE_THRESHOLD = 100f
            private val SHOW_THRESHOLD = 50f
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //but I want the fab back after the scrolling is done.  Not scroll down a little... that is just stupid.
                    if (!isVisible) {
                        fab.animate().translationY(0f).setInterpolator(DecelerateInterpolator(2F))
                            .start()
                        //scrollDist = 0;
                        isVisible = true
                        Log.v("c", "state changed, show be showing....")
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //so animate to slide down and then and set invisible variables or something.
                //  Check scrolled distance against the minimum
                if (isVisible && scrollDist > HIDE_THRESHOLD) {
                    //  Hide fab & reset scrollDist
                    fab.animate()
                        .translationY((fab.height + resources.getDimensionPixelSize(R.dimen.fab_margin)).toFloat())
                        .setInterpolator(AccelerateInterpolator(2F)).start()
                    scrollDist = 0
                    isVisible = false
                    Log.v("onScrolled", "maded fab invisible")
                } else if (!isVisible && scrollDist < -SHOW_THRESHOLD) {
                    //  Show fab & reset scrollDist
                    fab.animate().translationY(0f).setInterpolator(DecelerateInterpolator(2F))
                        .start()
                    scrollDist = 0
                    isVisible = true
                    Log.v("onScrolled", "maded fab visible")
                }

                //  Whether we scroll up or down, calculate scroll distance
                if (isVisible && dy > 0 || !isVisible && dy < 0) {
                    scrollDist += dy
                }
            }
        })
        fab.setOnClickListener {
            IsCatInput = false
            showInputDialog("Input New Data")
        }

        // enable ActionBar app icon to behave as action to toggle nav drawer
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        mDrawerlayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        mDrawerToggle = object : ActionBarDrawerToggle(
            this,  // host activity
            mDrawerlayout,  //drawerlayout object
            toolbar,  //toolbar
            R.string.drawer_open,  //open drawer description  required!
            R.string.drawer_close
        ) {
            //closed drawer description
            //called once the drawer has closed.
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                supportActionBar!!.title = "Categories"
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }

            //called when the drawer is now open.
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                supportActionBar!!.setTitle(R.string.app_name)
                invalidateOptionsMenu() // creates call to onPrepareOptionsMenu()
            }
        }
        //To disable the icon for the drawer, change this to false
        //mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerlayout.addDrawerListener(mDrawerToggle)
        //setup the addcat "button"
        addBut = findViewById<View>(R.id.addCat) as FloatingActionButton
        addBut.setOnClickListener {
            IsCatInput = true
            showInputDialog("Add New Category")
        }
        //lastly setup the listview with some simple categories via an array.
        catAdapter = ArrayAdapter<String>(
            this,
            R.layout.drawer_list_item,
            ArrayList<String>()
        )
        //mViewModel.getAllCat() );
        mViewModel.catLD.observe(
            this
        ) { strings ->
            catAdapter.clear()
            catAdapter.addAll(strings)
        }
        mDrawerList = findViewById<View>(R.id.left_drawer) as ListView
        mDrawerList.adapter = catAdapter
        mDrawerList.setItemChecked(mViewModel.getlist(), true) //set the highlight correctly.
        mDrawerList.onItemClickListener =
            OnItemClickListener { arg0, view, position, index -> //yes, this should do something for more interesting.  but this is an example.
                val item = mDrawerList.adapter.getItem(position).toString()

                //change the list view to correct one.
                mViewModel.setlist(position)

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(position, true)
                //now close the drawer!
                mDrawerlayout.closeDrawers()
            }
    }

    /**
     * We are going to add data
     * setup a dialog fragment to ask the user for the new item data or category.
     */
    fun showInputDialog(title: String?) {
        val inflater = LayoutInflater.from(this)
        val textenter = inflater.inflate(R.layout.layout_dialog, null)
        val userinput = textenter.findViewById<View>(R.id.item_added) as EditText
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AppTheme_Dialog))
        builder.setView(textenter).setTitle(title)
        builder.setPositiveButton(
            "Add"
        ) { dialog, id ->
            //add the list and allow the observer to fixes the lists.
            if (IsCatInput) { //add new category
                mViewModel.addCat(userinput.text.toString())
            } else { //add new data item to list.
                mViewModel.addItem(userinput.text.toString())
            }
            //Toast.makeText(getBaseContext(), userinput.getText().toString(), Toast.LENGTH_LONG).show();
        }
            .setNegativeButton(
                "Cancel"
            ) { dialog, id -> dialog.cancel() }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
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
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig)
    }
}
