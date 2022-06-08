package com.extremex.kotex_libs

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.extremex.attendable.R

class EventListAdapter (var rootContext: Context, var resources: Int, var items: List<EventViewHandler>): ArrayAdapter<EventViewHandler>(rootContext, resources, items) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val  layoutInflater : LayoutInflater = LayoutInflater.from(rootContext)
        val view : View = layoutInflater.inflate(resources,null)

        val nameText : TextView = view.findViewById(R.id.EventName)
        val descriptiontext : TextView = view.findViewById(R.id.EventDescription)
        val dueDatetext: TextView = view.findViewById(R.id.EventDue)
        val remove: ImageButton = view.findViewById(R.id.DeleteEvent)
        val pref = context.getSharedPreferences("EVENTS", AppCompatActivity.MODE_PRIVATE)
        val prefEdit = pref.edit()

        val listItems :EventViewHandler = items[position]
        nameText.text = listItems._eventName_
        descriptiontext.text = listItems._eventDescription_
        dueDatetext.text = listItems._eventDueDate_


        remove.setOnClickListener {
            val progress: ProgressDialog = ProgressDialog(context)
            progress.setTitle("Please Wait")
            progress.setMessage("Applying Changes")
            progress.setCancelable(false)
            progress.show()
            prefEdit.remove(listItems._eventName_)
            prefEdit.remove(listItems._eventDescription_)
            prefEdit.remove(listItems._eventDueDate_)
            prefEdit.apply()
            progress.dismiss()

        }

        return view
    }
}