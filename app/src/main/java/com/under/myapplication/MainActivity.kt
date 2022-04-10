package com.under.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import com.google.gson.Gson
import com.under.myapplication.databinding.ActivityMainBinding
import com.under.myapplication.model.DataBase
import com.under.myapplication.model.Session
import com.under.myapplication.model.State

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadData()
        if(DataBase.isOnSession()){
            startActivity(Intent(this, UserActivity::class.java))
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun loadData() {
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference",Context.MODE_PRIVATE)
        val json = sharedPref.getString("state","NO_STATE")
        Log.e(">>>","loadDataMainActivity: ${json.toString()}")
        if(json != "NO_STATE"){
            //Deserializacion
            val jsonDes = Gson().fromJson(json, State::class.java)
            DataBase.setPost(jsonDes.posts)
            DataBase.setSession(jsonDes.session)
            DataBase.setUsers(jsonDes.users)
        }
    }
}