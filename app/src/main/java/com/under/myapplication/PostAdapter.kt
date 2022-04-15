package com.under.myapplication

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.under.myapplication.model.DataBase
import java.io.File

class PostAdapter: RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        //Inflate: XML->View
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post_row,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postN = DataBase.getPost()[position]
        holder.date.text = postN.date
        holder.location.text = postN.location
        holder.profileName.text = postN.getProfileName()
        if(postN.getProfilePic() == ""){holder.profilePic.setImageResource(R.drawable.default_profile_pic)}
        else{holder.profilePic.setImageURI(Uri.fromFile(File(postN.getProfilePic())))}
        holder.updateText.text = postN.updateText
        if(postN.haveImage()){
            Log.e(">>>",postN.updateImagePath!!)
            holder.updateImage.visibility = View.VISIBLE
            holder.updateImage.setImageURI(Uri.fromFile(File(postN.updateImagePath)))
        }else{
            holder.updateImage.visibility = View.GONE
        }
        if(position == itemCount - 1){
            holder.separator.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return DataBase.getPost().size
    }
}