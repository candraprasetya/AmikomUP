package com.kardusinfo.amikomup.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Schedule(
    var myUid: String? = "",
    var class_name: String? = "",
    var lecturer: String? = "",
    var day: String? = "",
    var room: String? = "",
    var time: String? = ""
)