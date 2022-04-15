package com.under.myapplication

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    var date:TextView = itemView.findViewById(R.id.postRowDate)
    var location:TextView = itemView.findViewById(R.id.postRowLocation)
    var profileName: TextView = itemView.findViewById(R.id.postRowProfileName)
    var updateText: TextView = itemView.findViewById(R.id.postTextView)
    var profilePic: de.hdodenhof.circleimageview.CircleImageView = itemView.findViewById(R.id.postRowProfilePic)
    var updateImage: ImageView = itemView.findViewById(R.id.postRowImage)
    var separator: View  = itemView.findViewById(R.id.separator)

    init {

    }
}