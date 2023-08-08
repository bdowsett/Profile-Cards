package com.example.profilecardlayout

data class UserProfile(
        val name: String,
        val status: Boolean,
        val drawableId: Int
        )


val userProfiles = listOf(
        UserProfile(name = "Rodrigo Riddle", status = false, drawableId = R.drawable.profile_picture),
      UserProfile(name = "Kenny Morgan", status = true, drawableId = R.drawable.profile_picture),
        UserProfile(name = "Rodrigo Riddle", status = false, drawableId = R.drawable.profile_picture),
     UserProfile(name = "Jenny Taylor", status = true, drawableId = R.drawable.profile_picture)


)