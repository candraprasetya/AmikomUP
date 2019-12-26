package com.kardusinfo.amikomup.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.model.Bimbingan

class BimbinganViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var topikBimbingan: TextView = itemView.findViewById(R.id.bimbinganTopikText)
    private var dosenBimbingan: TextView = itemView.findViewById(R.id.bimbinganDosenText)
    private var waktuBimbingan: TextView = itemView.findViewById(R.id.bimbinganWaktuText)
    private var tanggalBimbingan: TextView = itemView.findViewById(R.id.bimbinganTanggalText)

    fun bind(bimbingan: Bimbingan) {
        topikBimbingan.text = bimbingan.topik
        dosenBimbingan.text = bimbingan.dosen
        waktuBimbingan.text = bimbingan.waktu
        tanggalBimbingan.text = bimbingan.tanggal
    }
}