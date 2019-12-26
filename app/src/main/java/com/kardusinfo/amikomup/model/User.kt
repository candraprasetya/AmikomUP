package com.kardusinfo.amikomup.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var myUid: String? = "",
    var username: String? = "",
    var nim: String? = "",
    var email: String? = "",
    var password: String? = "",
    var profileImage: String? = "",
    var role: String? = ""
)