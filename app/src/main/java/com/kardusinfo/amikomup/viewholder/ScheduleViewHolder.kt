package com.kardusinfo.amikomup.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.model.Bimbingan
import com.kardusinfo.amikomup.model.Schedule
import kotlinx.android.synthetic.main.item_bimbingan.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var class_name: TextView = itemView.findViewById(R.id.tvClassName)
    private var lecturer_name: TextView = itemView.findViewById(R.id.tvTeacher)
    private var day_name: TextView = itemView.findViewById(R.id.tvDay)
    private var room_name: TextView = itemView.findViewById(R.id.tvRoom)
    private var time_class: TextView = itemView.findViewById(R.id.tvTime)

    fun bind(schedule: Schedule) {
        class_name.text = schedule.class_name
        lecturer_name.text = schedule.lecturer_name
        day_name.text = schedule.day
        room_name.text = schedule.class_room
        time_class.text = schedule.time
    }
}