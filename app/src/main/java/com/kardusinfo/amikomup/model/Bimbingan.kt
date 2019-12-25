package com.kardusinfo.amikomup.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Bimbingan(
    var myUid: String? = "",
    var dosen: String? = "",
    var topik: String? = "",
    var matkul: String? = "",
    var tanggal: String? = "",
    var waktu: String? = "",
    var status: String? = ""
)