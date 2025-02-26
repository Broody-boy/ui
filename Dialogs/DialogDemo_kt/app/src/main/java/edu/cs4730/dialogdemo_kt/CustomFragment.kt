package edu.cs4730.dialogdemo_kt

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper

/**
 * This shows how to use the dialog that have been extended.  Note the listeners are implemented
 * in the mainActivity and call the displaylog method at the bottom of this fragment.
 * This may not be the way you want to implements your listeners.  It was easier for communication of
 * the fragments.  Sometimes you want to the listeners om the dialog itself, if you have access to the
 * necessary functions and data.
 */
class CustomFragment : Fragment() {
    var TAG = "CustomFragment"
    var logger: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_custom, container, false)
        logger = myView.findViewById(R.id.logger_custom)
        myView.findViewById<View>(R.id.btn_alert1).setOnClickListener {
            val newDialog: myDialogFragment =
                myDialogFragment.newInstance(myDialogFragment.DIALOG_TYPE_ID)
            newDialog.show(requireActivity().supportFragmentManager, "myDialog")
        }
        myView.findViewById<View>(R.id.btn_alert2).setOnClickListener {
            val newDialog: myDialogFragment =
                myDialogFragment.newInstance(myDialogFragment.DIALOG_GAMEOVER_ID)
            newDialog.show(requireActivity().supportFragmentManager, "myDialog")
        }
        myView.findViewById<View>(R.id.btn_alert3).setOnClickListener {
            val newDialog: myAlertDialogFragment =
                myAlertDialogFragment.newInstance(R.string.alert_dialog_two_buttons_title)
            newDialog.show(requireActivity().supportFragmentManager, "myAlertDialog")
        }
        myView.findViewById<View>(R.id.btn_edit).setOnClickListener {
            val newDialog = myEditNameDialogFrag()
            newDialog.show(requireActivity().supportFragmentManager, "myEditDialog")
        }
        myView.findViewById<View>(R.id.inline_button).setOnClickListener {
            showInputDialog(
                "Inline Fragment"
            )
        }
        myView.findViewById<View>(R.id.mutli_input_btn)
            .setOnClickListener { //MultiInputDialogFragment newDialog = new MultiInputDialogFragment();
                val newDialog: MultiInputDialogFragment =
                    MultiInputDialogFragment.newInstance("Jim", null)
                newDialog.show(requireActivity().supportFragmentManager, "myMultiInputDialog")
            }
        return myView
    }

    /*
     * simple method to display data to the logger textview.
     */
    fun displaylog(item: String) {
        Log.v(TAG, item)
        logger!!.append(item + "\n")
    }

    /*
   We are going to add data
   * setup a dialog fragment to ask the user for the new item data or category.
   */
    fun showInputDialog(title: String?) {
        val inflater = LayoutInflater.from(activity)
        val textenter: View = inflater.inflate(R.layout.layout_custom_dialog, null)
        val userinput = textenter.findViewById<View>(R.id.item_added) as EditText
        val builder = AlertDialog.Builder(
            ContextThemeWrapper(
                activity, R.style.AppTheme_Dialog
            )
        )
        builder.setView(textenter).setTitle(title)
        builder.setPositiveButton(
            "Add"
        ) { dialog, id ->
            displaylog("data is " + userinput.text.toString())
            //Toast.makeText(getBaseContext(), userinput.getText().toString(), Toast.LENGTH_LONG).show();
        }
            .setNegativeButton(
                "Cancel"
            ) { dialog, id ->
                displaylog("dialog canceled")
                dialog.cancel()
            }
        //you can create the dialog or just use the now method in the builder.
       // val dialog = builder.create()
       // dialog.show()
        builder.show()
    }
}
