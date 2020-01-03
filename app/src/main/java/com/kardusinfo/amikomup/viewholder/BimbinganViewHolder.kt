package com.kardusinfo.amikomup.viewholder

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.model.Bimbingan
import com.kardusinfo.amikomup.view.bimbingan.DetailBahasanActivity
import kotlinx.android.synthetic.main.item_bimbingan.view.*

class BimbinganViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var topikBimbingan: TextView = itemView.findViewById(R.id.bimbinganTopikText)
    private var dosenBimbingan: TextView = itemView.findViewById(R.id.bimbinganDosenText)
    private var waktuBimbingan: TextView = itemView.findViewById(R.id.bimbinganWaktuText)
    private var tanggalBimbingan: TextView = itemView.findViewById(R.id.bimbinganTanggalText)

    fun bind(bimbingan: Bimbingan) {
        with(itemView) {
            bimbinganDosenText.text = "sadfas"
            itemView.setOnClickListener {
                val intent = Intent(context, DetailBahasanActivity::class.java)
                context.startActivity(intent)
            }
        }
        topikBimbingan.text = bimbingan.topik
        dosenBimbingan.text = bimbingan.dosen
        waktuBimbingan.text = bimbingan.waktu
        tanggalBimbingan.text = bimbingan.tanggal

    }
}