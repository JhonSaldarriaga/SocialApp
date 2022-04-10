package com.under.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.under.myapplication.databinding.ActivityUserBinding
import com.under.myapplication.model.DataBase
import com.under.myapplication.model.State

class UserActivity : AppCompatActivity() {

    private val binding: ActivityUserBinding by lazy {
        ActivityUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference",Context.MODE_PRIVATE)
        val json = sharedPref.getString("state","NO_STATE")
        Log.e(">>>","onResumeMainActivity: ${json.toString()}")
        if(json != "NO_STATE"){
            //Deserializacion
            val jsonDes = Gson().fromJson(json, State::class.java)
            DataBase.setPost(jsonDes.posts)
            DataBase.setSession(jsonDes.session)
            DataBase.setUsers(jsonDes.users)
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        //Serializacion
        val json = Gson().toJson(State(DataBase.getUsers(),DataBase.getPost(),DataBase.getSession()))
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference",Context.MODE_PRIVATE)
        sharedPref.edit().putString("state",json).apply()
    }
}