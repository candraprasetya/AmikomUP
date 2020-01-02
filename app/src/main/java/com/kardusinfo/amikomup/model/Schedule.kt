package com.kardusinfo.amikomup.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Schedule(
    var uid: String? = "",
    var class_name: String? = "",
    var lecturer_name: String? = "",
    var day: String? = "",
    var class_room: String? = "",
    var time: String? = ""
)